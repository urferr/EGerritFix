/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.egerrit.internal.core.command.ChangeOption;
import org.eclipse.egerrit.internal.core.command.GetChangeCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.junit.Before;
import org.junit.Test;

public class GetModifiedFilesCommandTest extends CommandTestWithSimpleReview {

	@Before
	public void createDraftReview() {
		createReviewWithSimpleFile(false);
		amendLastCommit(false);

	}

	@Test
	public void testCommand() throws EGerritException {
		//Get the changeInfo object we just created
		GetChangeCommand changeCmd = fGerrit.getChange(change_id);
		changeCmd.addOption(ChangeOption.ALL_REVISIONS);
		ChangeInfo changeInfo = changeCmd.call();
		assertEquals(2, changeInfo.getRevisions().size());

		//Obtain the newest revision (the second) and compare with the first one.
		//Two files should have changed: the commit message and one of the data file
		RevisionInfo latestRevision = changeInfo.getRevisionByNumber(2);
		Map<String, FileInfo> changedFiles = fGerrit.getFilesModifiedSince(changeInfo.getId(), latestRevision.getId(),
				changeInfo.getRevisionByNumber(1).getId()).call();
		assertEquals(2, changedFiles.size());
	}

}
