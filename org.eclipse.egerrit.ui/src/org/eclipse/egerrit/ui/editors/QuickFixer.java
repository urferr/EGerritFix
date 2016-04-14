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
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * This class is the entry point for the markers customization/generation
 *
 * @since 1.0
 */
public class QuickFixer implements IMarkerResolutionGenerator {
	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			Object message = marker.getAttribute("message"); //$NON-NLS-1$
			int messageWithOutUserDate = (new String(message.toString())).indexOf(' ',
					(new String(message.toString())).indexOf(' ') + 1);
			boolean isDraft = (boolean) marker.getAttribute("isDraft"); //$NON-NLS-1$
			String truncatedMsg = new String(message.toString());
			if (messageWithOutUserDate > 0) {
				int length = Math.min(20, message.toString().substring(messageWithOutUserDate).length());
				truncatedMsg = message.toString().substring(messageWithOutUserDate, messageWithOutUserDate + length)
						+ " ..."; //$NON-NLS-1$
			}
			if (isDraft) {
				return new IMarkerResolution[] { new QuickFixReplyToComment("Reply to comment: " + truncatedMsg),
						new QuickFixDeleteDraftComment("Delete draft comment: " + truncatedMsg) };
			} else {
				return new IMarkerResolution[] { new QuickFixReplyToComment("Reply to comment: " + truncatedMsg),
						new QuickFixReplyDoneToComment("Reply done to: " + truncatedMsg) };
			}

		} catch (CoreException e) {
			return new IMarkerResolution[0];
		}
	}
}
