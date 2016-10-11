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

package org.eclipse.egerrit.internal.ui.tabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.hyperlink.HyperlinkManager;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetectorExtension2;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenterExtension;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenterExtension2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseEvent;

/**
 * @author lmcbout Note: this class is a copy of the class HyperlinkManager
 */
public class EGerritHyperlinkManager extends HyperlinkManager {
	/**
	 * Text operation code for requesting to open the hyperlink at the caret position.
	 *
	 * @see #openHyperlink()
	 * @since 3.6
	 */

	/** The text viewer on which this hyperlink manager works. */
	private ITextViewer fTextViewer;

	/** The session is active. */
	private boolean fActive;

	/** The key modifier mask of the default hyperlink modifier. */
	private int fHyperlinkStateMask;

	/**
	 * The active key modifier mask.
	 *
	 * @since 3.3
	 */
	private int fActiveHyperlinkStateMask;

	/** The active hyperlinks. */
	private IHyperlink[] fActiveHyperlinks;

	/** The hyperlink detectors. */
	private IHyperlinkDetector[] fHyperlinkDetectors;

	/** The hyperlink presenter. */
	private IHyperlinkPresenter fHyperlinkPresenter;

	/** The detection strategy. */
	private final org.eclipse.jface.text.hyperlink.HyperlinkManager.DETECTION_STRATEGY fDetectionStrategy;

	/**
	 * Creates a new hyperlink manager.
	 *
	 * @param all2
	 *            the detection strategy one of {{@link #ALL}, {@link #FIRST}, {@link #LONGEST_REGION_ALL},
	 *            {@link #LONGEST_REGION_FIRST}}
	 * @param detectionStrategy
	 */
	public EGerritHyperlinkManager(DETECTION_STRATEGY detectionStrategy) {
		super(detectionStrategy);
		Assert.isNotNull(detectionStrategy);
		fDetectionStrategy = detectionStrategy;
	}

	/**
	 * Installs this hyperlink manager with the given arguments.
	 *
	 * @param textViewer
	 *            the text viewer
	 * @param hyperlinkPresenter
	 *            the hyperlink presenter
	 * @param hyperlinkDetectors
	 *            the array of hyperlink detectors, must not be empty
	 * @param eventStateMask
	 *            the SWT event state mask to activate hyperlink mode
	 */
	@Override
	public void install(ITextViewer textViewer, IHyperlinkPresenter hyperlinkPresenter,
			IHyperlinkDetector[] hyperlinkDetectors, int eventStateMask) {
		super.install(textViewer, hyperlinkPresenter, hyperlinkDetectors, eventStateMask);
		Assert.isNotNull(textViewer);
		Assert.isNotNull(hyperlinkPresenter);
		fTextViewer = textViewer;
		//Create a document
		if (fTextViewer.getDocument() == null) {
			IDocument document = new Document(fTextViewer.getTextWidget().getText());
			fTextViewer.setDocument(document);
		}
		fHyperlinkPresenter = hyperlinkPresenter;
		fTextViewer.addTextListener(this);

		fHyperlinkPresenter.install(fTextViewer);
	}

	/**
	 * Sets the hyperlink detectors for this hyperlink manager.
	 * <p>
	 * It is allowed to call this method after this hyperlink manger has been installed.
	 * </p>
	 *
	 * @param hyperlinkDetectors
	 *            and array of hyperlink detectors, must not be empty
	 */
	@Override
	public void setHyperlinkDetectors(IHyperlinkDetector[] hyperlinkDetectors) {
		super.setHyperlinkDetectors(hyperlinkDetectors);
		Assert.isTrue(hyperlinkDetectors != null && hyperlinkDetectors.length > 0);
		if (fHyperlinkDetectors == null) {
			fHyperlinkDetectors = hyperlinkDetectors;
		} else {
			synchronized (fHyperlinkDetectors) {
				fHyperlinkDetectors = hyperlinkDetectors;
			}
		}
	}

	/**
	 * Deactivates the currently shown hyperlinks.
	 */
	@Override
	protected void deactivate() {
//		fHyperlinkPresenter.hideHyperlinks();
		fActive = false;
	}

	/**
	 * Finds hyperlinks at the current offset.
	 *
	 * @return the hyperlinks or <code>null</code> if none.
	 */
	@Override
	protected IHyperlink[] findHyperlinks() {
		int offset = getCurrentTextOffset();
		if (offset == -1) {
			return null;
		}

		IRegion region = new Region(offset, 0);
		return findHyperlinks(region);
	}

	/**
	 * Returns the hyperlinks in the given region or <code>null</code> if none.
	 *
	 * @param region
	 *            the selection region
	 * @return the array of hyperlinks found or <code>null</code> if none
	 * @since 3.7
	 */
	private IHyperlink[] findHyperlinks(IRegion region) {
		List<IHyperlink> allHyperlinks = new ArrayList<>(fHyperlinkDetectors.length * 2);
		synchronized (fHyperlinkDetectors) {
			for (IHyperlinkDetector detector : fHyperlinkDetectors) {
				if (detector == null) {
					continue;
				}

				if (detector instanceof IHyperlinkDetectorExtension2) {
					int stateMask = ((IHyperlinkDetectorExtension2) detector).getStateMask();
					if (stateMask != -1 && stateMask != fActiveHyperlinkStateMask) {
						continue;
					} else if (stateMask == -1 && fActiveHyperlinkStateMask != fHyperlinkStateMask) {
						continue;
					}
				} else if (fActiveHyperlinkStateMask != fHyperlinkStateMask) {
					continue;
				}

				boolean canShowMultipleHyperlinks = fHyperlinkPresenter.canShowMultipleHyperlinks();
				if (fTextViewer.getDocument() == null) {
					IDocument document = new Document(fTextViewer.getTextWidget().getText());
					fTextViewer.setDocument(document);
				}
				IHyperlink[] hyperlinks = detector.detectHyperlinks(fTextViewer, region, canShowMultipleHyperlinks);
				if (hyperlinks == null) {
					continue;
				}

				Assert.isLegal(hyperlinks.length > 0);

				if (fDetectionStrategy == FIRST) {
					if (hyperlinks.length == 1) {
						return hyperlinks;
					}
					return new IHyperlink[] { hyperlinks[0] };
				}
				allHyperlinks.addAll(Arrays.asList(hyperlinks));
			}
		}

		if (allHyperlinks.isEmpty()) {
			return null;
		}

		if (fDetectionStrategy != ALL) {
			int maxLength = computeLongestHyperlinkLength(allHyperlinks);
			Iterator<IHyperlink> iter = new ArrayList<>(allHyperlinks).iterator();
			while (iter.hasNext()) {
				IHyperlink hyperlink = iter.next();
				if (hyperlink.getHyperlinkRegion().getLength() < maxLength) {
					allHyperlinks.remove(hyperlink);
				}
			}
		}

		if (fDetectionStrategy == LONGEST_REGION_FIRST) {
			return new IHyperlink[] { allHyperlinks.get(0) };
		}

		return allHyperlinks.toArray(new IHyperlink[allHyperlinks.size()]);

	}

/*
* @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
*/
	@Override
	public void mouseDown(MouseEvent event) {
		if (fHyperlinkPresenter instanceof IHyperlinkPresenterExtension2 && fActiveHyperlinks != null) {

			StyledText source = (StyledText) event.getSource();

			((HyperLinkPresenter) fHyperlinkPresenter).setOffset(source.getCaretOffset());

			((IHyperlinkPresenterExtension2) fHyperlinkPresenter).showHyperlinks(fActiveHyperlinks, fActive);

		}
		if (fHyperlinkPresenter instanceof IHyperlinkPresenterExtension) {
			if (!((IHyperlinkPresenterExtension) fHyperlinkPresenter).canHideHyperlinks()) {
				return;
			}
		}

		if (!isRegisteredStateMask(event.stateMask)) {
			if (fActive) {
				deactivate();
			}

			return;
		}

		if (event.button != 1) {
			deactivate();
			return;
		}

		fActive = true;
		fActiveHyperlinkStateMask = event.stateMask & SWT.MODIFIER_MASK;

		StyledText text = fTextViewer.getTextWidget();
		if (text == null || text.isDisposed()) {
			deactivate();
			return;
		}

		if (text.isTextSelected()) {
			deactivate();
			return;
		}

		fActiveHyperlinks = findHyperlinks();
	}

/*
* @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
*/
	@Override
	public void mouseUp(MouseEvent e) {

		int cursorOffset = ((StyledText) e.getSource()).getCaretOffset();
		if (!fActive) {
			fActiveHyperlinks = null;
			return;
		}

		if (e.button != 1) {
			fActiveHyperlinks = null;
		}

		deactivate();
		IHyperlink currentHyperlink = null;
		if (fActiveHyperlinks == null) {
			fActiveHyperlinks = findHyperlinks();
		}
		if (fActiveHyperlinks != null) {
			for (IHyperlink fActiveHyperlink : fActiveHyperlinks) {
				IRegion region = fActiveHyperlink.getHyperlinkRegion();
				if (region.getOffset() < cursorOffset && cursorOffset < (region.getOffset() + region.getLength())) {
					currentHyperlink = fActiveHyperlink;
					break;
				}
			}

			if (currentHyperlink != null) {
				showHyperlinks(true);
			}

		}
	}

/*
* @see org.eclipse.swt.events.MouseMoveListener#mouseMove(org.eclipse.swt.events.MouseEvent)
*/
	@Override
	public void mouseMove(MouseEvent event) {
	}

	/**
	 * Checks whether the given state mask is registered.
	 *
	 * @param stateMask
	 *            the state mask
	 * @return <code>true</code> if a detector is registered for the given state mask
	 * @since 3.3
	 */
	private boolean isRegisteredStateMask(int stateMask) {
		if (stateMask == fHyperlinkStateMask) {
			return true;
		}

		synchronized (fHyperlinkDetectors) {
			for (IHyperlinkDetector fHyperlinkDetector : fHyperlinkDetectors) {
				if (fHyperlinkDetector instanceof IHyperlinkDetectorExtension2) {
					if (stateMask == ((IHyperlinkDetectorExtension2) fHyperlinkDetector).getStateMask()) {
						return true;
					}
				}
			}
		}
		return false;
	}

/*
* @see org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events.FocusEvent)
*/
	@Override
	public void focusGained(FocusEvent e) {
		showAllLinks();
	}

	/**
	 *
	 */
	private void showAllLinks() {
		//Set all hyperlinks visible
		if (fActiveHyperlinks == null) {
			if (fTextViewer != null && !fTextViewer.getTextWidget().getText().isEmpty()) {
				if (fTextViewer.getDocument() == null) {
					return;
				}
				IRegion region = new Region(0, ((TextViewer) fTextViewer).getBottomIndexEndOffset());
				fActiveHyperlinks = findHyperlinks(region);
				if (fActiveHyperlinks != null && fActiveHyperlinks.length > 1) {
					showHyperlinks(true);
				} else {
					//Do not trigger the open
					showHyperlinks(false);
				}
			}
		}
	}

	/**
	 * Opens the hyperlink at the current caret location directly if there's only one link, else opens the hyperlink
	 * control showing all the hyperlinks at that location.
	 *
	 * @param takesFocusWhenVisible
	 *            <code>true</code> if the control takes focus when visible, <code>false</code> otherwise
	 * @return <code>true</code> if at least one hyperlink has been found at the caret location, <code>false</code>
	 *         otherwise
	 * @since 3.7
	 */
	private boolean showHyperlinks(boolean takesFocusWhenVisible) {

		if (fActiveHyperlinks == null || fActiveHyperlinks.length == 0) {
			return false;
		}
		if (fActiveHyperlinks.length == 1 && takesFocusWhenVisible) {
			fActiveHyperlinks[0].open();
		} else {
			fHyperlinkPresenter.showHyperlinks(fActiveHyperlinks);
		}
		return true;

	}

	/**
	 * Opens the hyperlink at the caret location or opens a chooser if more than one hyperlink is available.
	 *
	 * @return <code>true</code> if at least one hyperlink has been found at the caret location, <code>false</code>
	 *         otherwise
	 * @see #OPEN_HYPERLINK
	 * @since 3.6
	 */
	@Override
	public boolean openHyperlink() {
		fActiveHyperlinkStateMask = fHyperlinkStateMask;

		if (fHyperlinkPresenter instanceof IHyperlinkPresenterExtension) {
			if (!((IHyperlinkPresenterExtension) fHyperlinkPresenter).canHideHyperlinks()) {
				return false;
			}
		}
		ITextSelection sel = (ITextSelection) ((TextViewer) fTextViewer).getSelection();
		int offset = sel.getOffset();
		if (offset == -1) {
			return false;
		}

		IRegion region = new Region(offset, 0);
		fActiveHyperlinks = findHyperlinks(region);
		return showHyperlinks(true);
	}
}