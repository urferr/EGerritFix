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

package org.eclipse.egerrit.ui.editors.model;

import org.eclipse.egerrit.internal.model.FileInfo;

public class GerritDiffNodeTwoRevisions extends GerritDiffNode {

	public GerritDiffNodeTwoRevisions(int kind) {
		super(kind);
	}

	private FileInfo otherFileInfo;

	public void setOtherFileInfo(FileInfo file) {
		otherFileInfo = file;
	}

	public FileInfo getOtherFileInfo() {
		return otherFileInfo;
	}
}
