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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.AddReviewerCommand;
import org.eclipse.egerrit.core.command.DeleteReviewerCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.AddReviewerInput;
import org.eclipse.egerrit.core.rest.AddReviewerResult;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.DeleteReviewerCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class DeleteReviewerCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.DeleteReviewerCommand#call()}.
	 */
	@Test
	public void testCall() {
		// Add a valid reviewer ...
		String user = "test1";
		AddReviewerCommand addCommand = fGerrit.addReviewer(change_id);
		AddReviewerInput addreviewerInput = new AddReviewerInput();
		addreviewerInput.setReviewer(user);

		addCommand.setCommandInput(addreviewerInput);

		AddReviewerResult reviewerCmdResult = null;
		try {
			reviewerCmdResult = addCommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		//Now verify the Delete
		DeleteReviewerCommand delCommand = fGerrit.deleteReviewer(change_id, user);
		try {
			delCommand.call();
			assertEquals(0, fGerrit.getReviewers(change_id).call().length);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
