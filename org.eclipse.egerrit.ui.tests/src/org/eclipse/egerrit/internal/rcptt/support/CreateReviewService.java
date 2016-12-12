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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview;
import org.eclipse.egerrit.ui.tests.EGerritUITestsPlugin;
import org.eclipse.rcptt.ecl.core.Command;
import org.eclipse.rcptt.ecl.runtime.ICommandService;
import org.eclipse.rcptt.ecl.runtime.IProcess;

/**
 * This class implements the create-review ECL command. For more details on how to implement ECL commands, please see
 * https://www.eclipse.org/rcptt/documentation/userguide/ecl/new-command-guide/
 */
public class CreateReviewService implements ICommandService {

	public CreateReviewService() {
		// ignore
	}

	@Override
	public IStatus service(Command cmd, IProcess result) throws InterruptedException, CoreException {
		String httpServer = ((CreateReview) cmd).getServer();
		CreateReview createCmd = (CreateReview) cmd;

		if (httpServer == null) {
			return new Status(IStatus.ERROR, "org.eclipse.egerrit.ui.tests", //$NON-NLS-1$
					"HttpServer must be specified in create-review command"); //$NON-NLS-1$
		}
		String project = ((CreateReview) cmd).getProject();
		if (project == null) {
			project = "egerrit/RCPTTtest"; //$NON-NLS-1$
		}

		String filename = ((CreateReview) cmd).getFilename();

		if (filename == null) {
			filename = "src/EGerritTestReviewFile.java"; //$NON-NLS-1$
		}

		boolean isDraft = createCmd.isIsDraft();
		try {
			result.getOutput().write(ReviewFactory.createReview(httpServer, project, isDraft, filename));
		} catch (Exception e) {
			return new Status(IStatus.ERROR, EGerritUITestsPlugin.PLUGIN_ID,
					"An error occurred while creating the review", e); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}

}
