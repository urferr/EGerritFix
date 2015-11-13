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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.CherryPickRevisionCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.CherryPickInput;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.CherryPickRevisionCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class CherryPickRevisionCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 */
	@Test
	public void testCall() {
		CherryPickRevisionCommand command = fGerrit.cherryPickRevision(change_id, commit_id);
		CherryPickInput cherryPickInput = new CherryPickInput();
		cherryPickInput.setMessage("Implementing Feature X");
		cherryPickInput.setDestination("HEAD");
		command.setCommandInput(cherryPickInput);
		try {
			ChangeInfo result = null;
			result = command.call();
			assertNotEquals(change_id, result.getId());
			assertEquals("Implementing Feature X", result.getSubject());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
