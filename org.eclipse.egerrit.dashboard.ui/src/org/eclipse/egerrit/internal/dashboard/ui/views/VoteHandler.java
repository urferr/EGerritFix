/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.views;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.egerrit.internal.dashboard.ui.utils.UIUtils;
import org.eclipse.egerrit.internal.model.ApprovalInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class providing the logic to enable tooltip and click on the vote columns in the dashboard
 */
class VoteHandler implements Listener, MouseListener {
	private static Logger logger = LoggerFactory.getLogger(VoteHandler.class);

	private static final String URL_REG_EXP = "(?:^|[\\W])((http|https|ftp|file):\\/\\/|[A-Za-z][\\.|\\/])(([\\w\\-]+[\\.|\\/]){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)"; //$NON-NLS-1$

	private static final Pattern URLpattern = Pattern.compile(URL_REG_EXP);

	private static final String OPEN_URL_FOR_VERIFY_KEY = "openURLFromVerify"; //$NON-NLS-1$

	private TableViewer table;

	//We keep this so we can access the gerrit client
	private GerritTableView view;

	private Shell shell;

	VoteHandler(Shell shell, GerritTableView view, TableViewer table) {
		this.table = table;
		this.view = view;
		this.shell = shell;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		//Nothing to do
	}

	@Override
	public void mouseDown(MouseEvent e) {
		Event event = new Event();
		event.x = e.x;
		event.y = e.y;
		String requestedLabel = getRequestedLabel(event);
		ChangeInfo review = fromEventToChangeInfo(event);
		if (review == null) {
			return;
		}

		Optional<Object> messageToShow = selectInformationFromMessage(review, requestedLabel, m -> {
			Matcher matcher = URLpattern.matcher(((ChangeMessageInfo) m).getMessage());
			if (matcher.find()) {
				return matcher.group();
			}
			return null;
		});
		if (messageToShow.isPresent()) {
			String uriToServer = (String) messageToShow.get();
			//Add a safety dialog to confirm the action
			if (UIUtils.showConfirmDialog(OPEN_URL_FOR_VERIFY_KEY, shell, Messages.GerritTableView_openExternalPage,
					NLS.bind(Messages.GerritTableView_confirmOpenExternalPage, uriToServer)) == 1) {
				return;
			}
			try {
				IWebBrowser browser = PlatformUI.getWorkbench()
						.getBrowserSupport()
						.createBrowser("org.eclipse.egerrit.browser"); //$NON-NLS-1$
				browser.openURL(new URL(uriToServer));
			} catch (PartInitException | MalformedURLException e1) {
				logger.debug("Error opening URL :" + e1.toString()); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		//Nothing to do
	}

	@Override
	public void handleEvent(Event event) {
		String labelRequested = getRequestedLabel(event);
		if (labelRequested == null) {
			return;
		}
		ChangeInfo review = fromEventToChangeInfo(event);
		if (review == null) {
			return;
		}

		Optional<Object> messageToShow = selectInformationFromMessage(review, labelRequested, Function.identity());
		if (messageToShow.isPresent()) {
			final ToolTip tooltip = new ToolTip(shell, SWT.None);
			String authorName = ((ChangeMessageInfo) messageToShow.get()).getAuthor().getName();
			tooltip.setMessage(((ChangeMessageInfo) messageToShow.get()).getMessage() + "\nby " + authorName); //$NON-NLS-1$
			tooltip.setVisible(true);
			tooltip.setAutoHide(true);
		}
	}

	private String getRequestedLabel(Event event) {
		ViewerCell viewerCell = table.getCell(new Point(event.x, event.y));
		if (viewerCell != null) {
			return view.getReviewTable().getColumnLabel(viewerCell.getColumnIndex());
		}
		return null;
	}

	private ChangeInfo fromEventToChangeInfo(Event event) {
		ViewerCell viewerCell = table.getCell(new Point(event.x, event.y));
		if (viewerCell != null) {
			Object element = viewerCell.getViewerRow().getItem().getData();
			if (element instanceof ChangeInfo) {
				return (ChangeInfo) element;
			}
		}
		return null;
	}

	private Optional<Object> selectInformationFromMessage(ChangeInfo review, String label, Function mapper) {
		ApprovalInfo vote = review.getMostRelevantVote(label);
		Optional<Object> messageToShow = review.getMessages()
				.stream()
				.filter(m -> m.getDate().equals(vote.getDate())) //The message corresponding to a vote has the same date than the vote itself
				.map(mapper)
				.filter(m -> m != null)
				.findFirst();
		return messageToShow;
	}

	void connect() {
		table.getTable().addListener(SWT.MouseHover, this);
		table.getTable().addMouseListener(this);
	}
}
