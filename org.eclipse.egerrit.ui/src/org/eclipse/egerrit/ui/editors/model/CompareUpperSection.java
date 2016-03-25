package org.eclipse.egerrit.ui.editors.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareViewerSwitchingPane;
import org.eclipse.compare.internal.CompareUIPlugin;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.compare.internal.ViewerDescriptor;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * This class represents the upper section of the compare editor. This is where we are showing the list of files that
 * are part of the patchset.
 */
public class CompareUpperSection extends CompareViewerSwitchingPane {

	private CompareEditorInput fCompareEditorInput;

	private ViewerDescriptor fSelectedViewerDescriptor;

	Label leftPatch, rightPatch;

	public CompareUpperSection(Composite parent, int style, boolean visibility, CompareEditorInput cei) {
		super(parent, style, visibility);
		fCompareEditorInput = cei;
		//These two values can't be set in createTopLeft() because when it gets called fCompareEditorInput is not set yet
		leftPatch.setText(resolveShortName(((GerritMultipleInput) fCompareEditorInput).getLeftSide()));
		rightPatch.setText(resolveShortName(((GerritMultipleInput) fCompareEditorInput).getRightSide()));
	}

	private String resolveShortName(String toResolve) {
		RevisionInfo match = ((GerritMultipleInput) fCompareEditorInput).getChangeInfo().getRevisions().get(toResolve);
		if (match != null) {
			return "Patch set " + match.get_number();
		}
		return toResolve;
	}

	private CompareConfiguration getCompareConfiguration() {
		return fCompareEditorInput.getCompareConfiguration();
	}

	@Override
	protected Viewer getViewer(Viewer oldViewer, Object input) {
		if (input instanceof ICompareInput) {
			if (fSelectedViewerDescriptor != null) {
				ViewerDescriptor[] array = CompareUIPlugin.getDefault().findStructureViewerDescriptor(oldViewer,
						(ICompareInput) input, getCompareConfiguration());
				List list = array != null ? Arrays.asList(array) : Collections.EMPTY_LIST;
				if (list.contains(fSelectedViewerDescriptor)) {
					// use selected viewer only when appropriate for the new input
					fCompareEditorInput.setStructureViewerDescriptor(fSelectedViewerDescriptor);
					Viewer viewer = fCompareEditorInput.findStructureViewer(oldViewer, (ICompareInput) input, this);
					return viewer;
				}
				// fallback to default otherwise
				fSelectedViewerDescriptor = null;
			}

			fCompareEditorInput.setStructureViewerDescriptor(null);
			Viewer viewer = fCompareEditorInput.findStructureViewer(oldViewer, (ICompareInput) input, this);
			fCompareEditorInput.setStructureViewerDescriptor(fSelectedViewerDescriptor);
			return viewer;
		}
		return null;
	}

	private void fillMenuItemForChangeInfo(MenuManager menu, Label labelToUpdate, boolean side) {
		ArrayList<RevisionInfo> revisions = new ArrayList<RevisionInfo>(
				((GerritMultipleInput) fCompareEditorInput).getChangeInfo().getRevisions().values());
		revisions.sort((o1, o2) -> o2.get_number() - o1.get_number());
		revisions.stream().forEach(rev -> menu
				.add(new SwitchPatchAction((GerritMultipleInput) fCompareEditorInput, rev, labelToUpdate, side)));
	}

	@Override
	protected Control createTopLeft(Composite p) {
		final Composite composite = new Composite(p, SWT.NONE) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				return super.computeSize(wHint, Math.max(24, hHint), changed);
			}
		};

		composite.setLayout(new GridLayout(5, false));

		//Setup the text and button to select the left revision
		leftPatch = new Label(composite, SWT.LEFT | SWT.CENTER);
		GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gridData.widthHint = 90;
		leftPatch.setLayoutData(gridData);

		Button leftPatchSelector = new Button(composite, SWT.ARROW | SWT.DOWN);
		GridData gridDataLeftPathSelector = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gridDataLeftPathSelector.heightHint = 20;
		gridDataLeftPathSelector.widthHint = 20;
		leftPatchSelector.setLayoutData(gridDataLeftPathSelector);

		leftPatchSelector.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MenuManager mgr = new MenuManager();
				fillMenuItemForChangeInfo(mgr, leftPatch, true);
				mgr.add(new SwitchPatchAction((GerritMultipleInput) fCompareEditorInput, "WORKSPACE", leftPatch, true));
				mgr.add(new SwitchPatchAction((GerritMultipleInput) fCompareEditorInput, "BASE", leftPatch, true));
				mgr.createContextMenu(composite).setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//Nothing to do
			}
		});

		Label separator = new Label(composite, SWT.CENTER);
		separator.setText("  /  ");

		//Setup the text and button to select the right revision
		rightPatch = new Label(composite, SWT.NONE);
		GridData rightPatchData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		rightPatchData.widthHint = 90;
		rightPatch.setLayoutData(rightPatchData);

		Button rightPatchSelector = new Button(composite, SWT.DROP_DOWN | SWT.ARROW | SWT.DOWN);
		GridData gridDataRightPatchSelector = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gridDataRightPatchSelector.heightHint = 20;
		gridDataRightPatchSelector.widthHint = 20;
		rightPatchSelector.setLayoutData(gridDataRightPatchSelector);
		rightPatchSelector.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MenuManager mgr = new MenuManager();
				fillMenuItemForChangeInfo(mgr, rightPatch, false);
				mgr.createContextMenu(composite).setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//Nothing to do
			}
		});
		return composite;
	}

	@Override
	protected boolean inputChanged(Object input) {
		return getInput() != input || fCompareEditorInput.getStructureViewerDescriptor() != fSelectedViewerDescriptor;
	}

	@Override
	public void setInput(Object input) {
		super.setInput(input);
		if (getViewer() == null || !Utilities.okToUse(getViewer().getControl())) {
			return;
		}
		ViewerDescriptor[] vd = null;
		if (getInput() instanceof ICompareInput) {
			vd = CompareUIPlugin.getDefault().findStructureViewerDescriptor(getViewer(), (ICompareInput) getInput(),
					getCompareConfiguration());
		}
	}

	@Override
	public void setText(String label) {
		//Do nothing
	}

	@Override
	public void setImage(Image image) {
		Composite c = (Composite) getTopLeft();
		Control[] children = c.getChildren();
		for (Control element : children) {
			if (element instanceof CLabel) {
				CLabel cl = (CLabel) element;
				if (cl != null && !cl.isDisposed()) {
					cl.setImage(image);
				}
				return;
			}
		}
	}

	@Override
	public void addMouseListener(MouseListener listener) {
		Composite c = (Composite) getTopLeft();
		Control[] children = c.getChildren();
		for (Control element : children) {
			if (element instanceof CLabel) {
				CLabel cl = (CLabel) element;
				cl.addMouseListener(listener);
			}
		}
	}
}
