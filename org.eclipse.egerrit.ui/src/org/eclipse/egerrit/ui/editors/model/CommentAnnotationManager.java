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

package org.eclipse.egerrit.ui.editors.model;

import java.util.Iterator;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;

/**
 * This annotation manager makes sure that when an annotation is added at the same offset than an existing one, the
 * existing annotation is not grown like it would normally be, but instead it is shifted.
 * 
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
			super.addAnnotation(an, new Position(existingPosition.getOffset() + position.getLength() + 1,
					existingPosition.length - (position.length + 1)));
			super.addAnnotation(annotation, position);
		}
	}
}
