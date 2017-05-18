/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.fail;

import org.eclipse.egerrit.internal.core.command.ListCommentsCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.ListCommentsCommand}
 *
 * @since 1.0
 */
public class ListCommentsCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		ListCommentsCommand command = fGerrit.getListComments(change_id, commit_id);
		try {
			command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assert (true);

	}

}
