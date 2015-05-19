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

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @since 1.0
 */
public class CompareInput extends CompareEditorInput {

	public CompareInput() {
		super(new CompareConfiguration());
	}

	public String left = "";

	public String right = "";

	public void setLeft(String left) {
		this.left = left;
	}

	public String getLeft() {
		return left;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getRight() {
		return right;
	}

	@Override
	protected Object prepareInput(IProgressMonitor pm) {
		CompareItem ancestor = new CompareItem("Common", "contents", 0);
		CompareItem left = new CompareItem("Left", getLeft(), 0);
		CompareItem right = new CompareItem("Right", getRight(), 0);
		return new DiffNode(null, Differencer.ADDITION, ancestor, left, right);
	}
}
