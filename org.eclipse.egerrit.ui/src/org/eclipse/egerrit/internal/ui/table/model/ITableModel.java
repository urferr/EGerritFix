/*******************************************************************************
 * Copyright (c) 2015 Ericsson.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.table.model;

/**
 * Interface definition used by the reviw tables.
 * 
 * @since 1.0
 */
public interface ITableModel {

	public abstract String getName();

	public abstract int getWidth();

	public abstract boolean getResize();

	public abstract boolean getMoveable();

	public abstract int getAlignment();

	public abstract String[] getColumnName();

	public abstract int getMinimumWidth();

}