/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.internal.core.command.ListBranchesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.BranchInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.ListBranchesCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ListBranchesCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() throws Exception {
		// Run test
		ListBranchesCommand listBranchesCommand = fGerrit.listBranches(Common.TEST_PROJECT);
		try {
			BranchInfo[] listBranchesResult = listBranchesCommand.call();
			assertEquals(listBranchesResult[0].getRef(), "HEAD");
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
