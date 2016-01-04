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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.egerrit.core.command.ChangeState;
import org.eclipse.egerrit.core.command.ChangeStatus;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.GerritRepo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GerritClientAuthenticationTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private final ChangeState IS_WATCHED = ChangeState.IS_WATCHED;

	private final ChangeState IS_STARRED = ChangeState.IS_STARRED;

	private final ChangeStatus OPEN = ChangeStatus.OPEN;

	// ------------------------------------------------------------------------
	// 0. Helpers
	// ------------------------------------------------------------------------

	private GerritRepository buildRepo(String scheme, String host, int port, String path, String phost, int pport,
			String user, String pwd, boolean acceptSSC) {
		HttpHost proxy = (phost != null) ? new HttpHost(phost, pport) : null;
		GerritCredentials creds = new GerritCredentials(user, pwd);
		GerritRepository repo = new GerritRepository(scheme, host, port, path).setProxy(proxy)
				.setCredentials(creds)
				.acceptSelfSignedCerts(acceptSSC);
		GerritServerInformation serverInfo;
		try {
			serverInfo = new GerritServerInformation(
					new URI(scheme, null, host, port, path, null, null).toASCIIString(), "Test server");
			serverInfo.setUserName(user);
			serverInfo.setPassword(pwd);
			repo.setServerInfo(serverInfo);
			assertNotNull(repo);
			if (!repo.connect()) {
				fail();
			}
		} catch (URISyntaxException e) {
			fail(e.getMessage());
		}
		return repo;
	}

	private ChangeInfo[] getChanges(String testcase, GerritRepository repo) {

		try {
			GerritClient gerrit = GerritFactory.create(repo);
			QueryChangesCommand command = gerrit.queryChanges();
			command.setMaxNumberOfResults(10);
			String username = repo.getCredentials().getUsername();
			if (username != null) {
				command.addQuery("owner:" + username);
				command.addQuery(OPEN.getValue());
				command.addQuery(IS_WATCHED.getValue());
				command.addQuery(IS_STARRED.getValue());
			}
			ChangeInfo[] changes = command.call();
			if (changes != null) {
				System.out.println(testcase + "\t: Received " + changes.length + " changes");
				for (ChangeInfo change : changes) {
					System.out.println(change);
				}
			}
			return changes;
		} catch (EGerritException e) {
			System.err.println("-----");
			System.err.println(testcase + " failed with: " + e.getMessage());
			e.printStackTrace();
			fail();
		}
		return null;
	}

	// ------------------------------------------------------------------------
	// 1. Without Authentication
	// ------------------------------------------------------------------------

	/**
	 * No SSL, no proxy, no auth (local test vm)
	 */
	@Test
	public void testGerritRepository_NoSSL_NoProxy_NoAuth() {
		String CASE = "NoSSL_NoProxy_NoAuth";
		String PROTOCOL = "http";
		String HOST = "192.168.50.4";
		int PORT = 28294;
		String PATH = "/gerrit-2.9.4";
		String USER = null;
		String PASSWORD = null;
		String PROXY_HOST = null;
		int PROXY_PORT = 0;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

	/**
	 * SSL, no proxy, no auth (Ericsson Gerrit)
	 */
	@Test
	public void testGerritRepository_SSL_NoProxy_NoAuth() {
		String CASE = "SSL_NoProxy_NoAuth";
		String PROTOCOL = "https";
		String HOST = "gerrit.ericsson.se";
		int PORT = 0;
		String PATH = "";
		String USER = null;
		String PASSWORD = null;
		String PROXY_HOST = null;
		int PROXY_PORT = 0;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

	/**
	 * SSL, proxy, no auth (Eclipse)
	 */
	@Test
	public void testGerritRepository_SSL_Proxy_NoAuth() {
		String CASE = "SSL_Proxy_NoAuth";
		String PROTOCOL = "https";
		String HOST = "git.eclipse.org";
		int PORT = 0;
		String PATH = "/r";
		String USER = null;
		String PASSWORD = null;
		String PROXY_HOST = "www-proxy.lmc.ericsson.se";
		int PROXY_PORT = 8080;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

	// ------------------------------------------------------------------------
	// 2. Authentication Schemes
	// ------------------------------------------------------------------------

	/**
	 * OpenID
	 */
//	@Test
	public void testGerritRepository_SSL_NoProxy_OpenID() {
		String CASE = "NoSSL_NoProxy_OpenID";
		String PROTOCOL = "http";
		String HOST = "192.168.50.4";
		int PORT = 28294;
		String PATH = "/gerrit-2.9.4";
		String USER = null;
		String PASSWORD = "";
		String PROXY_HOST = null;
		int PROXY_PORT = 0;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

	/**
	 * HTTP
	 */
//	@Test
	public void testGerritRepository_SSL_NoProxy_HTTP() {
		String CASE = "NoSSL_NoProxy_HTTP";
		String PROTOCOL = "http";
		String HOST = "192.168.50.4";
		int PORT = 28294;
		String PATH = "/gerrit-2.9.4";
		String USER = null;
		String PASSWORD = "";
		String PROXY_HOST = null;
		int PROXY_PORT = 0;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

//	/**
//	 * LDAP (Ericsson)
//     */
//	@Test
//	public void testGerritRepository_SSL_NoProxy_UserPass() {
//		String CASE 	   = "SSL_NoProxy_UserPass";
//		String PROTOCOL    = "https";
//		String HOST 	   = "gerrit.ericsson.se";
//		int    PORT 	   = 0;
//		String PATH 	   = "";
//		String USER 	   = TestCredentials.ERICSSON_USR;
//		String PASSWORD    = TestCredentials.ERICSSON_PWD;
//		String PROXY_HOST  = null;
//		int    PROXY_PORT  = 0;
//		boolean ACCEPT_SSC = false;
//
//		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD, ACCEPT_SSC);
//		ChangeInfo[] changes = getChanges(CASE, repo);
//		assertNotNull(changes);
//	}
//
//	/**
//	 * LDAP (Eclipse)
//     */
//	@Test
//	public void testGerritRepository_SSL_Proxy_UserPass() {
//		String CASE 	   = "SSL_Proxy_UserPass";
//		String PROTOCOL    = "https";
//		String HOST 	   = "git.eclipse.org";
//		int    PORT 	   = 0;
//		String PATH 	   = "/r";
//		String USER 	   = TestCredentials.ECLIPSE_USR;
//		String PASSWORD    = TestCredentials.ECLIPSE_PWD;
//		String PROXY_HOST  = "www-proxy.lmc.ericsson.se";
//		int    PROXY_PORT  = 8080;
//		boolean ACCEPT_SSC = false;
//
//		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD, ACCEPT_SSC);
//		ChangeInfo[] changes = getChanges(CASE, repo);
//		assertNotNull(changes);
//	}

	/**
	 * User Becomes Any Account 1 (user name)
	 */
	@Test
	public void testGerritRepository_NoSSL_NoProxy_Dev1() {
		String CASE = "NoSSL_NoProxy_Dev";
		String PROTOCOL = "http";
		String HOST = "192.168.50.4";
		int PORT = 28294;
		String PATH = "/gerrit-2.9.4";
		String USER = "test1";
		String PASSWORD = "egerritTest";
		String PROXY_HOST = null;
		int PROXY_PORT = 0;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

	/**
	 * User Becomes Any Account 2 (email address)
	 */
	@Test
	public void testGerritRepository_NoSSL_NoProxy_Dev2() {
		String CASE = "NoSSL_NoProxy_Dev2";
		String PROTOCOL = "http";
		String HOST = "192.168.50.4";
		int PORT = 28294;
		String PATH = "/gerrit-2.9.4";
		String USER = "test1@egerrit.eclipse.org";
		String PASSWORD = "";
		String PROXY_HOST = null;
		int PROXY_PORT = 0;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

	/**
	 * User Becomes Any Account 3 (account id)
	 */
	@Test
	public void testGerritRepository_NoSSL_NoProxy_Dev3() {
		String CASE = "NoSSL_NoProxy_Dev3";
		String PROTOCOL = "http";
		String HOST = "192.168.50.4";
		int PORT = 28294;
		String PATH = "/gerrit-2.9.4";
		String USER = "1000001";
		String PASSWORD = "";
		String PROXY_HOST = null;
		int PROXY_PORT = 0;
		boolean ACCEPT_SSC = false;

		GerritRepository repo = buildRepo(PROTOCOL, HOST, PORT, PATH, PROXY_HOST, PROXY_PORT, USER, PASSWORD,
				ACCEPT_SSC);
		ChangeInfo[] changes = getChanges(CASE, repo);
		assertNotNull(changes);
	}

}