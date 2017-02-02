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
 * Test the case where the edit list represents the replacement of lines
 */
public class MarkerRepositionerReplaceTest {

	private MarkerRepositioner repositioner;

	@Before
	public void createReplaceEdit() throws Exception {
		EditList list = new EditList();
		list.add(new Edit(3, 5, 3, 7)); //replace 2 lines by 4 lines
		repositioner = new MarkerRepositioner();
		repositioner.setEdits(list);
	}

	@Test
	public void markerBefore() {
		org.junit.Assert.assertEquals(1, repositioner.getNewPositionFor(1));
	}

	@Test
	public void markerAfter() {
		org.junit.Assert.assertEquals(12, repositioner.getNewPositionFor(10));
	}

	@Test
	public void markerInTheMiddle() {
		org.junit.Assert.assertEquals(4, repositioner.getNewPositionFor(4));
	}

	@Test
	public void onFirstLine() {
		org.junit.Assert.assertEquals(3, repositioner.getNewPositionFor(3));
	}

	@Test
	public void onLastLine() {
		org.junit.Assert.assertEquals(7, repositioner.getNewPositionFor(5));
	}

	@Test
	public void justAfterLine() {
		org.junit.Assert.assertEquals(8, repositioner.getNewPositionFor(6));
	}
}
