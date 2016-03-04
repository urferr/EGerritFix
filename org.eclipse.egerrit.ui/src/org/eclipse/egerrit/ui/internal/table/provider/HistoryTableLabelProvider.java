/*******************************************************************************
 * Copyright (c) 2013, 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the history label provider
 ******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.provider;

import java.text.SimpleDateFormat;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * This class implements the History table UI label provider.
 *
 * @since 1.0
 */
public class HistoryTableLabelProvider extends ObservableMapLabelProvider implements ITableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private final String EMPTY_STRING = ""; //$NON-NLS-1$

	private final SimpleDateFormat formatTimeOut = new SimpleDateFormat("yyyy MMM dd hh:mm a"); //$NON-NLS-1$

	private GerritClient gerritClient;

	private static final String ANONYMOUS_COMMENT = "showAnonymousComments.gif"; //$NON-NLS-1$

	private static final String AUTHOR_COMMENT = "showAuthorComments.gif"; //$NON-NLS-1$

	private static final String OTHER_COMMENT = "showComments.gif"; //$NON-NLS-1$

	// For the images
	private static ImageRegistry fImageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it, and automatically disposes of them the
	 * SWT Display is disposed.
	 */
	static {

		String iconPath = "icons/"; //$NON-NLS-1$

		fImageRegistry.put(ANONYMOUS_COMMENT, EGerritUIPlugin.getImageDescriptor(iconPath + ANONYMOUS_COMMENT));
		fImageRegistry.put(AUTHOR_COMMENT, EGerritUIPlugin.getImageDescriptor(iconPath + AUTHOR_COMMENT));
		fImageRegistry.put(OTHER_COMMENT, EGerritUIPlugin.getImageDescriptor(iconPath + OTHER_COMMENT));
	}

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public HistoryTableLabelProvider(IObservableMap[] iObservableMaps, GerritClient gerritClient) {
		super(iObservableMaps);
		this.gerritClient = gerritClient;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Return the text associated to the column
	 *
	 * @param Object
	 *            structure of the table
	 * @param int
	 *            column index
	 * @return String text associated to the column
	 */
	@Override
	public String getColumnText(Object aObj, int aIndex) {
		if (aObj instanceof ChangeMessageInfo) {
			ChangeMessageInfo changeMessageInfo = (ChangeMessageInfo) aObj;
			switch (aIndex) {
			case 0:
				return ""; //$NON-NLS-1$
			case 1:
				return Utils.formatDate(changeMessageInfo.getDate(), formatTimeOut);
			case 2:
				if (changeMessageInfo.getAuthor() != null) {
					return changeMessageInfo.getAuthor().getName();
				}
				break;
			case 3:
				String msg = changeMessageInfo.getMessage().replaceAll("[\\t\\n\\r]", " "); //$NON-NLS-1$//$NON-NLS-2$
				msg = msg.replaceFirst("([Pp]atch [Ss]et )(\\d[.:])", "$1 $2");
				return msg;
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * Return the image associated to the column
	 *
	 * @param Object
	 *            structure of the table
	 * @param int
	 *            column index
	 * @return Image Image according to the selected column
	 */
	@Override
	public Image getColumnImage(Object aObj, int aIndex) {
		Image image = null;
		if (aObj instanceof ChangeMessageInfo) {
			ChangeMessageInfo changeMessageInfo = (ChangeMessageInfo) aObj;
			switch (aIndex) {
			case 0:
				boolean hasComments = UIUtils.hasComments(changeMessageInfo.getMessage());
				if (hasComments) {
					if (gerritClient.getRepository().getServerInfo().isAnonymous()) {
						return fImageRegistry.get(ANONYMOUS_COMMENT);
					} else {
						String currentUser = gerritClient.getRepository().getCredentials().getUsername();
						AccountInfo author = changeMessageInfo.getAuthor();
						if (author != null && (currentUser.equals(author.getEmail())
								|| currentUser.equals(author.getName()) || currentUser.equals(author.getUsername()))) {
							return fImageRegistry.get(AUTHOR_COMMENT);
						} else {
							return fImageRegistry.get(OTHER_COMMENT);
						}
					}
				}
				break;
			case 1:
				return image;
			case 2:
				return image;
			case 3:
				return image;
			case 4:
				return image;

			default:
				return image;
			}
		}
		return image;
	}
}
