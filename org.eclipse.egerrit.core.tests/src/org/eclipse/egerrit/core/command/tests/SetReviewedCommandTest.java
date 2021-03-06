/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.internal.core.command.GetReviewedFilesCommand;
import org.eclipse.egerrit.internal.core.command.SetReviewedCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.QueryChangesCommand}
 *
 * @since 1.0
 */
public class SetReviewedCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() throws Exception {
		SetReviewedCommand command = fGerrit.setReviewed(change_id, commit_id, fileInfo);
		try {
			command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
		GetReviewedFilesCommand getCommand = fGerrit.getReviewed(change_id, commit_id);
		try {
			String[] result = getCommand.call();
			assertEquals(filename, result[0]);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
