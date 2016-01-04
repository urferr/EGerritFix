package org.eclipse.egerrit.internal.model;

import org.eclipse.egerrit.internal.model.impl.CommitInfoImpl;
import org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl;
import org.eclipse.emf.ecore.EObject;

public class ModifiedCommitInfoImpl extends CommitInfoImpl {
	@Override
	public String getCommit() {
		String commitId = super.getCommit();
		if (commitId != null)
			return commitId;
		StringToRevisionInfoImpl mapEntry = getIncludingMapEntry();
		if (mapEntry != null) {
			return mapEntry.getKey();
		}
		return null;
	}

	private StringToRevisionInfoImpl getIncludingMapEntry() {
		EObject firstContainer = eContainer();
		if (firstContainer == null) {
			return null;
		}
		EObject potentialMapContianer = firstContainer.eContainer();
		if (potentialMapContianer instanceof StringToRevisionInfoImpl) {
			return (StringToRevisionInfoImpl) potentialMapContianer;
		}
		return null;
	}
}
