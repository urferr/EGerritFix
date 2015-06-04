/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the preferences
 *******************************************************************************/
package org.eclipse.egerrit.dashboard.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.dashboard.GerritPlugin;
import org.eclipse.equinox.security.storage.EncodingUtils;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 *
 * @since 1.0
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = GerritPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
//		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING, "Default value");
		StringBuilder sb = new StringBuilder();
		sb.append(PreferenceConstants.P_GERRIT_ECLIPSE_SERVER);
		sb.append(PreferenceConstants.LIST_SEPARATOR);
		sb.append(PreferenceConstants.P_GERRIT_ECLIPSE_LABEL);
		sb.append(PreferenceConstants.LIST_SEPARATOR);
		sb.append(false);
		store.setDefault(PreferenceConstants.P_GERRIT_LISTS, sb.toString());
//		store.setDefault(PreferenceConstants.P_GERRIT_LISTS, PreferenceConstants.P_GERRIT_ECLIPSE_SERVER);
		//Secure Begin
		ISecurePreferences securePref = SecurePreferencesFactory.getDefault();
		ISecurePreferences serverPreference = securePref
				.node(EncodingUtils.encodeSlashes(PreferenceConstants.P_GERRIT_ECLIPSE_SERVER));
		try {
//			serverPreference.put(PreferenceConstants.P_USER, PreferenceConstants.P_ANONYMOUS, true);
			serverPreference.put(PreferenceConstants.P_USER, "", true); //$NON-NLS-1$
			serverPreference.put(PreferenceConstants.P_PASSWORD, "", true); //$NON-NLS-1$
		} catch (StorageException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}

		//Test Secure END

	}

}
