/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.commands.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.dashboard.ui.GerritUi;
import org.eclipse.egerrit.internal.dashboard.ui.utils.UIConstants;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.process.ReplyProcess;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

/**
 * This class creates contribution items used in the dynamic reply menu option
 */
public class VoteMenuGenerator extends CompoundContributionItem implements IWorkbenchContribution {

	public static final String VIEW_ID = "org.eclipse.egerrit.dashboard.ui.views.GerritTableView"; //$NON-NLS-1$

	private GerritClient fGerritClient;

	private IServiceLocator fServiceLocator;

	@Override
	protected IContributionItem[] getContributionItems() {
		IContributionItem[] contributionItems = null;
		IWorkbench workbench = GerritUi.getDefault().getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		IViewPart viewPart = page.findView(VIEW_ID);
		if (viewPart != null && viewPart instanceof GerritTableView) {
			GerritTableView gerritTable = (GerritTableView) viewPart;
			fGerritClient = gerritTable.getGerritClient();
			contributionItems = getData();

		}
		return contributionItems;
	}

	@Override
	public void initialize(IServiceLocator serviceLocator) {
		fServiceLocator = serviceLocator;
	}

	private IContributionItem[] getData() {
		ISelection selection = fServiceLocator.getService(ISelectionService.class).getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return new IContributionItem[0];
		}
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		Iterator<?> selections = structuredSelection.iterator();
		while (selections.hasNext()) {
			Object element = selections.next();
			if (!(element instanceof ChangeInfo)) {
				continue;
			}

			ChangeInfo changeInfo = (ChangeInfo) element;
			//Adjust the list of label which the current user can set to a maximum value
			String loginUser = fGerritClient.getRepository().getServerInfo().getUserName();
			Map<String, Integer> labelsToCreateEntryFor = changeInfo.getLabelsNotAtMax(loginUser);
			if (labelsToCreateEntryFor.isEmpty()) {
				return new IContributionItem[0];
			}

			List<IContributionItem> menusForVotes = new ArrayList<>();
			for (Entry<String, Integer> label : labelsToCreateEntryFor.entrySet()) {
				menusForVotes.add(createItem(label.getKey(), String.valueOf(label.getValue()),
						label.getKey() + "  +" + label.getValue())); //$NON-NLS-1$
			}

			//Add a selection for all buttons when there is more than one selection
			if (menusForVotes.size() > 1) {
				menusForVotes.add(createItem(ReplyProcess.REPLY_ALL_BUTTONS, null, ReplyProcess.REPLY_ALL_BUTTONS));
			}
			return menusForVotes.toArray(new IContributionItem[menusForVotes.size()]);
		}
		return new IContributionItem[0];
	}

	/**
	 * Create each contribution item
	 *
	 * @param key
	 * @param value
	 * @param menuLabel
	 * @return CommandContributionItem
	 */
	private ContributionItem createItem(String key, String value, String menuLabel) {
		//Add the label value as parameters
		Map<String, String> params = new HashMap<>();
		params.put(UIConstants.REPLY_COMMAND_ID_LABEL_PARAM, key);
		params.put(UIConstants.REPLY_COMMAND_ID_VALUE_PARAM, value);
		CommandContributionItemParameter menuEntry = new CommandContributionItemParameter(fServiceLocator, menuLabel,
				UIConstants.REPLY_COMMAND_VOTE_ID, params, null, null, null, menuLabel, menuLabel, menuLabel,
				CommandContributionItem.STYLE_PUSH, null, true);
		return new CommandContributionItem(menuEntry);
	}

}
