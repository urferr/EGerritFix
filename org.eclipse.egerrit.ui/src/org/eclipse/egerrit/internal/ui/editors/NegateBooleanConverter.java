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

import org.eclipse.core.databinding.UpdateValueStrategy;

/**
 * Return the boolean value opposite to the one provided
 */
class NegateBooleanConverter extends UpdateValueStrategy {
	@Override
	public Object convert(Object value) {
		return !((Boolean) value).booleanValue();
	}
}
