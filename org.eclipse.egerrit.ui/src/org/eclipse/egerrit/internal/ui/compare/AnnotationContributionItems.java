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

package org.eclipse.egerrit.internal.ui.compare;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.internal.ui.EGerritUIPlugin;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * This class is used to define the Annotation items found in the compare editor toolbar.
 */
public class AnnotationContributionItems extends CompoundContributionItem {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getContributionItems.
	 *
	 * @return IContributionItem[]
	 */

	@Override
	protected IContributionItem[] getContributionItems() {

		final List<IContributionItem> list = new ArrayList<IContributionItem>();

		CommandContributionItemParameter params = new CommandContributionItemParameter(
				EGerritUIPlugin.getDefault()
						.getWorkbench()
						.getActiveWorkbenchWindow()
						.getActivePage()
						.getActivePart()
						.getSite(),
				UICompareUtils.NEXT_COMMENT_ANNOTATION_COMMAND, UICompareUtils.NEXT_COMMENT_ANNOTATION_COMMAND, null,
				ImageDescriptor.createFromURL(EGerritUIPlugin.getDefault()
						.getBundle()
						.getEntry(UICompareUtils.NEXT_COMMENT_ANNOTATION_ICON_FILE)),
				null, null, Messages.UICompareUtils_NEXT_COMMENT_ANNOTATION_COMMAND_NAME,
				UICompareUtils.NEXT_COMMENT_ANNOTATION_COMMAND_MNEMONIC,
				Messages.UICompareUtils_NEXT_COMMENT_ANNOTATION_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH,
				null, true);

		list.add(new CommandContributionItem(params));

		params = new CommandContributionItemParameter(
				EGerritUIPlugin.getDefault()
						.getWorkbench()
						.getActiveWorkbenchWindow()
						.getActivePage()
						.getActivePart()
						.getSite(),
				UICompareUtils.PREVIOUS_COMMENT_ANNOTATION_COMMAND, UICompareUtils.PREVIOUS_COMMENT_ANNOTATION_COMMAND,
				null,
				ImageDescriptor.createFromURL(EGerritUIPlugin.getDefault()
						.getBundle()
						.getEntry(UICompareUtils.PREVIOUS_COMMENT_ANNOTATION_ICON_FILE)),
				null, null, Messages.UICompareUtils_PREVIOUS_COMMENT_ANNOTATION_COMMAND_NAME,
				UICompareUtils.PREVIOUS_COMMENT_ANNOTATION_COMMAND_MNEMONIC,
				Messages.UICompareUtils_PREVIOUS_COMMENT_ANNOTATION_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH,
				null, true);

		list.add(new CommandContributionItem(params));

		UICompareUtils.setCurrentElement(0);

		return list.toArray(new IContributionItem[list.size()]);
	}

	/**
	 * Method getContributionItems.
	 *
	 * @return IContributionItem[]
	 */
	public IContributionItem[] getallContributionItems() {

		return getContributionItems();
	}
}
