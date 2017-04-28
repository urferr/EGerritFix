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

package org.eclipse.egerrit.internal.ui.table.filter;

import java.util.ArrayList;

import org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class AuthorKindFilter extends ViewerFilter {

	private boolean fIsMachine;

	//Enum for potential CI user
	private enum AuthorCiName {
		// 	Name
		HUDSON("Hudson"), //$NON-NLS-1$
		JENKINS("Jenkins"), // //$NON-NLS-1$
		CI(" CI"); //$NON-NLS-1$

		private final String name;

		private AuthorCiName(String aName) {
			name = aName;
		}

		private String getName() {
			return name;
		}

		private static String[] getAllNames() {
			ArrayList<String> listName = new ArrayList<>();
			for (AuthorCiName st : AuthorCiName.values()) {
				listName.add(st.getName());
			}
			return listName.toArray(new String[] {});
		}
	}

	public AuthorKindFilter(boolean isMachine) {
		fIsMachine = isMachine;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		ChangeMessageInfoImpl row = (ChangeMessageInfoImpl) element;
		if (row != null && row.getAuthor() != null) {
			if (fIsMachine) {
				return hasCiAuthor(row.getAuthor().getName());
			} else {
				return !hasCiAuthor(row.getAuthor().getName());
			}
		} else {
			return true;
		}
	}

	private boolean hasCiAuthor(String authorName) {
		boolean included = false;
		String[] testName = AuthorCiName.getAllNames();
		for (String st : testName) {
			if (authorName.toLowerCase().contains(st.toLowerCase())) {
				included = true;
			}
		}
		return included;
	}
}
