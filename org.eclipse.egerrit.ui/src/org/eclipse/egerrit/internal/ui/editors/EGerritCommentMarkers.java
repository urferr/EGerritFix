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

package org.eclipse.egerrit.internal.ui.editors;

public class EGerritCommentMarkers {
	public static final String COMMENT_MARKER_ID = "org.eclipse.egerrit.ui.commentMarker"; //$NON-NLS-1$

	public static final String ATTR_COMMENT_INFO = "commentInfo"; //$NON-NLS-1$

	public static final String ATTR_FILE_INFO = "fileInfo"; //$NON-NLS-1$

	public static final String ATTR_GERRIT_CLIENT = "gerritClient"; //$NON-NLS-1$

	public static final String ATTR_IS_DRAFT = "isDraft"; //$NON-NLS-1$

	public static final String ATTR_MESSAGE = "message"; //$NON-NLS-1$

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private EGerritCommentMarkers() {
	}
}
