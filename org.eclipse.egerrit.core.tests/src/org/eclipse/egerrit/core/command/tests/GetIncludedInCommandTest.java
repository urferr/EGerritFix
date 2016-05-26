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

import org.eclipse.egerrit.internal.core.command.GetIncludedInCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.IncludedInInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.GetIncludedInCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GetIncludedInCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		GetIncludedInCommand command = fGerrit.getIncludedIn(change_id);
		IncludedInInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
		// Verify result
		String res = result.getBranches().toString();
		assertTrue("[]".equals(res));

	}

}
