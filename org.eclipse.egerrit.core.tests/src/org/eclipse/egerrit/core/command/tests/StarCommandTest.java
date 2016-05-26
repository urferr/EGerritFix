/*******************************************************************************
 * Copyright (c) 2016 Ericsson
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

import org.eclipse.egerrit.internal.core.command.GetChangeCommand;
import org.eclipse.egerrit.internal.core.command.StarChangeCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.SubmitCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class StarCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		// Code with a star indicator
		StarChangeCommand starCommand = fGerrit.starChange(changeInfo.get_number());
		try {
			starCommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		//And verify that the Star is available
		//And make sure that it is there
		GetChangeCommand getCommand2 = fGerrit.getChange(change_id);
		try {
			ChangeInfo result = getCommand2.call();
			assertTrue("Star flag is set", result.isStarred());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
