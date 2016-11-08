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
package org.eclipse.egerrit.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.egerrit.core.command.tests.CommandTestWithSimpleReview;
import org.eclipse.egerrit.internal.ui.editors.CheckoutRevision;
import org.eclipse.egerrit.internal.ui.table.model.BranchMatch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the CheckoutRevision class
 *
 * @since 1.0
 */
public class CheckoutRevisionTest extends CommandTestWithSimpleReview {

	//Help to create a commit in a new branch
	private void createCommit(String branchName, boolean includeCurrentChangeIdInMessage) throws Exception {
		createAnotherBranch(branchName);

		filename = "folder/EGerritTestReviewFile" + getClass().getSimpleName() + System.currentTimeMillis() //$NON-NLS-1$
				+ ".java"; //$NON-NLS-1$
		fileContent = "Hello reviewers {community} !\n This is the second line \n This is the third line \n"; //$NON-NLS-1$
		gitAccess.addFile(filename, fileContent);

		String commitMsg = "CheckoutRevisionTest" + System.currentTimeMillis(); //$NON-NLS-1$
		if (includeCurrentChangeIdInMessage) {
			commitMsg += "\n\nChange-Id: " + gitAccess.getChangeId(); //$NON-NLS-1$
		}
		gitAccess.commitFile(commitMsg, false, false);
	}

	//Helper to create a new branch
	private void createAnotherBranch(String branchName) {
		try {
			gitAccess.createAndCheckoutBranch(branchName);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Return the default branchname proposed by the egit fetch dialog
	 *
	 * @param selectedBranch
	 */
	private String getDefaultCheckoutBranchName() {
		return changeInfo.get_number() + "/" //$NON-NLS-1$
				+ changeInfo.getUserSelectedRevision().get_number();
	}

	/**
	 * Modify the existing changeInfo data structure, so it would not match any of the branch identification
	 */
	private void fudgeChangeInfo() {
		//Change the changeId and the commit string after setting our local variables
		changeInfo.setChange_id("anotherChangeId"); //$NON-NLS-1$
		changeInfo.getRevision().getCommit().setCommit("modifiedCommitId"); //$NON-NLS-1$
	}

	/**
	 * Test a more complete version of the checkout revision logic.
	 *
	 * @throws Exception
	 */
	@Test
	public void completeTestCheckout() throws Exception {
		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		checkoutRevision.run();
		assertEquals(gitAccess.getCurrentBranch(), "master"); //$NON-NLS-1$

		//Create a new branch and check it out. Add a second commit
		//This allows to test the patch set 2, but we need to remove the first branch and keep the second one only
		String secondBranch = "secondBranch"; //$NON-NLS-1$
		createAnotherBranch(secondBranch);
		amendLastCommit(false);
		loadReview();
		gitAccess.branchRemove("master");//Remove the old branch for the first test. This is necessary otherwise the UI opens up. //$NON-NLS-1$

		checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		checkoutRevision.run();
		assertEquals(gitAccess.getCurrentBranch(), secondBranch);
	}

	/**
	 * Test method for one branch with a PERFECT MATCH.
	 */
	@Test
	public void testPerfectMatch() {
		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		Map<String, BranchMatch> potentialBranches = checkoutRevision.findAllPotentialBranches(gitRepo.getRepository());
		assertEquals(1, potentialBranches.size());
		BranchMatch match = potentialBranches.get("master"); //$NON-NLS-1$
		assertNotNull(match);
		assertEquals(BranchMatch.PERFECT_MATCH, match);
	}

	/**
	 * Test for two perfect matches
	 */
	@Test
	public void testTwoPerfectMatches() {
		createAnotherBranch("secondBranch"); //$NON-NLS-1$
		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		Map<String, BranchMatch> potentialBranches = checkoutRevision.findAllPotentialBranches(gitRepo.getRepository());
		assertEquals(2, potentialBranches.size());
		{
			BranchMatch match = potentialBranches.get("master"); //$NON-NLS-1$
			assertNotNull(match);
			assertEquals(BranchMatch.PERFECT_MATCH, match);
		}

		{
			BranchMatch match = potentialBranches.get("secondBranch"); //$NON-NLS-1$
			assertNotNull(match);
			assertEquals(BranchMatch.PERFECT_MATCH, match);
		}
	}

	/**
	 * Test method for two branches with a two MATCH: PERFECT and CHANGE_ID_MATCH.
	 */
	@Test
	public void testOnePerfectMatchOneChangeId() throws Exception {
		createCommit("secondBranch", true); //$NON-NLS-1$

		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		Map<String, BranchMatch> potentialBranches = checkoutRevision.findAllPotentialBranches(gitRepo.getRepository());
		assertEquals(2, potentialBranches.size());
		{
			BranchMatch candidateBranch = potentialBranches.get("secondBranch"); //$NON-NLS-1$
			Assert.assertNotNull(candidateBranch);
			assertEquals(candidateBranch, BranchMatch.CHANGE_ID_MATCH);
		}
		{
			BranchMatch candidateBranch = potentialBranches.get("master"); //$NON-NLS-1$
			Assert.assertNotNull(candidateBranch);
			assertEquals(candidateBranch, BranchMatch.PERFECT_MATCH);
		}
	}

	/**
	 * Test match on branch name when there is only one branch in the repo
	 */
	@Test
	public void testMatchOnBranchName() throws Exception {
		createCommit(getDefaultCheckoutBranchName(), true);
		fudgeChangeInfo();

		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		Map<String, BranchMatch> potentialBranches = checkoutRevision.findAllPotentialBranches(gitRepo.getRepository());

		assertEquals(1, potentialBranches.size());
		BranchMatch candidateBranch = potentialBranches.get(getDefaultCheckoutBranchName());
		Assert.assertNotNull(candidateBranch);
		assertEquals(BranchMatch.BRANCH_NAME_MATCH, candidateBranch);
	}

	/**
	 * Test one perfect match and two matches on change id
	 */
	@Test
	public void testOnePerfectMatchTwoChangeIdMatches() throws Exception {
		createCommit("secondBranch", true); //$NON-NLS-1$
		createCommit("thirdBranch", true); //$NON-NLS-1$

		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		Map<String, BranchMatch> potentialBranches = checkoutRevision.findAllPotentialBranches(gitRepo.getRepository());

		assertEquals(3, potentialBranches.size());

		{
			BranchMatch candidateBranch = potentialBranches.get("master"); //$NON-NLS-1$
			Assert.assertNotNull(candidateBranch);
			assertEquals(BranchMatch.PERFECT_MATCH, candidateBranch);
		}

		{
			BranchMatch candidateBranch = potentialBranches.get("secondBranch"); //$NON-NLS-1$
			Assert.assertNotNull(candidateBranch);
			assertEquals(BranchMatch.CHANGE_ID_MATCH, candidateBranch);
		}
		{
			BranchMatch candidateBranch = potentialBranches.get("thirdBranch"); //$NON-NLS-1$
			Assert.assertNotNull(candidateBranch);
			assertEquals(BranchMatch.CHANGE_ID_MATCH, candidateBranch);
		}
	}

	/**
	 * Test match based on branch name. Starts from 2 branches.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMatchBasedOnBranchName() throws Exception {
		createCommit(getDefaultCheckoutBranchName(), true);
		fudgeChangeInfo();

		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		Map<String, BranchMatch> potentialBranches = checkoutRevision.findAllPotentialBranches(gitRepo.getRepository());

		assertEquals(1, potentialBranches.size());
		BranchMatch candidateBranch = potentialBranches.get(getDefaultCheckoutBranchName());
		Assert.assertNotNull(candidateBranch);
		assertEquals(BranchMatch.BRANCH_NAME_MATCH, candidateBranch);
	}

	/**
	 * Test match based on branch name. Starts from 2 branches. No matches expected reason: there is no Change-Id in the
	 * commit message
	 *
	 * @throws Exception
	 */
	@Test
	public void testNoMatchBasedOnBranchName() throws Exception {
		createCommit(getDefaultCheckoutBranchName(), false);
		fudgeChangeInfo();

		CheckoutRevision checkoutRevision = new CheckoutRevision(changeInfo.getRevision(), fGerrit);
		Map<String, BranchMatch> potentialBranches = checkoutRevision.findAllPotentialBranches(gitRepo.getRepository());

		// No matches are expected since the branch don't have the changeId in the commit message
		assertEquals(0, potentialBranches.size());
	}
}
