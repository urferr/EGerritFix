/*******************************************************************************
 * Copyright (c) 2015 Ericssona
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

import org.eclipse.egerrit.internal.core.command.ChangeCommitMsgCommand;
import org.eclipse.egerrit.internal.core.command.PublishChangeEditCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.ChangeEditMessageInput;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.ChangeCommitMsgCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ChangeCommitMsgCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.command.ChangeCommitMsgCommand#call()}.
	 */
//	@Test
	public void testCall() {
		ChangeCommitMsgCommand editMessageCmd = fGerrit.editMessage(change_id);
		ChangeEditMessageInput changeEditMessageInput = new ChangeEditMessageInput();
		changeEditMessageInput.setMessage("A new message to be saved ...");

		editMessageCmd.setCommandInput(changeEditMessageInput);
		PublishChangeEditCommand publishChangeEditCmd = fGerrit.publishChangeEdit(change_id);

		try {
			editMessageCmd.call();
			publishChangeEditCmd.call();
			System.out.println(fGerrit.getChange(change_id).call().getSubject());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

	}
}
