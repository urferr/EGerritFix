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

package org.eclipse.egerrit.ui.editors.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @since 1.0
 */
public class ChangeDetailEditorInput implements IEditorInput {

	public ChangeDetailEditorInput() {
	}

	@Override
	public Object getAdapter(Class adapter) {
		// ignore
		return null;
	}

	@Override
	public boolean exists() {
		// ignore
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// ignore
		return null;
	}

	@Override
	public String getName() {
		// ignore
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		// ignore
		return null;
	}

	@Override
	public String getToolTipText() {
		// ignore
		return null;
	}
}
