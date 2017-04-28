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

import java.util.Set;

import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.compare.CommentableCompareItem;
import org.eclipse.egerrit.internal.ui.compare.GerritDiffNode;
import org.eclipse.egerrit.internal.ui.compare.GerritDifferences;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

public class FileInfoCompareCellLabelProvider extends CellLabelProvider {

	private static final String COMMENTS = Messages.FileInfoCompareCellLabelProvider_1;

	private static final String DRAFTS = Messages.FileInfoCompareCellLabelProvider_2;

	//Layout selection for the file path. Also as a default value
	private boolean nameFirst = true;

	private IMapChangeListener mapChangeListener = new IMapChangeListener() {
		public void handleMapChange(MapChangeEvent event) {
			Set<?> affectedElements = event.diff.getChangedKeys();
			if (!affectedElements.isEmpty()) {
				LabelProviderChangedEvent newEvent = new LabelProviderChangedEvent(
						FileInfoCompareCellLabelProvider.this, affectedElements.toArray());
				fireLabelProviderChanged(newEvent);
			}
		}
	};

	public FileInfoCompareCellLabelProvider(IObservableMap... attributeMaps) {
		for (IObservableMap attributeMap : attributeMaps) {
			attributeMap.addMapChangeListener(mapChangeListener);
		}
	}

	public String getLabel(Object element, int columnIdx) {
		GerritDiffNode gerrritNode = (GerritDiffNode) element;
		switch (columnIdx) {
		case 0:
			return ""; //$NON-NLS-1$ //Here we return an empty string to make sure that nothing shows in the coluimn when the user anonymous
		case 1: {
			switch (gerrritNode.getKind()) {
			case GerritDifferences.NO_CHANGE:
				return ""; //$NON-NLS-1$
			case GerritDifferences.ADDITION:
				return "A"; //$NON-NLS-1$
			case GerritDifferences.DELETION:
				return "D"; //$NON-NLS-1$
			case GerritDifferences.RENAMED:
				return "R"; //$NON-NLS-1$
			case GerritDifferences.COPIED:
				return "C"; //$NON-NLS-1$
			case GerritDifferences.CHANGE:
			default:
				return "M"; //$NON-NLS-1$
			}
		}
		case 2:
			return gerrritNode.getLabelName(nameFirst);
		case 3:
			if (gerrritNode.getLeft() == null || !(gerrritNode.getLeft() instanceof CommentableCompareItem)) {
				return ""; //$NON-NLS-1$
			}
			return prettyPrintComments((CommentableCompareItem) gerrritNode.getLeft());
		case 4:
			if (gerrritNode.getRight() == null || !(gerrritNode.getRight() instanceof CommentableCompareItem)) {
				return ""; //$NON-NLS-1$
			}
			return prettyPrintComments((CommentableCompareItem) gerrritNode.getRight());
		case 5:
			StringBuilder modifySize = new StringBuilder();
			FileInfo file = gerrritNode.getDiffFileInfo() != null
					? gerrritNode.getDiffFileInfo()
					: gerrritNode.getFileInfo();
			modifySize.append('+');
			modifySize.append(Integer.toString(file.getLines_inserted()));
			modifySize.append('/');
			modifySize.append('-');
			modifySize.append(Integer.toString(file.getLines_deleted()));
			return modifySize.toString();
		default:
			return ""; //$NON-NLS-1$
		}

	}

	private String prettyPrintComments(CommentableCompareItem commentableCompareItem) {
		String result = ""; //$NON-NLS-1$
		int commentsCount = commentableCompareItem.getComments().size();
		int draftsCount = commentableCompareItem.getDrafts().size();
		if (draftsCount != 0) {
			result += DRAFTS + draftsCount;
			if (commentsCount != 0) {
				result += " "; //$NON-NLS-1$
			}
		} else {
			result = "                     "; //$NON-NLS-1$
		}

		if (commentsCount != 0) {
			result += COMMENTS + commentsCount;
		}
		return result;
	}

	@Override
	public void update(ViewerCell cell) {
		if (cell != null) {
			Object obj = cell.getElement();
			if (obj instanceof GerritDiffNode) {
				cell.setText(getLabel(obj, cell.getColumnIndex()));
				if (cell.getColumnIndex() == 0) {
					GerritDiffNode gerrritNode = (GerritDiffNode) obj;
					FileInfo fileInfo = gerrritNode.getFileInfo();
					Image image = getReviewedStateImage(fileInfo.isReviewed());
					cell.setImage(image);
				}
			}
		}
	}

	/**
	 * Return an image representing the reviewed state of the object
	 *
	 * @param int
	 *            aState
	 * @return Image
	 */
	private Image getReviewedStateImage(boolean aState) {
		if (aState) {
			return EGerritImages.get(EGerritImages.CHECKED_IMAGE);
		} else {
			return null;
		}
	}

	/**
	 * Adjust the format of the FilePath
	 *
	 * @param b
	 */
	public void setFileNameFirst(boolean b) {
		nameFirst = b;
	}

	/**
	 * Get the file name order
	 *
	 * @return boolean
	 */
	public boolean getFileOrder() {
		return nameFirst;
	}
}
