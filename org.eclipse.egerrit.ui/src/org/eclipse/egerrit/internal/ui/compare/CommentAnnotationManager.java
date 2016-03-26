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

package org.eclipse.egerrit.internal.ui.compare;

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;

/**
 * This annotation manager makes sure that when an annotation is added at the same offset than an existing one, the
 * existing annotation is not grown like it would normally be, but instead it is shifted.
 */
public class CommentAnnotationManager extends AnnotationModel {
	@Override
	public void addAnnotation(Annotation annotation, Position position) {
		Iterator<?> it = getAnnotationIterator(position.getOffset(), position.getLength(), true, true);
		if (!it.hasNext()) {
			super.addAnnotation(annotation, position);
			return;
		}
		Annotation an = (Annotation) it.next();
		Position existingPosition = getPosition(an);
		if (existingPosition.getOffset() == position.getOffset()) {
			removeAnnotation(an);
			super.addAnnotation(an,
					new Position(existingPosition.getOffset() + position.getLength() + getDefaultLineDelimiterSize(),
							existingPosition.length - (position.length + getDefaultLineDelimiterSize())));
			super.addAnnotation(annotation, position);
		}
	}

	private int getDefaultLineDelimiterSize() {
		if (fDocument instanceof IDocumentExtension4) {
			return ((IDocumentExtension4) fDocument).getDefaultLineDelimiter().length();
		}
		return getDefaultLineDelimiter().length();
	}

	//Copied from AbstractDocument#getDefaultLineDelimiter
	private String getDefaultLineDelimiter() {

		String lineDelimiter = null;

		try {
			lineDelimiter = fDocument.getLineDelimiter(0);
		} catch (BadLocationException x) {
		}

		if (lineDelimiter != null) {
			return lineDelimiter;
		}

		String sysLineDelimiter = System.getProperty("line.separator"); //$NON-NLS-1$
		String[] delimiters = fDocument.getLegalLineDelimiters();
		Assert.isTrue(delimiters.length > 0);
		for (String delimiter : delimiters) {
			if (delimiter.equals(sysLineDelimiter)) {
				lineDelimiter = sysLineDelimiter;
				break;
			}
		}

		if (lineDelimiter == null) {
			lineDelimiter = delimiters[0];
		}

		return lineDelimiter;

	}
}
