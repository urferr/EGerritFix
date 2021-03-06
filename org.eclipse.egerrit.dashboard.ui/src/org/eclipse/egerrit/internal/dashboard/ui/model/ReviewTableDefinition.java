/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the table view information
 *   Marc-Andre Laperle - Add Status to dashboard
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.model;

import org.eclipse.swt.SWT;

/**
 * This class implements the review table view information.
 *
 * @since 1.0
 */
// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------
// Definition of the review table list :name, width of the column, Resizeable,
// Moveable
public enum ReviewTableDefinition {
	STARRED("", 20, false, true, SWT.LEFT), //$NON-NLS-1$
	ID(Messages.ReviewTableDefinition_id, 60, true, true, SWT.LEFT), //
	SUBJECT(Messages.ReviewTableDefinition_subject, 180, true, true, SWT.LEFT), //
	STATUS(Messages.ReviewTableDefinition_status, 100, true, true, SWT.LEFT), //
	OWNER(Messages.ReviewTableDefinition_owner, 140, true, true, SWT.LEFT), //
	PROJECT(Messages.ReviewTableDefinition_project, 170, true, true, SWT.LEFT), //
	BRANCH(Messages.ReviewTableDefinition_branch, 90, true, true, SWT.LEFT), //
	UPDATED(Messages.ReviewTableDefinition_updated, 140, true, true, SWT.LEFT);

	private final String fHeader;

	private final int fwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private ReviewTableDefinition(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
		fHeader = aName;
		fwidth = aWidth;
		fResize = aResize;
		fMoveable = aMove;
		fAlignment = align;
	}

	public String getName() {
		return fHeader;
	}

	public int getWidth() {
		return fwidth;
	}

	public boolean getResize() {
		return fResize;
	}

	public boolean getMoveable() {
		return fMoveable;
	}

	public int getAlignment() {
		return fAlignment;
	}

	/**
	 * Provide an index array width the default order definition
	 *
	 * @return int[]
	 */
	public static int[] getDefaultOrder() {
		int size = ReviewTableDefinition.values().length;
		int[] order = new int[size];
		for (int index = 0; index < size; index++) {
			order[index] = index;
		}
		return order;
	}
}
