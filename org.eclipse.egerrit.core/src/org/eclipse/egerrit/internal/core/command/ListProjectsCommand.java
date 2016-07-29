/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.command;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.model.ProjectInfo;

import com.google.gson.reflect.TypeToken;

/**
 * The command: GET /projects/?d=&p=cdt&n=5
 * <p>
 * https://gerrit-review.googlesource.com/Documentation/rest-api-projects.html#list-projects
 */
public class ListProjectsCommand extends BaseCommand<Map<String, ProjectInfo>> {

	/**
	 * Construct a suggest account command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 */
	public ListProjectsCommand(GerritRepository gerritRepository) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, new TypeToken<Map<String, ProjectInfo>>() {
		}.getType());
		setPathFormat("/projects/"); //$NON-NLS-1$
		addQueryParameter("d", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Sets the string to be used to query a list of matching projects.
	 */
	public void setQuery(String query) {
		addQueryParameter("p", query); //$NON-NLS-1$
	}

	/**
	 * Sets the maximum number of suggestions.
	 */
	public void setMaxNumberOfResults(int limit) {
		addQueryParameter("n", Integer.toString(limit)); //$NON-NLS-1$
	}
}
