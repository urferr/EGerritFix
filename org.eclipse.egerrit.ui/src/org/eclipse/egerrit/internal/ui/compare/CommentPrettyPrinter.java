/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.compare;

import java.text.SimpleDateFormat;

import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.internal.model.CommentInfo;

//Helper class to pretty print a comment
public class CommentPrettyPrinter {
	final static SimpleDateFormat formatTimeOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$

	static String printComment(CommentInfo comment) {
		return CommentPrettyPrinter.printName(comment) + '\t' + comment.getMessage() + '\t' + printDate(comment);
	}

	public static String printDate(CommentInfo comment) {
		return Utils.formatDate(comment.getUpdated(), CommentPrettyPrinter.formatTimeOut);
	}

	static String printName(CommentInfo comment) {
		if (comment.getAuthor() == null) {
			return "Draft"; //$NON-NLS-1$
		}
		return comment.getAuthor().getName();
	}
}
