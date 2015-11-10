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

package org.eclipse.egerrit.core.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class implements the property change support model. It is used to populate the fields using the databinding.
 *
 * @since 1.0
 */
public class PropertyChangeModel {

	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	/**
	 * Allow to add a property change listener
	 *
	 * @param String
	 *            propertyName
	 * @param PropertyChangeListener
	 *            listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (propertyChangeSupport != null) {
			propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		}
	}

	/**
	 * Initiate a property change listener
	 *
	 * @param String
	 *            propertyName
	 * @param Object
	 *            oldValue
	 * @param Object
	 *            newValue
	 */
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (propertyChangeSupport != null) {
			propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

}
