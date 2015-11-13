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

import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.UpdateDraftCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.UpdateDraftCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class UpdateDraftCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		// Create a draft comment
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, commit_id);
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setLine(2);
		commentInfo.setMessage("This is a test comment");
		commentInfo.setPath(filename);
		command.setCommandInput(commentInfo);

		CommentInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Run test
		UpdateDraftCommand updCommand = fGerrit.updateDraftComments(change_id, commit_id, result.getId());
		CommentInfo updCommentInfo = new CommentInfo();
		updCommentInfo.setLine(2);
		updCommentInfo.setMessage("This is an UPDATED test comment");
		updCommentInfo.setPath(filename);
		updCommand.setCommandInput(updCommentInfo);

		try {
			CommentInfo updResult = updCommand.call();
			assertEquals(updResult.getLine(), updCommentInfo.getLine());
			assertEquals(updResult.getMessage(), updCommentInfo.getMessage());
			assertEquals(updResult.getPath(), updCommentInfo.getPath());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

	}
}
