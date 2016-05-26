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

package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.egerrit.internal.core.command.GetCommitMsgCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommitInfo;

public class CommitMsgCompareItem extends PatchSetCompareItem {

	@Override
	protected byte[] loadFileContent() {
		GetCommitMsgCommand msg = gerrit.getCommitMsg(getChangeId(), fileInfo.getRevision().getId());
		CommitInfo commitInfo;
		try {
			commitInfo = msg.call();
		} catch (EGerritException e) {
			return new byte[0];
		}
		return commitInfo.getMessage().getBytes();
	}
}
