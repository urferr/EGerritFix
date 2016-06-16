/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper for the actual HttpClient. Adds handling of authentication and self-signed certificates.
 *
 * @since 1.0
 */
@SuppressWarnings("restriction")
public class GerritHttpClient {

	private static Logger logger = LoggerFactory.getLogger(GerritHttpClient.class);

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	// The target repository
	private GerritRepository fRepository;

	// The wrapped HTTP client
	private HttpClient fHttpClient = null;

	// The client's credentials
	private GerritCredentials fCredentials = null;

	private CredentialsProvider fCredentialsProvider = new BasicCredentialsProvider();

	// To handle authentication
	private CookieStore fCookieStore = new BasicCookieStore();

	// To handle self-signed cerificates
	private X509HostnameVerifier fHostNameVerifier = new AllowAllHostnameVerifier();

	private int fStatus;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	@SuppressWarnings("unused")
	private GerritHttpClient() {
	}

	/**
	 * @param repository
	 *            The gerrit repository
	 * @param creds
	 *            The user credentials
	 */
	public GerritHttpClient(GerritRepository repository, GerritCredentials creds) {
		fRepository = repository;
		try {
			// Basic builder
			HttpClientBuilder builder = HttpClients.custom()
					.setDefaultCookieStore(fCookieStore)
					.setHostnameVerifier(fHostNameVerifier)
					.setUserAgent(EGerritCorePlugin.getDefault().getUserAgent());
			// Handle proxy settings
			HttpHost proxy = fRepository.getProxy();
			if (proxy != null) {
				builder.setProxy(proxy);
			}

			// Handle self-signed certificates (SSC)
			SSLContextBuilder sslContext = new SSLContextBuilder().loadTrustMaterial(null,
					new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext.build());
			builder.setSSLSocketFactory(sslsf);

			// Handle user credentials
			if (creds != null && creds.getUsername() != null && !creds.getUsername().isEmpty()) {
				fCredentials = creds;
				AuthScope scope = new AuthScope(fRepository.getHost());
				fCredentialsProvider.setCredentials(scope, fCredentials.getGerritCredentials());
				builder.setDefaultCredentialsProvider(fCredentialsProvider);
			}
			// Build the client
			fHttpClient = builder.build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------
	// HttpClient mini-wrap
	// ------------------------------------------------------------------------

	public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
		return fHttpClient.execute(request);
	}

	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
			throws IOException, ClientProtocolException {
		if (gotKey) {
			request.addHeader(X_GERRIT_AUTHORITY_TAG, fKey);
		}
		return fHttpClient.execute(request, responseHandler);
	}

	// ------------------------------------------------------------------------
	// Helper stuff
	// ------------------------------------------------------------------------

	public CookieStore getCookieStore() {
		return fCookieStore;
	}

	// ------------------------------------------------------------------------
	// Authentication
	// ------------------------------------------------------------------------

	public boolean authenticate() {

		if (fCredentials == null) {
			return true;
		}

		// OpenId authentication
		if (fCredentials.isOpenIdAuthenticated()) {
			String openIdProvider = fCredentials.getOpenIdProvider();
			Credentials creds = fCredentials.getCredentials();
			return openIdAuthentication(openIdProvider, creds);
		}

		// HTTP authentication
		if (fCredentials.isHttpAuthenticated()) {
			Credentials creds = fCredentials.getHttpCredentials();
			return httpAuthentication(creds);
		}

		// Username + Password authentication
		Credentials creds = fCredentials.getCredentials();
		if (creds != null) {
			if (userPasswordAuthentication(creds, false)) {
				return true;
			}
			return userPasswordAuthentication(creds, true);
		}

		return false;
	}

	private static final String ACCOUNTS_SELF_REQUEST = "/accounts/self"; //$NON-NLS-1$

	private static final String ACCEPT_TAG = "Accept"; //$NON-NLS-1$

	private static final String JSON_TAG = "application/json"; //$NON-NLS-1$

	private static final String X_GERRIT_AUTHORITY_TAG = "X-Gerrit-Auth"; //$NON-NLS-1$

	private boolean testAuthentication() {
		int status = 0;
		try {
			URIBuilder builder = fRepository.getURIBuilder(false);
			String path = new StringBuilder(builder.getPath()).append(ACCOUNTS_SELF_REQUEST).toString();
			URI uri = builder.setPath(path).build();
			HttpGet request = new HttpGet(uri);
			request.addHeader(ACCEPT_TAG, JSON_TAG);
			if (gotKey) {
				request.addHeader(X_GERRIT_AUTHORITY_TAG, fKey);
			}
			logger.debug("Request: " + uri.toString()); //$NON-NLS-1$
			HttpResponse response = execute(request);
			StatusLine statusLine = response.getStatusLine();
			logger.debug("Result : " + statusLine.toString()); //$NON-NLS-1$
			status = statusLine.getStatusCode();
			EntityUtils.consume(response.getEntity());
		} catch (ClientProtocolException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		} catch (IOException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		}
		return status == HttpStatus.SC_OK;
	}

	// ------------------------------------------------------------------------
	// OpenId authentication
	// ------------------------------------------------------------------------

	// To be implemented
	private boolean openIdAuthentication(String provider, Credentials creds) {
		return false;
	}

	// ------------------------------------------------------------------------
	// HTTP authentication
	// ------------------------------------------------------------------------

	private boolean httpAuthentication(Credentials creds) {
		if (creds != null && creds.getUserPrincipal() != null) {
			return true;
		} else {
			return false;
		}
	}

	// ------------------------------------------------------------------------
	// Username + Password authentication
	// Access the "self" endpoint by providing the user credentials in the
	// request header. The client's credential provider (set in constructor)
	// is already properly configured.
	//
	// In the case of a "user becomes any account" the user name can be
	// either a userID, an email address or a numerical accountID.
	// ------------------------------------------------------------------------

	private static final String LOGIN_REQUEST = "/login/mine"; //$NON-NLS-1$

	private boolean userPasswordAuthentication(Credentials creds, boolean userBecomesAnyAccount) {
		fStatus = 0;
		try {
			URIBuilder builder = fRepository.getURIBuilder(false);
			String path = new StringBuilder(builder.getPath()).append(LOGIN_REQUEST).toString();
			URI uri = builder.setPath(path).build();
			HttpPost request = new HttpPost(uri);
			List<NameValuePair> authParams = getAuthParams(userBecomesAnyAccount);
			request.setEntity(new UrlEncodedFormEntity(authParams, Consts.UTF_8));
			logger.debug("Request: " + uri.toString()); //$NON-NLS-1$
			HttpResponse response = execute(request);
			StatusLine statusLine = response.getStatusLine();
			logger.debug("Result : " + statusLine.toString()); //$NON-NLS-1$
			fStatus = statusLine.getStatusCode();
			EntityUtils.consume(response.getEntity());
			if (fStatus == HttpStatus.SC_MOVED_TEMPORARILY) {
				extractGerritCookie();
				fStatus = getGerritAuthKey();
			}
			if (fStatus == HttpStatus.SC_OK) {
				return testAuthentication();
			}
		} catch (ClientProtocolException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		} catch (IOException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		}
		return fStatus == HttpStatus.SC_OK;
	}

	/*
	 * Format the authentication parameters depending on authentication type
	 *
	 * For normal (non-development) servers, set:
	 * - "username"
	 * - "password"
	 *
	 * For development servers, set one of the following:
	 * - "user_name"
	 * - "preferred_email"
	 * - "account_id"
	 *
	 * @param dev
	 * @return
	 */
	private List<NameValuePair> getAuthParams(boolean dev) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String userName = fCredentials.getUsername();
		if (dev) {
			String field = "user_name"; //$NON-NLS-1$
			if (userName.contains("@")) { //$NON-NLS-1$
				field = "preferred_email"; //$NON-NLS-1$
			} else {
				try {
					Integer.decode(userName);
					field = "account_id";//$NON-NLS-1$
				} catch (NumberFormatException e) {
					// Do nothing (and use "user_name")
				}
			}
			params.add(new BasicNameValuePair(field, userName));
		} else {
			params.add(new BasicNameValuePair("username", userName)); //$NON-NLS-1$
			params.add(new BasicNameValuePair("password", fCredentials.getPassword())); //$NON-NLS-1$
		}
		return params;
	}

	private static final String LOGIN_COOKIE_NAME = "GerritAccount"; //$NON-NLS-1$

	private void extractGerritCookie() {
		CookieStore cookies = getCookieStore();
		for (Cookie cookie : cookies.getCookies()) {
			if (LOGIN_COOKIE_NAME.equals(cookie.getName())) {
				setGerritAuthCookie(cookie);
			}
		}
	}

	private volatile Cookie fAuthCookie;

	private volatile String fCookiePath;

	private synchronized void setGerritAuthCookie(Cookie cookie) {
		Cookie oldCookie = fAuthCookie;
		fAuthCookie = cookie;
		if (fAuthCookie != null) {
			fCookiePath = cookie.getPath();
			if (!fAuthCookie.equals(oldCookie)) {
				getCookieStore().addCookie(fAuthCookie);
			}
		} else {
			gotKey = false;
		}
	}

	private int getGerritAuthKey() {
		int status = 0;
		try {
			URIBuilder builder = fRepository.getURIBuilder(false);
			URI uri = builder.setPath(fCookiePath).build();
			HttpGet request = new HttpGet(uri);
			logger.debug("Request: " + uri.toString()); //$NON-NLS-1$
			HttpResponse response = execute(request);
			StatusLine statusLine = response.getStatusLine();
			logger.debug("Result : " + statusLine.toString()); //$NON-NLS-1$
			status = statusLine.getStatusCode();
			if (status == HttpStatus.SC_OK) {
				extractGerritAuthKey(response);
			}
			EntityUtils.consume(response.getEntity());
		} catch (ClientProtocolException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		} catch (IOException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		}
		return status;
	}

	private final String GERRIT_KEY_TAG = "gerrit_hostpagedata.xGerritAuth="; //$NON-NLS-1$

	private volatile String fKey;

	private volatile boolean gotKey;

	private void extractGerritAuthKey(HttpResponse response) {
		gotKey = false;
		getAuthKeyForGerrit212(response);
		if (gotKey) {
			return;
		}
		getAuthKeyPreviousTo212(response);
	}

	private void getAuthKeyPreviousTo212(HttpResponse response) {
		try {
			String contents = EntityUtils.toString(response.getEntity());
			int loc = contents.indexOf(GERRIT_KEY_TAG);
			if (loc >= 0) {
				int beginIndex = loc + GERRIT_KEY_TAG.length() + 1;
				int endIndex = contents.indexOf('"', beginIndex);
				fKey = contents.substring(beginIndex, endIndex);
				gotKey = true;
			}
		} catch (IOException e) {
			logger.debug("Problem reading auth key ", e); //$NON-NLS-1$
		}
	}

	private void getAuthKeyForGerrit212(HttpResponse response) {
		gotKey = false;
		Header header = response.getFirstHeader("Set-Cookie"); //$NON-NLS-1$
		if (header != null) {
			HeaderElement[] elements = header.getElements();
			for (HeaderElement headerElement : elements) {
				if (headerElement.getName().equals("XSRF_TOKEN")) { //$NON-NLS-1$
					fKey = headerElement.getValue();
					gotKey = true;
				}
			}
		}
	}

	/*
	 * return the error code of the http connection

	 * @return
	 */
	public int getStatus() {
		return fStatus;
	}

}