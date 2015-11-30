/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.PublishDraftChangeCommand;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.PublishDraftChangeCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class PublishDraftChangeCommandTest extends CommandTest {

	@Before
	public void createDraftReview() {
		createReviewWithSimpleFile(true);
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.PublishDraftChangeCommand#call()}.
	 */
	@Test
	public void testCall() {

		// Run test
		PublishDraftChangeCommand publishCommand = fGerrit.publishDraftChange(change_id);
		try {
			publishCommand.call();
			QueryChangesCommand comd = fGerrit.queryChanges();
			comd.setMaxNumberOfResults(10);
			comd.addQuery("is:open");
			ChangeInfo[] changes = comd.call();
			for (ChangeInfo s : changes) {
				if (s.getId().compareTo(change_id) == 0) {
					assertTrue(true);
					return;
				}
			}
			fail("Change not found");
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
