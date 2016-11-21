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

package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.compare.structuremergeviewer.Differencer;

public class GerritDifferences extends Differencer {
	/**
	 * Difference constant (value 0) indicating no difference.
	 */
	public static final int NO_CHANGE = Differencer.NO_CHANGE;

	/**
	 * Difference constant (value 1) indicating one side was added.
	 */
	public static final int ADDITION = 16 + Differencer.ADDITION;

	/**
	 * Difference constant (value 2) indicating one side was removed.
	 */
	public static final int DELETION = 32 + Differencer.DELETION;

	/**
	 * Difference constant (value 3) indicating side changed.
	 */
	public static final int CHANGE = 64 + Differencer.CHANGE;

	/**
	 * Difference constant (value 4) indicating renaming.
	 */
	public static final int RENAMED = 128;

	/**
	 * Difference constant (value 5) indicating renaming.
	 */
	public static final int COPIED = 256;

	/**
	 * Difference constant (value 6) indicating renaming.
	 */
	public static final int REWRITTEN = 512;
}
