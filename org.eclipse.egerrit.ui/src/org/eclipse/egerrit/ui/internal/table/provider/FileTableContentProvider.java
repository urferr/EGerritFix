/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the file table view content provider.
 ******************************************************************************/
package org.eclipse.egerrit.ui.internal.table.provider;

import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class implements the content provider of the file table view.
 *
 * @since 1.0
 */

/*
 * The content provider class is responsible for providing objects to the
 * view. It can wrap existing objects in adapters or simply return objects
 * as-is. These objects may be sensitive to the current input of the view,
 * or ignore it and always show the same content.
 */

public class FileTableContentProvider implements IStructuredContentProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer aViewer, Object aOldInput, Object aNewInput) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object aInputElement) {
		//GerritPlugin.Ftracer.traceInfo("getElements() content provider Object: " + aInputElement);
		if (aInputElement instanceof FileInfo[]) {
			FileInfo[] itemList = (FileInfo[]) aInputElement;
			return itemList;
		}
		//This null will generate an error if we reach this point, may be we should log an error and
		// initiate an empty structure to return
		//return null;
		return new FileInfo[] {};
	}

}
