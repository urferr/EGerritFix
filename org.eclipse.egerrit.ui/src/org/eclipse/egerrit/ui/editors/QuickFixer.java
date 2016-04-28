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

package org.eclipse.egerrit.ui.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

/**
 * This class is the entry point for the markers customization/generation
 *
 * @since 1.0
 */
public class QuickFixer implements IMarkerResolutionGenerator2 {
	//Draft -> modify or delete
	//Comment -> reply, reply done (if it is not done),
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			boolean isDraft = (boolean) marker.getAttribute(EGerritCommentMarkers.ATTR_IS_DRAFT);
			CommentInfo element = (CommentInfo) marker.getAttribute(EGerritCommentMarkers.ATTR_COMMENT_INFO);
			String truncatedMsg = UIUtils.formatMessageForQuickFix(element);
			String fullMessage = UIUtils.formatMessageForMarkerView(element);
			if (isDraft) {
				return new IMarkerResolution[] { new QuickFixModifyDraft("Modify draft: " + truncatedMsg, fullMessage),
						new QuickFixDeleteDraftComment("Delete draft: " + truncatedMsg, fullMessage) };
			} else {
				if (element.getMessage().equalsIgnoreCase("done")) {
					return new IMarkerResolution[] {
							new QuickFixReplyToComment("Reply to: " + truncatedMsg, fullMessage) };
				}
				return new IMarkerResolution[] { new QuickFixReplyToComment("Reply to: " + truncatedMsg, fullMessage),
						new QuickFixReplyDoneToComment("Reply done to: " + truncatedMsg, fullMessage) };
			}

		} catch (CoreException e) {
			return new IMarkerResolution[0];
		}
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return true;
	}
}
