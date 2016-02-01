package org.eclipse.egerrit.internal.model;

import org.eclipse.egerrit.internal.model.impl.FileInfoImpl;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class ModifiedFileInfoImpl extends FileInfoImpl {

	@Override
	public String getPath() {
		return ((StringToFileInfoImpl)this.eContainer()).getKey();
	}
	
	@Override
	public RevisionInfo getRevision() {
		return ((StringToRevisionInfoImpl) this.eContainer().eContainer().eContainer()).getValue();
	}
	
	@Override
	public EList<CommentInfo> getAllComments() {
		EList<CommentInfo> allComments = new BasicEList<>();
		allComments.addAll(getComments());
		allComments.addAll(getDraftComments());
		return allComments;
	}
}
