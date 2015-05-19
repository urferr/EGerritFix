/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.ui.swtbot.tests;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarDropDownButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class) 
public class NavigatetoCompareTest {

	private static SWTWorkbenchBot bot;

	private static final Logger fLogger = Logger.getRootLogger();

	@BeforeClass
	public static void beforeClass() throws Exception {
		// don't use SWTWorkbenchBot here which relies on Platform 3.x
		bot = new SWTWorkbenchBot();
		SWTBotUtils.failIfUIThread();
		
		fLogger.addAppender(new ConsoleAppender(new SimpleLayout()));

		SWTBotUtils.closeView("welcome", bot);

                SWTBotUtils.waitForJobs();

//		SWTBotPreferences.PLAYBACK_DELAY = 100;

		SWTBotPreferences.TIMEOUT = 80000;

	}


	@Test
	public void openReviewAndCompare() {

		bot = new SWTWorkbenchBot(); // reset...

		
		// open Gerrit Dashboard preferences
		bot.menu("Window").menu("Preferences").click();
		SWTBotTree treeBot = bot.tree();
		SWTBotTreeItem treeItem = treeBot.getTreeItem("Gerrit Dashboard Preferences").select();



		try {
			SWTBotShell secStorage = bot.shell("Secure Storage");
			if (secStorage != null) {
				bot.shell("Secure Storage").activate();
				sleep();
				bot.textWithLabel("Password:").setText("password");
//				try {
//				if (secStorage.bot().textWithLabel("Confirm password:") != null) {
//					secStorage.bot().textWithLabel("Confirm password:").setText("password");
//				}
//				} catch (WidgetNotFoundException e)
//				{ 
//				}
				bot.button("OK").click();			
			}			
		} catch (WidgetNotFoundException e)
		{}
        

		treeBot.setFocus();
		treeBot.getTreeItem("Gerrit Dashboard Preferences").select();

		
		// delete if exists already
		SWTBotTable eventsEditor = bot.shell("Preferences").bot().table();
		eventsEditor.setFocus();
		if (eventsEditor.indexOf("VagrantTest2.9.4") != -1) {
			eventsEditor.select("VagrantTest2.9.4");
			bot.button("Remove").click();
		}

		
		// enter a server configuration in the preferences dialog
		bot.button("New...").click();
		
		bot.textWithLabel("Scheme:").setText("http");
		bot.textWithLabel("Host Id:").setText("192.168.50.4");
		bot.textWithLabel("Port:").setText("28294");
		bot.textWithLabel("Server Path:").setText("/gerrit-2.9.4");
		bot.textWithLabel("Gerrit Server Name:").setText("VagrantTest2.9.4");
		bot.button("OK").click();
		
		
		// click ok in the preferences dialog
		bot.button("OK").click();
		System.out.println("3");
		

//		try {
//			GitAccess gAccess = new GitAccess();
//			Git git = gAccess.getGitProject();
//
//			gAccess.addFile("EGerritTestReviewFile.java", "Hello reviewers community !");
//			gAccess.pushFile();
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}

		// select a gerrit server
		bot.menu("Window").menu("Show View").click().menu("Other...").click();
		SWTBotTree treeBot2 = bot.tree();
		treeBot2.expandNode("Other", true);
		SWTBotTreeItem treeItem2 = treeBot2.getTreeItem("Other").getNode("eGerrit Dashboard").doubleClick();
		
		// secure storage screen
//		try {
//			SWTBotShell secStorage = bot.shell("Secure Storage");
//			if (secStorage != null) {
//				bot.shell("Secure Storage").activate();
//				sleep();
//			    bot.button("No").click();			
//			}
//		} catch (WidgetNotFoundException e) 
//		{}
		
		
		SWTBotView egerritDash = bot.viewById("org.eclipse.egerrit.dashboard.ui.views.GerritTableView");
		SWTBotToolbarDropDownButton dropDown = egerritDash.toolbarDropDownButton("Select current Gerrit repository");
		dropDown.menuItem("VagrantTest2.9.4").click();

		// escape drop down
		bot.activeShell().pressShortcut(Keystrokes.ESC);

		// select a review with subject Test commit message
		bot = new SWTWorkbenchBot(); // reset...
		egerritDash = bot.viewById("org.eclipse.egerrit.dashboard.ui.views.GerritTableView");
		egerritDash.setFocus();
		eventsEditor = bot.activeShell().bot().table();
		int rowIdx = eventsEditor.indexOf("Test commit message", 2);
		if (rowIdx != -1) {
			eventsEditor.select(rowIdx);
			eventsEditor.doubleClick(rowIdx, 0);
		}

		// select the Editor "org.eclipse.egerrit.ui.editors.ChangeDetailEditor"
		SWTBotEditor gerritEditor = bot.editorById("org.eclipse.egerrit.ui.editors.ChangeDetailEditor");
		gerritEditor.setFocus();

		// activate the Files tab
		bot.tabItem("Files").activate();

		// select the table list (first one), and select a file EGerritTestReviewFile.java and fire up CompareEditor
		eventsEditor = bot.activeShell().bot().table(0);
		rowIdx = eventsEditor.indexOf("EGerritTestReviewFile.java", 2);
		if (rowIdx != -1) {
			eventsEditor.select(rowIdx);
			eventsEditor.doubleClick(rowIdx, 0);
		}

		assert (true);
		return;

	}

	@AfterClass
	public static void sleep() {
		bot.sleep(2001);
	}
}
