/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    - Jacques Bouthillier Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.internal.core.command.SuggestReviewersCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.SuggestReviewerInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.SubmitCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SuggestReviewersCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {

		// now do test and check the "test" users
		SuggestReviewersCommand suggestReviewersCmd = fGerrit.suggestReviewers(change_id);
		suggestReviewersCmd.setMaxNumberOfResults(10);
		suggestReviewersCmd.setQuery("tes");

		SuggestReviewerInfo[] res = null;
		try {
			res = suggestReviewersCmd.call();
			assertEquals(2, res.length);//We have two user starting with  "tes"
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
