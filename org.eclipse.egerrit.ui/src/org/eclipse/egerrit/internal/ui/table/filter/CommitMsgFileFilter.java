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

package org.eclipse.egerrit.internal.ui.table.filter;

import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * This class implements the File COMMIT_MSG filter.
 *
 * @since 1.0
 */
public class CommitMsgFileFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		String file = ((StringToFileInfoImpl) element).getKey();
		if ("/COMMIT_MSG".equalsIgnoreCase(file)) { //$NON-NLS-1$
			return false;
		}
		return true;
	}
}
