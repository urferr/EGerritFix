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

package org.eclipse.egerrit.internal.core;

import org.apache.http.HttpHost;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

/**
 * The EGerritCorePlugin class controls the plug-in life cycle
 * <p>
 * It also provides the plug-in's logging facility
 *
 * @since 1.0
 */
public class EGerritCorePlugin extends Plugin {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.eclipse.egerrit.core"; //$NON-NLS-1$

	/** The shared instance */
	private static EGerritCorePlugin fPlugin;

	/**
	 * User agent string used to communicate with the gerrit server
	 */
	private String userAgent;

	private BundleContext ctx;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 */
	public EGerritCorePlugin() {
		setDefault(this);
	}

	// ------------------------------------------------------------------------
	// Accessors
	// ------------------------------------------------------------------------

	/**
	 * Returns the plug-in instance.
	 *
	 * @return the shared instance
	 */
	public static EGerritCorePlugin getDefault() {
		return fPlugin;
	}

	// Sets plug-in instance
	private static void setDefault(EGerritCorePlugin plugin) {
		fPlugin = plugin;
	}

	// ------------------------------------------------------------------------
	// Plugin
	// ------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setDefault(this);
		ctx = context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		setDefault(null);
		super.stop(context);
		ctx = null;
	}

	// ------------------------------------------------------------------------
	// Logging
	// ------------------------------------------------------------------------

	/**
	 * Log an IStatus object directly
	 *
	 * @param status
	 *            The status to log
	 */
	public static void log(IStatus status) {
		if (fPlugin != null) {
			fPlugin.getLog().log(status);
		} else {
			System.out.println(status);
		}
	}

	/**
	 * @param status
	 * @param message
	 * @param exception
	 */
	private static void log(int status, String message, Throwable exception) {
		String version = getBundleVersion();
		StringBuilder sb = new StringBuilder();
		sb.append("Plug-in Version: " + version + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		sb.append(message);
		log(new Status(status, PLUGIN_ID, sb.toString(), exception));
	}

	// LogInfo

	/**
	 * Logs a message with severity INFO in the runtime log of the plug-in.
	 *
	 * @param message
	 *            The message to log
	 */
	public static void logInfo(String message) {
		logInfo(message, null);
	}

	/**
	 * Logs a message and exception with severity INFO in the runtime log of the plug-in.
	 *
	 * @param message
	 *            The message to log
	 * @param exception
	 *            The corresponding exception
	 */
	public static void logInfo(String message, Throwable exception) {
		log(IStatus.INFO, message, exception);
	}

	// LogWarning

	/**
	 * Logs a message and exception with severity WARNING in the runtime log of the plug-in.
	 *
	 * @param message
	 *            The message to log
	 */
	public static void logWarning(String message) {
		logWarning(message, null);
	}

	/**
	 * Logs a message and exception with severity WARNING in the runtime log of the plug-in.
	 *
	 * @param message
	 *            The message to log
	 * @param exception
	 *            The corresponding exception
	 */
	public static void logWarning(String message, Throwable exception) {
		log(IStatus.WARNING, message, exception);
	}

	// LogError

	/**
	 * Logs a message and exception with severity ERROR in the runtime log of the plug-in.
	 *
	 * @param message
	 *            The message to log
	 */
	public static void logError(String message) {
		logError(message, null);
	}

	/**
	 * Logs a message and exception with severity ERROR in the runtime log of the plug-in.
	 *
	 * @param message
	 *            The message to log
	 * @param exception
	 *            The corresponding exception
	 */
	public static void logError(String message, Throwable exception) {
		log(IStatus.ERROR, message, exception);
	}

	/**
	 * Return the http user agent
	 */
	String getUserAgent() {
		if (userAgent == null) {
			String productId = System.getProperty("eclipse.product", "unknownProduct");
			String buildId = System.getProperty("eclipse.buildId", "unknownBuildId"); //$NON-NLS-1$ //$NON-NLS-2
			String pluginVersion = getBundle().getVersion().toString();
			userAgent = productId + '/' + buildId + ' ' + PLUGIN_ID + '/' + pluginVersion;
		}
		return userAgent;
	}

	/**
	 * Getting the bundle version is public to allow the JUNIT
	 *
	 * @return String
	 */
	public static String getBundleVersion() {
		//Testing for the eclipse runtime here
		String ret = "";
		final Bundle bdleEgerritUi = Platform.getBundle(PLUGIN_ID);
		if (bdleEgerritUi != null) {
			Version version = bdleEgerritUi.getVersion();
			ret = version.toString();
		}
		return ret;
	}

	public IProxyService getProxyService() {
		ServiceReference<IProxyService> sr = ctx.getServiceReference(IProxyService.class);
		try {
			return ctx.getService(sr);
		} finally {
			if (ctx != null) {
				ctx.ungetService(sr);
			}
		}
	}

	public HttpHost getProxyForHost(String hostname) {
		IProxyService proxyService = getProxyService();
		if (proxyService == null) {
			return null;
		}
		IProxyData[] potentialProxies = proxyService.getProxyDataForHost(hostname);
		if (potentialProxies != null && potentialProxies.length > 0) {
			IProxyData proxyData = potentialProxies[0];
			return new HttpHost(proxyData.getHost(), proxyData.getPort(), proxyData.getType());
		}
		return null;
	}
}
