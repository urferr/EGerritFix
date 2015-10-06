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

package org.eclipse.egerrit.core.rest;

/**
 * The structure is a sub-structure of the CommitInfo structure.
 *
 * @since 1.0
 */
public class CommentRange {
	protected int startLine;

	protected int startCharacter;

	protected int endLine;

	protected int endCharacter;

	public CommentRange() {
	}

	/**
	 * @param sl
	 * @param sc
	 * @param el
	 * @param ec
	 */
	public CommentRange(int sl, int sc, int el, int ec) {
		startLine = sl;
		startCharacter = sc;
		endLine = el;
		endCharacter = ec;
	}

	/**
	 * @return int
	 */
	public int getStartLine() {
		return startLine;
	}

	/**
	 * @return int
	 */
	public int getStartCharacter() {
		return startCharacter;
	}

	/**
	 * @return int
	 */
	public int getEndLine() {
		return endLine;
	}

	/**
	 * @return int
	 */
	public int getEndCharacter() {
		return endCharacter;
	}

	/**
	 * @param int
	 *            sl
	 */
	public void setStartLine(int sl) {
		startLine = sl;
	}

	/**
	 * @param int
	 *            sc
	 */
	public void setStartCharacter(int sc) {
		startCharacter = sc;
	}

	/**
	 * @param int
	 *            el
	 */
	public void setEndLine(int el) {
		endLine = el;
	}

	/**
	 * @param int
	 *            ec
	 */
	public void setEndCharacter(int ec) {
		endCharacter = ec;
	}

	/**
	 * @param Object
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CommentRange) {
			CommentRange other = (CommentRange) obj;
			return startLine == other.startLine && startCharacter == other.startCharacter && endLine == other.endLine
					&& endCharacter == other.endCharacter;
		}
		return false;
	}

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		int h = startLine;
		h = h * 31 + startCharacter;
		h = h * 31 + endLine;
		h = h * 31 + endCharacter;
		return h;
	}

	/**
	 * @return String
	 */
	@Override
	public String toString() {
		return "Range[startLine=" + startLine + ", startCharacter=" + startCharacter + ", endLine=" + endLine
				+ ", endCharacter=" + endCharacter + "]";
	}
}
