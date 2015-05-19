/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.tests;

import org.eclipse.egerrit.core.command.tests.GerritCommandTest;
import org.eclipse.egerrit.core.command.tests.GetChangeCommandTest;
import org.eclipse.egerrit.core.command.tests.GetCommitMsgCommandTest;
import org.eclipse.egerrit.core.command.tests.GetContentCommandTest;
import org.eclipse.egerrit.core.command.tests.GetMergeableCommandTest;
import org.eclipse.egerrit.core.command.tests.QueryChangesCommandTest;
import org.eclipse.egerrit.core.rest.tests.AccountInfoTest;
import org.eclipse.egerrit.core.rest.tests.ActionInfoTest;
import org.eclipse.egerrit.core.rest.tests.ApprovalInfoTest;
import org.eclipse.egerrit.core.rest.tests.ChangeInfoTest;
import org.eclipse.egerrit.core.rest.tests.ChangeMessageInfoTest;
import org.eclipse.egerrit.core.rest.tests.CommitInfoTest;
import org.eclipse.egerrit.core.rest.tests.FetchInfoTest;
import org.eclipse.egerrit.core.rest.tests.FileInfoTest;
import org.eclipse.egerrit.core.rest.tests.GitPersonInfoTest;
import org.eclipse.egerrit.core.rest.tests.LabelInfoTest;
import org.eclipse.egerrit.core.rest.tests.ProblemInfoTest;
import org.eclipse.egerrit.core.rest.tests.RevisionInfoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// ------------------------------------------------------------------------
// Test suite
// ------------------------------------------------------------------------

@RunWith(Suite.class)
@SuiteClasses({
	// org.eclipse.egerrit.core
	EGerritCorePluginTest.class,
		GerritRepositoryTest.class,
		GerritClientAuthenticationTest.class,
	GerritFactoryTest.class,
	GerritTest.class,
	Gerrit_2_9Test.class,

	// org.eclipse.egerrit.core.rest
	AccountInfoTest.class, ActionInfoTest.class, ApprovalInfoTest.class,
		ChangeInfoTest.class, ChangeMessageInfoTest.class,
		CommitInfoTest.class, FetchInfoTest.class, FileInfoTest.class,
	GitPersonInfoTest.class, LabelInfoTest.class,
		ProblemInfoTest.class,
		RevisionInfoTest.class,

	// org.eclipse.egerrit.core.command
	GerritCommandTest.class, QueryChangesCommandTest.class,
		GetChangeCommandTest.class, GetContentCommandTest.class,
		GetCommitMsgCommandTest.class, GetMergeableCommandTest.class })
// ------------------------------------------------------------------------
// Constructor
// ------------------------------------------------------------------------
/**
 * AllEGerritCoreTests
 *
 * @since 1.0
 */
public class AllEGerritCoreTests {
}
