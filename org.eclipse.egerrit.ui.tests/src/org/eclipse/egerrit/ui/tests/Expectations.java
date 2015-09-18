/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.ui.tests;

import org.eclipse.jface.text.Position;

class Expectations {
	Position[] positions;

	int numberOfComments = -1;

	boolean noModifications = false;

	int line = -1;

	String contentAtLine;

	static public Expectations expectations(int numberOfComments, Position... positions) {
		Expectations expectations = new Expectations();
		expectations.numberOfComments = numberOfComments;
		expectations.positions = positions;
		return expectations;
	}

	static public Expectations noModifications() {
		Expectations expectations = new Expectations();
		expectations.noModifications = true;
		return expectations;
	}

	static public Expectations expectations(int line, String contentAtLine, Position... positions) {
		Expectations expectations = new Expectations();
		expectations.contentAtLine = contentAtLine;
		expectations.line = line;
		expectations.positions = positions;
		return expectations;
	}
}