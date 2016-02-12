package org.eclipse.egerrit.internal.model;

import org.eclipse.egerrit.internal.model.impl.ModelFactoryImpl;

public class ModifiedEGerritFactory extends ModelFactoryImpl {
	@Override
	public ChangeInfo createChangeInfo() {
		return new ModifiedChangeInfoImpl();
	}
	
	@Override
	public CommitInfo createCommitInfo() {
		return new ModifiedCommitInfoImpl();
	}

	@Override
	public RevisionInfo createRevisionInfo() {
		return new ModifiedRevisionInfoImpl();
	}

	@Override
	public FileInfo createFileInfo() {
		return new ModifiedFileInfoImpl();
	}
}
