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
import org.eclipse.egerrit.internal.core.command.ListCommentsCommand;
import org.eclipse.egerrit.internal.core.command.PublishDraftRevisionCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.PublishDraftRevisionCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class PublishDraftRevisionCommandTest extends CommandTestWithSimpleReview {
//	@Test
	public void testCall() {
		// create a comment
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, commit_id);
		CommentInfo commentInfo = ModelFactory.eINSTANCE.createCommentInfo();
		commentInfo.setLine(1);
		commentInfo.setMessage("This is a test comment");
		commentInfo.setPath(filename);
		command.setCommandInput(commentInfo);
		try {
			command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// now publish it
		PublishDraftRevisionCommand command3 = fGerrit.publishDraftRevision(change_id, "current");
		try {
			command3.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		ListCommentsCommand listCommentsCommand = fGerrit.getListComments(change_id, commit_id);
		try {
			assertEquals(commentInfo, listCommentsCommand.call().get(filename).get(0));
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
