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

package org.eclipse.egerrit.ui.tests;

import static org.eclipse.egerrit.ui.tests.Expectations.expectations;
import static org.eclipse.egerrit.ui.tests.Expectations.noModifications;
import static org.eclipse.egerrit.ui.tests.InputData.inputData;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DeletionTests extends EditionLimiterTests {

	@Parameters(name = "{index}, {0}")
	public static List<Object[]> getInputs() {
		List<Object[]> things = new ArrayList<Object[]>();
		String initialDocument = "000000000\n111111111\n222222222\n333333333"; //$NON-NLS-1$
		//Delete a character on a line of code
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "<DEL>"), noModifications() });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "<DEL>"), noModifications() });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "<DEL>"), noModifications() });
		things.add(new Object[] { inputData(initialDocument, null, 1, 9, "<DEL>"), noModifications() });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "<DEL>"), noModifications() });

		//Delete a character from a comment
		String documentWithComment = "000000000\naaaa\n111111111\n222222222\n333333333";
		String[] comments = new String[] { "aaaa\n" };
		things.add(new Object[] { inputData(documentWithComment, comments, 1, 1, "<DEL>"),
				expectations(1, "aaa", new Position(10, 3)) });

		//Delete a complete line

		//Delete a segment
		return things;
	}

	@Test
	public void testDeletion() {
		processKeys();
		assertExpectations();
	}
}
