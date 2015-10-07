/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *     Jacques Bouthillier - Add new command for the search operator
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egerrit.core.GerritRepository;

/**
 * The Gerrit command base class for queries.
 *
 * @param <T>
 *            the return type which is expected from {@link #call()}
 * @since 1.0
 */
public abstract class QueryCommand<T> extends GerritCommand<T> {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	public static final String OWNER = "OWNER"; //$NON-NLS-1$

	public static final String STATE = "STATE"; //$NON-NLS-1$

	public static final String STATUS = "STATUS"; //$NON-NLS-1$

	public static final String TOPIC = "TOPIC"; //$NON-NLS-1$

	public static final String CONFLICTS = "CONFLICTS"; //$NON-NLS-1$

	public static final String FREE = "FREE"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/** The maximum number of results per query */
	protected int fCount = 0;

	/** The list of request parameters */
	protected final Map<String, List<String>> fQueryParameters = new LinkedHashMap<String, List<String>>();

	/** The list of request options */
	protected final List<String> fQueryOptions = new ArrayList<String>();

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	protected QueryCommand(GerritRepository gerritRepository, Type returnType) {
		super(gerritRepository, returnType);
	}

	// ------------------------------------------------------------------------
	// Query parameters handling
	// ------------------------------------------------------------------------

	/**
	 * Adds a parameter to the list of command parameters
	 *
	 * @param parameter
	 *            the parameter to add
	 */
	protected void addParameter(String key, String value) {
		if (!fQueryParameters.containsKey(key)) {
			fQueryParameters.put(key, new ArrayList<String>());
		}
		fQueryParameters.get(key).add(value);
	}

	/**
	 * Concatenate the query parameters in the form: "param1+param2+...+paramN"
	 *
	 * @return the concatenated query parameters
	 */
	protected String buildParametersList() {
		StringBuilder sb = new StringBuilder();
		Iterator<String> parameters = fQueryParameters.keySet().iterator();
		while (parameters.hasNext()) {
			Iterator<String> values = fQueryParameters.get(parameters.next()).listIterator();
			while (values.hasNext()) {
				sb.append(values.next());
				if (values.hasNext()) {
					sb.append(" "); //$NON-NLS-1$
				}
			}
			if (parameters.hasNext()) {
				sb.append("+"); //$NON-NLS-1$
			}
		}
		return sb.toString().trim();
	}

	// ------------------------------------------------------------------------
	// Query options handling
	// ------------------------------------------------------------------------

	public void addOption(ChangeOption option) {
		fQueryOptions.add(option.getValue());
	}

	// ------------------------------------------------------------------------
	// Query count
	// ------------------------------------------------------------------------

	public void setCount(int count) {
		fCount = count;
	}

}
