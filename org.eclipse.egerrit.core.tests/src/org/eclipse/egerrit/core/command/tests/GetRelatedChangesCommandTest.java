/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.internal.core.command.GetRelatedChangesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.RelatedChangesInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.QueryChangesCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GetRelatedChangesCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 */
	@Test
	public void testCall() {
		GetRelatedChangesCommand command = fGerrit.getRelatedChanges(change_id, commit_id);
		try {
			RelatedChangesInfo result = command.call();
			assertTrue("[]".equals(result.getChanges().toString()));
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
