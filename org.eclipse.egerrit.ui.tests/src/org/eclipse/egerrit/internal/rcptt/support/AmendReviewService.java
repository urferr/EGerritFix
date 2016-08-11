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
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription;
import org.eclipse.egerrit.ui.tests.EGerritUITestsPlugin;
import org.eclipse.rcptt.ecl.core.Command;
import org.eclipse.rcptt.ecl.runtime.ICommandService;
import org.eclipse.rcptt.ecl.runtime.IProcess;

/**
 * This class implements the amend-review ECL command.
 */
public class AmendReviewService implements ICommandService {

	public AmendReviewService() {
		// ignore
	}

	@Override
	public IStatus service(Command command, IProcess context) throws InterruptedException, CoreException {
		AmendReview amendCmd = (AmendReview) command;
		ReviewDescription cmdArgument = (ReviewDescription) amendCmd.getReview();
		if (cmdArgument == null) {
			return new Status(IStatus.ERROR, EGerritUITestsPlugin.PLUGIN_ID, "Parameter review is missing"); //$NON-NLS-1$
		}

		boolean draftRequested = amendCmd.isIsDraft();
		try {
			context.getOutput()
					.write(ReviewFactory.amendReview(cmdArgument.getLocalClone(), cmdArgument.getGerritServerURL(),
							cmdArgument.getProjectName(), cmdArgument.getLastChangeId(), draftRequested));
		} catch (Exception e) {
			return new Status(IStatus.ERROR, EGerritUITestsPlugin.PLUGIN_ID,
					"An error occurred while amending the review " + cmdArgument.getLastChangeId(), e); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}

}
