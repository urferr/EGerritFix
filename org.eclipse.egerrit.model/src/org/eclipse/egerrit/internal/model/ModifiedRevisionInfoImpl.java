/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - Initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.internal.model;

import java.util.List;

import org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.util.EcoreEMap;

/**
 * @author Jacques Bouthillier
 */
public class ModifiedRevisionInfoImpl extends RevisionInfoImpl {

	@Override
	public boolean isActionAllowed(String action) {
		EMap<String, ActionInfo> actionsAvailable = getActions();

		if (actionsAvailable != null) {
			ActionInfo actionInfo = actionsAvailable.get(action);

			if (actionInfo != null) {
				if (action.equals(ActionConstants.REBASE.getName())) {
					/*
					 * Checking if the action is defined. If so, then just
					 * return true, not checking the value "isEnabled()",
					 * sometimes, it return false even if the action is defined.
					 * For example, rebase "ActionInfo" sometimes doesn't have
					 * the "enabled" attribute even though it's possible to
					 * rebase the revision. This is why we decided to return
					 * true when the action is defined, disregarding the status
					 * of the "enabled" attribute.
					 */
					return true;
				}
				return actionInfo.isEnabled();
			}
		}
		return false;
	}

	@Override
	public String getId() {
		return ((StringToRevisionInfoImpl) this.eContainer()).getKey();
	}

	@Override
	public ChangeInfo getChangeInfo() {
		return (ChangeInfo) this.eContainer().eContainer();
	}

	@Override
	public boolean isRebaseable() {
		return isActionAllowed(ActionConstants.REBASE.getName());
	}

	@Override
	public boolean isSubmitable() {
		return isActionAllowed(ActionConstants.SUBMIT.getName());
	}

	@Override
	public boolean isCherrypickable() {
		return isActionAllowed(ActionConstants.CHERRYPICK.getName());
	}

	@Override
	public boolean isDeleteable() {
		return isActionAllowed("/");
	}

	@Override
	public boolean isPublishable() {
		return isActionAllowed(ActionConstants.PUBLISH.getName());
	}

	@Override
	public EMap<String, FileInfo> getFiles() {
		synchronized (this) {
			if (files == null) {
				files = new EcoreEMap<String, FileInfo>(ModelPackage.Literals.STRING_TO_FILE_INFO,
						StringToFileInfoImpl.class, this, ModelPackage.REVISION_INFO__FILES);
			}
			return files;
		}
	}
	
	@Override
	public String getBaseCommit() {
		List<CommitInfo> parents = getCommit().getParents();
		if (parents == null || parents.isEmpty()) {
			return null;
		}
		return parents.get(0).getCommit();
	}
}
