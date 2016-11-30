/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.osgi.framework.Version;
import org.slf4j.LoggerFactory;

/**
 * A GerritRepository represents the interface to a gerrit repository.
 * <p>
 * In this implementation, the gerrit repository is accessible through HTTP(S) for the REST operations.
 *
 * @since 1.0
 */
public class GerritRepository {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(GerritRepository.class);

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	public final static Version NO_VERSION = new Version(0, 0, 0);

	/** The gerrit scheme */
	private final String fScheme;

	/** The gerrit host name */
	private final String fHostname;

	/** The gerrit port */
	private final int fPort;

	/** The host URL */
	private final String fPath;

	/** The gerrit host */
	private final HttpHost fHost;

	/** The user credentials for the repository */
	private GerritCredentials fCredentials = null;

	/** The proxy host (if applicable) */
	private boolean fAcceptSelfSignedCerts;

	/** The repository version */
	private Version fVersion = null;

	/** The HTTP client */
	private GerritHttpClient fHttpClient = null;

	/** The gerrit server information */
	private GerritServerInformation serverInfo = null;
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	private int fStatus;

	/**
	 * The constructor
	 *
	 * @param protocol
	 *            The transport protocol (http, https, ...)
	 * @param host
	 *            The host URL
	 * @param port
	 *            The gerrit port
	 * @param path
	 *            The gerrit path
	 */
	public GerritRepository(String scheme, String host, int port, String path) {
		fScheme = scheme;
		fHostname = host;
		fPort = port;
		if (fHostname != null && !fHostname.isEmpty()) {
			//Host name may not be blank
			fHost = new HttpHost(fHostname, fPort, fScheme);
		} else {
			fHost = null;
		}
		fPath = path;
	}

	/**
	 * The constructor
	 *
	 * @param urlString
	 *            The repository URL
	 */
	public GerritRepository(String urlString) throws EGerritException {
		try {
			URL url = new URL(urlString);
			fScheme = url.getProtocol();
			fHostname = url.getHost();
			fPort = url.getPort();
			fHost = new HttpHost(fHostname, fPort, fScheme);
			fPath = url.getPath();
		} catch (MalformedURLException e) {
			throw new EGerritException(e.getLocalizedMessage());
		}
	}

	// ------------------------------------------------------------------------
	// Setters
	// ------------------------------------------------------------------------

	public GerritRepository acceptSelfSignedCerts(boolean accept) {
		fAcceptSelfSignedCerts = accept;
		return this;
	}

	public GerritRepository setCredentials(GerritCredentials credentials) {
		fCredentials = credentials;
		fHttpClient = null;//need to reset the HttpClient when resetting the Credentials
		return this;
	}

	// ------------------------------------------------------------------------
	// Getters
	// ------------------------------------------------------------------------

	/**
	 * @return the repository version
	 */
	public Version getVersion() {
		if (fVersion == null) {
			fVersion = queryVersion();
		}
		return fVersion;
	}

	/**
	 * @return the transport protocol
	 */
	public String getScheme() {
		return fScheme;
	}

	/**
	 * @return the host URL
	 */
	public HttpHost getHost() {
		return fHost;
	}

	/**
	 * @return
	 */
	public String getPath() {
		return fPath;
	}

	/**
	 * @return the host name
	 */
	public String getHostname() {
		return fHost != null ? fHost.getHostName() : ""; //$NON-NLS-1$
	}

	/**
	 * @return the gerrit port
	 */
	public int getPort() {
		return fHost != null ? fHost.getPort() : -1;
	}

	/**
	 * @return the user ID
	 */
	public GerritCredentials getCredentials() {
		return fCredentials;
	}

	// ------------------------------------------------------------------------
	// Operations
	// ------------------------------------------------------------------------

	/**
	 * Validate the gerrit connection and retrieves the server version.
	 *
	 * @throws EGerritException
	 */
	public boolean connect() {
		fVersion = null;
		fHttpClient = getHttpClient();
		if (fHttpClient != null) {
			fVersion = queryVersion();
		}
		return fVersion != null;
	}

	/**
	 * @return the HTTP client for this repository
	 */
	public GerritHttpClient getHttpClient() {
		if (fHttpClient == null) {
			GerritHttpClient client = new GerritHttpClient(this, fCredentials);
			if (client.authenticate()) {
				fHttpClient = client;
			}
			fStatus = client.getStatus();
		}
		return fHttpClient;
	}

	/**
	 * Construct a base URI of the form "<prot>://<host>:<port>/<path>" which can then be augmented by the interested
	 * parties.
	 *
	 * @return the generic URI to access the repository for REST operations
	 */
	public URIBuilder getURIBuilder(boolean requiresAuthentication) {

		// Build the path
		StringBuilder sb = new StringBuilder(fPath);
		if (requiresAuthentication) {
			sb.append("/a"); //$NON-NLS-1$
		}
		String path = sb.toString();

		URIBuilder builder = new URIBuilder().setScheme(fScheme).setHost(fHostname).setPath(path);
		if (fPort > 0) {
			builder.setPort(fPort);
		}

		return builder;
	}

	/**
	 * Returns the server information
	 *
	 * @return the {@link GerritServerInformation} associated with this repository
	 */
	public GerritServerInformation getServerInfo() {
		return serverInfo;
	}

	/**
	 * Sets the server information
	 */
	public void setServerInfo(GerritServerInformation serverInfo) {
		this.serverInfo = serverInfo;
	}

	// ------------------------------------------------------------------------
	// Version discovery
	// ------------------------------------------------------------------------

	private static final String VERSION_REQUEST = "/config/server/version"; //$NON-NLS-1$

	private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n"; //$NON-NLS-1$

	public static final int SSL_PROBLEM = -1;

	public static final int SSL_INVALID_ROOT_CERTIFICATE = -2;

	public Version queryVersion() {
		Version version = null;
		try {
			URIBuilder builder = getURIBuilder(false);
			String path = new StringBuilder(builder.getPath()).append(VERSION_REQUEST).toString();
			URI uri = builder.setPath(path).build();
			HttpUriRequest request = new HttpGet(uri);
			logger.debug("Request: " + uri.toString()); //$NON-NLS-1$

			ResponseHandler<String> rh = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					StatusLine statusLine = response.getStatusLine();
					logger.debug("Result : " + statusLine.toString()); //$NON-NLS-1$
					int status = statusLine.getStatusCode();
					if (status < 200 || status >= 300) {
						throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
					}
					HttpEntity entity = response.getEntity();
					if (entity == null) {
						throw new ClientProtocolException("Response has no content"); //$NON-NLS-1$
					}
					// The response has the general form:
					// ")]}'\n\"<version>\"\n"
					// To extract raw server version, we have to abstract the
					// JSON
					// prefix, the version enclosing quotes and the final line
					// feed.
					int prefixLength = JSON_NON_EXECUTABLE_PREFIX.length();
					String rawResult = EntityUtils.toString(entity);
					logger.debug("Server version information is: '" + rawResult + "'"); //$NON-NLS-1$ //$NON-NLS-2$
					return rawResult.substring(prefixLength + 1, rawResult.length() - 2);
				}
			};

			GerritHttpClient gerritClient = getHttpClient();
			if (gerritClient != null) {
				String result = getHttpClient().execute(request, rh);
				version = parseVersion(result);
			}
		} catch (HttpResponseException httpException) {
			if (httpException.getStatusCode() == 404) {
				version = NO_VERSION;
			}
			if (httpException.getStatusCode() != 404) {
				EGerritCorePlugin.logError(httpException.getLocalizedMessage(), httpException);
			}
		} catch (SSLHandshakeException e) {
			fStatus = SSL_PROBLEM;
			if (e.getCause().getClass().getName().equals("sun.security.validator.ValidatorException")) { //$NON-NLS-1$
				fStatus = SSL_INVALID_ROOT_CERTIFICATE;
			}
			EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
		} catch (IOException | URISyntaxException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
		}

		return version;
	}

	private static final Pattern MAJOR_MINOR_MICRO_VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?"); //$NON-NLS-1$

	private static final Pattern MAJOR_MINOR_QUALIFIER_VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)-(\\w+).*"); //$NON-NLS-1$

	private static final Pattern MAJOR_MINOR_MICRO_QUALIFIER_VERSION_PATTERN = Pattern
			.compile("(\\d+)\\.(\\d+).(\\d+)-(\\w+).*"); //$NON-NLS-1$

	private Version parseVersion(String rawVersion) {

		// The raw version string has the general form:
		// "<major>.<minor>[[.<micro>]-<qualifier>]"

		Matcher matcher = MAJOR_MINOR_MICRO_VERSION_PATTERN.matcher(rawVersion);
		if (matcher.matches()) {
			return new Version(rawVersion);
		}

		matcher = MAJOR_MINOR_QUALIFIER_VERSION_PATTERN.matcher(rawVersion);
		if (matcher.matches()) {
			return new Version(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), 0,
					matcher.group(3));

		}

		matcher = MAJOR_MINOR_MICRO_QUALIFIER_VERSION_PATTERN.matcher(rawVersion);
		if (matcher.matches()) {
			return new Version(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)), matcher.group(4));

		}
		return Version.emptyVersion;
	}

	public GerritClient instantiateGerrit() {
		GerritClient gerrit = null;
		try {
			gerrit = GerritFactory.create(this);
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(formatGerritVersion() + e.getLocalizedMessage(), e);
		}
		return gerrit;
	}

	/**
	 * Format a string with the Gerrit version if available
	 *
	 * @return String
	 */
	public String formatGerritVersion() {
		String gerritVersion;
		StringBuilder sb = new StringBuilder();
		sb.append("Gerrit server: " + (serverInfo != null ? serverInfo.getServerURI() : "Unknown")); //$NON-NLS-1$ //$NON-NLS-2$
		sb.append("\nVersion: "); //$NON-NLS-1$
		gerritVersion = fVersion != null ? fVersion.toString() : "Unknown"; //$NON-NLS-1$
		sb.append(gerritVersion);
		sb.append("\n"); //$NON-NLS-1$
		return sb.toString();
	}

	/*
	 * return the error code of the http connection
	
	 * @return
	 */
	public int getStatus() {
		return fStatus;
	}
}
