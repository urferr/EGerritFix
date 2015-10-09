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

class InputData {
	String[] keys;

	int line, offset;

	String document;

	String[] comments;

	//Represent the input to the tests
	static public InputData inputData(String document, String[] comments, int line, int offset, String... keys) {
		InputData data = new InputData();
		data.document = document;
		data.comments = comments;
		data.line = line;
		data.offset = offset;
		data.keys = keys;
		return data;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("insert from ").append(line).append(' ').append(offset).append(" keys:").append('>');
		for (String string : keys) {
			result.append(string.replace("\n", "<enter>")).append(' ');
		}
		result.append('<');
		return result.toString();
	}

}