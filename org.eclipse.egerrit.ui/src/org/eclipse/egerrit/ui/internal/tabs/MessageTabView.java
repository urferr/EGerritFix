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

package org.eclipse.egerrit.ui.internal.tabs;

import java.text.SimpleDateFormat;
import java.util.Observable;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.ChangeCommitMsgCommand;
import org.eclipse.egerrit.core.command.PublishChangeEditCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeEditMessageInput;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.CommitInfo;
import org.eclipse.egerrit.ui.internal.utils.DataConverter;
import org.eclipse.egerrit.ui.internal.utils.EGerritConstants;
import org.eclipse.egerrit.ui.internal.utils.LinkDashboard;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * This class is used in the editor to handle the Gerrit message view
 *
 * @since 1.0
 */
public class MessageTabView extends Observable {

	private Text msgTextData;

	private Link msgAuthorData;

	private Label msgDatePushData;

	private Link msgCommitterData;

	private Label msgDatecommitterData;

	private Link msgCommitidData;

	private Label msgParentIdData;

	private Link msgChangeIdData;

	private final SimpleDateFormat formatTimeOut = new SimpleDateFormat("MMM d, yyyy  hh:mm a"); //$NON-NLS-1$

	private final String TITLE = "Gerrit Server ";

	private String fMessageBuffer;

	private GerritClient fGerritClient;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public MessageTabView() {
	}

	/**
	 * @param fGerritClient
	 * @param tabFolder
	 * @param listMessages
	 *            List<ChangeMessageInfo>
	 */
	public void create(GerritClient gerritClient, TabFolder tabFolder, CommitInfo commitInfo, ChangeInfo changeInfo) {
		fGerritClient = gerritClient;
		createControls(tabFolder, commitInfo, changeInfo);
	}

	private boolean isEditingAllowed() {
		return !fGerritClient.getRepository().getServerInfo().isAnonymous();
	}

	private void createControls(TabFolder tabFolder, CommitInfo commitInfo, final ChangeInfo changeInfo) {
		final TabItem tabMessages = new TabItem(tabFolder, SWT.NONE);
		tabMessages.setText("Messages");

		final Composite messagesComposite = new Composite(tabFolder, SWT.NONE);
		tabMessages.setControl(messagesComposite);
		messagesComposite.setLayout(new GridLayout(4, false));

		msgTextData = new Text(messagesComposite, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		msgTextData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		msgTextData.setEditable(isEditingAllowed());

		final Button btnCancel = new Button(messagesComposite, SWT.NONE);
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");
		btnCancel.setEnabled(false);

		final Button fBtnSave = new Button(messagesComposite, SWT.NONE);
		GridData gd_btnSave_1 = new GridData(SWT.LEFT, SWT.TOP, false, false, 3, 1);
		fBtnSave.setLayoutData(gd_btnSave_1);
		fBtnSave.setText("Save");
		fBtnSave.setEnabled(false);

		if (isEditingAllowed()) {
			btnCancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					msgTextData.setText(getfMessageBuffer());
					fBtnSave.setEnabled(false);
					btnCancel.setEnabled(false);
				}
			});
		}

		if (isEditingAllowed()) {
			ModifyListener textModifiedListener = new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					fBtnSave.setEnabled(true);
					btnCancel.setEnabled(true);
				}
			};
			msgTextData.addModifyListener(textModifiedListener);
		}

		//LINE 1 - Author: <authorname> date
		Label lblAuthor = new Label(messagesComposite, SWT.NONE);
		lblAuthor.setText("Author:");
		lblAuthor.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

		msgAuthorData = new Link(messagesComposite, SWT.NONE);
		msgAuthorData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		msgAuthorData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LinkDashboard linkDash = new LinkDashboard(fGerritClient);
				//Need to put the text in quotes since there might be some empty spaces or special char in the user name
				linkDash.invokeRefreshDashboardCommand(EGerritConstants.OWNER, "\"" + e.text + "\"" + " status:open"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			}
		});

		msgDatePushData = new Label(messagesComposite, SWT.NONE);
		msgDatePushData.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		//LINE 2 - Committer: <committername> date
		Label lblCommitter = new Label(messagesComposite, SWT.NONE);
		lblCommitter.setText("Committer:");
		lblCommitter.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

		msgCommitterData = new Link(messagesComposite, SWT.NONE);
		msgCommitterData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		msgCommitterData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LinkDashboard linkDash = new LinkDashboard(fGerritClient);
				//Need to put the text in quotes since there might be some empty spaces or special char in the user name
				linkDash.invokeRefreshDashboardCommand(EGerritConstants.OWNER, "\"" + e.text + "\"" + " status:open"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			}
		});

		msgDatecommitterData = new Label(messagesComposite, SWT.NONE);
		GridData gd_DateCommitter = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
		msgDatecommitterData.setLayoutData(gd_DateCommitter);

		//LINE 3 - Commit: <commitId>
		Label lblCommit = new Label(messagesComposite, SWT.NONE);
		lblCommit.setText("Commit:");
		lblCommit.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

		msgCommitidData = new Link(messagesComposite, SWT.NONE);
		msgCommitidData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LinkDashboard linkDash = new LinkDashboard(fGerritClient);
				linkDash.invokeRefreshDashboardCommand("", e.text);
			}
		});

		msgCommitidData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));

		//LINE 4 - Parents: <parentId>
		Label lblParents = new Label(messagesComposite, SWT.NONE);
		lblParents.setText("Parent(s):");
		lblParents.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

		msgParentIdData = new Label(messagesComposite, SWT.NONE);
		msgParentIdData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));

		//LINE 5 - ChangeId: <id>
		Label lblChangeid = new Label(messagesComposite, SWT.NONE);
		lblChangeid.setText("Change-Id:");
		lblChangeid.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

		msgChangeIdData = new Link(messagesComposite, SWT.NONE);
		msgChangeIdData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		msgChangeIdData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LinkDashboard linkDash = new LinkDashboard(fGerritClient);
				linkDash.invokeRefreshDashboardCommand("", e.text);
			}
		});

		if (isEditingAllowed()) {
			fBtnSave.addSelectionListener(new SelectionAdapter() {
				/**
				 *
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					ChangeCommitMsgCommand editMessageCmd = fGerritClient.editMessage(changeInfo.getId());
					ChangeEditMessageInput changeEditMessageInput = new ChangeEditMessageInput();
					changeEditMessageInput.setMessage(msgTextData.getText());
					editMessageCmd.setCommandInput(changeEditMessageInput);
					PublishChangeEditCommand publishChangeEditCmd = fGerritClient.publishChangeEdit(changeInfo.getId());

					try {
						editMessageCmd.call();
						publishChangeEditCmd.call();
					} catch (EGerritException e3) {
						EGerritCorePlugin
								.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
					}
					fBtnSave.setEnabled(false);
					btnCancel.setEnabled(false);
					setfMessageBuffer(msgTextData.getText());
					notifyObservers();
					LinkDashboard linkDash = new LinkDashboard(fGerritClient);
					linkDash.invokeRefreshDashboardCommand("", ""); //$NON-NLS-1$ //$NON-NLS-2$
				}
			});
		}

		new Label(messagesComposite, SWT.NONE);

		//Set the binding for this section
		msgTabDataBindings(commitInfo, changeInfo);
	}

	protected DataBindingContext msgTabDataBindings(CommitInfo commitInfo, ChangeInfo changeInfo) {
		DataBindingContext bindingContext = new DataBindingContext();

		//
		IObservableValue observeMsgTextDataWidget = WidgetProperties.text().observe(msgTextData);
		IObservableValue msgTextDataValue = BeanProperties.value("message").observe(commitInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeMsgTextDataWidget, msgTextDataValue, null, null);
		//
		IObservableValue observeTextMsgAuthorDataWidget = WidgetProperties.text().observe(msgAuthorData);
		IObservableValue msgAuthorDataValue = BeanProperties.value("author").observe(commitInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextMsgAuthorDataWidget, msgAuthorDataValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.gitPersonConverter()));

		//
		IObservableValue TextMsgAuthorDateDataWidget = WidgetProperties.text().observe(msgDatePushData);
		IObservableValue msgAuthorDateDataValue = BeanProperties.value(CommitInfo.class, "author.date").observe( //$NON-NLS-1$
				commitInfo);
		bindingContext.bindValue(TextMsgAuthorDateDataWidget, msgAuthorDateDataValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.gerritTimeConverter(formatTimeOut)));

		//
		IObservableValue observeTextMsgCommitterDataWidget = WidgetProperties.text().observe(msgCommitterData);
		IObservableValue msgCommitterDataValue = BeanProperties.value("committer").observe(commitInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextMsgCommitterDataWidget, msgCommitterDataValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.gitPersonConverter()));

		//
		IObservableValue TextMsgCommitterDateDataWidget = WidgetProperties.text().observe(msgDatecommitterData);
		IObservableValue msgCommitterDateDataValue = BeanProperties.value(CommitInfo.class, "committer.date").observe( //$NON-NLS-1$
				commitInfo);
		bindingContext.bindValue(TextMsgCommitterDateDataWidget, msgCommitterDateDataValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.gerritTimeConverter(formatTimeOut)));

		//
		IObservableValue observeTextMsgCommitidDataWidget = WidgetProperties.text().observe(msgCommitidData);
		IObservableValue msgCommitidDataValue = BeanProperties.value("commit").observe(commitInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextMsgCommitidDataWidget, msgCommitidDataValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.linkText()));
		//
		IObservableValue observeTextMsgParentIdDataWidget = WidgetProperties.text().observe(msgParentIdData);
		IObservableValue msgParentIdDataValue = BeanProperties.value("parents").observe(commitInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextMsgParentIdDataWidget, msgParentIdDataValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.commitInfoIDConverter()));
		//
		IObservableValue observeTextMsgChangeIdDataWidget = WidgetProperties.text().observe(msgChangeIdData);
		IObservableValue msgChangeIdDataValue = BeanProperties.value("change_id").observe(changeInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextMsgChangeIdDataWidget, msgChangeIdDataValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.linkText()));

		return bindingContext;
	}

	/**
	 * @return the fMessageBuffer
	 */
	public String getfMessageBuffer() {
		return fMessageBuffer;
	}

	/**
	 * @param fMessageBuffer
	 *            the fMessageBuffer to set
	 */
	public void setfMessageBuffer(String fMessageBuffer) {
		this.fMessageBuffer = fMessageBuffer;
	}

	/**
	 * Notify the registered observer
	 */
	@Override
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}

}
