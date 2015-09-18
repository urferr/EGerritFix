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
import static org.eclipse.egerrit.ui.tests.InputData.inputData;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InsertionTests extends EditionLimiterTests {

	@Parameters(name = "{index}, {0}")
	public static List<Object[]> getInputs() {
		List<Object[]> things = new ArrayList<Object[]>();
		String initialDocument = "000000000\n111111111\n222222222\n333333333"; //$NON-NLS-1$

		//Insert a character on a line of code
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "a"), expectations(1, new Position(0, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "a"), expectations(1, new Position(10, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "a"), expectations(1, new Position(20, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 9, "a"), expectations(1, new Position(20, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "a"), expectations(1, new Position(40, 2)) });

		//Insert a "\n" then "a"
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "\n", "a"),
				expectations(1, new Position(0, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "\n", "a"),
				expectations(1, new Position(10, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\n", "a"),
				expectations(1, new Position(20, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 9, "\n", "a"),
				expectations(1, new Position(20, 2)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "\n", "a"),
				expectations(1, new Position(40, 2)) });

		//Paste "abc"
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "abc"), expectations(1, new Position(0, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 0, "abc"), expectations(1, new Position(10, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 5, "abc"), expectations(1, new Position(20, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 9, "abc"), expectations(1, new Position(20, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 3, 9, "abc"), expectations(1, new Position(40, 4)) });

		//Paste "abc\n"
		things.add(
				new Object[] { inputData(initialDocument, null, 0, 0, "abc\n"), expectations(1, new Position(0, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 0, "abc\n"), expectations(1, new Position(10, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 5, "abc\n"), expectations(1, new Position(20, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 9, "abc\n"), expectations(1, new Position(20, 4)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 3, 9, "abc\n"), expectations(1, new Position(40, 4)) });

		//Paste "\nabc\n"
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "\nabc\n"),
				expectations(1, new Position(0, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "\nabc\n"),
				expectations(1, new Position(10, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\nabc\n"),
				expectations(1, new Position(20, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 9, "\nabc\n"),
				expectations(1, new Position(20, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "\nabc\n"),
				expectations(1, new Position(40, 5)) });

		//Type characters one by one
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(0, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(20, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(20, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(10, 5)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(40, 5)) });

		//Insert in comment
		String documentWithComment = "000000000\naaaa\n111111111\n222222222\n333333333";
		String[] comments = new String[] { "aaaa\n" };
		things.add(new Object[] { inputData(documentWithComment, comments, 1, 0, "b"),
				expectations(1, "baaaa", new Position(10, 6)) });
		things.add(new Object[] { inputData(documentWithComment, comments, 1, 1, "b"),
				expectations(1, "abaaa", new Position(10, 6)) });
		things.add(new Object[] { inputData(documentWithComment, comments, 1, 4, "b"),
				expectations(1, "aaaab", new Position(10, 6)) });
		return things;
	}

	@Test
	public void testInsertion() {
		processKeys();
		assertExpectations();
	}
}
