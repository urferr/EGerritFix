/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.table.model;

import org.eclipse.egerrit.internal.ui.utils.Messages;

/**
 * The enum value for the branch match state
 *
 * @since 1.0
 */
public enum BranchMatch {

	// ------------------------------------------------------------------------
	// Branch Match
	// ------------------------------------------------------------------------

	PERFECT_MATCH(Messages.Branch_perfect_match), //Commit id is matching
	CHANGE_ID_MATCH(Messages.Branch_change_Id), //Change Id is matching
	BRANCH_NAME_MATCH(Messages.Branch_branch_name);//Default branch name is matching
	// ------------------------------------------------------------------------
	// Enum -> string value handling
	// ------------------------------------------------------------------------

	/** The string value of the enum element */
	private String fState;

	/**
	 * The initializer
	 *
	 * @param state
	 *            the string value of the enum element
	 */
	BranchMatch(String state) {
		fState = state;
	}

	/**
	 * @return the corresponding string
	 */
	public String getValue() {
		return fState;
	}
}
