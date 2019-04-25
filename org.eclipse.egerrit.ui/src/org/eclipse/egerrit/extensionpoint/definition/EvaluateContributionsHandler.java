/*******************************************************************************
 * Copyright (c) 2019 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.extensionpoint.definition;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.egerrit.ui.extension.IExternalCmd;

/**
 * Evaluate the external contribution to EGerrit
 */
public class EvaluateContributionsHandler extends AbstractHandler {

	private static final String IEXTERNALCOMMAND_ID = "org.eclipse.egerrit.ui.extensionpoint.externalCmd"; //$NON-NLS-1$

//  AbstractHandler new method
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		execute(reg);
		return null;
	}

	public void execute(IExtensionRegistry registry) {

		IConfigurationElement[] config = registry.getConfigurationElementsFor(IEXTERNALCOMMAND_ID);
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof IExternalCmd) {
					executeExtension(o);
				}
			}
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void executeExtension(final Object o) {
		ISafeRunnable runnable = new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				System.out.println("Exception in client \n" + e.getMessage()); //$NON-NLS-1$
			}

			@Override
			public void run() throws Exception {
				System.out.println("run() in EvaluateContribution for: " + ((IExternalCmd) o).toString()); //$NON-NLS-1$
				((IExternalCmd) o).cmd1();
			}
		};
		SafeRunner.run(runnable);
	}

}
