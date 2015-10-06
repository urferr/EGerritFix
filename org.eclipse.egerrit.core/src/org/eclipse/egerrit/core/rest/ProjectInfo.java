/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

import java.util.Map;

public class ProjectInfo {

	private String id;

	//The name of the project.
	private String name;

	//	The name of the parent project.
	//	?-<n> if the parent project is not visible (<n> is a number which is increased for each non-visible project).
	private String parent;

	//The description of the project.
	private String description;

	//ACTIVE, READ_ONLY or HIDDEN.
	private String state;

	//	Map of branch names to HEAD revisions.
	private Map<String, String> branches;

	/**
	 * Get the id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the parent
	 * 
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Get the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the state
	 * 
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Get the branches
	 * 
	 * @return a map of branches
	 */
	public Map<String, String> getBranches() {
		return branches;
	}

}
