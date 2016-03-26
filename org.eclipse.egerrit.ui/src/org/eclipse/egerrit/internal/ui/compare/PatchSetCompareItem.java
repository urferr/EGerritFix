/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.compare;

import java.util.stream.Collectors;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.command.GetContentCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * The compare item is the input to the compare editor, and it ALSO is the document that is shown in the compare editor.
 * Finally it is responsible for pushing the new comments to the server
 */
public class PatchSetCompareItem extends CommentableCompareItem
		implements ITypedElement, IModificationDate, IEditableContent, IStreamContentAccessor {

	private static final String REVISION = "REVISION"; //$NON-NLS-1$

	public PatchSetCompareItem() {
		super(REVISION);
	}

	@Override
	public String getName() {
		return "Patch Set " + fileInfo.getRevision().get_number() + ": " //$NON-NLS-1$ //$NON-NLS-2$
				+ GerritCompareHelper.extractFilename(fileInfo.getPath());
	}

	@Override
	protected byte[] loadFileContent() {
		String fileContent = null;
		// Create query
		GetContentCommand command = gerrit.getContent(getChangeId(), fileInfo.getRevision().getId(),
				fileInfo.getPath());

		if (!"D".equals(fileInfo.getStatus())) { //$NON-NLS-1$
			try {
				fileContent = command.call();
				if (fileContent == null) {
					fileContent = ""; //$NON-NLS-1$
				}
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + e.getMessage());
			}
		} else {
			fileContent = ""; //$NON-NLS-1$
		}
		return org.apache.commons.codec.binary.Base64.decodeBase64(fileContent);
	}

	@Override
	protected EList<CommentInfo> filterComments(EList<CommentInfo> eList) {
		return eList.stream()
				.filter(comment -> comment.getSide() == null || REVISION.equals(comment.getSide()))
				.collect(Collectors.toCollection(BasicEList::new));
	}
}
