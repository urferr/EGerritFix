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

package org.eclipse.egerrit.internal.rcptt.support;

import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.junit.Test;

public class ReviewFactory {

	public String server;

	public String project;

	private GerritRepository fRepository;

	private String filename;

	private String fileContent;

	private GitAccess gitAccess;

	private static final String A_PROJECT_A_JAVA = "A.java"; //$NON-NLS-1$

	private static final String INITIAL_CONTENT_FILE_A = "Hello";

	private static final String NEW_CONTENT_FILE_A = "Hello reviewer community \n\n Now is the time to do some testing \n \n";

//	private static final String DOT_PROJECT_FILE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
//			+ "<projectDescription>\n" + "	<name>aProject</name>\n" + "	<comment></comment>\n" + "	<projects>\n"
//			+ "	</projects>\n" + "	<buildSpec>\n" + "		<buildCommand>\n"
//			+ "			<name>org.eclipse.jdt.core.javabuilder</name>\n" + "			<arguments>\n"
//			+ "			</arguments>\n" + "		</buildCommand>\n" + "	</buildSpec>\n" + "	<natures>\n"
//			+ "		<nature>org.eclipse.jdt.core.javanature</nature>\n" + "	</natures>\n" + "</projectDescription>\n";

	public String createReview(String server, String project) throws Exception {
		this.server = server;
		this.project = project;
		if (filename == null) {
			filename = A_PROJECT_A_JAVA;
		}
		if (fileContent == null) {
			fileContent = INITIAL_CONTENT_FILE_A;
		}
		initGitAccess();
		initGerritConnection();
		createReviewWithSimpleFile(false);
		return gitAccess.getChangeId();
	}

	private void initGerritConnection() throws Exception {
		fRepository = new GerritRepository(server);
		fRepository.setCredentials(new GerritCredentials(Common.USER, Common.PASSWORD));
		fRepository.getCredentials().setHttpCredentials(Common.USER, Common.PASSWORD);
		fRepository.setServerInfo(new GerritServerInformation(server, server + "(created by rcptthelper)")); //$NON-NLS-1$
	}

	private void initGitAccess() throws Exception {
		gitAccess = new GitAccess(server, project);
		gitAccess.getGitProject();
	}

	private void createReviewWithSimpleFile(boolean draft) {
		try {
			filename = "src/EGerritTestReviewFile.java"; //$NON-NLS-1$
			fileContent = "Hello reviewers {community} !\n This is the second line \n"; //$NON-NLS-1$
			gitAccess.addFile(filename, fileContent);
			gitAccess.pushFile(draft, false);
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}

	@Test
	public void coucou() {
		try {
			String s = new ReviewFactory().createReview("http://localhost:28112", "egerrit/RCPTTtest");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
