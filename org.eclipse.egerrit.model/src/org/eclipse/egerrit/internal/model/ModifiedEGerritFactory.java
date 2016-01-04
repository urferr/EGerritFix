package org.eclipse.egerrit.internal.model;

import org.eclipse.egerrit.internal.model.impl.ModelFactoryImpl;

public class ModifiedEGerritFactory extends ModelFactoryImpl {
	@Override
	public ChangeInfo createChangeInfo() {
		ChangeInfo changeInfo = new ModifiedChangeInfoImpl();
		return changeInfo;
	}
	
	@Override
	public CommitInfo createCommitInfo() {
		CommitInfo commitInfo = new ModifiedCommitInfoImpl();
		return commitInfo;
	}
}
