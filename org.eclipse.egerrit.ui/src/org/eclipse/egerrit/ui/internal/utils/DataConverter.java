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
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.GitPersonInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.editors.QueryHelpers;
import org.eclipse.egerrit.ui.internal.table.model.SubmitType;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

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
	 * @param fChangeInfo
	 *            ChangeInfo
	 * @return an IConverter from the Submit type structure to a format to display
	 */
	public static IConverter submitTypeConverter(final ChangeInfo fChangeInfo) {
		IConverter converter = new Converter(MergeableInfo.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !fromObject.equals("")) { //$NON-NLS-1$
					MergeableInfo mInfo = (MergeableInfo) fromObject;
					return SubmitType.getEnumName(mInfo.getSubmit_type());
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
	 * Insert the tag for the link text
	 *
	 * @return
	 */
	public static IConverter linkText() {
		return new Converter(String.class, String.class) {

			@Override
			public Object convert(Object fromObject) {
				if (fromObject == null) {
					return "";
				}
				return new StringBuilder().append("<a>").append(fromObject).append("</a>").toString();
			}
		};
	}

	/**
	 * Convert a string to a Document
	 *
	 * @param gerritClient
	 * @param selection
	 * @param changeInfo
	 * @param changeInfo
	 * @param selection
	 * @param gerritClient
	 * @return IConverter
	 */
	public static IConverter fromStringToDocument(GerritClient gerritClient) {
		return new Converter(String.class, IDocument.class) {

			@Override
			public Object convert(Object fromObject) {
				if (fromObject == null) {
					return null;
				}
				ChangeMessageInfo message = ((ChangeMessageInfo) fromObject);

				//There is no comment
				if (!message.isComment()) {
					return new Document(message.getMessage());
				}

				//Get comments and format the message with those
				ChangeInfo containingChange = (ChangeInfo) message.eContainer();
				RevisionInfo selectedRevision = containingChange.getRevisionByNumber(message.get_revision_number());
				if (selectedRevision != null) {
					QueryHelpers.loadComments(gerritClient, selectedRevision);
				}

				return new Document(message.getMessage() + "\n" + formatMessageWithComments(message, selectedRevision)); //$NON-NLS-1$
			}
		};

	}

	/**
	 * Creating a string with the file path and associated comments to this file.
	 *
	 * @param chmsgInfo
	 * @param revInfo
	 * @return String
	 */
	private static String formatMessageWithComments(ChangeMessageInfo chmsgInfo, RevisionInfo revInfo) {
		StringBuilder sb = new StringBuilder();
		Collection<FileInfo> files = revInfo.getFiles().values();
		for (FileInfo fileInfo : files) {
			if (!fileInfo.getComments().isEmpty()) {
				sb.append("\n"); //$NON-NLS-1$
				sb.append(fileInfo.getPath());
				Iterator<CommentInfo> commentsIter = fileInfo.getComments().iterator();
				while (commentsIter.hasNext()) {
					//List the comments
					CommentInfo comment = commentsIter.next();
					if (comment.getUpdated().equals(chmsgInfo.getDate())) {
						sb.append("\n\t Comment: ");
						sb.append(comment.getMessage());
					}
				}
				sb.append("\n"); //$NON-NLS-1$
			}
		}
		return sb.toString();
	}

	/**
	 * Insert the tag for the link text
	 *
	 * @param fChangeInfo
	 * @return
	 */
	public static IConverter patchSetSelected(ChangeInfo changeInfo) {
		return new Converter(String.class, String.class) {
			@Override
			public Object convert(Object fromObject) {
				if (fromObject == null) {
					return ""; //$NON-NLS-1$
				}
				final String PATCHSET = "Patch Sets ";
				final String SEPARATOR = "/"; //$NON-NLS-1$
				RevisionInfo revInfo = changeInfo.getUserSelectedRevision();
				StringBuilder sb = new StringBuilder();
				sb.append(PATCHSET);
				sb.append(revInfo.get_number());
				sb.append(SEPARATOR);
				sb.append(changeInfo.getRevision().get_number());
				return sb.toString();
			}
		};
	}
}