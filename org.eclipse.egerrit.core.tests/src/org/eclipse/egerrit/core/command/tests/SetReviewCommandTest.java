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

import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ReviewInfo;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.SetReviewCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SetReviewCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		// Code-Review +2
		SetReviewCommand command2 = fGerrit.setReview(change_id, commit_id);
		ReviewInput reviewInput = new ReviewInput();
		reviewInput.setMessage("This review is NOW ready to go ...");
		command2.setCommandInput(reviewInput);

		try {
			ReviewInfo result2 = command2.call();
			assertEquals(result2.getLabels(), reviewInput.getLabels());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

	}
}
