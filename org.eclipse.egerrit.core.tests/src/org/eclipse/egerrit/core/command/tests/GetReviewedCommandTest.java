/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.GetReviewedFilesCommand;
import org.eclipse.egerrit.core.command.SetReviewedCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.junit.Test;

public class GetReviewedCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetReviewedCommand#call()}.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCall() throws Exception {
		// Create a review for setup
		SetReviewedCommand command = fGerrit.setReviewed(change_id, commit_id, filename);
		try {
			command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		//Test that the value is indeed here
		GetReviewedFilesCommand getCommand = fGerrit.getReviewed(change_id, commit_id);
		try {
			String[] result = getCommand.call();
			assertEquals(filename, result[0]);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
