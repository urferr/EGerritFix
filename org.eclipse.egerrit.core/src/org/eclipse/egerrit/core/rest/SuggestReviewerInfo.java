/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marc Khouzam - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

import org.eclipse.egerrit.core.model.PropertyChangeModel;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#suggested-reviewer-info">
 * SuggestedReviewerInfo</a> entity contains information about a reviewer that can be added to a change (an account or a
 * group).
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 */
public class SuggestReviewerInfo extends PropertyChangeModel {
	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	private AccountInfo account;

	private GroupBaseInfo group;

	public AccountInfo getAccount() {
		return account;
	}

	public GroupBaseInfo getGroup() {
		return group;
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SuggestReviewerInfo other = (SuggestReviewerInfo) obj;
		if (account == null) {
			if (other.account != null) {
				return false;
			}
		} else if (!account.equals(other.account)) {
			return false;
		}
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		return true;
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "SuggestReviewerInfo [" + account + group + "]";
	}
}
