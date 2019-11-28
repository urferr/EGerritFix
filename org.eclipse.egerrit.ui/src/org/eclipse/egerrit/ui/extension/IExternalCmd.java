/*******************************************************************************
 * Copyright (c) 2019 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.extension;

import java.util.List;

/**
 * Interface returning some of the current review information to be used in an external plug-in
 */
public interface IExternalCmd {

	class ExternalInfo {
		public static String workspacePath;

		public static String filePath;

		public static String project;

		public static String serverPath;

		public static String serverName;

		public static String patchSet;

		public static String reviewCommit;

		public static List<String> listCommitFiles;

	};

	// Method to be implemented from the Extension point plugin
	public void cmd1();

	// Implementing default methods
	public default String getWorkspacePath() {
		return ExternalInfo.workspacePath;
	};

	public default String getFilePath() {
		return ExternalInfo.filePath;
	};

	public default String getProject() {
		return ExternalInfo.project;
	};

	public default String getServerPath() {
		return ExternalInfo.serverPath;
	};

	public default String getserverName() {
		return ExternalInfo.serverName;
	};

	public default String getPatchSet() {
		return ExternalInfo.patchSet;
	};

	public default String getReviewCommit() {
		return ExternalInfo.reviewCommit;
	};

	public default List<String> getListCommitFiles() {
		return ExternalInfo.listCommitFiles;
	};
}
