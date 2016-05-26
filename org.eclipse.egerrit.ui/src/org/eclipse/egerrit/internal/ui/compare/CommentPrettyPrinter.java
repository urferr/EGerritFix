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

import org.eclipse.egerrit.internal.core.utils.Utils;
import org.eclipse.egerrit.internal.model.CommentInfo;

//Helper class to pretty print a comment
public class CommentPrettyPrinter {

	static String printComment(CommentInfo comment) {
		return CommentPrettyPrinter.printName(comment) + '\t' + comment.getMessage() + '\t' + printDate(comment);
	}

	public static String printDate(CommentInfo comment) {
		return Utils.prettyPrintDate(comment.getUpdated());
	}

	static String printName(CommentInfo comment) {
		if (comment.getAuthor() == null) {
			return "Draft"; //$NON-NLS-1$
		}
		return comment.getAuthor().getName();
	}
}
