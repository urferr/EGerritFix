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

import org.eclipse.egerrit.internal.core.command.GetMergeableCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.QueryChangesCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GetMergeableCommandTest extends CommandTestWithSimpleReview {

	// ------------------------------------------------------------------------
	// Test an actual request
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 */
	@Test
	public void testCall() {
		ChangeInfo changeInfo = ModelFactory.eINSTANCE.createChangeInfo();
		changeInfo.setId(change_id);
		GetMergeableCommand command = fGerrit.getMergeable(changeInfo);
		try {
			MergeableInfo result = command.call();
			assertEquals("MERGE_IF_NECESSARY", result.getSubmit_type());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
