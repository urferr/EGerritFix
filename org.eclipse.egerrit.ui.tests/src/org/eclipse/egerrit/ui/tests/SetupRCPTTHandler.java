/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Created for Review Gerrit Dashboard project
 *
 ******************************************************************************/
package org.eclipse.egerrit.ui.tests;

import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

/**
 * This class implements the RCPTT setup entry point to GitAccess
 *
 * @since 1.0
 */
public class SetupRCPTTHandler extends AbstractHandler implements IElementUpdater {

	@SuppressWarnings("finally")
	@Override
	public Object execute(final ExecutionEvent aEvent) {

		try {
			RCPTTInitialSetOfReviews setup = new RCPTTInitialSetOfReviews();
			setup.setupRepo();
			setup.setUp();

			RCPTTInitialSetOfReviews setup2 = new RCPTTInitialSetOfReviews();
			setup2.setupRepo();
			setup2.createReviewWithSimpleFile(true);
			setup2.amendLastCommit(true);

		} catch (Exception e1) {
			fail(e1.getMessage());
		} finally {
			return Status.OK_STATUS;
		}
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
	}
}
