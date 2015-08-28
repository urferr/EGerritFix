/*******************************************************************************
 * Copyright (c) 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the label provider
 ******************************************************************************/

package org.eclipse.egerrit.dashboard.ui.preferences;

import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This class implements the Preference label provider.
 *
 * @author Jacques Bouthillier
 * @since 1.0
 */
public class GerritServerTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private final String[] fTitles = { "Server name", "URL", "User" };

	private final String EMPTY_STRING = ""; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public GerritServerTableLabelProvider() {
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
	public String getColumnText(Object aObj, int aIndex) {
		// GerritPlugin.Ftracer.traceWarning("getColumnText object: " + aObj
		// + "\tcolumn: " + aIndex);
		if (aObj instanceof GerritServerInformation) {
			GerritServerInformation serverInfo = (GerritServerInformation) aObj;
			switch (aIndex) {
			case 0:
				return serverInfo.getName();
			case 1:
				return serverInfo.getServerURI();
			case 2:
				return serverInfo.getUserName();
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
	public Image getColumnImage(Object aObj, int aIndex) {
		return null;
	}

	public void createColumns(TableViewer viewer) {
		for (String title : fTitles) {
			TableColumn column = new TableViewerColumn(viewer, SWT.NONE).getColumn();
			column.setText(title);
			column.setResizable(true);
		}
	}
}
