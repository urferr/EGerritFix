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

	private File checkoutFolder;

	private final String fUrl = Common.SCHEME + "://" + Common.HOST + ":" + Common.PORT + Common.PATH + "/"
			+ Common.TEST_PROJECT;

	/**
	 * instantiates a Git object connected to the server.
	 */
	public Git getGitProject() throws Exception {
		if (fGit == null) {
			cloneRepo();
		}
		return fGit;
	}

	private void cloneRepo() throws Exception {
		CloneCommand cloneCmd = Git.cloneRepository();
		checkoutFolder = createTempFolder("egerrit");
		System.out.println(checkoutFolder);
		cloneCmd.setGitDir(new File(checkoutFolder, ".git")).setURI(fUrl).setDirectory(checkoutFolder);
		cloneCmd.setBare(false).setBranch("master").setNoCheckout(false);
		fGit = cloneCmd.call();
	}

	/**
	 * Creates a file and stage it
	 *
	 * @param fileName,
	 *            the path relative to the root of repo
	 * @param content
	 * @return
	 */
	public void addFile(String fileName, String content) throws Exception {
		new File(checkoutFolder, fileName).getParentFile().mkdirs();
		try (Writer writer = new FileWriter(new File(checkoutFolder, fileName))) {
			writer.write(content.toString());
		}
		fGit.add().addFilepattern(fileName).call();
	}

	/**
	 * Modify a file and stage it
	 *
	 * @param fileName
	 * @param content
	 * @return
	 */
	public void modifyFile(String fileName, String newContent) throws Exception {
		File gitDir = fGit.getRepository().getDirectory();
		new File(gitDir, fileName).delete();
		addFile(fileName, newContent);
	}
//
//	@Test
//	public void doSomething() throws Exception {
//		getGitProject();
//		addFile("gerritDemoProject/b.txt", "a file called b");
//		commitAndPush("new file");
//		modifyFile("gerritDemoProject/b.txt", "file content modified");
//		pushFile();
//	}

	/**
	 * Commit the staged changes to the git repository.
	 *
	 * @param commitMessage
	 * @throws Exception
	 */
	public void commitAndPush(String commitMessage) throws Exception {
		fGit.commit()
				.setCommitter(Common.USER, Common.EMAIL)
				.setMessage(commitMessage == null ? "a commit" : commitMessage)
				.call();
		fGit.push()
				.setCredentialsProvider(new UsernamePasswordCredentialsProvider(Common.USER, Common.PASSWORD))
				.call();
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
		CredentialsProvider creds = new UsernamePasswordCredentialsProvider(Common.USER, Common.PASSWORD);
		System.out.println("isInteractive: " + creds.isInteractive());
		RevCommit call = command.setAuthor("Test", Common.EMAIL) //$NON-NLS-1$
				.setCommitter(Common.USER, Common.EMAIL)
				.setInsertChangeId(true)
				.setMessage("Test commit message")
				.call();
		int cid = call.getFullMessage().indexOf("Change-Id: ");
		fChange_id = call.getFullMessage().substring(cid + "Change-Id: ".length()).trim();
		Iterable<PushResult> result = fGit.push()
				.setCredentialsProvider(creds)
				.setRefSpecs(new RefSpec(refSpec))
				.call();

		Collection<RemoteRefUpdate> crru = result.iterator().next().getRemoteUpdates();
		RemoteRefUpdate rru = crru.iterator().next();
		fCommit_id = rru.getNewObjectId().toString().substring("AnyObjectId[".length(),
				rru.getNewObjectId().toString().length() - 1);

	}

	private static File createTempFolder(String prefix) throws IOException {
		File location = File.createTempFile(prefix, null);
		location.delete();
		location.mkdirs();
		return location;
	}

	/**
	 * Return the changed id of the last commit changed pushed to gerrit
	 *
	 * @return change id
	 */
	public String getChangeId() {
		return fChange_id;
	}

	public String getCommitId() {
		return fCommit_id;
	}
}
