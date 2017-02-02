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

package org.eclipse.egerrit.ui.tests;

import org.eclipse.egerrit.internal.ui.utils.MarkerRepositioner;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the case where the edit list represents the full file is deleted
 */
public class MarkerRepositionerLimitTest {

	private MarkerRepositioner repositioner;

	@Before
	public void createDeleteEdit() throws Exception {
		EditList list = new EditList();
		list.add(new Edit(0, 5, 0, 0));
		repositioner = new MarkerRepositioner();
		repositioner.setEdits(list);
	}

	@Test
	public void deleteEverything() {
		org.junit.Assert.assertEquals(0, repositioner.getNewPositionFor(5));
	}
}
