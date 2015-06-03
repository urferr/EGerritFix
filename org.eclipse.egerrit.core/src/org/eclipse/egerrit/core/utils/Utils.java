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

package org.eclipse.egerrit.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.egerrit.core.EGerritCorePlugin;

/**
 * @author lmcbout
 * @since 1.0
 */
public class Utils {

	/**
	 * Format the UTC time from Gerrit data structure to a new desired format
	 *
	 * @param inDate
	 * @param formatOut
	 * @return String
	 */
	public static String formatDate(String inDate, SimpleDateFormat formatOut) {

		SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");// Gerrit timestamp format

		Date dateNew = null;

		try {
			formatIn.setTimeZone(TimeZone.getTimeZone("UTC"));
			dateNew = formatIn.parse(inDate);
		} catch (ParseException ex) {
			EGerritCorePlugin.logError(ex.getMessage());
		}
		return formatOut.format(dateNew).toString();
	}

	/**
	 * Determines the right value for voting tally
	 *
	 * @param newState
	 * @param oldState
	 * @return Integer
	 */
	public static Integer getStateValue(Integer newState, Integer oldState) {
		Integer state = 0;
		if (newState < 0) {
			state = Math.min(oldState, newState);
		} else if (newState > 0) {
			state = Math.max(oldState, newState);
		} else {
			state = oldState;
		}

		return state;
	}

}
