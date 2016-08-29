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

import org.eclipse.egerrit.internal.core.command.SetReviewCommand;
import org.eclipse.egerrit.internal.core.command.SubmitCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.ReviewInput;
import org.eclipse.egerrit.internal.core.rest.SubmitInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.SubmitCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SubmitCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
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
			assertEquals(submitCmdResult.getChange_id(), change_id);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
