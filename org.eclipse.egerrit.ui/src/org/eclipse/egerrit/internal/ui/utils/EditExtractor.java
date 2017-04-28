/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.internal.ui.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.patch.FileHeader;

/**
 * Extracts the edit lists contained in a diff.
 * <p/>
 * A diff formatter is usually responsible for producing an output from a set of differences. In our case, since we are
 * only interested in knowing the structural changes that happened to the file, we create our own formatter to only
 * extract that information.
 */
class EditExtractor extends DiffFormatter {
	private EditList fileEdits = null;

	EditExtractor(OutputStream out) {
		super(out);
	}

	@Override
	public void format(final FileHeader head, final RawText a, final RawText b) throws IOException {
		//We short-circuit all work because the only thing that interest us is the list of edits
		fileEdits = head.toEditList();
	}

	public EditList getEdits() {
		return fileEdits;
	}
}
