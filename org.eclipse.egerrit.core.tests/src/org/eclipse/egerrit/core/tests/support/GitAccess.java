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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.lib.ObjectId;
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

	private String lastCommitId;

	private File checkoutFolder;

	private GerritRepository gerritRepo;

	private Gerrit gerrit;

	private final String fUrl = Common.SCHEME + "://" + Common.HOST + ":" + Common.PORT + Common.PATH + "/"
			+ Common.TEST_PROJECT;

	/**
	 * Instantiates a local git repo connected to the server.
	 */
	public Git getGitProject() throws Exception {
		gerritRepo = new GerritRepository(Common.SCHEME, Common.HOST, Common.PORT, Common.PATH);
		//TODO DEAL WITH HTTPS AND SUCH
		gerritRepo.setCredentials(new GerritCredentials(Common.USER, Common.PASSWORD));
		gerrit = GerritFactory.create(gerritRepo);
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
	 * @param fileName
	 *            , the path relative to the root of repo
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

	/**
	 * Commit the staged changes to the git repository.
	 *
	 * @param commitMessage
	 *            or null
	 * @throws Exception
	 */
	public void commitAndPush(String commitMessage) throws Exception {
		RevCommit commitId = fGit.commit()
				.setCommitter(Common.USER, Common.EMAIL)
				.setMessage(commitMessage == null ? "a commit" : commitMessage)
				.call();
		lastCommitId = ObjectId.toString(commitId.getId());
		fGit.push()
				.setCredentialsProvider(new UsernamePasswordCredentialsProvider(Common.USER, Common.PASSWORD))
				.call();
	}

	/**
	 * Commits and pushes to gerrit server
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
	 * Returns the changed id of the last commit changed pushed to gerrit
	 *
	 * @return change id
	 */
	public String getChangeId() {
		return fChange_id;
	}

	/**
	 * Returns the last commit id
	 *
	 * @return string of the commit id
	 */
	public String getCommitId() {
		return fCommit_id;
	}

	/**
	 * Add git repo to the list of repositories known by egit
	 */
	public void addToGitView() {
		RepositoryUtil repoUtil = Activator.getDefault().getRepositoryUtil();
		repoUtil.addConfiguredRepository(fGit.getRepository().getDirectory());
	}

	/**
	 * Remove git repo from the list of repositories known by egit
	 */
	public void removeFromGitView() {
		RepositoryUtil repoUtil = Activator.getDefault().getRepositoryUtil();
		repoUtil.removeDir(fGit.getRepository().getDirectory());
	}

	/**
	 * Import the path as a project in the workspace
	 *
	 * @param projectFilePath
	 * @throws CoreException
	 */
	public void importProject(String projectFilePath) throws CoreException {
		IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(
				new org.eclipse.core.runtime.Path(new File(checkoutFolder, projectFilePath).getAbsolutePath()));
		if (description != null) {
			String projectName = description.getName();
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (project.exists() == true) {
				if (project.isOpen() == false) {
					project.open(IResource.BACKGROUND_REFRESH, new NullProgressMonitor());
				}
			} else {
				project.create(description, new NullProgressMonitor());
				project.open(IResource.BACKGROUND_REFRESH, new NullProgressMonitor());
			}
		}
	}

	/**
	 * Remove a project from the file system but w/o deleting the underlying files
	 *
	 * @param projectFilePath
	 * @throws CoreException
	 */
	public void removeProject(String projectName) throws CoreException {
		ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).delete(false, true, new NullProgressMonitor());
	}

	/**
	 * Remove file from the git repository
	 *
	 * @param filename
	 *            relative filepath to remove
	 * @throws Exception
	 */
	public void removeFile(String filename) throws Exception {
		fGit.rm().addFilepattern(filename).call();
	}

	/**
	 * Get the object representing the gerrit server.
	 *
	 * @return
	 */
	public GerritRepository getGerritRepo() {
		return gerritRepo;
	}

	/**
	 * Return the id of the last commit done locally
	 *
	 * @return the commit id
	 */
	public String getLastLocalCommitId() {
		return lastCommitId;
	}

	/**
	 * Reset the local repository to the given commit id
	 *
	 * @param commitId
	 * @throws Exception
	 */
	public void resetTo(String commitId) throws Exception {
		fGit.reset().setMode(ResetType.HARD).setRef(commitId).call();
	}

	/**
	 * Get a ChangeInfo object from a given changeID
	 *
	 * @param changeId
	 * @return a fully populated ChangeInfo
	 * @throws Exception
	 */
	public ChangeInfo getChange(String changeId) throws Exception {
		GetChangeCommand command = gerrit.getChange(changeId);
		command.addOption(ChangeOption.ALL_FILES);
		command.addOption(ChangeOption.CURRENT_REVISION);
		command.addOption(ChangeOption.CURRENT_COMMIT);
		command.addOption(ChangeOption.MESSAGES);
		command.addOption(ChangeOption.DOWNLOAD_COMMANDS);

		ChangeInfo res = null;
		try {
			res = command.call();
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}
		return res;
	}

	public Gerrit getGerrit() {
		return gerrit;
	}
}
