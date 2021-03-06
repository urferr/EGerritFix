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
package org.eclipse.egerrit.internal.model;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;

/**
 * A bunch of helpers to deal with the model objects to facilitate navigation or other common operations
 */
public class ModelHelpers {

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private ModelHelpers() {
	}

	/**
	 * Sort the list of comments
	 * @param comments
	 * @return
	 */
	public static EList<CommentInfo> sortComments(EList<CommentInfo> comments) {
		Collections.sort(comments, (CommentInfo o1, CommentInfo o2) -> {
			if (o1.getLine() == o2.getLine()) {
				return o1.getUpdated().compareTo(o2.getUpdated());
			}
			if (o1.getLine() < o2.getLine()) {
				return -1;
			}
			return 1;
		});
		return comments;
	}

	/**
	 * return the revision info associated to the comment
	 * @param comment
	 * @return
	 */
	public static RevisionInfo getRevision(CommentInfo comment) {
		return getFileInfo(comment).getRevision();
	}

	/**
	 * return the FileInfo
	 * @param comment
	 * @return
	 */
	public static FileInfo getFileInfo(CommentInfo comment) {
		return (FileInfo) comment.eContainer();
	}

	/**
	 * Find the highest revision number/
	 * @param revisions
	 * @return
	 */
	public static int getHighestRevisionNumber(Collection<RevisionInfo> revisions) {
		int match = 0;
		for (RevisionInfo rev : revisions) {
			if (rev.get_number() > match) {
				match = rev.get_number();
			}
		}
		return match;
	}
}
