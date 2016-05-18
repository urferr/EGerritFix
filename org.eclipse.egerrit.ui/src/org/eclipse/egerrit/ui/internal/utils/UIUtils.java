/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in utility
 ******************************************************************************/

package org.eclipse.egerrit.ui.internal.utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;

import org.eclipse.core.resources.IFile;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.ModelHelpers;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.compare.CommentPrettyPrinter;
import org.eclipse.egerrit.ui.editors.ModelLoader;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.editors.ReplyDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the Gerrit UI utility.
 *
 * @since 1.0
 */

public class UIUtils {

	private static Logger logger = LoggerFactory.getLogger(UIUtils.class);

	/**
	 * To display some information to the end-user
	 *
	 * @param shell
	 * @param title
	 * @param message
	 */
	public static void displayInformation(final Shell shell, final String title, final String message) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openInformation(shell, title, message);
			}
		});
	}

	/**
	 * Compute the size of the Fonts used for this composite
	 *
	 * @param composite
	 * @return Point
	 */
	public static Point computeFontSize(Composite composite) {
		GC gc = new GC(composite);
		FontMetrics fm = gc.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();

		gc.dispose();
		return new Point(charWidth, fm.getHeight());
	}

	/**
	 * Remove the link argument and return the text
	 *
	 * @return String
	 */
	public static String getLinkText(String text) {
		text = text.replaceFirst("<a>", ""); //$NON-NLS-1$ //$NON-NLS-2$
		text = text.replaceFirst("</a>", ""); //$NON-NLS-1$//$NON-NLS-2$
		return text.trim();
	}

	public static void replyToChange(Shell shell, RevisionInfo revisionInfo, String reason, GerritClient client) {
		String current = revisionInfo.getId();
		final ReplyDialog replyDialog = new ReplyDialog(shell, reason, revisionInfo, client);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				int ret = replyDialog.open();
				if (ret == IDialogConstants.OK_ID) {
					//Fill the data structure for the reply
					ReviewInput reviewInput = new ReviewInput();
					reviewInput.setMessage(replyDialog.getValue());
					reviewInput.setLabels(replyDialog.getRadiosSelection());
					reviewInput.setDrafts(ReviewInput.DRAFT_PUBLISH);

					CompletableFuture.runAsync(() -> {
						try {
							SetReviewCommand reviewToEmit = client.setReview(revisionInfo.getChangeInfo().getId(),
									current);
							reviewToEmit.setCommandInput(reviewInput);
							reviewToEmit.call();
						} catch (EGerritException e1) {
							EGerritCorePlugin.logError(client.getRepository().formatGerritVersion() + e1.getMessage());

						}
					}).thenRun(() -> {
						ModelLoader loader = ModelLoader.initialize(client, revisionInfo.getChangeInfo());
						loader.loadBasicInformation();
						loader.dispose();
					});
				}
			}
		});
	}

	public static String revisionToString(RevisionInfo revisionInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(revisionInfo.get_number())); //
		sb.append("   ");
		appendCommitTime(sb, revisionInfo);
		sb.append("   ");
		appendUserCommiter(sb, revisionInfo);
		if (revisionInfo.isDraft()) {
			sb.append(" (draft)");
		}
		return sb.toString();
	}

	public static void postReply(GerritClient gerritClient, CommentInfo comment, String reply, String revisionId) {
		if (reply.trim().length() == 0) {
			return;
		}
		CreateDraftCommand publishDraft = gerritClient
				.createDraftComments(ModelHelpers.getRevision(comment).getChangeInfo().getId(), revisionId);

		CommentInfo replyData = ModelFactory.eINSTANCE.createCommentInfo();
		replyData.setMessage(reply);
		replyData.setInReplyTo(comment.getId());
		replyData.setLine(comment.getLine());
		replyData.setSide(comment.getSide());
		replyData.setPath(ModelHelpers.getFileInfo(comment).getPath());
		publishDraft.setCommandInput(replyData);
		try {
			ModelHelpers.getFileInfo(comment).getDraftComments().add(publishDraft.call());
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
		}
	}

	private static void appendCommitTime(StringBuilder sb, RevisionInfo revisionInfo) {
		if (revisionInfo.getCommit() != null) {
			sb.append(Utils.formatDate(revisionInfo.getCommit().getCommitter().getDate(),
					new SimpleDateFormat("MMM dd, yyyy hh:mm a"))); //$NON-NLS-1$
		}
	}

	private static void appendUserCommiter(StringBuilder sb, RevisionInfo revisionInfo) {
		if (revisionInfo.getCommit() != null) {
			if (revisionInfo.getCommit().getAuthor() != null) {
				//Use the Author
				sb.append(revisionInfo.getCommit().getAuthor().getName());
			}
			if (!revisionInfo.getCommit()
					.getAuthor()
					.getName()
					.equals(revisionInfo.getCommit().getCommitter().getName())) {
				//Add the committer if different than the Author
				sb.append("/"); //$NON-NLS-1$
				sb.append(revisionInfo.getCommit().getCommitter().getName());
			}
		}
	}

	public static String formatMessageForMarkerView(CommentInfo commentInfo) {
		String patchSet = ModelHelpers.getRevision(commentInfo).get_number() + "/" //$NON-NLS-1$
				+ ModelHelpers.getRevision(commentInfo).getChangeInfo().getRevisions().size();
		String author = commentInfo.getAuthor() != null ? commentInfo.getAuthor().getName() : "Me";
		return commentInfo.getMessage() + "\n\nWritten by " + author + " at "
				+ CommentPrettyPrinter.printDate(commentInfo) + " for patchset " + patchSet + ".";
	}

	public static String formatMessageForQuickFix(CommentInfo commentInfo) {
		String message = commentInfo.getMessage();
		if (message.length() < 20) {
			return message;
		}
		int nextSpace = message.indexOf(' ', 20);
		if (nextSpace == -1) {
			return message;
		}
		return message.substring(0, nextSpace) + " ..."; //$NON-NLS-1$
	}

	public static String getPatchSetString(CommentInfo comment) {
		return getPatchSetString(ModelHelpers.getRevision(comment));
	}

	public static String getPatchSetString(RevisionInfo revision) {
		return revision.get_number() + "/" //$NON-NLS-1$
				+ revision.getChangeInfo().getRevisions().size();
	}

	/**
	 * Open a compare editor against the another version of the file
	 *
	 * @param key
	 * @param gerritClient
	 * @param fileInfo
	 * @param changeInfo
	 * @param leftSide
	 */
	public static void open(GerritClient gerritClient, FileInfo fileInfo, ChangeInfo changeInfo, String leftSide) {
		OpenCompareEditor compareEditor = new OpenCompareEditor(gerritClient, changeInfo);
		compareEditor.compareFiles(leftSide, fileInfo.getRevision().getId(), fileInfo);
	}

	/**
	 * Open a file in a single editor
	 *
	 * @param key
	 * @param revInfo
	 * @param line
	 */
	public static void openSingleFile(Object key, GerritClient gerritClient, RevisionInfo revInfo, int line) {
		FileInfo fileInfo = revInfo.getFiles().get(key);
		if (fileInfo != null) {
			IFile workspaceFile = new OpenCompareEditor(gerritClient, revInfo.getChangeInfo())
					.getCorrespondingWorkspaceFile(fileInfo);

			if (workspaceFile == null) {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openError(shell, "File not found", "File " + fileInfo.getPath() + " could not be found");
				return;
			}
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			try {
				IDE.openEditor(page, workspaceFile);
			} catch (PartInitException e1) {
				logger.debug("Failed to delete marker", e1); //$NON-NLS-1$
			}
		}
	}
}
