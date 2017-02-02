/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import org.eclipse.egerrit.internal.core.command.GetDiffCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.DiffInfo;
import org.junit.Assert;
import org.junit.Test;

public class GetDiffCommandTest extends CommandTestWithSimpleReview {

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.command.AddReviewerCommand#call()}.
	 *
	 * @throws EGerritException
	 */
	@Test
	public void testCallRevision() throws EGerritException {
		GetDiffCommand diff = new GetDiffCommand(fGerrit.getRepository(), change_id, commit_id, "/COMMIT_MSG", 1); //$NON-NLS-1$
		DiffInfo result = diff.call();
		Assert.assertEquals(1, result.getContent().size());
		Assert.assertEquals(0, result.getContent().get(0).getAb().size());
		Assert.assertEquals(0, result.getContent().get(0).getA().size());
		Assert.assertEquals(0, result.getContent().get(0).getB().size());
	}

	@Test
	public void testCallFromBase() throws EGerritException {
		GetDiffCommand diff = new GetDiffCommand(fGerrit.getRepository(), change_id, commit_id, "/COMMIT_MSG", 0); //$NON-NLS-1$
		DiffInfo result = diff.call();
		Assert.assertEquals(1, result.getContent().size());
		Assert.assertEquals(0, result.getContent().get(0).getAb().size());
		Assert.assertEquals(0, result.getContent().get(0).getA().size());
		Assert.assertNotEquals(0, result.getContent().get(0).getB().size());
	}
}
