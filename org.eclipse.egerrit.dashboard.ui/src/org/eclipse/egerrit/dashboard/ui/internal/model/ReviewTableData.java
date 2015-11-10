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

	public void createReviewItem(String query, GerritServerInformation repository) {
		fReviewList = new HashMap<String, ChangeInfo>();
		fServerInfo = repository;
		fQuery = query;
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
