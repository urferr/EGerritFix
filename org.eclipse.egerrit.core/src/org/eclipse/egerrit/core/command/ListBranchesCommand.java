/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.BranchInfo;

/**
 * The command GET /projects/{project-name}/branches/
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-projects.html#list-branches
 * <p>
 *
 * @since 1.0
 */
public class ListBranchesCommand extends BaseCommand<BranchInfo[]> {
	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the project
	 */
	public ListBranchesCommand(GerritRepository gerritRepository, String project) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, BranchInfo[].class);
		setPathFormat("/projects/{project-name}/branches/"); //$NON-NLS-1$
		setSegmentToEncode("{project-name}", project); //$NON-NLS-1$
	}

}
