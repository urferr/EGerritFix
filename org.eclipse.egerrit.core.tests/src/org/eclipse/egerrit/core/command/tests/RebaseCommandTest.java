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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.egerrit.internal.core.command.RebaseCommand;
import org.eclipse.egerrit.internal.core.command.SetReviewCommand;
import org.eclipse.egerrit.internal.core.command.SubmitCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.ReviewInput;
import org.eclipse.egerrit.internal.core.rest.SubmitInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ReviewInfo;
import org.eclipse.jgit.api.Git;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.RebaseCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class RebaseCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() throws Exception {
		// Code-Review +2
		SetReviewCommand setReviewcommand = fGerrit.setReview(change_id, commit_id);
		ReviewInput reviewInput = new ReviewInput();
		reviewInput.setMessage("This review is NOW ready to go ...");
		Map obj = new HashMap();
		obj.put("Code-Review", "2");
		reviewInput.setLabels(obj);
		setReviewcommand.setCommandInput(reviewInput);
		try {
			setReviewcommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// now do test and submit ...
		SubmitCommand submitCmd = fGerrit.submit(change_id);
		SubmitInput submitInput = new SubmitInput();
		submitInput.setWait_for_merge(true);

		submitCmd.setCommandInput(submitInput);

		ChangeInfo submitCmdResult = null;
		try {
			submitCmdResult = submitCmd.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		gitAccess.modifyFile(filename, "Hello reviewers community!\n\n");
		gitAccess.pushFile(false, false);

		push();

		change_id = gitAccess.getChangeId();
		commit_id = gitAccess.getCommitId();

		// rebase the change ...
		RebaseCommand rebaseCmd = fGerrit.rebase(change_id);
		try {
			rebaseCmd.call();
		} catch (EGerritException e) {
			assertEquals(EGerritException.SHOWABLE_MESSAGE, e.getCode());
			return;
		}

		fail("The Rebase should have failed");

	}

	/**
	 * submit a change to create a rebase condition for test.
	 */
	public void push() {
		String change_id = null;
		String commit_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.modifyFile(filename, "Hello reviewers community!\n\nMODIFIED");
			gAccess.pushFile(false, false);

			change_id = gAccess.getChangeId();
			commit_id = gAccess.getCommitId();
			gAccess.close();
		} catch (Exception e1) {
			fail(e1.getMessage());
		}

		// Code-Review +2
		SetReviewCommand setReviewcommand = fGerrit.setReview(change_id, commit_id);
		ReviewInput reviewInput = new ReviewInput();
		reviewInput.setMessage("This review is NOW ready to go ...");
		Map obj = new HashMap();
		obj.put("Code-Review", "2");

		reviewInput.setLabels(obj);

		setReviewcommand.setCommandInput(reviewInput);

		ReviewInfo result2 = null;
		try {
			result2 = setReviewcommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// now do test and submit ...
		SubmitCommand submitCmd = fGerrit.submit(change_id);
		SubmitInput submitInput = new SubmitInput();
		submitInput.setWait_for_merge(true);

		submitCmd.setCommandInput(submitInput);

		ChangeInfo submitCmdResult = null;
		try {
			submitCmdResult = submitCmd.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
