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


import org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl;
import org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl;
import org.eclipse.emf.common.util.EMap;

/**
 * @author Jacques Bouthillier
 *
 */
public class ModifiedRevisionInfoImpl extends RevisionInfoImpl {
	
	@Override
	public boolean isActionAllowed(String action) {
		EMap<String, ActionInfo> actionsAvailable = getActions();

		if (actionsAvailable != null) {
			ActionInfo actionInfo = actionsAvailable.get(action);

			if (actionInfo != null) {
				//Checking if the action is defined. If so, then just return true, 
				//Not checking the value "isEnabled()", sometimes, 
				//it return false even if the action is defined
				//return actionInfo.isEnabled();
				return true;
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
}
