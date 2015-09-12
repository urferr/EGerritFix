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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.editors.model.GerritCompareInput;
import org.eclipse.egerrit.ui.editors.model.PatchSetCompareItem;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OpenCompareEditorTests {

	private static final String A_PROJECT_A_JAVA = "aProject/A.java"; //$NON-NLS-1$

	private static final String INITIAL_CONTENT_FILE_A = "public class A {\n" + "	\n"
			+ "	public static void main(String[] args) {\n" + "		throw new RuntimeException();\n" + "	}\n" + "\n"
			+ "	public void foo() {\n" + "		System.out.println(\"A\");\n" + "	}\n" + "\n" + "	\n"
			+ "	public void bar() {\n" + "	}\n" + "}"; //$NON-NLS-3$

	private static final String NEW_CONTENT_FILE_A = "public class A {\n" + "	\n"
			+ "	public static void main(String[] args) {\n" + "		throw new RuntimeException();\n" + "	}\n" + "\n"
			+ "	public void foo() {\n" + "		System.out.println(\"A\");\n" + "	}\n" + "\n" + "	\n" + "}";

	private static final String DOT_PROJECT_FILE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<projectDescription>\n" + "	<name>aProject</name>\n" + "	<comment></comment>\n" + "	<projects>\n"
			+ "	</projects>\n" + "	<buildSpec>\n" + "		<buildCommand>\n"
			+ "			<name>org.eclipse.jdt.core.javabuilder</name>\n" + "			<arguments>\n"
			+ "			</arguments>\n" + "		</buildCommand>\n" + "	</buildSpec>\n" + "	<natures>\n"
			+ "		<nature>org.eclipse.jdt.core.javanature</nature>\n" + "	</natures>\n" + "</projectDescription>\n";

	private final String SCHEME = Common.SCHEME;

	private final String HOST = Common.HOST;

	private final int PORT = Common.PORT;

	private final String PATH = Common.PATH;

	private final String PROXY_HOST = Common.PROXY_HOST;

	private final int PROXY_PORT = Common.PROXY_PORT;

	private final String USER = Common.USER;

	private final String PASSWORD = Common.PASSWORD;

	private GerritRepository fRepository;

	private static GitAccess gitAccess;

	@Before
	public void setUp() throws Exception {
		fRepository = new GerritRepository(SCHEME, HOST, PORT, PATH);
		if (PROXY_HOST != null) {
			fRepository.setProxy(new HttpHost(PROXY_HOST, PROXY_PORT));
		}
		fRepository.setCredentials(new GerritCredentials(USER, PASSWORD));
		fRepository.getCredentials().setHttpCredentials(USER, PASSWORD);
	}

	@BeforeClass
	public static void setupRepo() throws Exception {
		gitAccess = new GitAccess();
		gitAccess.getGitProject();
		gitAccess.addFile(A_PROJECT_A_JAVA, INITIAL_CONTENT_FILE_A);
		gitAccess.addFile("aProject/.project", DOT_PROJECT_FILE); //$NON-NLS-1$
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
		String beforeMods = gitAccess.getLastLocalCommitId();
		gitAccess.modifyFile(A_PROJECT_A_JAVA, NEW_CONTENT_FILE_A);
		gitAccess.pushFile();
		gitAccess.resetTo(beforeMods);

		GerritCompareInput input = getCompareInputFor(gitAccess.getChangeId());
		ICompareInput compareInput = input.prepareCompareInput(new NullProgressMonitor());
		assertTrue(compareInput.getLeft() instanceof LocalResourceTypedElement);
		assertNotNull(((PatchSetCompareItem) compareInput.getRight()).getContents());
	}

	@Test
	public void compareNewFile() throws Exception {
		String beforeMods = gitAccess.getLastLocalCommitId();
		String content = "this is file b"; //$NON-NLS-1$
		gitAccess.addFile("aProject/b.txt", content); //$NON-NLS-1$
		gitAccess.pushFile();
		gitAccess.resetTo(beforeMods);

		assertTrue(getCompareInputFor(gitAccess.getChangeId()).getTitle().contains("(no matching file in workspace)"));
	}

	@Test
	public void compareRemovedFile() throws Exception {
		String beforeMods = gitAccess.getLastLocalCommitId();
		gitAccess.removeFile(A_PROJECT_A_JAVA);
		gitAccess.pushFile();
		gitAccess.resetTo(beforeMods);

		GerritCompareInput input = getCompareInputFor(gitAccess.getChangeId());
		ICompareInput compareInput = input.prepareCompareInput(new NullProgressMonitor());
		assertTrue(compareInput.getLeft() instanceof LocalResourceTypedElement);
		assertNotNull(((PatchSetCompareItem) compareInput.getRight()).getContents());
	}

	@Test
	public void repoUnknown() throws Exception {
		gitAccess.removeFromGitView();
		try {
			String beforeMods = gitAccess.getLastLocalCommitId();
			gitAccess.modifyFile(A_PROJECT_A_JAVA, NEW_CONTENT_FILE_A);
			gitAccess.pushFile();
			gitAccess.resetTo(beforeMods);

			assertTrue(
					getCompareInputFor(gitAccess.getChangeId()).getTitle().contains("(no matching file in workspace)"));
		} finally {
			gitAccess.addToGitView();
		}

	}

	//This subclass is just here so we can access the value of compareinput
	public static class OpenCompareEditorForTest extends OpenCompareEditor {
		private GerritCompareInput input;

		public GerritCompareInput getCompareInput() {
			return input;
		}

		public OpenCompareEditorForTest(GerritClient gerrit, ChangeInfo changeInfo) {
			super(gerrit, changeInfo);
		}

		@Override
		public void openCompareEditor(GerritCompareInput input) {
			this.input = input;
		}
	}

	private GerritCompareInput getCompareInputFor(String changeId) throws Exception {
		ChangeInfo changeInfo = gitAccess.getChange(changeId);
		OpenCompareEditorForTest compareHelper = new OpenCompareEditorForTest(gitAccess.getGerrit(), changeInfo);
		Map<String, RevisionInfo> revisions = changeInfo.getRevisions();
		Entry<String, RevisionInfo> theRevision = revisions.entrySet().iterator().next();
		compareHelper.compareAgainstWorkspace(theRevision.getValue().getFiles().values().iterator().next());
		return compareHelper.getCompareInput();
	}
}
