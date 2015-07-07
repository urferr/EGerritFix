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

package org.eclipse.egerrit.core.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BufferedHttpEntity;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.exception.EGerritException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

/**
 * The Gerrit command base class.
 *
 * @param <T>
 *            the return type which is expected from {@link #call()}
 * @since 1.0
 */
public abstract class GerritCommand<T> implements Callable<T> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/** The Gerrit repository this class is interacting with */
	private final GerritRepository fGerritRepository;

	/** The concrete command's return type */
	private final Type fResultType;

	/** Indicates that authentication is required */
	protected boolean fAuthIsRequired;

	public static String JSON_HEADER = "application/json";

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * Constructs a new {@link GerritCommand} object which can interact with the specified Gerrit repository. All
	 * command classes returned by methods of this class will always interact with this Gerrit repository.
	 *
	 * @param gerritRepository
	 *            The Gerrit repository this class is interacting with
	 * @param returnType
	 *            The concrete command's return type
	 */
	protected GerritCommand(GerritRepository gerritRepository, Type returnType) {
		fGerritRepository = gerritRepository;
		fResultType = returnType;
		fAuthIsRequired = false;
	}

	// ------------------------------------------------------------------------
	// Getters/Setters
	// ------------------------------------------------------------------------

	/**
	 * @return the gerrit repository this class is interacting with
	 */
	public GerritRepository getRepository() {
		return fGerritRepository;
	}

	/**
	 * @return the command return type
	 */
	public Type getReturnType() {
		return fResultType;
	}

	/**
	 * @return the command authentication state
	 */
	public boolean requiresAuthentication() {
		return fAuthIsRequired;
	}

	/**
	 * @return the command authentication state
	 */
	public void requiresAuthentication(boolean requiresAuthentication) {
		fAuthIsRequired = requiresAuthentication;
	}

	// ------------------------------------------------------------------------
	// To be implement by the extending class
	// ------------------------------------------------------------------------

	/**
	 * The command-specific URI
	 *
	 * @return the request URI
	 */
	public abstract HttpRequestBase formatRequest();

	// ------------------------------------------------------------------------
	// Callable
	// ------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public T call() throws EGerritException {
		T result = null;
		try {
			HttpRequestBase request = formatRequest();
			request.addHeader("Accept", JSON_HEADER); //$NON-NLS-1$
			EGerritCorePlugin.logInfo("Request: " + request.getURI().toString()); //$NON-NLS-1$

			ResponseHandler<T> rh = new ResponseHandler<T>() {
				@Override
				public T handleResponse(final HttpResponse response) throws IOException {

					StatusLine statusLine = response.getStatusLine();
					EGerritCorePlugin.logInfo("Result : " + statusLine.toString()); //$NON-NLS-1$
					if (statusLine.getStatusCode() >= 300) {
						throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
					}
					HttpEntity entity = response.getEntity();
					BufferedHttpEntity myEntity = new BufferedHttpEntity(entity);
					if (entity == null) {
						throw new ClientProtocolException("Response has no content"); //$NON-NLS-1$
					}
					if (!GerritCommand.this.expectsJson()) {
						try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
							myEntity.writeTo(os);
							return (T) os.toString();
						}
					}
					Gson gson = new GsonBuilder().create();
					InputStreamReader reader = new InputStreamReader(myEntity.getContent());

					T result1 = null;
					try {
						result1 = gson.fromJson(reader, fResultType);
					} catch (JsonSyntaxException e) {
//                	e.printStackTrace();
						reader = new InputStreamReader(myEntity.getContent());
						JsonReader jReader = new JsonReader(reader);
						jReader.setLenient(true);
						try {
							String buf = gson.fromJson(jReader, String.class);
//                        IncludedInInfo buf = gson.fromJson(jReader, IncludedInInfo.class);

							result1 = (T) buf;
							reader.close();
						} catch (MalformedJsonException e2) {
							EGerritCorePlugin.logError(e2.getMessage());
							result1 = (T) "Invalid format";
						}
					}
					return result1;
				}
			};
			result = postProcessResult(fGerritRepository.getHttpClient().execute(request, rh));

		} catch (ClientProtocolException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
		}

		return result;
	}

	protected boolean expectsJson() {
		return true;
	}

	protected T postProcessResult(T result) {
		return result;
	}
}
