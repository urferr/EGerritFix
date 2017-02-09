/*******************************************************************************
 * Copyright (c) 2016-2017 Ericsson AB.
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

import java.io.File;

import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclFactory;

/**
 * A Helper class to create and amend reviews.
 */
public class ReviewFactory {

	public String server;

	public String project;

	private GerritRepository fRepository;

	private String filename = "src/EGerritTestReviewFile.java"; //$NON-NLS-1$;

	private String fileContent = "Hello reviewers {community} !\n This is the second line \n" //$NON-NLS-1$
			+ System.currentTimeMillis();

	private GitAccess gitAccess;

	public static ReviewDescription amendReview(String localRepo, String server, String project, String changeId,
			boolean isDraft) throws Exception {
		ReviewFactory factory = new ReviewFactory();
		factory.doAmendReview(localRepo, server, project, changeId, isDraft);

		ReviewDescription commandResult = egerriteclFactory.eINSTANCE.createReviewDescription();
		commandResult.setGerritServerURL(server);
		commandResult.setProjectName(project);
		commandResult.setLocalClone(factory.getGitAccess().getCheckoutFolder().getAbsolutePath());
		commandResult.setLastChangeId(changeId);
		commandResult.setIsDraft(isDraft);
		factory.close();
		return commandResult;
	}

	private void close() {
		gitAccess.close();
	}

	public static ReviewDescription createReview(String server, String project, boolean isDraft, String changedFilename,
			String fileContent) throws Exception {
		ReviewFactory factory = new ReviewFactory();
		String changeId = factory.doCreateReview(server, project, isDraft, changedFilename, fileContent);

		ReviewDescription commandResult = egerriteclFactory.eINSTANCE.createReviewDescription();
		commandResult.setGerritServerURL(server);
		commandResult.setProjectName(project);
		commandResult.setLocalClone(factory.getGitAccess().getCheckoutFolder().getAbsolutePath());
		commandResult.setLastChangeId(changeId);
		commandResult.setIsDraft(isDraft);
		factory.close();
		return commandResult;
	}

	private String doAmendReview(String localRepo, String server, String project, String changeId, boolean isDraft)
			throws Exception {
		gitAccess = new GitAccess(new File(localRepo));
		this.server = server;
		this.project = project;
		initGerritConnection();
		amendReview(changeId, isDraft);
		return gitAccess.getChangeId();
	}

	private String doCreateReview(String server, String project, boolean isDraft, String changedFilename,
			String fileContent) throws Exception {
		this.server = server;
		this.project = project;
		if (changedFilename != null) {
			this.filename = changedFilename;
		}
		if (fileContent != null) {
			this.fileContent = fileContent;
		}
		initGitAccess();
		createProjectInMaster();
		initGerritConnection();
		createReviewWithSimpleFile(isDraft);
		String changeId = gitAccess.getChangeId();
		gitAccess.close();
		return changeId;
	}

	//Conditionally create a .project file
	private void createProjectInMaster() throws Exception {
		String dotProjectContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<projectDescription>\n"
				+ "	<name>Project</name>\n" + "	<comment></comment>\n" + "	<projects>\n" + "	</projects>\n"
				+ "	<buildSpec>\n" + "	</buildSpec>\n" + "	<natures>\n" + "	</natures>\n" + "</projectDescription>"; //$NON-NLS-5$
		gitAccess.commitFileInMaster("Project/.project", dotProjectContent);
		gitAccess.close();

		//Re-initialize the gitAccess
		initGitAccess();
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
			gitAccess.addFile(filename, fileContent);
			gitAccess.pushFile(draft, false);
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}

	private void amendReview(String changeId, boolean draft) {
		try {
			gitAccess.addFile(filename, fileContent);
			gitAccess.pushFile("Another revision\n\nChange-Id: " + changeId, draft, true); //$NON-NLS-1$
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}

	private GitAccess getGitAccess() {
		return gitAccess;
	}
}
