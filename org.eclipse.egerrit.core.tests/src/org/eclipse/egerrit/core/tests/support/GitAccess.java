/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.core.tests.support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.Authenticator;
import java.util.Collection;

import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * A utility class for supporting JUnit tests scenarios
 *
 * @since 1.0
 */

@SuppressWarnings("nls")
public class GitAccess {
	private Git fGit = null;

	private String fChange_id;

	private String fCommit_id;

	private String fUrl;

	/**
	 * instantiates a Git object connected to the server.
	 */
	public Git getGitProject() throws Exception {

		if (fGit == null) {
			fUrl = Common.SCHEME + "://" + Common.HOST + ":" + Common.PORT
					+ Common.PATH + "/" + Common.TEST_PROJECT;

			CloneCommand cloneCmd = Git.cloneRepository();
			cloneCmd.setGitDir(createTempFolder("egerrit")).setURI(fUrl);

			fGit = cloneCmd.call();

		}
		return fGit;
	}

	/**
	 * Creates a file in preparation for a push to Gerrit server
	 *
	 * @param fileName
	 * @param content
	 * @return
	 */
	public void addFile(String fileName, String content) throws Exception {

		CloneCommand cloneCmd = Git.cloneRepository();
		cloneCmd.setGitDir(createTempFolder("egerrit")).setURI(fUrl);

		Writer writer = new FileWriter(fileName);
		try {
			writer.write(content.toString());
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// don't need to catch this
			}
		}

		fGit = cloneCmd.call();

		DirCache dc = fGit.add().addFilepattern(fileName).call();

	}

	/**
	 * Commits and pushes to gerrit server
	 *
	 * @return
	 */
	public void pushFile() throws Exception {
		Authenticator.setDefault(null);
		CommitCommand command = fGit.commit();
		String refSpec = "HEAD:refs/for/master";
		CredentialsProvider creds = new UsernamePasswordCredentialsProvider(
				Common.USER, Common.PASSWORD);
		System.out.println("isInteractive: " + creds.isInteractive());
		RevCommit call = command
				.setAuthor("Test", Common.EMAIL) //$NON-NLS-1$
				.setCommitter(Common.USER, Common.EMAIL)
				.setInsertChangeId(true).setMessage("Test commit message")
				.call();
		int cid = call.getFullMessage().indexOf("Change-Id: ");
		fChange_id = call.getFullMessage()
				.substring(cid + "Change-Id: ".length()).trim();
		Iterable<PushResult> result = fGit.push().setCredentialsProvider(creds)
				.setRefSpecs(new RefSpec(refSpec)).call();

		Collection<RemoteRefUpdate> crru = result.iterator().next()
				.getRemoteUpdates();
		RemoteRefUpdate rru = crru.iterator().next();
		fCommit_id = rru
				.getNewObjectId()
				.toString()
				.substring("AnyObjectId[".length(),
						rru.getNewObjectId().toString().length() - 1);

	}

	private static File createTempFolder(String prefix) throws IOException {
		File location = File.createTempFile(prefix, null);
		location.delete();
		location.mkdirs();
		return location;
	}

	public String getChangeId() {

		return fChange_id;

	}

	public String getCommitId() {
		return fCommit_id;
	}

}
