/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the table view information
 ******************************************************************************/
package org.eclipse.egerrit.dashboard.ui.internal.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.egerrit.core.rest.ChangeInfo;

/**
 * This class implements the review table view information.
 *
 * @since 1.0
 */
public class ReviewTableData {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	// The list of reviews indexed by the SHORT_CHANGE_ID
	private Map<String, ChangeInfo> fReviewList;

	private GerritServerInformation fServerInfo = null;

	private String fQuery = null;

	/**
	 * Create a new review entry to insert to the list of reviews
	 *
	 * @param Object
	 */
	public void createReviewItem(ChangeInfo[] aList, String aQuery, GerritServerInformation aServerInfo) {

		// Create the new object
		// if (fQuery != aQuery) {
		fReviewList = new ConcurrentHashMap<String, ChangeInfo>();
		for (ChangeInfo review : aList) {
			fReviewList.put(review.getId(), review);
		}
		fServerInfo = aServerInfo;
		fQuery = aQuery;

		// } else {
		// //Need to reset the list, we just created a null entry
		// reset();
		// }
	}

	public void createReviewItem(String query, GerritServerInformation repository) {
		fReviewList = new HashMap<String, ChangeInfo>();
		fServerInfo = repository;
		fQuery = query;
	}

	public void updateReviewItem(ChangeInfo task) {
		fReviewList.put(task.getId(), task);
	}

	public void deleteReviewItem(String taskId) {
		fReviewList.remove(taskId);
	}

	/**
	 * Provide the list of review available for the table list
	 *
	 * @return the list of Gerrit reviews
	 */
	public ChangeInfo[] getReviews() {
		if (fReviewList == null) {
			fReviewList = new HashMap<String, ChangeInfo>();
		}
		return fReviewList.values().toArray(new ChangeInfo[0]);
	}

	/**
	 * Provide the review with the specified ID
	 *
	 * @param id
	 *            the requested ID (SHORT_CHANGE_ID)
	 * @return the requested review (or null)
	 */
	public ChangeInfo getReview(String id) {
		if (id != null && fReviewList.containsKey(id)) {
			return fReviewList.get(id);
		}
		return null;
	}

	/**
	 * Return the query information used to populate the review table
	 *
	 * @return String
	 */
	public String getQueryInfo() {
		return fQuery;
	}

	public void init(ChangeInfo[] reviews) {
		if (reviews != null) {
			for (ChangeInfo review : reviews) {
				fReviewList.put(review.getId(), review);
				review.setLabels(review.getLabels());
			}
		}
	}

}
