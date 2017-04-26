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

import org.eclipse.compare.ICompareInputLabelProvider;
import org.eclipse.compare.ITypedElement;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * Provides the label in the header of each text editor
 */
class GerritCompareInputLabelProvider implements ICompareInputLabelProvider {

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// ignore
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// ignore
		return false;
	}

	@Override
	public void dispose() {
		// ignore
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// ignore
	}

	@Override
	public String getText(Object element) {
		// ignore
		return null;
	}

	@Override
	public Image getImage(Object element) {
		// ignore
		return null;
	}

	@Override
	public String getRightLabel(Object input) {
		if (!(input instanceof GerritDiffNode)) {
			return null;
		}
		return getLabelFromTypedElement(((GerritDiffNode) input).getRight());
	}

	@Override
	public Image getRightImage(Object input) {
		// ignore
		return null;
	}

	@Override
	public String getLeftLabel(Object input) {
		if (!(input instanceof GerritDiffNode)) {
			return null;
		}
		return getLabelFromTypedElement(((GerritDiffNode) input).getLeft());
	}

	private String getLabelFromTypedElement(ITypedElement toPrint) {
		if (toPrint == null) {
			return ""; //$NON-NLS-1$
		}
		if (toPrint instanceof CommentableCompareItem) {
			return ((CommentableCompareItem) toPrint).getUserReadableName();
		}
		return "";
	}

	@Override
	public Image getLeftImage(Object input) {
		// ignore
		return null;
	}

	@Override
	public String getAncestorLabel(Object input) {
		// ignore
		return null;
	}

	@Override
	public Image getAncestorImage(Object input) {
		// ignore
		return null;
	}

}
