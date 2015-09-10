/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

/**
 * Data model object for <a
 * href="https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#rebase-input">RebaseInput</a>
 *
 * @since 1.0
 */
public class RebaseInput {

	// The new parent revision. This can be a ref or a SHA1 to a concrete patchset.
	// Alternatively, a change number can be specified, in which case the current
	// patch set is inferred.
	// Empty string is used for rebasing directly on top of the target branch, which
	// effectively breaks dependency towards a parent change.
	private String base;

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base
	 *            the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * generate hashcode based on the method's members
	 *
	 * @return the calculated value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		return result;
	}

	/**
	 * evaluate object equality
	 *
	 * @return true if equal false otherwise
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
		RebaseInput other = (RebaseInput) obj;
		if (base == null) {
			if (other.base != null) {
				return false;
			}
		} else if (!base.equals(other.base)) {
			return false;
		}
		return true;
	}

	/**
	 * generate string based on the method's members
	 *
	 * @return the calculated value
	 */
	@Override
	public String toString() {
		return "RebaseInput [base=" + base + "]";
	}

}
