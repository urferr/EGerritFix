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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.internal.core.command.DeleteDraftChangeCommand;
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.DeleteDraftChangeCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class DeleteDraftChangeCommandTest extends CommandTest {

	@Before
	public void createDraftReview() {
		createReviewWithSimpleFile(true);
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.command.DeleteDraftChangeCommand#call()}.
	 */
	@Test
	public void testCall() {

		// Run test
		DeleteDraftChangeCommand command2 = fGerrit.deleteDraftChange(change_id);
		try {
			command2.call();
			QueryChangesCommand comd = fGerrit.queryChanges();
			comd.setMaxNumberOfResults(10);
			comd.addQuery(change_id);
			ChangeInfo[] changes = comd.call();
			assertEquals(0, changes.length);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
