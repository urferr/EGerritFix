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

package org.eclipse.egerrit.ui.editors.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.command.GetContentCommand;
import org.eclipse.egerrit.core.command.ListCommentsCommand;
import org.eclipse.egerrit.core.command.ListDraftsCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;

public class CompareItemFactory {

	private final static String TITLE = "Gerrit Server ";

	private final static SimpleDateFormat formatTimeOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$

	private Gerrit gerrit;

	private CompareItem newCompareItem = new CompareItem();

	public CompareItemFactory(Gerrit gerrit) {
		this.gerrit = gerrit;
	}

	/**
	 * Create an instance of a CompareItem
	 */
	public CompareItem createCompareItem(String filename, String change_id, FileInfo fileInfo,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Obtaining revision content", IProgressMonitor.UNKNOWN);

			newCompareItem.setFilename(filename);
			newCompareItem.setChange_id(change_id);
			newCompareItem.setGerritConnection(gerrit);
			newCompareItem.setRevision(fileInfo);

			String fileContent = null;

			// Create query
			if (gerrit != null) {
				GetContentCommand command = gerrit.getContent(change_id, fileInfo.getContainingRevisionInfo().getId(),
						filename);

				if (!"D".equals(fileInfo.getStatus())) { //$NON-NLS-1$
					try {
						fileContent = command.call();
						if (fileContent == null) {
							fileContent = ""; //$NON-NLS-1$
						}
					} catch (EGerritException e) {
						EGerritCorePlugin.logError(e.getMessage());
					} catch (ClientProtocolException e) {
						UIUtils.displayInformation(null, TITLE,
								e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
					}
				} else {
					fileContent = ""; //$NON-NLS-1$
				}
				Map<String, ArrayList<CommentInfo>> comments = loadComments(gerrit, change_id,
						fileInfo.getContainingRevisionInfo().getId());
				mergeCommentsInText(StringUtils.newStringUtf8(Base64.decodeBase64(fileContent)),
						comments.get(filename));

			}
		} finally

		{
			monitor.done();
		}
		return newCompareItem;

	}

	private Map<String, ArrayList<CommentInfo>> loadComments(Gerrit gerrit, String change_id, String revision_id) {
		Map<String, ArrayList<CommentInfo>> allComments = new HashMap<>();
		try {
			ListCommentsCommand getComments = gerrit.getListComments(change_id, revision_id);
			allComments = getComments.call();
			ListDraftsCommand getDrafts = gerrit.getDraftcomments(change_id, revision_id);
			mergeComments(getDrafts.call(), allComments);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EGerritException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allComments;

	}

	private void mergeComments(Map<String, ArrayList<CommentInfo>> source, Map<String, ArrayList<CommentInfo>> target) {
		Set<Entry<String, ArrayList<CommentInfo>>> entries = source.entrySet();
		for (Entry<String, ArrayList<CommentInfo>> entry : entries) {
			ArrayList<CommentInfo> targetValue = target.get(entry.getKey());
			if (targetValue == null) {
				target.put(entry.getKey(), entry.getValue());
			} else {
				targetValue.addAll(entry.getValue());
			}
		}
	}

	//Take the original text and merge the comments into it
	//The insertion of comments starts from by last comment and proceed toward the first one. This allows for the insertion line to always be correct.
	private void mergeCommentsInText(String text, ArrayList<CommentInfo> comments) {
		Document document = new Document(text);
		AnnotationModel gerritComments = new AnnotationModel();
		newCompareItem.setDocumentWithComments(document);
		newCompareItem.setGerritComments(gerritComments);
		gerritComments.connect(document);
		if (comments == null) {
			return;
		}

		sortComments(comments);

		for (CommentInfo commentInfo : comments) {
			//We only consider the comments that apply to the revision
			if (commentInfo.getSide() != null && !commentInfo.getSide().equals("REVISION")) { //$NON-NLS-1$
				continue;
			}
			if (commentInfo.getLine() > 0) {
				IRegion lineInfo;
				try {
					int insertionLineInDocument = commentInfo.getLine() - 1;
					lineInfo = document.getLineInformation(insertionLineInDocument);
					String lineDelimiter = document.getLineDelimiter(insertionLineInDocument);
					int insertionPosition = lineInfo.getOffset() + lineInfo.getLength()
							+ (lineDelimiter == null ? 0 : lineDelimiter.length());
					String formattedComment = formatComment(commentInfo, lineDelimiter != null,
							document.getDefaultLineDelimiter());
					document.replace(insertionPosition, 0, formattedComment);
					gerritComments.addAnnotation(new GerritCommentAnnotation(commentInfo, formattedComment),
							new Position(insertionPosition, formattedComment.length()));
				} catch (BadLocationException e) {
					//Ignore and continue
				}
			} else {
				try {
					document.replace(0, 0, formatComment(commentInfo, true, document.getDefaultLineDelimiter()));
				} catch (BadLocationException e) {
					//Ignore and continue
				}
			}
		}
	}

	private static String formatComment(CommentInfo comment, boolean endsWithDelimiter, String defaultDelimiter) {
		return (endsWithDelimiter ? "" : defaultDelimiter) + getName(comment) + '\t' + comment.getMessage() //$NON-NLS-1$
				+ '\t' + Utils.formatDate(comment.getUpdated(), formatTimeOut) + defaultDelimiter;
	}

	private static String getName(CommentInfo comment) {
		if (comment.getAuthor() == null) {
			return "Draft"; //$NON-NLS-1$
		}
		return comment.getAuthor().getName();
	}

	private static void sortComments(ArrayList<CommentInfo> comments) {
		Collections.sort(comments, new Comparator<CommentInfo>() {
			@Override
			public int compare(CommentInfo o1, CommentInfo o2) {
				if (o1.getLine() == o2.getLine()) {
					return o1.getUpdated().compareTo(o2.getUpdated());
				}
				if (o1.getLine() < o2.getLine()) {
					return -1;
				}
				return 1;
			}
		});
		Collections.reverse(comments);
	}
}
