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

import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.egerrit.core.command.GetRevisionActionsCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ActionInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.GetRevisionsActionsCommand}
 *
 * @since 1.0
 */
public class GetRevisionActionsCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 */
	@Test
	public void testCall() {
		GetRevisionActionsCommand command = fGerrit.getRevisionActions(change_id, commit_id);
		Map<String, ActionInfo> result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
		// Verify result
		assert (result != null);

	}

}
