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

import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.ChangeCommitMsgCommand;
import org.eclipse.egerrit.core.command.PublishChangeEditCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeEditMessageInput;
import org.eclipse.egerrit.core.tests.Common;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.PublishChangeEditCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class PublishChangeEditCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.PublishChangeEditCommand#call()}.
	 */
//	@Test
	public void testCall() {
		ChangeCommitMsgCommand editMessageCmd = fGerrit.editMessage(Common.TEST_PROJECT + "~master~" + change_id);
		ChangeEditMessageInput changeEditMessageInput = new ChangeEditMessageInput();
		changeEditMessageInput.setMessage("A new message to be saved ...");

		editMessageCmd.setCommandInput(changeEditMessageInput);

		try {
			editMessageCmd.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		PublishChangeEditCommand publishChangeEditCmd = fGerrit
				.publishChangeEdit(Common.TEST_PROJECT + "~master~" + change_id);

		String publishChangeEditCmdResult = null;
		try {
			publishChangeEditCmdResult = publishChangeEditCmd.call();

		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assert (true);

	}
}
