package org.eclipse.egerrit.internal.model;
/**
 *   Copyright (c) 2015 Ericsson AB
 *  
 *   All rights reserved. This program and the accompanying materials are
 *   made available under the terms of the Eclipse Public License v1.0 which
 *   accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *     Ericsson AB - Initial API and implementation
 */


import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;


public class ModifiedChangeInfoImpl extends ChangeInfoImpl {
	public ModifiedChangeInfoImpl() {
		eAdapters().add(new EContentAdapter() {
			@Override
			public void notifyChanged(Notification msg) {
				//Update the current revision field when revisions or the current revision id changes
				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__REVISIONS) || msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__CURRENT_REVISION)) {
					InternalEObject modifiedChangeInfo = getModifiedChangeInfo(msg.getNotifier());
					
					if (eNotificationRequired()) {
						eNotify(new ENotificationImpl(modifiedChangeInfo, Notification.SET, ModelPackage.Literals.CHANGE_INFO__REVISION, null,
								modifiedChangeInfo.eGet(ModelPackage.Literals.CHANGE_INFO__CURRENT_REVISION)));
					}
				}
				
				//Notify that the value of latest patch set could have changed
				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__REVISIONS)) {
					InternalEObject modifiedChangeInfo = getModifiedChangeInfo(msg.getNotifier());
					if (eNotificationRequired()) {
						eNotify(new ENotificationImpl(modifiedChangeInfo, Notification.SET, ModelPackage.Literals.CHANGE_INFO__LATEST_PATCH_SET, null,
								modifiedChangeInfo.eGet(ModelPackage.Literals.CHANGE_INFO__LATEST_PATCH_SET)));
					}
				}				
			}

			private InternalEObject getModifiedChangeInfo(Object object) {
				if (object instanceof ChangeInfo) {
					return (InternalEObject) object;
				}
				return getModifiedChangeInfo(((EObject) object).eContainer());
			}
			
		});
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

		ChangeInfo other = (ChangeInfo) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public RevisionInfo basicGetRevision() {
		return getRevisions().get(getCurrent_revision());
	}
	
	@Override
	public RevisionInfo basicGetLatestPatchSet() {
		Collection<RevisionInfo> revisions = getRevisions().values();
		RevisionInfo match = null;
		int topNumber = 0;
		for (RevisionInfo candidate : revisions) {
			if (candidate.get_number() > topNumber) {
				topNumber = candidate.get_number();
				match = candidate;
			}
		}
		return match;
	}
	
	@Override
	public RevisionInfo getRevisionByNumber(int revisionNumber) {
		Collection<RevisionInfo> revisions = getRevisions().values();
		for (RevisionInfo candidate : revisions) {
			if (candidate.get_number() == revisionNumber) {
				return candidate;
			}
		}
		return null;
	}

	@Override
	public boolean isActionAllowed(String action) {
		EMap<String, ActionInfo> actionsAvailable = getActions();

		if (actionsAvailable != null) {
			ActionInfo actionInfo = actionsAvailable.get(action);

			if (actionInfo != null) {
				return actionInfo.isEnabled();
			}
		}
		return false;
	}
}
