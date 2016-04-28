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

package org.eclipse.egerrit.ui.editors;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egerrit.internal.model.ApprovalInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
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
	private EMap<String, EList<String>> permitted_labels;

	private Composite keyComposite;

	private Composite radioButtonComposite;

	private Composite detailTextComposite;

	private final String DISPLAYWIDGET = "displayWidget"; //$NON-NLS-1$

	private LinkedHashMap<String, String> radioMap = new LinkedHashMap<String, String>();

	/**
	 * The constructor.
	 */
	public ReplyDialog(Shell shell, String reason, RevisionInfo revisionToReplyTo) {
		super(shell, "Reply to comment", buildMessage(reason, revisionToReplyTo), null, null);

		permitted_labels = revisionToReplyTo.getChangeInfo().getPermitted_labels();
		labelsInfo = revisionToReplyTo.getChangeInfo().getLabels();

		boolean isVoteAllowed = revisionToReplyTo.getId()
				.equals(revisionToReplyTo.getChangeInfo().getCurrent_revision());
		if (!isVoteAllowed) {
			labelsInfo = null;
		}
	}

	private static String buildMessage(String reason, RevisionInfo revisionToReplyTo) {
		if (reason == null) {
			return buildDefaultMessage(revisionToReplyTo);
		}
		return reason + buildDefaultMessage(revisionToReplyTo);
	}

	private static String buildDefaultMessage(RevisionInfo revisionToReplyTo) {
		return "Enter the reply to patchset " + UIUtils.getPatchSetString(revisionToReplyTo);
	}

	@Override
	protected int getInputTextStyle() {
		return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL;
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
		((GridData) this.getText().getLayoutData()).heightHint = 100;

		//Create the section handling the radio buttons
		if (labelsInfo != null) {
			createMiddleRadioSection(composite);
		}

		//Create the bottom section for the buttons
		parent.getShell().setMinimumSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return parent;
	}

	/**
	 * @param parent
	 */
	private void createMiddleRadioSection(Composite parent) {
		// Create a horizontal separator
		Label separator = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData sepGrid = new GridData(GridData.FILL_HORIZONTAL);
		sepGrid.grabExcessHorizontalSpace = true;
		separator.setLayoutData(sepGrid);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.marginTop = 3;
		composite.setLayout(gl_composite);
		GridData gd_composite = new GridData(SWT.FILL, SWT.LEFT, true, false, 1, 1);
		gd_composite.minimumHeight = 117;
		composite.setLayoutData(gd_composite);

		keyComposite = createComposite(composite, SWT.LEFT, 1);
		radioButtonComposite = createComposite(composite, SWT.CENTER, 1);
		detailTextComposite = createComposite(composite, SWT.LEFT, 1);

		//Create the dynamic selection
		createRadioButtonSelection();
	}

	private Composite createComposite(Composite parent, int horizontalSwt, int numColumn) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl_composite = new GridLayout(numColumn, true);
		gl_composite.marginTop = 3;
		composite.setLayout(gl_composite);
		GridData gd_composite = new GridData(horizontalSwt, SWT.CENTER, true, false);
		gd_composite.minimumHeight = 117;
		composite.setLayoutData(gd_composite);
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
		//Set into the variable the last setting of the radio
		getLastLabelSet();

		Iterator<Map.Entry<String, EList<String>>> iterator = permitted_labels.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, EList<String>> permittedlabel = iterator.next();
			EList<String> listPermitted = permittedlabel.getValue();
			Label rowLabel = new Label(keyComposite, SWT.BOLD);
			rowLabel.setText(permittedlabel.getKey());

			//Verify the last value set for this key
			String valueSet = radioMap.get(permittedlabel.getKey());
			int lastValue = -999; //Initialize the value
			if (valueSet != null) {
				lastValue = Integer.parseInt(valueSet);
			}

			//Create the text data for the selection
			Label detailLabel = new Label(detailTextComposite, SWT.NONE);
			GridData grid = new GridData(SWT.FILL, SWT.LEFT, true, false);
			grid.minimumWidth = fontSize.x * 50;
			detailLabel.setLayoutData(grid);

			Composite radioComposite = createButtonComposite(radioButtonComposite, maxRadio);

			radioComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			//Fill the dummy space
			int numEmpty = (maxRadio - listPermitted.size()) / 2;//Only half the number at the beginning
			createFillerRadioButtons(numEmpty, radioComposite);

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
				if (Integer.parseInt(listPermitted.get(i).trim()) == lastValue) {
					radios[i].setSelection(true);
					detailLabel.setText(description);
				}

				radios[i].addListener(SWT.Selection, radioGroupListener());
			}

			//Complete to fill the dummy space
			createFillerRadioButtons(numEmpty, radioComposite);
			radioComposite.pack();
			sizeRadio = radioComposite.getSize();
		}

		//Need to adjust the label position to align it with the buttons
		Point headerSize = headerLabels.getSize();
		GridLayout layout = (GridLayout) headerLabels.getLayout();

		//Set the margin for the radio button top labels
		if (sizeRadio != null) {
			int space = (sizeRadio.x - headerSize.x) / layout.numColumns;
			layout.marginWidth = space / 2;
			layout.horizontalSpacing = space;
			headerLabels.setLayout(layout);
		}
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
		Iterator<Map.Entry<String, EList<String>>> iterator = permitted_labels.entrySet().iterator();
		//Get the structure having all the possible options
		while (iterator.hasNext()) {
			Entry<String, EList<String>> permittedlabel = iterator.next();
			listPermitted = permittedlabel.getValue();
			if (listPermitted.size() == maxRadioChoice) {
				break;
			}
		}

		Label permittedLabel = new Label(keyComposite, SWT.NONE);
		//Create the text data display for the selection
		Label detailLabel = new Label(detailTextComposite, SWT.NONE);

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
		Iterator<Map.Entry<String, EList<String>>> iterator = permitted_labels.entrySet().iterator();
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
		return new Listener() {

			@Override
			public void handleEvent(Event event) {
				Widget wid = event.widget;
				Object obj = wid.getData();
				String keyLabel = (String) wid.getData((String) obj);
				String tootip = ((Button) wid).getToolTipText();
				Object objWidget = wid.getData(DISPLAYWIDGET);
				Label toShow = (Label) objWidget;
				toShow.setText(tootip);
				toShow.pack();
				radioMap.put(keyLabel, (String) obj);
			}
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
			Iterator<Map.Entry<String, LabelInfo>> labelIter = labelsInfo.entrySet().iterator();
			while (labelIter.hasNext()) {
				Entry<String, LabelInfo> entrylabel = labelIter.next();
				if (entrylabel.getKey().equals(key)) {
					LabelInfo labelInfo = entrylabel.getValue();
					EMap<String, String> mapValues = labelInfo.getValues();
					return mapValues.get(value);
				}
			}
		}
		return ret;
	}

	/**
	 * Read the received labels and keep the last setting for each labels. Will use it to set the radio buttons
	 * selection
	 */
	private void getLastLabelSet() {

		if (labelsInfo != null && !labelsInfo.isEmpty()) {

			Iterator<Map.Entry<String, LabelInfo>> labelIter = labelsInfo.entrySet().iterator();
			while (labelIter.hasNext()) {
				Entry<String, LabelInfo> entrylabel = labelIter.next();
				LabelInfo labelInfo = entrylabel.getValue();
				List<ApprovalInfo> listApproval = labelInfo.getAll();
				if (listApproval != null && !listApproval.isEmpty()) {
					ApprovalInfo lastInfo = listApproval.get(listApproval.size() - 1); //Get the value of the lastsetting
					if (lastInfo.getValue() != null) {
						radioMap.put(entrylabel.getKey(), StringConverter.asString(lastInfo.getValue()));
					}
				}
			}
		}
	}

	/**
	 * This method return the hashMap of the selected radio buttons
	 *
	 * @return LinkedHashMap<String, String>
	 */
	public Map<String, String> getRadiosSelection() {
		return radioMap;
	}
}
