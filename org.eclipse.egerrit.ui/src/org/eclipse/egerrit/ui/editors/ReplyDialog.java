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

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.egerrit.core.rest.ApprovalInfo;
import org.eclipse.egerrit.core.rest.LabelInfo;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used in the editor to handle the Reply button
 *
 * @since 1.0
 */
public class ReplyDialog extends Dialog {

	final static Logger logger = LoggerFactory.getLogger(ReplyDialog.class);

	// The labels of the change as a map that maps the label names to LabelInfo
	// entries. Only set if 'labels' or 'detailed labels' are requested.
	private Map<String, LabelInfo> labelsInfo;

	// A map of the permitted labels that maps a label name to the list of
	// values that are allowed for that label. Only set if 'detailed labels' are
	// requested.
	private Map<String, String[]> permitted_labels;

	private Text msgTextData;

	private Composite keyComposite;

	private Composite radioButtonComposite;

	private Composite detailTextComposite;

	private Composite buttonComposite;

	private final String DISPLAYWIDGET = "displayWidget"; //$NON-NLS-1$

	private Button post;

	private Button sendEmail;

	private Button cancel;

	private LinkedHashMap<String, String> radioMap = new LinkedHashMap<String, String>();

	private String message = ""; //$NON-NLS-1$

	private boolean eMailSet = true;
	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public ReplyDialog(Composite parent, Map<String, String[]> permitted_labels, Map<String, LabelInfo> labelsInfo) {
		super(parent.getShell());

		logger.debug("Open the Reply dialogue."); //$NON-NLS-1$
		this.permitted_labels = permitted_labels;
		this.labelsInfo = labelsInfo;

	}

	/**
	 * This method allows us to set a title to the dialog
	 *
	 * @param Shell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Reply dialog");
	}

	/**
	 * Create the reply dialog
	 *
	 * @param parent
	 *            Composite
	 */
	@Override
	protected Control createContents(Composite parent) {

		//	GridLayout layout = new GridLayout(5, false);
		parent.setLayout(new GridLayout(1, false));

		Point fontSize = UIUtils.computeFontSize(parent);
		ScrolledComposite sc_msgtxt = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_msgtxt.setExpandHorizontal(true);
		sc_msgtxt.setExpandVertical(true);
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true);
		grid.minimumHeight = fontSize.y * 6; //minimum 6 lines
		grid.minimumWidth = fontSize.x * 60;
		sc_msgtxt.setLayoutData(grid);

		msgTextData = new Text(sc_msgtxt, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		sc_msgtxt.setContent(msgTextData);

		//Create the section handling the radio buttons
		createMiddleRadioSection(parent);

		//Create the bottom section for the buttons
		createBottomButtons(parent);

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

	/**
	 * @param parent
	 */
	private void createBottomButtons(Composite parent) {
		Label separator = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData sepGrid = new GridData(GridData.FILL_HORIZONTAL);
		sepGrid.grabExcessHorizontalSpace = true;
		separator.setLayoutData(sepGrid);

		buttonComposite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(4, false);
		buttonComposite.setLayout(gridLayout);

		GridData gd_button = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_button.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gd_button);

		//Callback handle by the okPressed()
		post = createButton(buttonComposite, IDialogConstants.OK_ID, "Post", false);

		Label and = new Label(buttonComposite, SWT.NONE);
		and.setText("and");

		sendEmail = new Button(buttonComposite, SWT.CHECK);
		sendEmail.setText("send e-mail");
		sendEmail.setSelection(true);

		cancel = createButton(buttonComposite, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
		GridData gd_cancel = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gd_cancel.grabExcessHorizontalSpace = true;
		cancel.setLayoutData(gd_cancel);
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
			UIUtils.displayInformation(null, "Gerrit Reply", "No user login to set the status with the reply");
			return;
		}
		Composite headerLabels = getRadioButtonHeaderLabels(maxRadio);
		Point sizeRadio = null;
		Point fontSize = UIUtils.computeFontSize(detailTextComposite);
		//Set into the variable the last setting of the radio
		getLastLabelSet();

		//Sort the Labels in chronological order
		Map<String, String[]> sortedMap = new TreeMap<String, String[]>(new Comparator<String>() {

			@Override
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});
		sortedMap.putAll(permitted_labels);

		Iterator<Map.Entry<String, String[]>> iterator = sortedMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> permittedlabel = iterator.next();
			String[] listPermitted = permittedlabel.getValue();
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
			int numEmpty = (maxRadio - listPermitted.length) / 2;//Only half the number at the beginning
			createFillerRadioButtons(numEmpty, radioComposite);

			//Fill the radio buttons
			Button[] radios = new Button[listPermitted.length];
			for (int i = 0; i < listPermitted.length; i++) {
				//Create a radio and store extra data into the structure
				radios[i] = new Button(radioComposite, SWT.RADIO);
				radios[i].setData(listPermitted[i]);
				radios[i].setData(listPermitted[i], permittedlabel.getKey());
				radios[i].setData(DISPLAYWIDGET, detailLabel);//set the display label widget

				String description = labelSelectionDescription(permittedlabel.getKey(), listPermitted[i]);
				radios[i].setToolTipText(description);
				if (Integer.parseInt(listPermitted[i].trim()) == lastValue) {
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
		String[] listPermitted = null;
		Iterator<Map.Entry<String, String[]>> iterator = permitted_labels.entrySet().iterator();
		//Get the structure having all the possible options
		while (iterator.hasNext()) {
			Entry<String, String[]> permittedlabel = iterator.next();
			listPermitted = permittedlabel.getValue();
			if (listPermitted.length == maxRadioChoice) {
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
			Label[] radioLabels = new Label[listPermitted.length];
			for (int i = 0; i < listPermitted.length; i++) {
				radioLabels[i] = new Label(radioComposite, SWT.RADIO);
				radioLabels[i].setText(listPermitted[i]);
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
		Iterator<Map.Entry<String, String[]>> iterator = permitted_labels.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> permittedlabel = iterator.next();
			count = permittedlabel.getValue().length;
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
					Map<String, String> mapValues = labelInfo.getValues();
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
				if (listApproval != null) {
					ApprovalInfo lastInfo = listApproval.get(listApproval.size() - 1); //Get the value of the lastsetting
					if (lastInfo.getValue() != null) {
						radioMap.put(entrylabel.getKey(), StringConverter.asString(lastInfo.getValue()));
					}
				}
			}
		}
	}

	/**
	 * When the user select the "POST" button, process the data
	 */
	@Override
	protected void okPressed() {
		//Fill the data structure
		setMessage();
		setEmail();

		super.okPressed();
	}

	private void setMessage() {
		message = msgTextData.getText();
	}

	private void setEmail() {
		eMailSet = sendEmail.getSelection();
	}

	/**
	 * This method return the message text.
	 *
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * This method return the boolean value for the e-mail value.
	 *
	 * @return boolean
	 */
	public boolean getEmail() {
		return eMailSet;
	}

	/**
	 * This method return the hashMap of the selected radio buttons
	 *
	 * @return LinkedHashMap<String, String>
	 */
	public LinkedHashMap<String, String> getRadiosSelection() {
		return radioMap;
	}
}
