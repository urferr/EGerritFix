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

package org.eclipse.egerrit.ui.editors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

/**
 * The model loader is responsible for loading into an existing changeInfo object the information requested. In addition
 * to that it makes sure that the loaded information is kept up-to-date. There model loader splits the data in 3 logical
 * groups: 1) the basic information which contains the overall information necessary to present a review (actions,
 * history entries, list of revisions). 2) the details of the current revision (list of files, reviewed state, list of
 * comments and drafts). 3) the review details like the reviewers, etc. All calls in this are expected to be
 * asynchronous
 */
public class ModelLoader {
	private static Map<String, ModelLoader> loaders = new HashMap<>();

	private ChangeInfo changeInfo;

	private GerritClient gerritClient;

	private Adapter currentRevisionTracker = null;

	private Adapter detailsTracker = null;

	private Adapter basicInfoTracker = null;

	private int refCount = 0;

	private ModelLoader(GerritClient gerritClient, ChangeInfo changeInfo) {
		this.changeInfo = changeInfo;
		this.gerritClient = gerritClient;
	}

	public static ModelLoader initialize(GerritClient client, ChangeInfo changeInfo) {
		ModelLoader match = loaders.get(changeInfo.getId());
		if (match == null) {
			match = new ModelLoader(client, changeInfo);
			loaders.put(changeInfo.getId(), match);
		}
		match.refCount++;
		return match;
	}

	//load the basic information
	public void loadBasicInformation() {
		CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo));
		initializeBasicInformationTracker();
	}

	private void initializeDetailsTracker() {
		if (detailsTracker == null) {
			detailsTracker = new EContentAdapter() {
				@Override
				public void notifyChanged(Notification msg) {
					super.notifyChanged(msg);

					if (msg.getFeature() == null) {
						return;
					}

					if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__UPDATED)) {
						CompletableFuture
								.runAsync(() -> QueryHelpers.loadDetailedInformation(gerritClient, changeInfo));
					}
				}
			};
			changeInfo.eAdapters().add(detailsTracker);
		}
	}

	public void loadDetailedInformation() {
		CompletableFuture.runAsync(() -> QueryHelpers.loadDetailedInformation(gerritClient, changeInfo));
		initializeDetailsTracker();
	}

	private void initializeBasicInformationTracker() {
		if (basicInfoTracker == null) {
			basicInfoTracker = new EContentAdapter() {
				@Override
				public void notifyChanged(Notification msg) {
					super.notifyChanged(msg);

					if (msg.getFeature() == null) {
						return;
					}

					if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__UPDATED)) {
						CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo));
					}
				}
			};
			changeInfo.eAdapters().add(basicInfoTracker);
		}
	}

	//load and track the user selected revision
	public void loadCurrentRevision() {
		loadRevision(changeInfo.getCurrent_revision());
	}

	private void loadRevision(String revision) {
		CompletableFuture.runAsync(
				() -> QueryHelpers.loadRevisionDetails(gerritClient, changeInfo.getRevisions().get(revision)));
		initializeCurrentRevisionTracker();
	}

	private void initializeCurrentRevisionTracker() {
		if (currentRevisionTracker == null) {
			currentRevisionTracker = new EContentAdapter() {
				@Override
				public void notifyChanged(Notification msg) {
					super.notifyChanged(msg);

					if (msg.getFeature() == null) {
						return;
					}

					if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__USER_SELECTED_REVISION)) {
						CompletableFuture.runAsync(
								() -> QueryHelpers.loadRevisionDetails(gerritClient, changeInfo.getRevision()));
					}
				}
			};
			changeInfo.eAdapters().add(currentRevisionTracker);
		}
	}

	public void dispose() {
		refCount--;
		if (refCount == 0) {
			if (currentRevisionTracker != null) {
				changeInfo.eAdapters().remove(currentRevisionTracker);
			}
			if (detailsTracker != null) {
				changeInfo.eAdapters().remove(detailsTracker);
			}
			if (basicInfoTracker != null) {
				changeInfo.eAdapters().remove(basicInfoTracker);
			}
		}
	}

	public void reload() {
		//Calling loadBasicInformation will refresh the "updated" timestamp
		//which will cause other loads to occur
		loadBasicInformation();
	}
}
