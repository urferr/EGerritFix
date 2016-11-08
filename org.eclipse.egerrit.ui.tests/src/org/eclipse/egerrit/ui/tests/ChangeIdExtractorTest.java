/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.egerrit.internal.dashboard.ui.views.ChangeIdExtractor;
import org.junit.Test;

public class ChangeIdExtractorTest {

	@Test
	//Test incorrect URL
	public void incorrectURL() {
		@SuppressWarnings("restriction")
		ChangeIdExtractor extractor = new ChangeIdExtractor("bad"); //$NON-NLS-1$
		assertNull(extractor.getServer());
		assertNull(extractor.getChangeId());
	}

	@Test
	//Test the case where just server is specified
	public void justAServer() {
		ChangeIdExtractor extractor = new ChangeIdExtractor("https://git.eclipse.org/r/"); //$NON-NLS-1$
		assertEquals("https://git.eclipse.org/r", extractor.getServer().getServerURI()); //$NON-NLS-1$
		assertNull(extractor.getChangeId());
	}

	@Test
	//Test the case where the URL is a server and changeId
	public void serverAndChangeId() {
		ChangeIdExtractor extractor = new ChangeIdExtractor("https://git.eclipse.org/r/#/c/1234"); //$NON-NLS-1$
		assertEquals("https://git.eclipse.org/r", extractor.getServer().getServerURI()); //$NON-NLS-1$
		assertEquals("1234", extractor.getChangeId()); //$NON-NLS-1$

		ChangeIdExtractor extractor2 = new ChangeIdExtractor("https://git.eclipse.org/r/#/c/1234/"); //$NON-NLS-1$
		assertEquals("https://git.eclipse.org/r", extractor2.getServer().getServerURI()); //$NON-NLS-1$
		assertEquals("1234", extractor2.getChangeId()); //$NON-NLS-1$
	}

	@Test
	//Test the case where the URL is a server and changeId and more stuffs after it
	public void serverChangeIdAndMore() {
		ChangeIdExtractor extractor = new ChangeIdExtractor("https://git.eclipse.org/r/#/c/1234/somethingElse"); //$NON-NLS-1$
		assertEquals("https://git.eclipse.org/r", extractor.getServer().getServerURI()); //$NON-NLS-1$
		assertEquals("1234", extractor.getChangeId()); //$NON-NLS-1$
	}

}
