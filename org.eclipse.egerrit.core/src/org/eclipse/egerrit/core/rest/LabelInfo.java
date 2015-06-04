/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

import java.util.List;
import java.util.Map;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#label-info" >LabelInfo</a>
 * entity contains information about a label on a change, always corresponding to the current patch set.
 * <p>
 * There are two options that control the contents of LabelInfo: LABELS and DETAILED_LABELS.
 * <ul>
 * <li>For a quick summary of the state of labels, use LABELS.
 * <li>For detailed information about labels, including exact numeric votes for all users and the allowed range of votes
 * for the current user, use DETAILED_LABELS.
 * </ul>
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class LabelInfo {

	// ------------------------------------------------------------------------
	// The common fields
	// ------------------------------------------------------------------------

	// Whether the label is optional. Optional means the label may be set, but
	// it’s neither necessary for submission nor does it block submission if
	// set.
	private boolean optional = false;

	// ------------------------------------------------------------------------
	// Fields set by LABELS
	// ------------------------------------------------------------------------

	// One user who approved this label on the change (voted the maximum value).
	private AccountInfo approved;

	// One user who rejected this label on the change (voted the minimum value).
	private AccountInfo rejected;

	// One user who recommended this label on the change (voted positively,
	// but not the maximum value).
	private AccountInfo recommended;

	// One user who disliked this label on the change (voted negatively,
	// but not the minimum value).
	private AccountInfo disliked;

	// Whether the label blocks a submit operation.
	private boolean blocking = false;

	// The voting value of the user who recommended/disliked this label on the
	// change if it is not "+1" or "-1".
	private String value;

	// The default voting value for the label.
	private String default_value;

	// ------------------------------------------------------------------------
	// Fields set by DETAILED_LABELS
	// ------------------------------------------------------------------------

	// The list of all approvals for this label.
	private List<ApprovalInfo> all;

	// A map of all values that are allowed for this label.
	private Map<String, String> values;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return Whether the label is optional.
	 */
	public boolean isOptional() {
		return optional;
	}

	/**
	 * @return One user who approved this label on the change. May be null.
	 */
	public AccountInfo getApproved() {
		return approved;
	}

	/**
	 * @return One user who rejected this label on the change. May be null.
	 */
	public AccountInfo getRejected() {
		return rejected;
	}

	/**
	 * @return One user who recommended this label on the change. May be null.
	 */
	public AccountInfo getRecommended() {
		return recommended;
	}

	/**
	 * @return One user who disliked this label on the change. May be null.
	 */
	public AccountInfo getDisliked() {
		return disliked;
	}

	/**
	 * @return Whether the label blocks a submit operation.
	 */
	public boolean isBlocking() {
		return blocking;
	}

	/**
	 * @return The voting value of the user who recommended/disliked this label. May be null.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return The default voting value for the label. May be null.
	 */
	public String getDefaultValue() {
		return default_value;
	}

	/**
	 * @return The default voting value for the label. May be null.
	 */
	public List<ApprovalInfo> getAll() {
		return all;
	}

	/**
	 * @return A map of each voting value to its description for this label. May be null.
	 */
	public Map<String, String> getValues() {
		return values;
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
		result = prime * result + ((all == null) ? 0 : all.hashCode());
		result = prime * result + ((approved == null) ? 0 : approved.hashCode());
		result = prime * result + (blocking ? 1231 : 1237);
		result = prime * result + ((default_value == null) ? 0 : default_value.hashCode());
		result = prime * result + ((disliked == null) ? 0 : disliked.hashCode());
		result = prime * result + (optional ? 1231 : 1237);
		result = prime * result + ((recommended == null) ? 0 : recommended.hashCode());
		result = prime * result + ((rejected == null) ? 0 : rejected.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabelInfo other = (LabelInfo) obj;
		if (all == null) {
			if (other.all != null)
				return false;
		} else if (!all.equals(other.all))
			return false;
		if (approved == null) {
			if (other.approved != null)
				return false;
		} else if (!approved.equals(other.approved))
			return false;
		if (blocking != other.blocking)
			return false;
		if (default_value == null) {
			if (other.default_value != null)
				return false;
		} else if (!default_value.equals(other.default_value))
			return false;
		if (disliked == null) {
			if (other.disliked != null)
				return false;
		} else if (!disliked.equals(other.disliked))
			return false;
		if (optional != other.optional)
			return false;
		if (recommended == null) {
			if (other.recommended != null)
				return false;
		} else if (!recommended.equals(other.recommended))
			return false;
		if (rejected == null) {
			if (other.rejected != null)
				return false;
		} else if (!rejected.equals(other.rejected))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "LabelInfo [optional=" + optional + ", approved=" + approved + ", rejected=" + rejected
				+ ", recommended=" + recommended + ", disliked=" + disliked + ", blocking=" + blocking + ", value="
				+ value + ", default_value=" + default_value + ", all=" + all + ", values=" + values + "]";
	}

}
