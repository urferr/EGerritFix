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

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;

public abstract class CommandTest {

	protected GerritRepository fRepository;

	protected GerritClient fGerrit;

	protected String change_id;

	protected String commit_id;

	protected String change_sha;

	protected ChangeInfo changeInfo;

	protected String filename;

	protected String fileContent;

	protected GitAccess gitAccess;

	protected Git gitRepo;

	@Before
	public void setUp() throws Exception {
		GerritServerInformation serverInfo = new GerritServerInformation(
				new URI(Common.SCHEME, null, Common.HOST, Common.PORT, Common.PATH, null, null).toASCIIString(),
				"Test server"); //$NON-NLS-1$
		serverInfo.setUserName(Common.USER);
		serverInfo.setPassword(Common.PASSWORD);
		fRepository = new GerritRepository(Common.SCHEME, Common.HOST, Common.PORT, Common.PATH);
		if (Common.PROXY_HOST != null) {
			fRepository.setProxy(new HttpHost(Common.PROXY_HOST, Common.PROXY_PORT));
		}
		fRepository.setCredentials(new GerritCredentials(Common.USER, Common.PASSWORD));
		fRepository.getCredentials().setHttpCredentials(Common.USER, Common.PASSWORD);
		fRepository.setServerInfo(serverInfo);
		fGerrit = GerritFactory.create(fRepository);
	}

	/**
	 * Helper method to create a review and push it. It also initializes variables
	 *
	 * @param draft
	 *            indicates whether the review should be created as a draft
	 */
	public void createReviewWithSimpleFile(boolean draft) {
		try {
			gitAccess = new GitAccess();
			gitRepo = gitAccess.getGitProject();
			filename = "folder/EGerritTestReviewFile" + getClass().getSimpleName() + System.currentTimeMillis() //$NON-NLS-1$
					+ ".java"; //$NON-NLS-1$
			fileContent = "Hello reviewers {community} !\n This is the second line \n"; //$NON-NLS-1$
			gitAccess.addFile(filename, fileContent);
			gitAccess.pushFile(draft, false);

			change_sha = gitAccess.getChangeId();
			commit_id = gitAccess.getCommitId();

			changeInfo = fGerrit.getChange(change_sha).call();
			change_id = changeInfo.getId();
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
			gitAccess.pushFile("Another revision\n\nChange-Id: " + change_sha, draft, true);

		} catch (Exception e1) {
			fail(e1.getMessage());
		}
	}

	private void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles()) {
				delete(c);
			}
		}
		if (!f.delete()) {
			throw new FileNotFoundException("Failed to delete file: " + f);
		}
	}

	@After
	public void tearDown() throws IOException {
		delete(gitRepo.getRepository().getWorkTree());
	}
}
