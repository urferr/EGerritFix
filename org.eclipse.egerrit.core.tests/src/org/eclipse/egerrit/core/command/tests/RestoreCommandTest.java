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

import org.eclipse.egerrit.internal.core.command.AbandonCommand;
import org.eclipse.egerrit.internal.core.command.RestoreCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.AbandonInput;
import org.eclipse.egerrit.internal.core.rest.RestoreInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.RestoreCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class RestoreCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		// abandon the change ...
		AbandonCommand abandonCmd = fGerrit.abandon(change_id);
		AbandonInput abandonInput = new AbandonInput();
		abandonInput.setMessage("Abandoning review ...");

		abandonCmd.setCommandInput(abandonInput);

		try {
			abandonCmd.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// then restore it
		RestoreCommand restoreCmd = fGerrit.restore(change_id);
		RestoreInput restoreInput = new RestoreInput();
		restoreInput.setMessage("Restoring review ...");

		restoreCmd.setCommandInput(restoreInput);

		try {
			ChangeInfo restoreCmdResult = null;
			restoreCmdResult = restoreCmd.call();
			assertEquals(restoreCmdResult.getId(), change_id);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
