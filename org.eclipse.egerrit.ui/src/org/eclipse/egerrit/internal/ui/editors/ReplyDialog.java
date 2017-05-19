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

package org.eclipse.egerrit.internal.ui.editors;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.compare.GerritMultipleInput;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

/**
 * This class is used in the editor to handle the Reply button
 *
 * @since 1.0
 */
public class ReplyDialog extends InputDialog {

	// The labels of the change as a map that maps the label names to LabelInfo
	// entries. Only set if 'labels' or 'detailed labels' are requested.
	private EMap<String, LabelInfo> labelsInfo;

	// A map of the permitted labels that maps a label name to the list of
	// values that are allowed for that label. Only set if 'detailed labels' are
	// requested.
	private Map<String, EList<String>> permittedLabels;

	private Composite keyComposite;

	private Composite radioButtonComposite;

	private Composite detailTextComposite;

	private ScrolledComposite scrolledDraftArea;

	private static final String DISPLAYWIDGET = "displayWidget"; //$NON-NLS-1$

	private Map<String, Integer> lastUserVotes = new LinkedHashMap<>();

	private RevisionInfo fRevisionInfo;

	private GerritClient fGerritClient;

	/**
	 * The constructor.
	 *
	 * @param messageInfo
	 */
	public ReplyDialog(Shell shell, String reason, RevisionInfo revisionToReplyTo, GerritClient gerritClient,
			ChangeMessageInfo messageInfo) {
		super(shell, Messages.ReplyDialog_0, buildMessage(reason, revisionToReplyTo), buildMessageText(messageInfo),
				null);
		fRevisionInfo = revisionToReplyTo;
		fGerritClient = gerritClient;
		permittedLabels = revisionToReplyTo.getChangeInfo().getSortedPermittedLabels();
		labelsInfo = revisionToReplyTo.getChangeInfo().getLabels();
		boolean isVoteAllowed = revisionToReplyTo.getId()
				.equals(revisionToReplyTo.getChangeInfo().getCurrent_revision());
		if (!isVoteAllowed) {
			labelsInfo = null;
		}
	}

	/**
	 * Fill the message text with the comment message when available and add a two empty lines before starting the next
	 * message
	 *
	 * @param messageInfo
	 * @return String
	 */
	private static String buildMessageText(ChangeMessageInfo messageInfo) {
		if (messageInfo == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		String message = messageInfo.getMessage();
		sb.append("> "); //$NON-NLS-1$
		message = message.replaceAll("\n", "\n> "); //$NON-NLS-1$ //$NON-NLS-2$
		sb.append(message);
		sb.append("\n\n"); //$NON-NLS-1$
		return sb.toString();
	}

	private static String buildMessage(String reason, RevisionInfo revisionToReplyTo) {
		if (reason == null) {
			return buildDefaultMessage(revisionToReplyTo);
		}
		return reason + buildDefaultMessage(revisionToReplyTo);
	}

	private static String buildDefaultMessage(RevisionInfo revisionToReplyTo) {
		return Messages.ReplyDialog_1 + UIUtils.getPatchSetString(revisionToReplyTo);
	}

	@Override
	protected int getInputTextStyle() {
		return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP;
	}

	@Override
	protected int getShellStyle() {
		return SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE;
	}

	/**
	 * Create the reply dialog
	 *
	 * @param parent
	 *            Composite
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		((GridData) this.getText().getLayoutData()).heightHint = 150;
		((GridData) this.getText().getLayoutData()).grabExcessVerticalSpace = true;
		((GridData) this.getText().getLayoutData()).verticalAlignment = SWT.FILL;
		//Create area to display the draft comments
		createMessageArea(composite);
		GridLayout compositeLayout = (GridLayout) composite.getLayout();
		compositeLayout.verticalSpacing = 0;
		compositeLayout.marginHeight = 0;
		composite.setLayout(compositeLayout);

		//Create the section handling the radio buttons
		if (labelsInfo != null) {
			createMiddleRadioSection(composite);
		}

		// Add a control listener to initialize a default minimum size
		parent.getShell().addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				if (keyComposite != null) { //Null when we do not create this section.
					int widthKey = getMinimalWidth(keyComposite, -1);
					int widthRadio = getMinimalWidth(radioButtonComposite, -1);
					int widthdetail = getMinimalWidth(detailTextComposite, 350);
					int widthTotal = 0;
					if (widthKey < 25 || widthRadio < 25 || widthdetail < 25) {
						widthTotal = 250;
					} else {
						widthTotal = widthKey + widthRadio + widthdetail;
					}

					Point size = parent.getShell().computeSize(widthTotal, SWT.DEFAULT);
					parent.getShell().setMinimumSize(size);
					parent.getShell().setSize(size);

				} else {
					//set a minimum size, there is no review labels
					Point size = parent.getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
					parent.getShell().setMinimumSize(size);
					parent.getShell().setSize(size);
				}

				if (hasDrafts(fRevisionInfo)) {
					//Re-layout the DRAFT AREA
					scrolledDraftArea.layout();
				}
				//Position the reply dialog in the middle of the monitor
				Rectangle parentRec = parent.getShell().getBounds();
				Rectangle clientArea = parent.getClientArea();

				Point centerPos = new Point(parentRec.x + parentRec.width - clientArea.width, parentRec.y);
				parent.getShell().setLocation(centerPos);

				parent.getShell().removeControlListener(this);
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});

		getText().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.stateMask & SWT.CTRL) != 0 && e.keyCode == SWT.CR) {
					getOkButton().notifyListeners(SWT.Selection, new Event());
				}
			}
		});
		//Reset the field from the inputDialog
		resetErrorSection(composite);

		return parent;

	}

	/**
	 * Adjust the fields inside the input dialog
	 *
	 * @param composite
	 */
	private void resetErrorSection(Composite composite) {
		Control[] compChild = composite.getChildren();

		if (compChild != null) {
			//Adjust the Label not to expand when resizing, but keep the new height to the scroll text
			if (compChild[0] instanceof Label) {
				Label lbl = (Label) compChild[0];
				GridData gridData = (GridData) lbl.getLayoutData();
				gridData.grabExcessVerticalSpace = false;
				lbl.setLayoutData(gridData);
				lbl.setSize(lbl.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}

			//Lets adjust the error text not shown here
			if (compChild.length > 2 && compChild[2] instanceof Text) {
				Text txt = (Text) compChild[2];
				GridData gridData = (GridData) txt.getLayoutData();
				gridData.minimumHeight = 0;
				gridData.grabExcessVerticalSpace = false;
				txt.setLayoutData(gridData);
				txt.setSize(txt.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		}
	}

	/**
	 * Compute the minimal width to set for a composite
	 *
	 * @param composite
	 * @param minWidth
	 * @return int
	 */
	private int getMinimalWidth(Composite composite, int minWidth) {
		int min = 0;
		Point sizeComposite = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		//If min width <0, it means, we take whatever the value from the computation
		if (minWidth < 0) {
			min = sizeComposite.x;
		} else {
			if ((sizeComposite.x > minWidth) || (sizeComposite.x == 0)) {
				min = minWidth;
			} else {
				min = sizeComposite.x;
			}
		}
		return min;
	}

	/**
	 * Test if there are any DRAFTS comments in this revision
	 *
	 * @param revInfo
	 * @return boolean
	 */
	private boolean hasDrafts(RevisionInfo revInfo) {
		Collection<FileInfo> files = revInfo.getFiles().values();
		for (FileInfo fileInfo : files) {
			if (!fileInfo.getDraftComments().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private void createMessageArea(Composite parent) {
		// create message
		if (hasDrafts(fRevisionInfo)) {

			// Create the title area which will contain
			// a title, message, and image.
			scrolledDraftArea = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);

			GridData grid = new GridData(GridData.FILL_BOTH);
			grid.heightHint = 50;
			scrolledDraftArea.setLayoutData(grid);
			Composite composite = new Composite(scrolledDraftArea, SWT.NONE);

			GridLayout glComposite = new GridLayout(1, false);
			glComposite.marginTop = 0;
			glComposite.marginHeight = 0;
			composite.setLayout(glComposite);
			GridData gdComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			gdComposite.heightHint = 150;
			composite.setLayoutData(gdComposite);
			composite.addControlListener(new ControlListener() {

				@Override
				public void controlResized(ControlEvent e) {
					// ignore
					Rectangle size = composite.getParent().getClientArea();
					composite.setSize(composite.computeSize(size.width, SWT.DEFAULT));

					scrolledDraftArea.setMinWidth(size.x);
					Rectangle compoHeight = composite.getBounds();
					scrolledDraftArea.setMinHeight(compoHeight.height);
				}

				@Override
				public void controlMoved(ControlEvent e) {
				}
			});

			createLink(composite);

			scrolledDraftArea.setContent(composite);
			Point p = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			scrolledDraftArea.setMinHeight(p.y);
			scrolledDraftArea.setMinWidth(p.x);

			scrolledDraftArea.setExpandHorizontal(true);
			scrolledDraftArea.setExpandVertical(true);

		}
	}

	private void createLink(Composite composite) {
		Collection<FileInfo> files = fRevisionInfo.getFiles().values();
		for (FileInfo fileInfo : files) {
			if (!fileInfo.getDraftComments().isEmpty()) {
				Link linkFile = new Link(composite, SWT.NONE);
				linkFile.setToolTipText(Messages.ReplyDialog_2);
				linkFile.setText("<a>" + fileInfo.getPath() + "</a>"); //$NON-NLS-1$//$NON-NLS-2$
				linkFile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				linkFile.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						FileInfo fileInfo = fRevisionInfo.getFiles().get(e.text);
						if (fileInfo != null) {
							UIUtils.open(fGerritClient, fileInfo, fRevisionInfo.getChangeInfo(),
									GerritMultipleInput.BASE);
							cancelPressed();
						}
					}
				});

				Iterator<CommentInfo> commentsIter = fileInfo.getDraftComments().iterator();
				StringBuilder sb = new StringBuilder();
				while (commentsIter.hasNext()) {
					//List the drafts
					CommentInfo comment = commentsIter.next();
					sb.append("\t " + comment.getLine() + "\t "); //$NON-NLS-1$ //$NON-NLS-2$
					sb.append(comment.getMessage() + "\n"); //$NON-NLS-1$
				}
				Text commentLabel = new Text(composite, SWT.WRAP | SWT.MULTI);
				commentLabel.setBackground(composite.getBackground());
				commentLabel.setEditable(false);
				commentLabel.setText(sb.toString());
				GridData grid = new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1);
				commentLabel.setLayoutData(grid);
			}
		}
	}

	/**
	 * @param parent
	 */
	private void createMiddleRadioSection(Composite parent) {
		if (hasDrafts(fRevisionInfo)) {
			// Create a horizontal separator
			Label separator = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
			GridData sepGrid = new GridData(GridData.FILL_HORIZONTAL);
			sepGrid.grabExcessHorizontalSpace = true;
			separator.setLayoutData(sepGrid);
		}
		int maxRadio = getMaxCountLabels();
		if (maxRadio <= 0) {
			return;//no need to create the composite
		}
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout glComposite = new GridLayout(3, false);
		glComposite.marginTop = 3;
		composite.setLayout(glComposite);
		GridData gdComposite = new GridData(SWT.FILL, SWT.LEFT, true, false, 1, 1);
		int height = 75 + (30 * (permittedLabels.size() - 1));//Increase by 30  for each line to add after
		gdComposite.minimumHeight = height;
		gdComposite.heightHint = height;
		composite.setLayoutData(gdComposite);
		keyComposite = createComposite(composite, SWT.LEFT, 1, false);
		radioButtonComposite = createComposite(composite, SWT.CENTER, 1, false);
		detailTextComposite = createComposite(composite, SWT.LEFT, 1, true);

		//Create the dynamic selection
		createRadioButtonSelection();
	}

	private Composite createComposite(Composite parent, int horizontalSwt, int numColumn, boolean grabHorizon) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout glComposite = new GridLayout(numColumn, false);
		glComposite.marginTop = 3;
		composite.setLayout(glComposite);
		GridData gdComposite = new GridData(horizontalSwt, SWT.CENTER, grabHorizon, false);
		composite.setLayoutData(gdComposite);
		return composite;
	}

	/**
	 * Create a composite to maintain all radio buttons for a define label
	 *
	 * @param int
	 *            maxRadio
	 * @return Composite
	 */
	private Composite createButtonComposite(Composite composite, int maxRadio) {
		Composite radioComposite = new Composite(composite, SWT.NONE);
		GridLayout gridLayout = new GridLayout(maxRadio, true);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.marginTop = 0;
		radioComposite.setLayout(gridLayout);
		return radioComposite;
	}

	/**
	 * Create the radio buttons for any labels available to the review
	 */
	private void createRadioButtonSelection() {
		int maxRadio = getMaxCountLabels();
		if (maxRadio <= 0) {
			// No labels for the review.  This can happen for a merged review.
			return;
		}
		Composite headerLabels = getRadioButtonHeaderLabels(maxRadio);
		Point sizeRadio = null;
		Point fontSize = UIUtils.computeFontSize(detailTextComposite);

		//Get the last votes for all labels according to a user
		String loginUser = fGerritClient.getRepository().getServerInfo().getUserName();
		lastUserVotes = fRevisionInfo.getChangeInfo().getUserLastLabelSet(loginUser);

		//Set the radio buttons
		Iterator<Map.Entry<String, EList<String>>> iterator = permittedLabels.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, EList<String>> permittedlabel = iterator.next();
			sizeRadio = createARowRadioLabel(permittedlabel, maxRadio, fontSize.x);
		}

		//Need to adjust the label position to align it with the buttons
		Point headerSize = headerLabels.getSize();
		GridLayout layout = (GridLayout) headerLabels.getLayout();
		int radioHeight = fontSize.y; //default height

		//Set the margin for the radio button top labels
		if (sizeRadio != null) {
			int space = (sizeRadio.x - headerSize.x) / layout.numColumns;
			layout.marginWidth = space / 2;
			layout.horizontalSpacing = space;
			headerLabels.setLayout(layout);
			radioHeight = sizeRadio.y;
		}
		reAdjustRadioLayout(fontSize, radioHeight);
	}

	private Point createARowRadioLabel(Entry<String, EList<String>> permittedlabel, int maxRadio, int fontWidth) {
		EList<String> listPermitted = permittedlabel.getValue();
		Point sizeRadio = null;
		Label rowLabel = new Label(keyComposite, SWT.BOLD);
		rowLabel.setText(permittedlabel.getKey());

		//Create the text data for the selection
		Label detailLabel = new Label(detailTextComposite, SWT.NONE);
		GridData grid = new GridData(SWT.FILL, SWT.LEFT, true, false);
		grid.minimumWidth = fontWidth * 500;
		detailLabel.setLayoutData(grid);

		Composite radioComposite = createButtonComposite(radioButtonComposite, maxRadio);

		radioComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//Fill the dummy space
		int middlePoint = (maxRadio + 1) / 2; //button position for the column  = 0
		int minValue = Integer.parseInt(listPermitted.get(0)); //get the first minimum value
		int numEmptyBefore = middlePoint + minValue - 1;
		int numEmptyAfter = middlePoint - Integer.parseInt(listPermitted.get(listPermitted.size() - 1)) - 1;//compute the number of empty space to add at the end
		createFillerRadioButtons(numEmptyBefore, radioComposite);

		//Fill the radio buttons
		Button[] radios = new Button[listPermitted.size()];
		for (int i = 0; i < listPermitted.size(); i++) {
			//Create a radio and store extra data into the structure
			radios[i] = new Button(radioComposite, SWT.RADIO);
			radios[i].setData(listPermitted.get(i));
			radios[i].setData(listPermitted.get(i), permittedlabel.getKey());
			radios[i].setData(DISPLAYWIDGET, detailLabel);//set the display label widget

			String description = labelSelectionDescription(permittedlabel.getKey(), listPermitted.get(i));
			radios[i].setToolTipText(description);
			int userVotes = lastUserVotes.get(permittedlabel.getKey()) != null
					? lastUserVotes.get(permittedlabel.getKey())
					: 0;
			if (Integer.parseInt(listPermitted.get(i).trim()) == userVotes) {
				radios[i].setSelection(true);
				detailLabel.setText(description);
			}

			radios[i].addListener(SWT.Selection, radioGroupListener());
		}

		//Complete to fill the dummy space
		createFillerRadioButtons(numEmptyAfter, radioComposite);
		radioComposite.pack();
		sizeRadio = radioComposite.getSize();
		return sizeRadio;
	}

	/**
	 * Re-set the layout data for the radio area in the dialog
	 *
	 * @param fontSize
	 * @param radioHeight
	 */
	private void reAdjustRadioLayout(Point fontSize, int radioHeight) {
		int topMargin = 0;
		int verticalSpacing = radioHeight - fontSize.y + 4;
		Point compSize = radioButtonComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		GridData radioData = (GridData) radioButtonComposite.getLayoutData();
		radioData.minimumWidth = compSize.x;
		radioButtonComposite.setLayoutData(radioData);

		//Reset the layout for Labels area
		GridData gridData = (GridData) keyComposite.getLayoutData();
		gridData.heightHint = compSize.y;

		keyComposite.setLayoutData(gridData);
		GridLayout keyLayout = (GridLayout) keyComposite.getLayout();
		keyLayout.marginTop = topMargin;

		keyLayout.verticalSpacing = verticalSpacing;
		keyComposite.setLayout(keyLayout);
		Point size = keyComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		keyComposite.setSize(size);

		//Reset the layout for Details
		GridData gridDataDetail = (GridData) detailTextComposite.getLayoutData();
		gridDataDetail.heightHint = compSize.y;
		detailTextComposite.setLayoutData(gridDataDetail);

		GridLayout detailLayout = (GridLayout) detailTextComposite.getLayout();
		detailLayout.marginTop = topMargin;
		detailLayout.verticalSpacing = verticalSpacing;
		detailTextComposite.setLayout(detailLayout);
		detailTextComposite.setSize(detailTextComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * Create the header row for the dynamic selection for the radio buttons
	 *
	 * @param int
	 *            maxRadioChoice
	 * @return Composite
	 */
	private Composite getRadioButtonHeaderLabels(int maxRadioChoice) {
		EList<String> listPermitted = null;
		Iterator<Map.Entry<String, EList<String>>> iterator = permittedLabels.entrySet().iterator();
		//Get the structure having all the possible options
		while (iterator.hasNext()) {
			Entry<String, EList<String>> permittedlabel = iterator.next();
			listPermitted = permittedlabel.getValue();
			if (listPermitted.size() == maxRadioChoice) {
				break;
			}
		}

		new Label(keyComposite, SWT.NONE);
		//Create the text data display for the selection
		new Label(detailTextComposite, SWT.NONE);
		//Create the radio buttons header labels
		Composite radioComposite = createButtonComposite(radioButtonComposite, maxRadioChoice);
		radioComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, maxRadioChoice, 1));

		if (listPermitted != null) {
			Label[] radioLabels = new Label[listPermitted.size()];
			for (int i = 0; i < listPermitted.size(); i++) {
				radioLabels[i] = new Label(radioComposite, SWT.RADIO);
				radioLabels[i].setText(listPermitted.get(i));
			}
			radioComposite.pack();
		}
		return radioComposite;
	}

	/**
	 * Create filler buttons for the alignment
	 *
	 * @param num
	 * @param radioComposite
	 */
	private void createFillerRadioButtons(int num, Composite radioComposite) {
		Button[] emptyRadios = new Button[num];
		for (int i = 0; i < num; i++) {
			emptyRadios[i] = new Button(radioComposite, SWT.RADIO);
			emptyRadios[i].setVisible(false);
		}
	}

	/**
	 * Compute the maximum radio buttons to create for each key
	 *
	 * @return int
	 */
	private int getMaxCountLabels() {
		int maxButtons = 0;
		int count = 0;
		Iterator<Map.Entry<String, EList<String>>> iterator = permittedLabels.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, EList<String>> permittedlabel = iterator.next();
			count = permittedlabel.getValue().size();
			maxButtons = Math.max(maxButtons, count);
		}
		return maxButtons;
	}

	/**
	 * Listener handling any radio buttons selection
	 *
	 * @return Listener
	 */
	private Listener radioGroupListener() {

		return event -> {
			Widget wid = event.widget;
			Object obj = wid.getData();
			String keyLabel = (String) wid.getData((String) obj);
			String tootip = ((Button) wid).getToolTipText();
			Object objWidget = wid.getData(DISPLAYWIDGET);
			Label toShow = (Label) objWidget;
			toShow.setText(tootip);
			toShow.pack();
			String st = (String) obj;
			if (st.startsWith("+")) { //$NON-NLS-1$
				st = st.substring(1);//parse the string for the positive value
			}
			lastUserVotes.put(keyLabel, Integer.parseInt(st.trim()));
		};
	}

	/**
	 * Search the label description for a specific key/label
	 *
	 * @param key
	 * @param value
	 * @return String
	 */
	private String labelSelectionDescription(String key, String value) {
		String ret = ""; //$NON-NLS-1$
		if (labelsInfo != null && !labelsInfo.isEmpty()) {
			LabelInfo info = labelsInfo.get(key);
			return info.getValues().get(value);
		}
		return ret;
	}

	/**
	 * This method return the hashMap of the selected radio buttons
	 *
	 * @return LinkedHashMap<String, Integer>
	 */
	public Map<String, Integer> getRadiosSelection() {
		return lastUserVotes;
	}

}
