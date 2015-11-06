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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.DeleteDraftCommand;
import org.eclipse.egerrit.core.command.ListDraftsCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.DeleteDraftCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class DeleteDraftCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 */
	@Test
	public void testCall() {
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
		DeleteDraftCommand command2 = fGerrit.deleteDraft(change_id, commit_id, result.getId());
		try {
			command2.call();
			ListDraftsCommand listCommand = fGerrit.listDraftsComments(change_id, commit_id);
			assertTrue(listCommand.call().isEmpty());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
