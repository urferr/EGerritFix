/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    - Ericsson Communications Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.internal.core.command.SuggestAccountCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.SuggestAccount}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SuggestAccountCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {

		// now do test and check
		SuggestAccountCommand suggestAcccountCmd = fGerrit.suggestAccount();
		suggestAcccountCmd.setMaxNumberOfResults(20);
		suggestAcccountCmd.setQuery("t");

		AccountInfo[] res = null;
		try {
			res = suggestAcccountCmd.call();
			assertEquals(2, res.length);//We have two user starting with  "tes"
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
