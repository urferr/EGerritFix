/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.completion;

import org.eclipse.jface.fieldassist.ContentProposal;

/**
 * This class provides a direct constructor to create a ContentProvider with a content and a label. This allows to show
 * one string to the user for selection, and once chosen, insert a different string. For example, the label can be:
 * "org.eclipse.cdt (C/C++ project)" which, once selected, will be used as "org.eclipse.cdt"
 */
class SearchContentProposal extends ContentProposal {
	SearchContentProposal(String content, String label) {
		// Add a space to the query if it is a complete operation (e.g. "status:open") so the user can keep on typing.
		super(content.endsWith(":") ? content : content + ' ', label, null); //$NON-NLS-1$
	}
}