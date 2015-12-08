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

import static org.junit.Assert.fail;

import java.net.URI;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;

public class RCPTTInitialSetOfReviews {

	private static final String A_PROJECT_A_JAVA = "A.java"; //$NON-NLS-1$

	private static final String INITIAL_CONTENT_FILE_A = "Hello";

	private static final String NEW_CONTENT_FILE_A = "Hello reviewer community \n\n Now is the time to do some testing \n \n";

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

	private String filename;

	private String fileContent;

	private static GitAccess gitAccess;

	public void setUp() throws Exception {
		fRepository = new GerritRepository(SCHEME, HOST, PORT, PATH);
		if (PROXY_HOST != null) {
			fRepository.setProxy(new HttpHost(PROXY_HOST, PROXY_PORT));
		}
		fRepository.setCredentials(new GerritCredentials(USER, PASSWORD));
		fRepository.getCredentials().setHttpCredentials(USER, PASSWORD);
		fRepository.setServerInfo(new GerritServerInformation(
				new URI(SCHEME, null, HOST, PORT, PATH, null, null).toASCIIString(), "Test server"));
	}

	public void setupRepo() throws Exception {

		gitAccess = new GitAccess();
		gitAccess.setTestProject("egerrit/RCPTTtest");
		gitAccess.getGitProject();
		for (int i = 0; i < 10; i++) {
			gitAccess.addFile(A_PROJECT_A_JAVA, INITIAL_CONTENT_FILE_A);
			gitAccess.addFile("aProject/.project", DOT_PROJECT_FILE); //$NON-NLS-1$
			gitAccess.commitAndPush("initial commit"); //$NON-NLS-1$

			String beforeMods = gitAccess.getLastLocalCommitId();
			gitAccess.modifyFile(A_PROJECT_A_JAVA, NEW_CONTENT_FILE_A);
			gitAccess.pushFile("Test commit message number " + new Integer(i + 1), false, false);
			gitAccess.resetTo(beforeMods);
		}
	}

	public void createReviewWithSimpleFile(boolean draft) {
		try {
			filename = "folder/EGerritTestReviewFile" + getClass().getSimpleName() + System.currentTimeMillis() //$NON-NLS-1$
					+ ".java"; //$NON-NLS-1$
			fileContent = "Hello reviewers {community} !\n This is the second line \n"; //$NON-NLS-1$
			gitAccess.addFile(filename, fileContent);
			gitAccess.pushFile(draft, false);

		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}

	/**
	 * Helper method to add a file to an existing review and push it.
	 *
	 * @param draft
	 *            indicates whether to commit to the draft branch
	 */
	public void amendLastCommit(boolean draft) {
		try {
			filename = "folder/EGerritTestReviewFile" + getClass().getSimpleName() + System.currentTimeMillis() //$NON-NLS-1$
					+ ".java"; //$NON-NLS-1$
			fileContent = "Hello reviewers {community} !\n This is the second line \n This is the third line \n"; //$NON-NLS-1$

			gitAccess.addFile(filename, fileContent);
			gitAccess.pushFile("Another revision\n\nChange-Id: " + gitAccess.getChangeId(), draft, true);

		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}

}
