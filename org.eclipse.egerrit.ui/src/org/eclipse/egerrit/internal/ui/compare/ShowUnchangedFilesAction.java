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

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Provide the action that allow to only show/hide files that have changed between two revisions Given a revision and a
 * base the viewer is populated with a filter and the appropriate data. This is mostly relevant when the base is either
 * a revisionId. When "BASE" is specified nothing happens because between a given revision and base, all files must
 * always be presented. Note though that in this case the button stays enabled to avoid the user to wonder why the
 * button is enabled/disabled. When "WORKSPACE" is specified, the button is disabled and the filter is passed a empty
 * set which means that no filtering should happen.
 */
public class ShowUnchangedFilesAction extends Action {
	private static final String ICONS_COMPARE_WITH_OTHER_GIF = "icons/compare_with_other.gif"; //$NON-NLS-1$

	private boolean filterOn = false;

	//The reference to the viewer where the filter applies
	private Supplier<TreeViewer> viewer;

	//The actual filtering logic
	private ChangedFileFilter filter;

	//Future used to get the data. This stays null if we are comparing against "BASE" or "WORKSPACE" or if there was a pb retrieving the data
	private CompletableFuture<Map<String, FileInfo>> data;

	//The value to compare against
	private String base;

	private RevisionInfo revisionInfo;

	private GerritClient client;

	public ShowUnchangedFilesAction(GerritClient client, RevisionInfo revisionInfo, String base,
			Supplier<TreeViewer> viewerRef) {
		this.viewer = viewerRef;
		this.filter = new ChangedFileFilter();
		this.client = client;
		this.revisionInfo = revisionInfo;
		this.base = base;

		setText("Show/hide files that have not been changed");
		setDescription("Show/hide files that have not been changed");
		setImageDescriptor(EGerritUIPlugin.getImageDescriptor(ICONS_COMPARE_WITH_OTHER_GIF));
		if (base.equals("WORKSPACE")) {
			setToolTipText("This option can't be used when comparing with the workspace");
			setEnabled(false);
		} else {
			setToolTipText("Show/hide files that have not been changed");
		}
		setChecked(filterOn);
		if (base.equals("BASE") || base.equals("WORKSPACE")) {
			//We short circuit here because we don't need to retrieve data for this two cases
			return;
		}
		retrieveData();
	}

	//Asynchronously retrieve the data necessary to filter the content
	private void retrieveData() {
		data = CompletableFuture.supplyAsync(new Supplier<Map<String, FileInfo>>() {
			@Override
			public Map<String, FileInfo> get() {
				try {
					return client.getFilesModifiedSince(revisionInfo.getChangeInfo().getChange_id(),
							revisionInfo.getId(), base).call();
				} catch (EGerritException e) {
					return null;
				}
			}
		});
	}

	@Override
	public void run() {
		if (data != null) {
			try {
				filter.setFilesToShow(data.get());
			} catch (InterruptedException | ExecutionException e) {
				//to do log something
			}
			if (filterOn) {
				viewer.get().removeFilter(filter);
			} else {
				viewer.get().addFilter(filter);
			}
		}
		filterOn = !filterOn;
		setChecked(filterOn);

	}
}
