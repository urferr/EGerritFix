package org.eclipse.egerrit.ui.swtbot.tests;
/*******************************************************************************
 * Copyright (c) 2014, 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Matthew Khouzam - Initial API and implementation
 *******************************************************************************/

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

/**
 * SWTBot Helper functions
 *
 * @author Matthew Khouzam
 */
public final class SWTBotUtils {

	private SWTBotUtils() {
	}

//    private static final String TRACING_PERSPECTIVE_ID = TracingPerspectiveFactory.ID;

	/**
	 * Waits for all Eclipse jobs to finish
	 */
	public static void waitForJobs() {
		while (!Job.getJobManager().isIdle()) {
			delay(100);
		}
	}

	/**
	 * Sleeps current thread for a given time.
	 *
	 * @param waitTimeMillis
	 *            time in milliseconds to wait
	 */
	public static void delay(final long waitTimeMillis) {
		try {
			Thread.sleep(waitTimeMillis);
		} catch (final InterruptedException e) {
			// Ignored
		}
	}

	/**
	 * Focus on the main window
	 *
	 * @param shellBots
	 *            swtbotshells for all the shells
	 */
	public static void focusMainWindow(SWTBotShell[] shellBots) {
		for (SWTBotShell shellBot : shellBots) {
			if (shellBot.getText().toLowerCase().contains("eclipse")) {
				shellBot.activate();
			}
		}
	}

	/**
	 * Close a view with a title
	 *
	 * @param title
	 *            the title, like "welcome"
	 * @param bot
	 *            the workbench bot
	 */
	public static void closeView(String title, SWTWorkbenchBot bot) {
		final List<SWTBotView> openViews = bot.views();
		for (SWTBotView view : openViews) {
			if (view.getTitle().equalsIgnoreCase(title)) {
				view.close();
				bot.waitUntil(ConditionHelpers.ViewIsClosed(view));
			}
		}
	}

	/**
	 * Switch to a given perspective
	 *
	 * @param id
	 *            the perspective id (like "org.eclipse.linuxtools.tmf.ui.perspective"
	 */
	public static void switchToPerspective(final String id) {
		UIThreadRunnable.syncExec(new VoidResult() {
			@Override
			public void run() {
				try {
					PlatformUI.getWorkbench().showPerspective(id, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
				} catch (WorkbenchException e) {
					fail(e.getMessage());
				}
			}
		});
	}

	/**
	 * If the test is running in the UI thread then fail
	 */
	public static void failIfUIThread() {
		if (Display.getCurrent() != null && Display.getCurrent().getThread() == Thread.currentThread()) {
			fail("SWTBot test needs to run in a non-UI thread. Make sure that \"Run in UI thread\" is unchecked in your launch configuration or"
					+ " that useUIThread is set to false in the pom.xml");
		}

	}

	private static String getFullNodeName(final SWTBotTreeItem treeItem, String prefix) {
		List<String> nodes = treeItem.getNodes();
		String nodeName = "";
		for (String node : nodes) {
			if (node.startsWith(prefix)) {
				nodeName = node;
			}
		}
		return nodeName;
	}

	/**
	 * Select the traces folder
	 *
	 * @param bot
	 *            a given workbench bot
	 * @param projectName
	 *            the name of the project (it needs to exist or else it would time out)
	 * @return a {@link SWTBotTreeItem} of the "Traces" directory
	 */
	public static SWTBotTreeItem selectTracesFolder(SWTWorkbenchBot bot, String projectName) {
		SWTBotView projectExplorerBot = bot.viewByTitle("Project Explorer");
		projectExplorerBot.show();
		SWTBotTreeItem treeItem = projectExplorerBot.bot().tree().getTreeItem(projectName);
		treeItem.select();
		treeItem.expand();
		SWTBotTreeItem treeNode = null;
		for (String node : treeItem.getNodes()) {
			if (node.matches("Traces\\s\\[(\\d)*\\]")) {
				treeNode = treeItem.getNode(node);
				break;
			}
		}
		assertNotNull(treeNode);
		return treeNode;
	}

	/**
	 * Open a view by id.
	 *
	 * @param id
	 *            view id.
	 */
	public static void openView(final String id) {
		final PartInitException res[] = new PartInitException[1];
		UIThreadRunnable.syncExec(new VoidResult() {
			@Override
			public void run() {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(id);
				} catch (PartInitException e) {
					res[0] = e;
				}
			}
		});
		if (res[0] != null) {
			fail(res[0].getMessage());
		}
		waitForJobs();
	}
}
