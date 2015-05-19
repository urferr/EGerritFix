/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;

/**
 * @since 1.0
 */
public class CompareItem implements IStreamContentAccessor, ITypedElement, IModificationDate {
	private final String contents, name;

private final long time;

CompareItem(String name, String contents, long time) {
	this.name = name;
	this.contents = contents;
	this.time = time;
}

public InputStream getContents() throws CoreException {
	return new ByteArrayInputStream(contents.getBytes());
}

public org.eclipse.swt.graphics.Image getImage() {
	return null;
}

public long getModificationDate() {
	return time;
}

public String getName() {
	return name;
}

public String getString() {
	return contents;
}

public String getType() {
	return ITypedElement.TEXT_TYPE;
}
}
