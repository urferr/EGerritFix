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

package org.eclipse.egerrit.internal.ui.table.provider;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egerrit.internal.ui.table.model.BranchMatch;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This class implements the local Branch selection label provider.
 *
 * @author Jacques Bouthillier
 * @since 1.0
 */

public class BranchListLabelProvider extends LabelProvider implements ITableLabelProvider {
	private final String[] fTitles = { Messages.BranchSelectionTableModel_branch,
			Messages.BranchSelectionTableModel_match };

	private final String EMPTY_STRING = ""; //$NON-NLS-1$

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
		if (aObj instanceof Entry) {
			Map.Entry<String, BranchMatch> map = (Map.Entry<String, BranchMatch>) aObj;

			switch (aIndex) {
			case 0:
				return map.getKey();
			case 1:
				return map.getValue().getValue();
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
