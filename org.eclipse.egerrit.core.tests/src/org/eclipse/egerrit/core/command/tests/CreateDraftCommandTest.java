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

import org.eclipse.egerrit.internal.core.command.CreateDraftCommand;
import org.eclipse.egerrit.internal.core.command.ListDraftsCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.QueryChangesCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class CreateDraftCommandTest extends CommandTestWithSimpleReview {

	@Test
	public void testCall() {
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, commit_id);
		CommentInfo commentInfo = ModelFactory.eINSTANCE.createCommentInfo();
		commentInfo.setLine(2);
		commentInfo.setMessage("This is a test comment");
		commentInfo.setPath(filename);
		command.setCommandInput(commentInfo);

		try {
			CommentInfo result = command.call();
			assertEquals(result.getLine(), commentInfo.getLine());
			assertEquals(result.getMessage(), commentInfo.getMessage());
			assertEquals(result.getPath(), commentInfo.getPath());

			ListDraftsCommand listCommand = fGerrit.listDraftsComments(change_id, commit_id);
			assertEquals(1, listCommand.call().size());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateCommentOnLine0() {
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, commit_id);
		CommentInfo commentInfo = ModelFactory.eINSTANCE.createCommentInfo();
		commentInfo.setLine(0);
		commentInfo.setMessage("This is a test comment");
		commentInfo.setPath(filename);
		command.setCommandInput(commentInfo);

		try {
			CommentInfo result = command.call();
			assertEquals(result.getLine(), commentInfo.getLine());
			assertEquals(result.getMessage(), commentInfo.getMessage());
			assertEquals(result.getPath(), commentInfo.getPath());

			ListDraftsCommand listCommand = fGerrit.listDraftsComments(change_id, commit_id);
			assertEquals(1, listCommand.call().size());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
