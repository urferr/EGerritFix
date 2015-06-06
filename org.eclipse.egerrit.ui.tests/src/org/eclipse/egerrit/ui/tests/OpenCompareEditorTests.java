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

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.editors.model.CompareInput;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class OpenCompareEditorTests {

	private static final String A_PROJECT_A_TXT = "aProject/a.txt"; //$NON-NLS-1$

	private static final String INITIAL_CONTENT_FILE_A = "this is file a"; //$NON-NLS-1$

	private static GitAccess gitAccess;

	@BeforeClass
	public static void setupRepo() throws Exception {
		gitAccess = new GitAccess();
		gitAccess.getGitProject();
		gitAccess.addFile(A_PROJECT_A_TXT, INITIAL_CONTENT_FILE_A);
		gitAccess.addFile("aProject/.project",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><projectDescription><name>aProject</name></projectDescription>"); //$NON-NLS-1$
		gitAccess.commitAndPush("initial commit"); //$NON-NLS-1$
		gitAccess.addToGitView();
		gitAccess.importProject("aProject/.project"); //$NON-NLS-1$
	}

	@AfterClass
	public static void tearDownRepo() throws CoreException {
		gitAccess.removeFromGitView();
		gitAccess.removeProject("aProject"); //$NON-NLS-1$
	}

	@Test
	public void compareModifiedFile() throws Exception {
		final String newContent = "new content for a"; //$NON-NLS-1$

		String beforeMods = gitAccess.getLastLocalCommitId();
		gitAccess.modifyFile(A_PROJECT_A_TXT, newContent);
		gitAccess.pushFile();
		gitAccess.resetTo(beforeMods);

		CompareInput input = new CompareInput();
		input.setLeft(INITIAL_CONTENT_FILE_A);
		input.setRight(newContent);
		assertEquals(input, getCompareInputFor(gitAccess.getChangeId()));
	}

	@Test
	public void compareNewFile() throws Exception {
		String beforeMods = gitAccess.getLastLocalCommitId();
		String content = "this is file b"; //$NON-NLS-1$
		gitAccess.addFile("aProject/b.txt", content); //$NON-NLS-1$
		gitAccess.pushFile();
		gitAccess.resetTo(beforeMods);

		CompareInput input = new CompareInput();
		input.setLeft(""); //$NON-NLS-1$
		input.setRight(content);
		assertEquals(input, getCompareInputFor(gitAccess.getChangeId()));
	}

	@Test
	public void compareRemovedFile() throws Exception {
		String beforeMods = gitAccess.getLastLocalCommitId();
		gitAccess.removeFile(A_PROJECT_A_TXT);
		gitAccess.pushFile();
		gitAccess.resetTo(beforeMods);

		CompareInput input = new CompareInput();
		input.setLeft(INITIAL_CONTENT_FILE_A);
		input.setRight(""); //$NON-NLS-1$
		assertEquals(input, getCompareInputFor(gitAccess.getChangeId()));
	}

	@Test
	public void repoUnknown() throws Exception {
		gitAccess.removeFromGitView();
		try {
			final String newContent = "new content for a"; //$NON-NLS-1$

			String beforeMods = gitAccess.getLastLocalCommitId();
			gitAccess.modifyFile(A_PROJECT_A_TXT, newContent);
			gitAccess.pushFile();
			gitAccess.resetTo(beforeMods);

			CompareInput input = new CompareInput();
			input.setLeft(""); //$NON-NLS-1$
			input.setRight(newContent);
			assertEquals(input, getCompareInputFor(gitAccess.getChangeId()));
		} finally {
			gitAccess.addToGitView();
		}

	}

	//This subclass is just here so we can access the value of compareinput
	public static class OpenCompareEditorForTest extends OpenCompareEditor {
		private CompareInput input;

		public CompareInput getCompareInput() {
			return input;
		}

		public OpenCompareEditorForTest(GerritRepository gerritRepo, ChangeInfo changeInfo) {
			super(gerritRepo, changeInfo);
		}

		@Override
		public void openCompareEditor(CompareInput input) {
			this.input = input;
		}
	}

	private CompareInput getCompareInputFor(String changeId) throws Exception {
		ChangeInfo changeInfo = gitAccess.getChange(changeId);
		OpenCompareEditorForTest compareHelper = new OpenCompareEditorForTest(gitAccess.getGerritRepo(), changeInfo);
		Map<String, RevisionInfo> revisions = changeInfo.getRevisions();
		Entry<String, RevisionInfo> theRevision = revisions.entrySet().iterator().next();
		compareHelper.compareAgainstWorkspace(theRevision.getValue().getFiles().values().iterator().next(),
				theRevision);
		return compareHelper.getCompareInput();
	}
}
