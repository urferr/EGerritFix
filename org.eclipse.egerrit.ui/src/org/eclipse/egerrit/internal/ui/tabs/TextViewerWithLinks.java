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

package org.eclipse.egerrit.internal.ui.tabs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.text.hyperlink.HyperlinkManager;
import org.eclipse.jface.text.hyperlink.HyperlinkManager.DETECTION_STRATEGY;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetectorExtension;
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
		fHyperlinkManager = new AlwaysOnLinkManager(HyperlinkManager.ALL);
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

	/*
	 * @see org.eclipse.jface.text.ITextViewerExtension6#setHyperlinkDetectors(org.eclipse.jface.text.hyperlink.IHyperlinkDetector[], int)
	 * @since 3.1
	 */
	@Override
	public void setHyperlinkDetectors(IHyperlinkDetector[] hyperlinkDetectors, int eventStateMask) {
		if (fHyperlinkDetectors != null) {
			for (IHyperlinkDetector fHyperlinkDetector : fHyperlinkDetectors) {
				if (fHyperlinkDetector instanceof IHyperlinkDetectorExtension) {
					((IHyperlinkDetectorExtension) fHyperlinkDetector).dispose();
				}
			}
		}

		boolean enable = hyperlinkDetectors != null && hyperlinkDetectors.length > 0;
		fHyperlinkStateMask = eventStateMask;
		fHyperlinkDetectors = hyperlinkDetectors;
		if (enable) {
			if (fHyperlinkManager != null) {
				fHyperlinkManager.setHyperlinkDetectors(fHyperlinkDetectors);
				fHyperlinkManager.setHyperlinkStateMask(fHyperlinkStateMask);
			}
			ensureHyperlinkManagerInstalled();
		} else {
			if (fHyperlinkManager != null) {
				fHyperlinkManager.uninstall();
			}
			fHyperlinkManager = null;
		}
	}

	/**
	 * Ensures that the hyperlink manager has been installed if a hyperlink detector is available.
	 *
	 * @since 3.1
	 */
	private void ensureHyperlinkManagerInstalled() {
		if (fHyperlinkDetectors != null && fHyperlinkDetectors.length > 0 && fHyperlinkPresenter != null
				&& fHyperlinkManager == null) {
			DETECTION_STRATEGY strategy = fHyperlinkPresenter.canShowMultipleHyperlinks()
					? HyperlinkManager.ALL
					: HyperlinkManager.FIRST;
			fHyperlinkManager = new EGerritHyperlinkManager(strategy);
			fHyperlinkManager.install(this, fHyperlinkPresenter, fHyperlinkDetectors, fHyperlinkStateMask);
		}
	}
}
