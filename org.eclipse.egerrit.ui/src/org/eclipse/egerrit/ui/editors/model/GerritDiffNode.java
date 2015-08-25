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

package org.eclipse.egerrit.ui.editors.model;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;

/**
 * GerritDiffNode is used as input to the compare editor.
 * <p>
 * It is necessary in order to be able to react to saves (e.g. reload the editor)
 *
 */
public class GerritDiffNode extends DiffNode {

	public GerritDiffNode(IDiffContainer parent, int kind, ITypedElement ancestor, ITypedElement left,
			ITypedElement right) {
		super(parent, kind, ancestor, left, right);
	}

	@Override
	public void fireChange() {
		super.fireChange();
	}

}
