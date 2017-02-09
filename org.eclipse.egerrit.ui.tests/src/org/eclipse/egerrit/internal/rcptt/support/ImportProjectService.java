/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.rcptt.support;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription;
import org.eclipse.egerrit.ui.tests.EGerritUITestsPlugin;
import org.eclipse.egit.core.internal.gerrit.GerritUtil;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.rcptt.ecl.core.Command;
import org.eclipse.rcptt.ecl.runtime.ICommandService;
import org.eclipse.rcptt.ecl.runtime.IProcess;

/**
 * Implementation of the import-project RCPTT command. Give a locally cloned repository, and a path to a project name,
 * this command will: import the project in the workspace; configure the git repo in the git repo view (this is done for
 * free); configure the gerrit push urls in the git repo
 */
public class ImportProjectService implements ICommandService {

	public ImportProjectService() {
		// ignore
	}

	@Override
	public IStatus service(Command cmd, IProcess result) throws InterruptedException, CoreException {
		ImportProject importCmd = (ImportProject) cmd;
		ReviewDescription cmdArgument = (ReviewDescription) importCmd.getReview();

		String projectName = importCmd.getProjectName();
		if (cmdArgument == null) {
			return new Status(IStatus.ERROR, EGerritUITestsPlugin.PLUGIN_ID, "Parameter review is missing"); //$NON-NLS-1$
		}

		if (projectName == null) {
			return new Status(IStatus.ERROR, EGerritUITestsPlugin.PLUGIN_ID, "Project name to import is missing"); //$NON-NLS-1$
		}

		importProject(cmdArgument.getLocalClone(), projectName);
		try {
			configureGerrit(cmdArgument.getLocalClone(), cmdArgument.getGerritServerURL());
		} catch (Exception e) {
			return new Status(IStatus.ERROR, EGerritUITestsPlugin.PLUGIN_ID,
					"An error occurred while configuring Gerrit connection in repo " + cmdArgument.getLocalClone(), e); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}

	//Import a project contained into the repo into the workspace
	private void importProject(String localRepo, String projectName) throws CoreException {
		IPath workDir = new Path(localRepo);//.removeLastSegments(1);
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceRoot root = workspace.getRoot();
		final IPath projectDir = workDir.append(projectName);
		final IProjectDescription projectDescription = workspace
				.loadProjectDescription(projectDir.append(IProjectDescription.DESCRIPTION_FILE_NAME));
		final IProject project = root.getProject(projectDescription.getName());
		if (project.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, EGerritUITestsPlugin.PLUGIN_ID,
					"The project " + projectName + " already exists.")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		project.create(projectDescription, new NullProgressMonitor());
		project.open(new NullProgressMonitor());
	}

	private void configureGerrit(String localRepoPath, String remoteName) throws Exception {
		Repository repository = new FileRepository(localRepoPath + "/.git");
		StoredConfig config = repository.getConfig();
		config.load();
		List<RemoteConfig> allRemoteConfigs = RemoteConfig.getAllRemoteConfigs(config);
		RemoteConfig remoteConfig = null;
		for (RemoteConfig rc : allRemoteConfigs) {
			if (rc.getName().equals("origin")) { //$NON-NLS-1$
				remoteConfig = rc;
				break;
			}
		}
		if (remoteConfig == null) {
			return;
		}
		GerritUtil.configurePushRefSpec(remoteConfig, "HEAD:refs/for/master");
		GerritUtil.configureFetchNotes(remoteConfig);
		remoteConfig.update(config);
		config.save();
	}
}