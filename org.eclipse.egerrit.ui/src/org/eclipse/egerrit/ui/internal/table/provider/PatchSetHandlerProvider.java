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

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.internal.utils.DataConverter;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * This class handle the user selection of a patch set
 */
public class PatchSetHandlerProvider {

	private ChangeInfo fChangeInfo;

	private Button fButtonPatchSet;

	private Label patchsetlabel;

	private static final String SEPARATOR_PIPE = " | "; //$NON-NLS-1$

	private static final String OTHER_COMMENT = "showComments.gif"; //$NON-NLS-1$

	// For the images
	private static ImageRegistry fImageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it, and automatically disposes of them the
	 * SWT Display is disposed.
	 */
	static {

		String iconPath = "icons/"; //$NON-NLS-1$
		fImageRegistry.put(OTHER_COMMENT, EGerritUIPlugin.getImageDescriptor(iconPath + OTHER_COMMENT));
	}

	/**
	 * Create a button and add all handling for this patchset selection button
	 *
	 * @param parent
	 * @param changeInfo
	 * @return Button
	 */
	public Button create(Composite parent, ChangeInfo changeInfo) {
		this.fChangeInfo = changeInfo;
		patchsetlabel = new Label(parent, SWT.NONE);
		fButtonPatchSet = new Button(parent, SWT.DROP_DOWN | SWT.ARROW | SWT.DOWN);
		fButtonPatchSet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fButtonPatchSet.addSelectionListener(pullDownPatchSet());
		patchsetSelectionBinding();
		return fButtonPatchSet;
	}

	private SelectionAdapter pullDownPatchSet() {
		return (new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				org.eclipse.swt.widgets.Menu menu = new org.eclipse.swt.widgets.Menu(shell, SWT.POP_UP);

				RevisionInfoComparator valueCompare = new RevisionInfoComparator(fChangeInfo.getRevisions());
				TreeMap<String, RevisionInfo> sortedListRevision = new TreeMap<String, RevisionInfo>(valueCompare);
				//Sort the Map first
				sortedListRevision.putAll(fChangeInfo.getRevisions().map());

				//Create the menu items
				Iterator<RevisionInfo> iterator = sortedListRevision.values().iterator();
				while (iterator.hasNext()) {
					RevisionInfo revInfo = iterator.next();
					MenuItem menuItem = getMenuItem(menu, revInfo);
				}

				//Position the menu under the button
				Point loc = fButtonPatchSet.getLocation();
				Rectangle rect = fButtonPatchSet.getBounds();

				Point mLoc = new Point(loc.x - 1, loc.y + rect.height);

				menu.setLocation(shell.getDisplay().map(fButtonPatchSet.getParent(), null, mLoc));

				menu.setVisible(true);
			}
		});

	}

	/**
	 * Create a menu item under the patch set button
	 *
	 * @param menu
	 * @param revisionInfo
	 * @return MenuItem
	 */
	private MenuItem getMenuItem(org.eclipse.swt.widgets.Menu menu, RevisionInfo revisionInfo) {

//		final MenuItem item = new MenuItem(menu, SWT.PUSH | SWT.CHECK | SWT.RADIO);
//		MenuItem item = new MenuItem(menu, SWT.CHECK);
		MenuItem item = new MenuItem(menu, SWT.RADIO);
		//Set a data structure with the RevisionInfo for this item
		item.setData(revisionInfo);
		StringBuilder sb = new StringBuilder();
		boolean comments = revisionInfo.isCommented();

		if (comments) {
			item.setSelection(true);
			item.setImage(fImageRegistry.get(OTHER_COMMENT));
		}
		sb.append(revisionInfo.isDraft() ? "Draft " : "             "); //$NON-NLS-1$//$NON-NLS-2$
		if (revisionInfo.get_number() < 10) {
			sb.append("  "); //$NON-NLS-1$
		}
		sb.append(Integer.toString(revisionInfo.get_number())); //
		sb.append(SEPARATOR_PIPE);
		sb.append(getCommitTime(revisionInfo));
		sb.append(SEPARATOR_PIPE);
		sb.append(getUserCommiter(revisionInfo)); //
		item.setText(sb.toString());
		item.setImage(fImageRegistry.get(OTHER_COMMENT));
		item.addSelectionListener(patchSetItemSelection());
		return item;
	}

	/**
	 * When patch set item selected, adjust the appropriate data in the detail editor
	 *
	 * @return SelectionAdapter
	 */
	private SelectionAdapter patchSetItemSelection() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				RevisionInfo revInfo = (RevisionInfo) ((MenuItem) e.getSource()).getData();

				//Attach the patchset button with binding to the current revision
				fChangeInfo.setUserSelectedRevision(revInfo);
			}
		};
	}

	/**
	 * @param revisionInfo
	 * @return String
	 */
	private String getCommitTime(RevisionInfo revisionInfo) {
		if (revisionInfo.getCommit() != null) {
			return Utils.formatDate(revisionInfo.getCommit().getCommitter().getDate(),
					new SimpleDateFormat("MMM dd, yyyy hh:mm a")); //$NON-NLS-1$
		} else {
			return "CommitInfo empty";
		}
	}

	/**
	 * @param revisionInfo
	 * @return
	 */
	private String getUserCommiter(RevisionInfo revisionInfo) {
		StringBuilder sb = new StringBuilder();
		if (revisionInfo.getCommit() != null) {
			if (revisionInfo.getCommit().getAuthor() != null) {
				//Use the Author
				sb.append(revisionInfo.getCommit().getAuthor().getName());
			}
			if (!revisionInfo.getCommit()
					.getAuthor()
					.getName()
					.equals(revisionInfo.getCommit().getCommitter().getName())) {
				//Add the committer if different than the Author
				sb.append("/"); //$NON-NLS-1$
				sb.append(revisionInfo.getCommit().getCommitter().getName());
			}
			return sb.toString();

		} else {
			return "CommitInfo Author null";
		}
	}

	private class RevisionInfoComparator implements Comparator<Object> {
		EMap<String, RevisionInfo> base;

		public RevisionInfoComparator(EMap<String, RevisionInfo> eMap) {
			this.base = eMap;
		}

		@Override
		public int compare(Object o1, Object o2) {
			RevisionInfo rev1 = base.get(o1);
			RevisionInfo rev2 = base.get(o2);
			return rev2.get_number() - rev1.get_number();
		}
	}

	/**
	 * Add binding to this patch set button
	 */
	private void patchsetSelectionBinding() {
		final DataBindingContext bindingContext = new DataBindingContext();

		FeaturePath selectRevision = FeaturePath.fromList(ModelPackage.Literals.CHANGE_INFO__USER_SELECTED_REVISION);
		IObservableValue observerValue = EMFProperties.value(selectRevision).observe(fChangeInfo);

		bindingContext.bindValue(WidgetProperties.text().observe(patchsetlabel), observerValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.patchSetSelected(fChangeInfo)));

		//See when a REBASE occurs, the current revision is updated
		IObservableValue observerRevisionsValue = EMFProperties
				.value(ModelPackage.Literals.CHANGE_INFO__CURRENT_REVISION).observe(fChangeInfo);

		bindingContext.bindValue(WidgetProperties.text().observe(patchsetlabel), observerRevisionsValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.patchSetSelected(fChangeInfo)));
	}
}
