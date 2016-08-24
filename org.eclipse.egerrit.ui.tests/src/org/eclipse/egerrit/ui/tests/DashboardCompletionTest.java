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

import java.net.URI;
import java.util.HashMap;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritFactory;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.dashboard.ui.completion.SearchContentProposalProvider;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.junit.Before;
import org.junit.Test;

public class DashboardCompletionTest {
	private GerritClient fGerrit;

	@Before
	public void setUp() throws Exception {
		ChangeInfo ci = ModelFactory.eINSTANCE.createChangeInfo();
		EStructuralFeature l = ci.eClass().getEStructuralFeature("labels");
		HashMap<String, LabelInfo> labels = new HashMap<String, LabelInfo>();
		labels.put("a", ModelFactory.eINSTANCE.createLabelInfo());
		ci.eSet(ModelPackage.eINSTANCE.getChangeInfo_Labels(), labels);
		GerritServerInformation serverInfo = new GerritServerInformation(
				new URI(Common.SCHEME, null, Common.HOST, Common.PORT, Common.PATH, null, null).toASCIIString(),
				"Test server"); //$NON-NLS-1$
		serverInfo.setUserName(Common.USER);
		serverInfo.setPassword(Common.PASSWORD);
		GerritRepository fRepository = new GerritRepository(Common.SCHEME, Common.HOST, Common.PORT, Common.PATH);
		if (Common.PROXY_HOST != null) {
			fRepository.setProxy(new HttpHost(Common.PROXY_HOST, Common.PROXY_PORT));
		}
		fRepository.setCredentials(new GerritCredentials(Common.USER, Common.PASSWORD));
		fRepository.getCredentials().setHttpCredentials(Common.USER, Common.PASSWORD);
		fRepository.setServerInfo(serverInfo);
		fGerrit = GerritFactory.create(fRepository);
	}

	@Test
	public void testProjectCompletion() {
		SearchContentProposalProvider provider = new SearchContentProposalProvider(null);
		provider.setGerritClient(fGerrit);
		IContentProposal[] proposals = provider.getProposals("project:ege", 11); //$NON-NLS-1$
		assertEquals(2, proposals.length);
	}

	@Test
	public void testBuiltInCompletion() {
		SearchContentProposalProvider provider = new SearchContentProposalProvider(null);
		provider.setGerritClient(fGerrit);
		IContentProposal[] proposals = provider.getProposals("s", 1); //Here we test "s" without any specific reason
		assertEquals(10, proposals.length);
	}

	@Test
	public void testDefaultProposals() {
		SearchContentProposalProvider provider = new SearchContentProposalProvider(null);
		provider.setGerritClient(fGerrit);
		IContentProposal[] proposals = provider.getProposals("", 0);
		assertEquals(3, proposals.length);
	}

	@Test
	public void testAccountCompletion() {
		SearchContentProposalProvider provider = new SearchContentProposalProvider(null);
		provider.setGerritClient(fGerrit);
		IContentProposal[] proposals = provider.getProposals("owner:tes", 9);
		assertEquals(2, proposals.length);
	}

}
