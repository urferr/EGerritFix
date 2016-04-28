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

package org.eclipse.egerrit.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Helper class to handle images
 */
public class EGerritImages {

	private static ImageRegistry fgImageRegistry = new ImageRegistry();

	public static final String AUTHOR_COMMENT = "icons/showAuthorComments.gif"; //$NON-NLS-1$

	public static final String ANONYMOUS_COMMENT = "icons/showAnonymousComments.gif"; //$NON-NLS-1$

	public static final String OTHER_COMMENT = "icons/showAnonymousComments.gif"; //$NON-NLS-1$

	public static final String DELETE_QUICKFIX = "icons/ovr16/delete.gif"; //$NON-NLS-1$

	public static final String DONE_QUICKFIX = "icons/ovr16/done.png"; //$NON-NLS-1$

	public static final String EDIT_QUICKFIX = "icons/ovr16/edit.gif"; //$NON-NLS-1$

	public static final String REPLY_QUICKFIX = "icons/ovr16/reply.png"; //$NON-NLS-1$

	static {
		fgImageRegistry.put(AUTHOR_COMMENT, EGerritUIPlugin.getImageDescriptor(AUTHOR_COMMENT));
		fgImageRegistry.put(ANONYMOUS_COMMENT, EGerritUIPlugin.getImageDescriptor(ANONYMOUS_COMMENT));
		fgImageRegistry.put(OTHER_COMMENT, EGerritUIPlugin.getImageDescriptor(OTHER_COMMENT));
		fgImageRegistry.put(DELETE_QUICKFIX, EGerritUIPlugin.getImageDescriptor(DELETE_QUICKFIX));
		fgImageRegistry.put(DONE_QUICKFIX, EGerritUIPlugin.getImageDescriptor(DONE_QUICKFIX));
		fgImageRegistry.put(EDIT_QUICKFIX, EGerritUIPlugin.getImageDescriptor(EDIT_QUICKFIX));
		fgImageRegistry.put(REPLY_QUICKFIX, EGerritUIPlugin.getImageDescriptor(REPLY_QUICKFIX));
	}

	/**
	 * Returns the image descriptor for the given key in this registry. Might be called in a non-UI thread.
	 *
	 * @param key
	 *            the image's key
	 * @return the image descriptor for the given key
	 */
	public static ImageDescriptor getDescriptor(String key) {
		return fgImageRegistry.getDescriptor(key);
	}

	/**
	 * Returns the image managed under the given key in this registry.
	 *
	 * @param key
	 *            the image's key
	 * @return the image managed under the given key
	 */
	public static Image get(String key) {
		return fgImageRegistry.get(key);
	}
}
