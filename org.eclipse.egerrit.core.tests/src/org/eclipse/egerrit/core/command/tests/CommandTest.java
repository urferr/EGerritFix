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
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritFactory;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.command.ChangeOption;
import org.eclipse.egerrit.internal.core.command.GetChangeCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;

public abstract class CommandTest {

	protected GerritRepository fRepository;

	protected GerritClient fGerrit;

	protected String change_id;

	protected String commit_id;

	protected ChangeInfo changeInfo;

	protected String filename;

	protected String fileContent;

	protected GitAccess gitAccess;

	protected Git gitRepo;

	protected FileInfo fileInfo;

	@Before
	public void setUp() throws Exception {
		ChangeInfo ci = ModelFactory.eINSTANCE.createChangeInfo();
		HashMap<String, LabelInfo> labels = new HashMap<String, LabelInfo>();
		labels.put("a", ModelFactory.eINSTANCE.createLabelInfo()); //$NON-NLS-1$
		ci.eSet(ModelPackage.eINSTANCE.getChangeInfo_Labels(), labels);
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
		gitAccess = new GitAccess();
	}

	/**
	 * Helper method to create a review and push it. It also initializes variables
	 *
	 * @param draft
	 *            indicates whether the review should be created as a draft
	 */
	public void createReviewWithSimpleFile(boolean draft) {
		try {
			gitRepo = gitAccess.getGitProject();
			gitAccess.addToGitView();
		} catch (Exception e1) {
			System.err.println(e1);
			fail(e1.getMessage());
		}
		filename = "folder/EGerritTestReviewFile" + getClass().getSimpleName() + System.currentTimeMillis() //$NON-NLS-1$
				+ ".java"; //$NON-NLS-1$
		fileContent = "Hello reviewers {community} !\n This is the second line \n"; //$NON-NLS-1$
		try {
			gitAccess.addFile(filename, fileContent);
		} catch (Exception e1) {
			System.err.println(e1);
			fail(e1.getMessage());
		}
		try {
			gitAccess.pushFile(draft, false);
		} catch (Exception e1) {
			System.err.println(e1);
			fail(e1.getMessage());
		}
		commit_id = gitAccess.getCommitId();

		loadReview();
	}

	/**
	 * Helper to load the review
	 */
	public void loadReview() {
		try {
			GetChangeCommand command = fGerrit.getChange(gitAccess.getChangeId());
			command.addOption(ChangeOption.ALL_REVISIONS);
			command.addOption(ChangeOption.ALL_COMMITS);
			changeInfo = command.call();
			change_id = changeInfo.getChange_id();
			RevisionInfo currentRevision = changeInfo.getRevision();
			changeInfo.setUserSelectedRevision(currentRevision);

			if (!currentRevision.isDraft()) {
				Map<String, FileInfo> files = fGerrit
						.getFiles(changeInfo.getId(), changeInfo.getUserSelectedRevision().getId())
						.call();
				currentRevision.getFiles().putAll(files);
				currentRevision.setFilesLoaded(true);
				fileInfo = currentRevision.getFiles().get(filename);
				fileInfo.getPath();
			}
		} catch (EGerritException e) {
			fail(e.getMessage());
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
			gitAccess.pushFile("Another revision\n\nChange-Id: " + gitAccess.getChangeId(), draft, true); //$NON-NLS-1$

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
			throw new FileNotFoundException("Failed to delete file: " + f); //$NON-NLS-1$
		}
	}

	@After
	public void tearDown() throws IOException {
		gitAccess.close();
		delete(gitRepo.getRepository().getWorkTree());
		fRepository.getHttpClient().close();
	}
}
