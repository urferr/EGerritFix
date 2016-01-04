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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.QueryChangesCommand}
 *
 * @since 1.0
 */
public class GetChangeCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		GetChangeCommand command = fGerrit.getChange(change_id);
		command.addOption(ChangeOption.DETAILED_LABELS);
		ChangeInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		if (result != null) {
			assertEquals(change_id, result.getId());
		}
	}

	@Test
	public void testWithRevisions() {
		GetChangeCommand command = fGerrit.getChange(change_id);
		command.addOption(ChangeOption.DETAILED_LABELS);
		command.addOption(ChangeOption.ALL_REVISIONS);
		ChangeInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		if (result != null) {
			assertEquals(change_id, result.getId());
		}
	}
}
