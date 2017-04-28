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

package org.eclipse.egerrit.internal.ui.table.provider;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.utils.Utils;
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * This class implements the History table UI label provider.
 *
 * @since 1.0
 */
public class HistoryTableLabelProvider extends ObservableMapLabelProvider implements ITableLabelProvider {
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private GerritClient gerritClient;

	public HistoryTableLabelProvider(IObservableMap[] iObservableMaps, GerritClient gerritClient) {
		super(iObservableMaps);
		this.gerritClient = gerritClient;
	}

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
				return Utils.prettyPrintDate(changeMessageInfo.getDate());
			case 2:
				if (changeMessageInfo.getAuthor() != null) {
					return changeMessageInfo.getAuthor().getName();
				}
				break;
			case 3:
				String msg = changeMessageInfo.getMessage().replaceAll("[\\t\\n\\r]", " "); //$NON-NLS-1$//$NON-NLS-2$
				msg = msg.replaceFirst("([Pp]atch [Ss]et )(\\d[.:])", "$1 $2"); //$NON-NLS-1$ //$NON-NLS-2$
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
				boolean hasComments = changeMessageInfo.isComment();
				if (hasComments) {
					if (gerritClient.getRepository().getServerInfo().isAnonymous()) {
						return EGerritImages.get(EGerritImages.ANONYMOUS_COMMENT);
					} else {
						String currentUser = gerritClient.getRepository().getCredentials().getUsername();
						AccountInfo author = changeMessageInfo.getAuthor();
						if (author != null && (currentUser.equals(author.getEmail())
								|| currentUser.equals(author.getName()) || currentUser.equals(author.getUsername()))) {
							return EGerritImages.get(EGerritImages.AUTHOR_COMMENT);
						} else {
							return EGerritImages.get(EGerritImages.ANONYMOUS_COMMENT);
						}
					}
				}
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			default:
				return image;
			}
		}
		return image;
	}
}
