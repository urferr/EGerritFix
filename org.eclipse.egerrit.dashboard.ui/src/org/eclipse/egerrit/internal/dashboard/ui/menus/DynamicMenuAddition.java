/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the dynamic menu selection
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.egerrit.internal.dashboard.ui.utils.UIConstants;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

/**
 * This class implements the Dynamic menu selection to pre-fill the list of gerrit project locations.
 *
 * @since 1.0
 */
public class DynamicMenuAddition extends CompoundContributionItem implements IWorkbenchContribution {
	private IServiceLocator fServiceLocator;

	@Override
	protected IContributionItem[] getContributionItems() {
		List<GerritServerInformation> servers = ServersStore.getAllServers();
		if (servers == null || servers.isEmpty()) {
			return new CommandContributionItem[0];
		}

		CommandContributionItem[] contributionItems = new CommandContributionItem[servers.size()];
		int count = 0;
		for (GerritServerInformation server : servers) {
			CommandContributionItemParameter menuEntry = new CommandContributionItemParameter(fServiceLocator,
					server.getName(), UIConstants.SELECT_SERVER_COMMAND_ID, CommandContributionItem.STYLE_CHECK);
			menuEntry.label = !server.getName().isEmpty() ? server.getName() : server.getServerURI();
			menuEntry.visibleEnabled = true;
			Map params = new HashMap();
			params.put(UIConstants.SELECT_SERVER_COMMAND_ID_PARAM, Integer.toString(server.hashCode()));
			menuEntry.parameters = params;
			contributionItems[count++] = new CommandContributionItem(menuEntry);
		}
		return contributionItems;
	}

	@Override
	public void initialize(IServiceLocator aServiceLocator) {
		fServiceLocator = aServiceLocator;
	}

}
