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

package org.eclipse.egerrit.internal.ui.editors;

import org.eclipse.ui.IMarkerResolution2;

/**
 * Base class for the quick fixes provided by egerrit
 */
abstract class EGerritQuickFix implements IMarkerResolution2 {
	private String label;

	private String description;

	EGerritQuickFix(String label, String completeMessage) {
		this.label = label;
		description = completeMessage.replace("\n", "<br/>"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
