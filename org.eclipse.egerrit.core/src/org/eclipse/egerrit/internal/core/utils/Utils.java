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

package org.eclipse.egerrit.internal.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;

/**
 * @author lmcbout
 * @since 1.0
 */
public class Utils {

	private final static SimpleDateFormat formatTimeOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$

	private final static SimpleDateFormat sameYearFormatTimeOut = new SimpleDateFormat("MMM dd HH:mm"); //$NON-NLS-1$

	private final static SimpleDateFormat sameDayFormatTimeOut = new SimpleDateFormat("HH:mm aa"); //$NON-NLS-1$

	private final static SimpleDateFormat differentYearFormatTimeOut = new SimpleDateFormat("MMM dd, yyyy"); //$NON-NLS-1$

	/**
	 * Format the UTC time from Gerrit data structure to a new desired format
	 *
	 * @param inDate
	 * @param formatOut
	 * @return String
	 */
	public static String formatDate(String inDate, SimpleDateFormat formatOut) {

		SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");// Gerrit timestamp format //$NON-NLS-1$

		Date dateNew = null;

		try {
			formatIn.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
			dateNew = formatIn.parse(inDate);
		} catch (ParseException ex) {
			EGerritCorePlugin.logError(ex.getMessage());
		}
		return formatOut.format(dateNew).toString();
	}

	/**
	 * Format the time from Gerrit data structure to a format depending on the current time
	 *
	 * @param inDate
	 * @return String
	 */
	public static String prettyPrintDate(String inDate) {
		java.util.Date date = null;
		long hourMSec = 3600000;
		try {
			formatTimeOut.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
			date = formatTimeOut.parse(inDate);
		} catch (ParseException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}
		boolean sameDay = DateUtils.isSameDay(date, Calendar.getInstance().getTime());
		Calendar today = Calendar.getInstance();
		Calendar currentReview = Calendar.getInstance();
		long diffMSec = today.getTimeInMillis() - date.getTime();
		currentReview.setTime(date);
		if (sameDay || (diffMSec <= (6 * hourMSec))) { // format if same day or not more than 6 hours apart
			return Utils.formatDate(inDate, sameDayFormatTimeOut);
		} else if (today.get(Calendar.YEAR) != currentReview.get(Calendar.YEAR)) {
			return Utils.formatDate(inDate, differentYearFormatTimeOut);
		} else {
			return Utils.formatDate(inDate, sameYearFormatTimeOut);
		}
	}
}
