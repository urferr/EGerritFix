/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public abstract class BaseCommand<T> {
	private static final String JSON_HEADER = "application/json"; //$NON-NLS-1$

	private static final Logger logger = LoggerFactory.getLogger(BaseCommand.class);

	private HttpRequestBase request;

	private GerritRepository server;

	private AuthentificationRequired fAuthIsRequired;

	/** The concrete command's return type */
	private final Type fResultType;

	private Object input;

	private HashMap<String, String> parameters = new HashMap<>(5);

	private String pathFormat;

	private List<NameValuePair> queryParameters = new ArrayList<>(5);

	protected String getPath() throws UnsupportedEncodingException {
		Set<Entry<String, String>> params = parameters.entrySet();
		String result = pathFormat;
		for (Entry<String, String> entry : params) {
			result = result.replace(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * Constructs a new {@link GerritCommand} object which can interact with the specified Gerrit repository. All
	 * command classes returned by methods of this class will always interact with this Gerrit repository.
	 *
	 * @param gerritRepository
	 *            The Gerrit repository this class is interacting with
	 * @param returnType
	 *            The concrete command's return type
	 */
	protected BaseCommand(GerritRepository gerritRepository, AuthentificationRequired authRequired,
			Class<? extends HttpRequestBase> operationType, Type returnType) {
		server = gerritRepository;
		fResultType = returnType;
		fAuthIsRequired = authRequired;
		instantiateRequest(operationType);
	}

	public T call() throws EGerritException {
		fillRequest();

		T result = null;
		try {

			logger.debug("Request: " + request.getURI().toString()); //$NON-NLS-1$

			ResponseHandler<T> rh = new ResponseHandler<T>() {
				@Override
				public T handleResponse(final HttpResponse response) throws IOException {

					StatusLine statusLine = response.getStatusLine();
					logger.debug("Result : " + statusLine.toString()); //$NON-NLS-1$
					if (statusLine.getStatusCode() >= 300) {
						if (errorsExpected()) {
							return null;
						}
						throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
					}
					if (statusLine.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
						//Nothing to handle since the buffer is OK but empty
						return null;
					}
					HttpEntity entity = response.getEntity();
					BufferedHttpEntity myEntity = new BufferedHttpEntity(entity);
					if (entity == null) {
						throw new ClientProtocolException("Response has no content"); //$NON-NLS-1$
					}
					if (!BaseCommand.this.expectsJson()) {
						try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
							myEntity.writeTo(os);
							return (T) os.toString();
						}
					}
					GsonBuilder builder = new GsonBuilder();
					builder.registerTypeAdapterFactory(new EMFTypeAdapterFactory());
					Gson gson = builder.create();
					InputStreamReader reader = new InputStreamReader(myEntity.getContent());

					return gson.fromJson(reader, fResultType);
				}
			};
			result = postProcessResult(server.getHttpClient().execute(request, rh));

		} catch (ClientProtocolException e) {
			if (!handleHttpException(e)) {
				EGerritCorePlugin.logError(server.formatGerritVersion() + "Transport error occurred " //$NON-NLS-1$
						+ request.getMethod() + ' ' + request.getURI().toASCIIString(), e);
				throw new EGerritException("An error occurred while contacting the server. Please see log for details"); //$NON-NLS-1$
			}
		} catch (JsonSyntaxException | IOException e) {
			EGerritCorePlugin.logError(server.formatGerritVersion() + "Transport error occurred " //$NON-NLS-1$
					+ request.getMethod() + ' ' + request.getURI().toASCIIString(), e);
			throw new EGerritException("An error occurred while contacting the server. Please see log for details"); //$NON-NLS-1$
		}
		return result;
	}

	private void instantiateRequest(Class<? extends HttpRequestBase> operationType) {
		try {
			request = operationType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.debug("Can't create ", e); //$NON-NLS-1$
			throw new IllegalStateException(e);
		}
	}

	private void fillRequest() throws EGerritException {
		setRequestURI();

		setInput();

		setHeaders();
	}

	private void setRequestURI() throws EGerritException {
		if (fAuthIsRequired.equals(AuthentificationRequired.YES) && server.getServerInfo().isAnonymous()) {
			throw new EGerritException(
					"This command requires an authenticated user but no user information is provided. Command name is " //$NON-NLS-1$
							+ this.getClass().getName());
		}
		boolean authenticatedURL = true;
		switch (fAuthIsRequired) {
		case YES:
			authenticatedURL = true;
			break;
		case NO:
			authenticatedURL = false;
			break;
		case DEPENDS:
			authenticatedURL = !server.getServerInfo().isAnonymous();
			break;
		}

		try {
			URIBuilder uriBuilder = new URIBuilder(server.getURIBuilder(authenticatedURL).toString() + getPath());
			if (!queryParameters.isEmpty()) {
				uriBuilder.addParameters(queryParameters);
			}
			request.setURI(uriBuilder.build());
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			throw new EGerritException(e);
		}
	}

	private void setInput() {
		if (input != null) {
			if (request instanceof HttpEntityEnclosingRequestBase) {
				StringEntity entity;
				try {
					entity = new StringEntity(new Gson().toJson(input));
					entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, BaseCommand.JSON_HEADER));
					((HttpEntityEnclosingRequestBase) request).setEntity(entity);
				} catch (UnsupportedEncodingException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				throw new IllegalArgumentException("Input should not be set if the request is not PUT or POST"); //$NON-NLS-1$
			}
		}
	}

	private void setHeaders() {
		Map<String, String> headers = getHeaders();
		if (headers == null) {
			return;
		}
		Set<Entry<String, String>> entries = headers.entrySet();
		for (Entry<String, String> entry : entries) {
			request.addHeader(entry.getKey(), entry.getValue());
		}
	}

	//?????
	protected boolean handleHttpException(ClientProtocolException e) throws EGerritException {
		return false;
	}

	//Allow a command to say that errors are expected (e.g. GetContentFromCommitCommand)
	protected boolean errorsExpected() {
		return false;
	}

	protected boolean expectsJson() {
		return true;
	}

	protected void setInput(Object input) {
		this.input = input;
	}

	protected T postProcessResult(T result) {
		return result;
	}

	protected Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", JSON_HEADER); //$NON-NLS-1$
		return headers;
	}

	protected void setSegment(String key, String value) {
		parameters.put(key, value);
	}

	protected void setSegmentToEncode(String key, String value) {
		try {
			parameters.put(key, URLEncoder.encode(value, "UTF-8")); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			logger.debug("Can't URL encode value: " + value); //$NON-NLS-1$
			throw new IllegalArgumentException(e);
		}
	}

	protected void addQueryParameter(String key, String value) {
		queryParameters.add(new BasicNameValuePair(key, value));
	}

	protected void setPathFormat(String pathPattern) {
		pathFormat = pathPattern;
	}

	protected GerritRepository getRepository() {
		return server;
	}
}
