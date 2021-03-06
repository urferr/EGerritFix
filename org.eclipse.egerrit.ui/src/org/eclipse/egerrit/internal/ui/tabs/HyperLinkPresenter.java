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

import org.eclipse.core.runtime.Assert;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.ITextViewerExtension4;
import org.eclipse.jface.text.ITextViewerExtension6;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenterExtension2;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * The default hyperlink presenter underlines the link and colors the line and the text with the given color.
 * <p>
 * It can only be used together with the {@link EGerritHyperlinkManager#FIRST} or the
 * {@link EGerritHyperlinkManager#LONGEST_REGION_FIRST} hyperlink strategy.
 * </p>
 *
 * @since 3.1
 */

/* Change the IHyperlinkPresenterExtension by IHyperlinkPresenterExtension2 for multi hyperlink */

class HyperLinkPresenter implements IHyperlinkPresenter, IHyperlinkPresenterExtension2, ITextPresentationListener,
		ITextInputListener, IDocumentListener, IPropertyChangeListener {

	private ISourceViewer fSourceViewer;

	private GerritClient fGerritClient;

	private ChangeInfo fChangeInfo;

	/**
	 * A named preference that holds the color used for hyperlinks.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string using class
	 * <code>PreferenceConverter</code>.
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	private static final String HYPERLINK_COLOR = "hyperlinkColor"; //$NON-NLS-1$

	/**
	 * A named preference that holds the preference whether to use the native link color.
	 * <p>
	 * The preference value is of type <code>Boolean</code>.
	 * </p>
	 *
	 * @since 3.5
	 */
	private static final String HYPERLINK_COLOR_SYSTEM_DEFAULT = "hyperlinkColor.SystemDefault"; //$NON-NLS-1$

	/** The text viewer. */
	private ITextViewer fTextViewer;

	/** The link color. */
	private Color fColor;

	/**
	 * Tells whether to use native link color.
	 *
	 * @since 3.5
	 */
	private boolean fIsUsingNativeLinkColor;

	/** The link color specification. May be <code>null</code>. */
	private RGB fRGB;

	/** Tells whether to dispose the color on uninstall. */
	private boolean fDisposeColor;

	/** The currently active region. */
	private IRegion fActiveRegion;

	/** The currently active style range as position. */
	private Position fRememberedPosition;

	/** The optional preference store. May be <code>null</code>. */
	private IPreferenceStore fPreferenceStore;

	private int fClickedOffset;

	/**
	 * Creates a new default hyperlink presenter which uses {@link #HYPERLINK_COLOR} to read the color from the given
	 * preference store.
	 *
	 * @param store
	 *            the preference store
	 */
	public HyperLinkPresenter(IPreferenceStore store) {
		fPreferenceStore = store;
		fDisposeColor = true;
	}

	/**
	 * Creates a new default hyperlink presenter.
	 *
	 * @param color
	 *            the hyperlink color or <code>null</code> if the existing text color should be preserved; to be
	 *            disposed by the caller
	 */
	public HyperLinkPresenter(Color color) {
		fColor = color;
	}

	/**
	 * Creating the presenter for EGerrit viewer
	 *
	 * @param rgb
	 * @param sourceViewer
	 * @param tableViewer
	 * @param gerritClient
	 * @param changeInfo
	 */
	HyperLinkPresenter(RGB rgb, ISourceViewer sourceViewer, GerritClient gerritClient, ChangeInfo changeInfo) {
		fRGB = rgb;
		fDisposeColor = true;
		fSourceViewer = sourceViewer;
		fGerritClient = gerritClient;
		fChangeInfo = changeInfo;

	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IHyperlinkControl#canShowMultipleHyperlinks()
	 */
	public boolean canShowMultipleHyperlinks() {
		return true;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IHyperlinkControl#activate(org.eclipse.jdt.internal.ui.javaeditor.IHyperlink[])
	 */
	public void showHyperlinks(IHyperlink[] hyperlinks) {
		Assert.isLegal(hyperlinks != null && hyperlinks.length >= 1);
		for (IHyperlink hyperlink : hyperlinks) {
			highlightRegion(hyperlink.getHyperlinkRegion());
		}
		if (hyperlinks != null && hyperlinks.length > 1) {
			for (IHyperlink hyperlink : hyperlinks) {
				if (isWithinRegion(hyperlink.getHyperlinkRegion()) && fClickedOffset != -1) {
					hyperlink.open();
				}
			}
		}
		setOffset(-1);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @since 3.4
	 */
	public boolean canHideHyperlinks() {
		return true;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IHyperlinkControl#deactivate()
	 */
	public void hideHyperlinks() {
		repairRepresentation();
		fRememberedPosition = null;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IHyperlinkControl#install(org.eclipse.jface.text.ITextViewer)
	 */
	public void install(ITextViewer textViewer) {
		Assert.isNotNull(textViewer);
		fTextViewer = textViewer;
		fTextViewer.addTextInputListener(this);
		if (fTextViewer instanceof ITextViewerExtension4) {
			((ITextViewerExtension4) fTextViewer).addTextPresentationListener(this);
		}

		if (fPreferenceStore != null) {
			fIsUsingNativeLinkColor = fPreferenceStore.getBoolean(HYPERLINK_COLOR_SYSTEM_DEFAULT);
			if (!fIsUsingNativeLinkColor) {
				fColor = createColorFromPreferenceStore();
			}
			fPreferenceStore.addPropertyChangeListener(this);
		} else if (fRGB != null) {
			StyledText text = fTextViewer.getTextWidget();
			if (text != null && !text.isDisposed()) {
				fColor = new Color(text.getDisplay(), fRGB);
			}
		}
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.javaeditor.IHyperlinkControl#uninstall()
	 */
	public void uninstall() {
		fTextViewer.removeTextInputListener(this);
		IDocument document = fTextViewer.getDocument();
		if (document != null) {
			document.removeDocumentListener(this);
		}

		if (fColor != null) {
			if (fDisposeColor) {
				fColor.dispose();
			}
			fColor = null;
		}

		if (fTextViewer instanceof ITextViewerExtension4) {
			((ITextViewerExtension4) fTextViewer).removeTextPresentationListener(this);
		}
		fTextViewer = null;

		if (fPreferenceStore != null) {
			fPreferenceStore.removePropertyChangeListener(this);
			fPreferenceStore = null;
		}
	}

	/**
	 * Sets the hyperlink foreground color.
	 *
	 * @param color
	 *            the hyperlink foreground color or <code>null</code> if the existing text color should be preserved
	 */
	public void setColor(Color color) {
		Assert.isNotNull(fTextViewer);
		Assert.isTrue(fPreferenceStore == null, "Cannot set color if preference store is set"); //$NON-NLS-1$
		if (fColor != null && fDisposeColor) {
			fColor.dispose();
		}
		fColor = color;
	}

	/*
	 * @see org.eclipse.jface.text.ITextPresentationListener#applyTextPresentation(org.eclipse.jface.text.TextPresentation)
	 */
	public void applyTextPresentation(TextPresentation textPresentation) {
		if (fActiveRegion == null) {
			return;
		}
		IRegion region = textPresentation.getExtent();
		if (fActiveRegion.getOffset() + fActiveRegion.getLength() >= region.getOffset()
				&& region.getOffset() + region.getLength() > fActiveRegion.getOffset()) {
			Color color = null;
			if (!fIsUsingNativeLinkColor) {
				color = fColor;
			}
			StyleRange styleRange = new StyleRange(fActiveRegion.getOffset(), fActiveRegion.getLength(), color, null);
			styleRange.underlineStyle = SWT.UNDERLINE_LINK;
			styleRange.underline = true;
			textPresentation.mergeStyleRange(styleRange);
		}
	}

	private void highlightRegion(IRegion region) {

		if (region.equals(fActiveRegion)) {
			return;
		}

		StyledText text = fTextViewer.getTextWidget();
		if (text == null || text.isDisposed()) {
			return;
		}

		// Invalidate region ==> apply text presentation
		fActiveRegion = region;
		if (fTextViewer instanceof ITextViewerExtension6) {
			((ITextViewerExtension2) fTextViewer).invalidateTextPresentation(region.getOffset(), region.getLength());
		} else {
			fTextViewer.invalidateTextPresentation();
		}
	}

	private void repairRepresentation() {

		if (fActiveRegion == null) {
			return;
		}

		int offset = fActiveRegion.getOffset();
		int length = fActiveRegion.getLength();
		fActiveRegion = null;

		// Invalidate ==> remove applied text presentation
		if (fTextViewer instanceof ITextViewerExtension2) {
			((ITextViewerExtension2) fTextViewer).invalidateTextPresentation(offset, length);
		} else {
			fTextViewer.invalidateTextPresentation();
		}

	}

	/*
	 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	public void documentAboutToBeChanged(DocumentEvent event) {
		if (fActiveRegion != null) {
			fRememberedPosition = new Position(fActiveRegion.getOffset(), fActiveRegion.getLength());
			try {
				event.getDocument().addPosition(fRememberedPosition);
			} catch (BadLocationException x) {
				fRememberedPosition = null;
			}
		}
	}

	/*
	 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	public void documentChanged(DocumentEvent event) {
		if (fRememberedPosition != null) {
			if (!fRememberedPosition.isDeleted()) {
				event.getDocument().removePosition(fRememberedPosition);
				fActiveRegion = new Region(fRememberedPosition.getOffset(), fRememberedPosition.getLength());
			} else {
				fActiveRegion = new Region(event.getOffset(), event.getLength());
			}
			fRememberedPosition = null;

			StyledText widget = fTextViewer.getTextWidget();
			if (widget != null && !widget.isDisposed()) {
				widget.getDisplay().asyncExec(new Runnable() {
					public void run() {
						hideHyperlinks();
					}
				});
			}
		}
	}

	/*
	 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentAboutToBeChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
	 */
	public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
		if (oldInput == null) {
			return;
		}
		hideHyperlinks();
		oldInput.removeDocumentListener(this);
	}

	/*
	 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
	 */
	public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
		if (newInput == null) {
			return;
		}
		newInput.addDocumentListener(this);

		IHyperlink[] links = null;
		if (fSourceViewer != null) {
			links = new HyperLinkDetector(fGerritClient, fChangeInfo).detectHyperlinks(fSourceViewer, null, true);
		}
		if (links != null) {
			showHyperlinks(links);
		}
	}

	/**
	 * Creates a color from the information stored in the given preference store.
	 *
	 * @return the color or <code>null</code> if there is no such information available or i f the text widget is not
	 *         available
	 */
	private Color createColorFromPreferenceStore() {
		StyledText textWidget = fTextViewer.getTextWidget();
		if (textWidget == null || textWidget.isDisposed()) {
			return null;
		}

		RGB rgb = null;

		if (fPreferenceStore.contains(HYPERLINK_COLOR)) {

			if (fPreferenceStore.isDefault(HYPERLINK_COLOR)) {
				rgb = PreferenceConverter.getDefaultColor(fPreferenceStore, HYPERLINK_COLOR);
			} else {
				rgb = PreferenceConverter.getColor(fPreferenceStore, HYPERLINK_COLOR);
			}

			if (rgb != null) {
				return new Color(textWidget.getDisplay(), rgb);
			}
		}

		return null;
	}

	/*
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (HYPERLINK_COLOR.equals(event.getProperty())) {
			if (fColor != null && fDisposeColor) {
				fColor.dispose();
			}
			fColor = createColorFromPreferenceStore();
			return;
		}

		if (HYPERLINK_COLOR_SYSTEM_DEFAULT.equals(event.getProperty())) {
			fIsUsingNativeLinkColor = fPreferenceStore.getBoolean(HYPERLINK_COLOR_SYSTEM_DEFAULT);
			if (!fIsUsingNativeLinkColor && fColor == null) {
				fColor = createColorFromPreferenceStore();
			}
			return;
		}
	}

	/**
	 * ShowHyperLinks come with IHyperlinkPresenterExtension2
	 */
	@Override
	public void showHyperlinks(IHyperlink[] activeHyperlinks, boolean takesFocusWhenVisible) {
		if (activeHyperlinks != null && activeHyperlinks.length == 1) {
			return;
		}

		Assert.isLegal(activeHyperlinks != null && activeHyperlinks.length >= 1);
		for (IHyperlink hyperlink : activeHyperlinks) {
			highlightRegion(hyperlink.getHyperlinkRegion());
		}
	}

	private boolean isWithinRegion(IRegion region) {

		if (fClickedOffset != -1 && fClickedOffset >= region.getOffset()
				&& fClickedOffset <= (region.getLength() + region.getOffset())) {
			return true;
		}

		return false;
	}

	/**
	 * save the location where the user clicked in the text area
	 */
	public void setOffset(int offset) {
		fClickedOffset = offset;
	}

}
