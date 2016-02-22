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

package org.eclipse.egerrit.ui.internal.table.provider;

import java.util.Set;

import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.editors.model.GerritDiffNode;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

public class FileInfoCompareCellLabelProvider extends CellLabelProvider {

	final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String NEW = "new :";

	private static final String COMMENTS = "comments : ";

	private static final String DRAFTS = "drafts : ";

	public static final String CHECKED_IMAGE = "greenCheck.png"; //$NON-NLS-1$

	// For the images
	private static final ImageRegistry fImageRegistry = new ImageRegistry();

	//Layout selection for the file path. Also as a default value
	private boolean nameFirst = true;

	/**
	 * Note: An image registry owns all of the image objects registered with it, and automatically disposes of them the
	 * SWT Display is disposed.
	 */
	static {

		String iconPath = "icons/"; //$NON-NLS-1$

		fImageRegistry.put(CHECKED_IMAGE, EGerritUIPlugin.getImageDescriptor(iconPath + CHECKED_IMAGE));

	}

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
		FileInfo fileInfo = gerrritNode.getFileInfo();
		switch (columnIdx) {
		case 0:
			if (fileInfo.isReviewed()) {
				return "1"; //$NON-NLS-1$
			}
			return ""; //$NON-NLS-1$ //Here we return an empty string to make sure that nothing shows in the coluimn when the user anonymous
		case 1:
			return fileInfo.getStatus();
		case 2:
			String previousName = ""; //$NON-NLS-1$
			if (fileInfo.getOld_path() != null) {
				previousName = " (was " + fileInfo.getOld_path() + ")";
			}
			String path = null;
			if (nameFirst) {
				path = fileInfo.getPath();
				int index = path.lastIndexOf("/"); //$NON-NLS-1$
				if (index != -1) {
					String fileName = path.substring(index + 1);
					String firstName = fileName + previousName + " - " + path.substring(0, index); //$NON-NLS-1$
					path = firstName;
				}
			} else {
				path = fileInfo.getPath();
				path += previousName;
			}
			return path;
		case 3:
			String commentString = ""; //$NON-NLS-1$
			int commentsCount = fileInfo.getCommentsCount();
			int draftsCount = fileInfo.getDraftsCount();
			if (draftsCount != 0) {
				commentString += DRAFTS + draftsCount;
				if (commentsCount != 0) {
					commentString += " "; //$NON-NLS-1$
				}
			} else {
				commentString = "                     "; //$NON-NLS-1$
			}

			if (commentsCount != 0) {
				commentString += COMMENTS + commentsCount;
			}
			return commentString;
		case 4:
			StringBuilder modifySize = new StringBuilder();
			modifySize.append('+');
			modifySize.append(Integer.toString(fileInfo.getLines_inserted()));
			modifySize.append('/');
			modifySize.append('-');
			modifySize.append(Integer.toString(fileInfo.getLines_deleted()));
			return modifySize.toString();
		default:
			return ""; //$NON-NLS-1$
		}

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
		if (aState == true) {
			return fImageRegistry.get(CHECKED_IMAGE);
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
