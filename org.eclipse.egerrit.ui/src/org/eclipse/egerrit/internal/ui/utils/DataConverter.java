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

package org.eclipse.egerrit.internal.ui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.GitPersonInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.table.model.SubmitType;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.osgi.util.NLS;

/**
 * This class in used to transform the Gerrit data to a databinding display value
 *
 * @since 1.0
 */
public class DataConverter {

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private DataConverter() {
	}

	/**
	 * @param outputTime
	 *            SimpleDateFormat requested
	 * @return an IConverter from the Gerrit Timestamp to a new format
	 */
	public static IConverter gerritTimeConverter(final SimpleDateFormat outputTime) {
		return new Converter(String.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");// Gerrit timestamp format //$NON-NLS-1$

				Date dateNew = null;

				if (fromObject != null && !"".equals(fromObject)) { //$NON-NLS-1$
					try {
						formatIn.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
						dateNew = formatIn.parse((String) fromObject);
					} catch (ParseException ex) {
						EGerritCorePlugin.logError(ex.getMessage());
					}
					return outputTime.format(dateNew);

				}
				return ""; //$NON-NLS-1$

			}
		};

	}

	/**
	 * @return an IConverter from the GitPersonInfo structure to a format having the name and e-mail information
	 */
	public static IConverter gitPersonConverter() {
		return new Converter(GitPersonInfo.class, String.class) {
			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !"".equals(fromObject)) { //$NON-NLS-1$
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

	}

	/**
	 * @return an IConverter from the Submit type structure to a format to display
	 */
	public static IConverter submitTypeConverter() {
		return new Converter(MergeableInfo.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !"".equals(fromObject)) { //$NON-NLS-1$
					MergeableInfo mInfo = (MergeableInfo) fromObject;
					return SubmitType.getEnumName(mInfo.getSubmit_type());
				} else {
					return null;
				}
			}
		};

	}

	/**
	 * @return an IConverter from the boolean structure to the "Cannot Merge" to display
	 */
	public static IConverter cannotMergeConverter() {
		return new Converter(Boolean.class, String.class) {

			@Override
			public Object convert(Object fromObject) {

				if (fromObject != null && !"".equals(fromObject)) { //$NON-NLS-1$
					return new Boolean((boolean) fromObject).booleanValue() ? "" : Messages.DataConverter_0; //$NON-NLS-1$
				} else {
					return null;
				}
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
				if (fromObject == null) {
					return ""; //$NON-NLS-1$
				}
				return new StringBuilder().append("<a>").append(fromObject).append("</a>").toString(); //$NON-NLS-1$ //$NON-NLS-2$
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
				ChangeMessageInfo message = (ChangeMessageInfo) fromObject;

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
		if (revInfo != null) {
			Collection<FileInfo> files = revInfo.getFiles().values();
			for (FileInfo fileInfo : files) {
				if (!fileInfo.getComments().isEmpty()) {
					sb.append("\n"); //$NON-NLS-1$
					sb.append(fileInfo.getPath());

					Iterator<CommentInfo> commentsIter = fileInfo.getComments().iterator();
					while (commentsIter.hasNext()) {
						//List the comments available for this timestamp only
						CommentInfo comment = commentsIter.next();
						if (chmsgInfo.getDate().equals(comment.getUpdated())) {
							sb.append(NLS.bind(Messages.DataConverter_4,
									new Object[] { comment.getLine(), comment.getMessage() }));
						}
					}
					sb.append("\n"); //$NON-NLS-1$
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Insert the tag for the link text
	 *
	 * @param fGerritClient
	 * @param fChangeInfo
	 * @return
	 */
	public static IConverter patchSetSelected(ChangeInfo changeInfo, GerritClient gerritClient) {
		return new Converter(String.class, String.class) {
			@Override
			public Object convert(Object fromObject) {
				if (fromObject == null || changeInfo.getUserSelectedRevision() == null) {
					//Force this string to be long to reserve the space because the first time things get rendered there is no selection
					return "                               "; //$NON-NLS-1$
				}
				RevisionInfo revInfo = changeInfo.getUserSelectedRevision();
				//initiate a request for the related changes  for the proper patch-set
				CompletableFuture.runAsync(() -> {
					QueryHelpers.loadRelatedChanges(gerritClient, changeInfo, revInfo.getId());
				});

				StringBuilder sb = new StringBuilder();
				sb.append(Messages.DataConverter_5);
				sb.append(revInfo.get_number());
				sb.append("/"); //$NON-NLS-1$
				sb.append(changeInfo.getRevision().get_number());
				return sb.toString();
			}
		};
	}
}