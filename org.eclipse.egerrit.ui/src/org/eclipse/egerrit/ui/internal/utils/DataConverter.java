/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.internal.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.rest.ApprovalInfo;
import org.eclipse.egerrit.core.rest.CommitInfo;
import org.eclipse.egerrit.core.rest.GitPersonInfo;
import org.eclipse.egerrit.core.rest.LabelInfo;
import org.eclipse.egerrit.core.utils.DisplayFileInfo;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.ui.internal.table.model.SubmitType;

/**
 * This class in used to transform the Gerrit data to a databinding display value
 *
 * @since 1.0
 */
public class DataConverter {

	/**
	 * @param outputTime
	 *            SimpleDateFormat requested
	 * @return an IConverter from the Gerrit Timestamp to a new format
	 */
	public static IConverter gerritTimeConverter(final SimpleDateFormat outputTime) {
		IConverter converter = new Converter(String.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");// Gerrit timestamp format //$NON-NLS-1$

				Date dateNew = null;

				if (fromObject != null && !fromObject.equals("")) { //$NON-NLS-1$
					try {
						formatIn.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
						dateNew = formatIn.parse((String) fromObject);
					} catch (ParseException ex) {
						EGerritCorePlugin.logError(ex.getMessage());
					}
					return outputTime.format(dateNew).toString();

				}
				return ""; //$NON-NLS-1$

			}
		};
		return converter;

	}

	/**
	 * @return an IConverter from the GitPersonInfo structure to a format having the name and e-mail information
	 */
	public static IConverter gitPersonConverter() {
		IConverter converter = new Converter(GitPersonInfo.class, String.class) {
			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !fromObject.equals("")) { //$NON-NLS-1$
					GitPersonInfo person = (GitPersonInfo) fromObject;
					if (person.getName() != null && person.getEmail() != null) {
						// "<a>  </a> is to allow the link selection of the text
						return "<a>" + person.getName() + "<" + person.getEmail() + ">" + "</a>"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					} else {
						return null;
					}
				} else {
					return null;
				}
			}
		};
		return converter;

	}

	/**
	 * @param type
	 *            String
	 * @return an IConverter from the Submit type structure to a format to display
	 */
	public static IConverter submitTypeConverter(final String type) {
		IConverter converter = new Converter(type, null) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !fromObject.equals("")) { //$NON-NLS-1$
					return SubmitType.valueOf((String) fromObject).getValue();
				} else {
					return null;
				}
			}
		};
		return converter;

	}

	/**
	 * @return an IConverter from the boolean structure to the "Cannot Merge" to display
	 */
	public static IConverter cannotMergeConverter() {
		IConverter converter = new Converter(Boolean.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !fromObject.equals("")) { //$NON-NLS-1$
					return new Boolean((boolean) fromObject).booleanValue() ? "" : "CANNOT MERGE"; //$NON-NLS-1$
				} else {
					return null;
				}
			}
		};
		return converter;

	}

	/**
	 * @return an IConverter from the a list of string to a string display
	 */
	public static <E> IConverter stringListConverter() {
		IConverter converter = new Converter(List.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !((ArrayList<?>) fromObject).isEmpty()) {
					return ((ArrayList<?>) fromObject).toString().replace('[', ' ').replace(']', ' ').trim();
				} else {
					return null;
				}
			}
		};
		return converter;

	}

	/**
	 * @return an IConverter to convert the commit info to a string format to display
	 */
	public static IConverter commitInfoIDConverter() {
		IConverter converter = new Converter(List.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !((ArrayList<?>) fromObject).isEmpty()) {
					return ((CommitInfo) ((ArrayList<?>) fromObject).get(0)).getCommit();
				} else {
					return null;
				}
			}
		};
		return converter;

	}

	/**
	 * @return an IConverter from the ChangeInfo.labels structure to a format having the global count information
	 */
	public static IConverter reviewersVoteConverter() {
		IConverter converter = new Converter(Map.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !fromObject.equals("")) { //$NON-NLS-1$
					String codeReviewed = null;
					int verifyState = 0;
					int codeReviewState = 0;
					Map<String, LabelInfo> labels = (Map<String, LabelInfo>) fromObject;
					for (Map.Entry<String, LabelInfo> entry : labels.entrySet()) {
						if (entry.getKey().compareTo("Verified") == 0) { //$NON-NLS-1$
							LabelInfo labelInfo = entry.getValue();
							if (labelInfo != null && labelInfo.getAll() != null) {
								for (ApprovalInfo it : labelInfo.getAll()) {
									if (it.getValue() != null) {
										verifyState = Utils.getStateValue(it.getValue(), verifyState);
									}
								}
							}
						} else if (entry.getKey().compareTo("Code-Review") == 0) { //$NON-NLS-1$
							LabelInfo labelInfo = entry.getValue();
							if (labelInfo != null && labelInfo.getAll() != null) {
								for (ApprovalInfo it : labelInfo.getAll()) {
									if (it.getValue() != null) {
										codeReviewState = Utils.getStateValue(it.getValue(), codeReviewState);
									}
								}
							}
						}
						String codeRev;
						if (codeReviewState > 0) {
							codeRev = "+" + Integer.toString(codeReviewState); //$NON-NLS-1$
						} else {
							codeRev = Integer.toString(codeReviewState);
						}
						String verify;
						if (verifyState > 0) {
							verify = "+" + Integer.toString(verifyState); //$NON-NLS-1$
						} else {
							verify = Integer.toString(verifyState);
						}

						codeReviewed = codeRev + "       " + verify; //$NON-NLS-1$
					}
					return codeReviewed;
				} else {
					return null;
				}
			}
		};
		return converter;
	}

	public static IConverter commentLineCounter() {
		return new Converter(Map.class, String.class) {
//		return new Converter(List.class, String.class) {

			@Override
			public Object convert(Object fromObject) {
				int lineInserted = 0;
				int lineDeleted = 0;
				List<DisplayFileInfo> modifiedFiles = (List<DisplayFileInfo>) ((Map<String, DisplayFileInfo>) fromObject)
						.values();
				for (DisplayFileInfo fileInfo : modifiedFiles) {
					lineInserted += fileInfo.getLinesInserted();
					lineDeleted += fileInfo.getLinesDeleted();
				}
				return new StringBuilder().append("+")
						.append(Integer.toString(lineInserted))
						.append("/")
						.append("-")
						.append(Integer.toString(lineDeleted))
						.toString();
			}
		};
	}

	/**
	 * Insert the tag for the link text
	 *
	 * @return
	 */
	public static IConverter linkText() {
		return new Converter(String.class, String.class) {

			@Override
			public Object convert(Object fromObject) {
				return new StringBuilder().append("<a>").append(fromObject).append("</a>").toString();
			}
		};
	}

}