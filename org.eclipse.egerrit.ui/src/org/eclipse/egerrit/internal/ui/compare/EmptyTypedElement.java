/*******************************************************************************
 * Copyright (c) 2007, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright (c) 2008, Roger C. Soares <rogersoares@intelinet.com.br>
 * Copyright (c) 2013, Robin Stocker <robin@nibor.org>
 * Copyright (c) 2016, Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.compare.ITypedElement;
import org.eclipse.swt.graphics.Image;

/**
 * ITypedElement without content. May be used to indicate that a file is not available. Copied from
 * GitCompareFileRevisionEditorInput
 */
class EmptyTypedElement implements ITypedElement {

	private String name;

	/**
	 * @param name
	 *            the name used for display
	 */
	EmptyTypedElement(String name) {
		this.name = name;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return ITypedElement.UNKNOWN_TYPE;
	}

}