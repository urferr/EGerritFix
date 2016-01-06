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

package org.eclipse.egerrit.ui.internal.tabs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.text.hyperlink.HyperlinkManager;
import org.eclipse.swt.widgets.Composite;

/**
 * A text viewer that will always show links
 */
public class TextViewerWithLinks extends org.eclipse.jface.text.source.SourceViewer {
	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public TextViewerWithLinks(Composite composite, int styles) {
		super(composite, null, styles);
	}

	public void resetLinkManager() {
		fHyperlinkManager.uninstall();
		fHyperlinkManager = new AlwaysOnLinkManager(HyperlinkManager.FIRST);
		fHyperlinkManager.install(this, fHyperlinkPresenter, fHyperlinkDetectors, fHyperlinkStateMask);
	}

	/**
	 * Needed by BeanPropertyListenerSupport.processListener() to add a property change
	 *
	 * @param propertyName
	 * @param listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Needed by BeanPropertyListenerSupport.processListener() to remove the property listener
	 *
	 * @param propertyName
	 * @param listener
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}
