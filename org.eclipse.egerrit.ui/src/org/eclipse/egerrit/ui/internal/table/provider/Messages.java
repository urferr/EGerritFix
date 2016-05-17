/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marc Khouzam - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.provider;

import org.eclipse.osgi.util.NLS;

/**
 * This class implements the internalization of the strings.
 *
 * @since 1.0
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.egerrit.ui.internal.table.provider.messages"; //$NON-NLS-1$

	public static String DeleteDraft_Text;

	public static String DeleteDraft_Tip;

	public static String DeleteDraft_Dialogue_Title;

	public static String DeleteDraft_Dialogue_Message;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
