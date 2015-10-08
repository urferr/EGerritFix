/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors.model;

import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.ui.editors.ChangeDetailEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * Editor input feeding a {@link ChangeDetailEditor}
 *
 * @since 1.0
 */
public class ChangeDetailEditorInput implements IEditorInput {

	private GerritClient client;

	private ChangeInfo change;

	public ChangeDetailEditorInput(GerritClient server, ChangeInfo change) {
		this.client = server;
		this.change = change;
	}

	/**
	 * Return the client used as part of the input
	 *
	 * @return
	 */
	public GerritClient getClient() {
		return client;
	}

	/**
	 * Return the changeInfo object used as part of the input
	 *
	 * @return
	 */
	public ChangeInfo getChange() {
		return change;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// ignore
		return null;
	}

	@Override
	public String getName() {
		return change.get_number() + " - " + change.getSubject(); //$NON-NLS-1$
	}

	@Override
	public IPersistableElement getPersistable() {
		//We don't want the editors to be persisted
		return null;
	}

	@Override
	public String getToolTipText() {
		return change.get_number() + " - " + change.getSubject() + " - " + change.getChange_id(); //$NON-NLS-1$ //$NON-NLS-2$	//Here we keep the change_id because it is shown to the user
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((change == null) ? 0 : change.hashCode());
		result = prime * result + ((client.getRepository().getServerInfo() == null)
				? 0
				: client.getRepository().getServerInfo().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ChangeDetailEditorInput other = (ChangeDetailEditorInput) obj;
		if (change == null) {
			if (other.change != null) {
				return false;
			}
		} else if (!change.equals(other.change)) {
			return false;
		}
		if (client.getRepository().getServerInfo() == null) {
			if (other.client.getRepository().getServerInfo() != null) {
				return false;
			}
		} else if (!client.getRepository().getServerInfo().equals(other.client.getRepository().getServerInfo())) {
			return false;
		}
		return true;
	}

}
