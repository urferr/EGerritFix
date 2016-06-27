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

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.egerrit.internal.core.command.CreateDraftCommand;
import org.eclipse.egerrit.internal.core.command.ListDraftsCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.ListDraftsCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ListDraftsCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		// create a comment
		final String commentMessage = "This is a test comment héhé";
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, commit_id);
		CommentInfo commentInfo = ModelFactory.eINSTANCE.createCommentInfo();
		commentInfo.setLine(2);
		commentInfo.setMessage(commentMessage);
		commentInfo.setPath(filename);
		command.setCommandInput(commentInfo);
		try {
			command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Run test
		ListDraftsCommand command2 = fGerrit.listDraftsComments(change_id, commit_id);
		try {
			Map<String, ArrayList<CommentInfo>> result2 = command2.call();
			assertEquals(1, result2.size());
			assertEquals(commentMessage, result2.get(filename).get(0).getMessage());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
