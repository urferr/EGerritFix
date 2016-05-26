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

package org.eclipse.egerrit.internal.ui.tabs;

import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IObservable;

/**
 * A Helper class to automatically dispose the EMF Observable values.
 */
public class ObservableCollector {
	HashSet<IObservable> observables = new HashSet<>();

	public ObservableCollector(DataBindingContext context) {
		Iterator<Binding> it = context.getBindings().iterator();
		while (it.hasNext()) {
			Binding binding = it.next();
			observables.add(binding.getModel());
			observables.add(binding.getTarget());
		}
	}

	public void dispose() {
		for (IObservable iObservable : observables) {
			if (!iObservable.isDisposed()) {
				iObservable.dispose();
			}
		}
	}
}
