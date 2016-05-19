/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.model;

import org.eclipse.egerrit.ui.internal.utils.Messages;

/**
 * This class implement the different values Gerrit return for the Submit Type. The Submit type used for this change,
 * can be MERGE_IF_NECESSARY, FAST_FORWARD_ONLY, REBASE_IF_NECESSARY, MERGE_ALWAYS or CHERRY_PICK. It allows to
 * Internationalize the display values of it.
 *
 * @since 1.0
 */
public enum SubmitType {
	FAST_FORWARD_ONLY(Messages.FastForwardOnly), //
	REBASE_IF_NECESSARY(Messages.RebaseIfNecessary), //
	MERGE_IF_NECESSARY(Messages.MergeIfNecessary), //
	MERGE_ALWAYS(Messages.MergeAlways), //
	CHERRY_PICK(Messages.CherryPick);

	/** The string value of the enum element */
	private String fOption;

	SubmitType(String option) {
		fOption = option;
	}

	public String getValue() {
		return fOption;
	}

	public static String getEnumName(String st) {
		if (st.equals(SubmitType.FAST_FORWARD_ONLY.name())) {
			return Messages.FastForwardOnly;
		} else if (st.equals(SubmitType.MERGE_IF_NECESSARY.name())) {
			return Messages.MergeIfNecessary;
		} else if (st.equals(SubmitType.REBASE_IF_NECESSARY.name())) {
			return Messages.RebaseIfNecessary;
		} else if (st.equals(SubmitType.MERGE_ALWAYS.name())) {
			return Messages.MergeAlways;
		} else if (st.equals(SubmitType.CHERRY_PICK.name())) {
			return Messages.CherryPick;
		} else {
			return ""; //$NON-NLS-1$
		}
	}
}
