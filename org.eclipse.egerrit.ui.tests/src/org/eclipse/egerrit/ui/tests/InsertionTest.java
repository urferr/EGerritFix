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
public class InsertionTest extends EditionLimiterTests {

	@Parameters(name = "{index}, {0}")
	public static List<Object[]> getInputs() {
		List<Object[]> things = new ArrayList<Object[]>();
		final String initialDocument = "000000000\n111111111\n222222222\n333333333"; //$NON-NLS-1$

		//Insert a character on a line of code
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "a"), expectations(1, new Position(0, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "a"), expectations(1, new Position(10, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "a"), expectations(1, new Position(20, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 9, "a"), expectations(1, new Position(20, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "a"), expectations(1, new Position(40, 1)) });

		//Insert a "\n" then "a"
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "\n", "a"),
				expectations(1, new Position(0, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "\n", "a"),
				expectations(1, new Position(10, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\n", "a"),
				expectations(1, new Position(20, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 9, "\n", "a"),
				expectations(1, new Position(20, 1)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "\n", "a"),
				expectations(1, new Position(40, 1)) });

		//Paste "abc"
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "abc"), expectations(1, new Position(0, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 0, "abc"), expectations(1, new Position(10, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 5, "abc"), expectations(1, new Position(20, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 9, "abc"), expectations(1, new Position(20, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 3, 9, "abc"), expectations(1, new Position(40, 3)) });

		//Paste "abc\n"
		things.add(
				new Object[] { inputData(initialDocument, null, 0, 0, "abc\n"), expectations(1, new Position(0, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 0, "abc\n"), expectations(1, new Position(10, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 5, "abc\n"), expectations(1, new Position(20, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 1, 9, "abc\n"), expectations(1, new Position(20, 3)) });
		things.add(
				new Object[] { inputData(initialDocument, null, 3, 9, "abc\n"), expectations(1, new Position(40, 3)) });

		//Paste "\nabc\n"
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "\nabc\n"),
				expectations(1, new Position(0, 3)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "\nabc\n"),
				expectations(1, new Position(10, 3)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\nabc\n"),
				expectations(1, new Position(20, 3)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 9, "\nabc\n"),
				expectations(1, new Position(20, 3)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "\nabc\n"),
				expectations(1, new Position(40, 3)) });

		//Type characters one by one
		things.add(new Object[] { inputData(initialDocument, null, 0, 0, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(0, 4)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(20, 4)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 5, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(20, 4)) });
		things.add(new Object[] { inputData(initialDocument, null, 1, 0, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(10, 4)) });
		things.add(new Object[] { inputData(initialDocument, null, 3, 9, "\n", "a", "b", "c", "\n"),
				expectations(1, new Position(40, 4)) });
		{
			//Insert in existing draft comment
			final String documentWithDraftComment = "000000000\naaaa\n111111111\n222222222\n333333333";
			final String[] comments = new String[] { "aaaa" };
			things.add(new Object[] { inputData(documentWithDraftComment, comments, 1, 0, "b"),
					expectations(1, "baaaa", 1, new Position(10, 5)) });
			things.add(new Object[] { inputData(documentWithDraftComment, comments, 1, 1, "b"),
					expectations(1, "abaaa", 1, new Position(10, 5)) });
			things.add(new Object[] { inputData(documentWithDraftComment, comments, 1, 4, "b"),
					expectations(1, "aaaab", 1, new Position(10, 5)) });

			//Insert new comment before another comment on line 1
			things.add(new Object[] { inputData(documentWithDraftComment, comments, 0, 0, "b"),
					expectations(0, "b", 2, new Position(0, 1), new Position(12, 4)) });
		}

		{
			//Insert in published comment
			final String documentWithPublishedComment = "000000000\nauthor\n111111111\n222222222\n333333333";
			final String[] publishedComments = new String[] { "author" };
			things.add(new Object[] { inputData(documentWithPublishedComment, publishedComments, 1, 0, "b"),
					expectations(1, "author", 2, new Position(10, 6), new Position(17, 1)) });
			things.add(new Object[] { inputData(documentWithPublishedComment, publishedComments, 1, 1, "b"),
					expectations(2, "b", 2, new Position(10, 6), new Position(17, 1)) });
			things.add(new Object[] { inputData(documentWithPublishedComment, publishedComments, 1, 4, "b"),
					expectations(2, "b", 2, new Position(10, 6), new Position(17, 1)) });
			//Insert new comment before another comment on line 1
			things.add(new Object[] { inputData(documentWithPublishedComment, publishedComments, 0, 0, "b"),
					expectations(0, "b", 2, new Position(0, 1), new Position(12, 6)) });
		}

		{
			//Bug 481048
			final String documentWithPublishedComment = "aaa\r\nauthor\n";
			final String[] publishedComments = new String[] { "author" };
			things.add(new Object[] { inputData(documentWithPublishedComment, publishedComments, 0, 3, "b", "b"),
					expectations(1, "bb", 2, new Position(5, 2), new Position(9, 6)) });

		}
		//Insert between two comments and reply to the first one

		//"Enter" followed by a comment

		//Try to completely delete a comment

		//Type content at the end of a published comment

		//Type content at the beginning of a published comment

		//Given a draft - go to the previous to last character and delete. Thsi should not delete the code following the comment

		return things;
	}

	@Test
	public void testInsertion() {
		processKeys();
		assertExpectations();
	}
}
