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
package org.eclipse.egerrit.internal.model.impl;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.FetchInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Revision Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#isDraft <em>Draft</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#isHas_draft_comments <em>Has draft comments</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#get_number <em>number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#getRef <em>Ref</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#getCommit <em>Commit</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#getFiles <em>Files</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#isReviewed <em>Reviewed</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl#isCommentsLoaded <em>Comments Loaded</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RevisionInfoImpl extends MinimalEObjectImpl.Container implements RevisionInfo {
	/**
	 * The default value of the '{@link #isDraft() <em>Draft</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDraft()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DRAFT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDraft() <em>Draft</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDraft()
	 * @generated
	 * @ordered
	 */
	protected boolean draft = DRAFT_EDEFAULT;

	/**
	 * The default value of the '{@link #isHas_draft_comments() <em>Has draft comments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHas_draft_comments()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_DRAFT_COMMENTS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHas_draft_comments() <em>Has draft comments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHas_draft_comments()
	 * @generated
	 * @ordered
	 */
	protected boolean has_draft_comments = HAS_DRAFT_COMMENTS_EDEFAULT;

	/**
	 * The default value of the '{@link #get_number() <em>number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_number()
	 * @generated
	 * @ordered
	 */
	protected static final int _NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #get_number() <em>number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_number()
	 * @generated
	 * @ordered
	 */
	protected int _number = _NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getRef() <em>Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRef()
	 * @generated
	 * @ordered
	 */
	protected static final String REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRef() <em>Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRef()
	 * @generated
	 * @ordered
	 */
	protected String ref = REF_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, FetchInfo> fetch;

	/**
	 * The cached value of the '{@link #getCommit() <em>Commit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommit()
	 * @generated
	 * @ordered
	 */
	protected CommitInfo commit;

	/**
	 * The cached value of the '{@link #getFiles() <em>Files</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiles()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, FileInfo> files;

	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, ActionInfo> actions;

	/**
	 * The default value of the '{@link #isReviewed() <em>Reviewed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REVIEWED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReviewed() <em>Reviewed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewed()
	 * @generated
	 * @ordered
	 */
	protected boolean reviewed = REVIEWED_EDEFAULT;

	/**
	 * The default value of the '{@link #isCommentsLoaded() <em>Comments Loaded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCommentsLoaded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMMENTS_LOADED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCommentsLoaded() <em>Comments Loaded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCommentsLoaded()
	 * @generated
	 * @ordered
	 */
	protected boolean commentsLoaded = COMMENTS_LOADED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RevisionInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.REVISION_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDraft() {
		return draft;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDraft(boolean newDraft) {
		boolean oldDraft = draft;
		draft = newDraft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVISION_INFO__DRAFT, oldDraft, draft));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHas_draft_comments() {
		return has_draft_comments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHas_draft_comments(boolean newHas_draft_comments) {
		boolean oldHas_draft_comments = has_draft_comments;
		has_draft_comments = newHas_draft_comments;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVISION_INFO__HAS_DRAFT_COMMENTS,
					oldHas_draft_comments, has_draft_comments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int get_number() {
		return _number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_number(int new_number) {
		int old_number = _number;
		_number = new_number;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVISION_INFO__NUMBER, old_number,
					_number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRef() {
		return ref;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRef(String newRef) {
		String oldRef = ref;
		ref = newRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVISION_INFO__REF, oldRef, ref));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, FetchInfo> getFetch() {
		if (fetch == null) {
			fetch = new EcoreEMap<String, FetchInfo>(ModelPackage.Literals.STRING_TO_FETCH_INFO,
					StringToFetchInfoImpl.class, this, ModelPackage.REVISION_INFO__FETCH);
		}
		return fetch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommitInfo getCommit() {
		return commit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommit(CommitInfo newCommit, NotificationChain msgs) {
		CommitInfo oldCommit = commit;
		commit = newCommit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.REVISION_INFO__COMMIT, oldCommit, newCommit);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommit(CommitInfo newCommit) {
		if (newCommit != commit) {
			NotificationChain msgs = null;
			if (commit != null)
				msgs = ((InternalEObject) commit).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.REVISION_INFO__COMMIT, null, msgs);
			if (newCommit != null)
				msgs = ((InternalEObject) newCommit).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.REVISION_INFO__COMMIT, null, msgs);
			msgs = basicSetCommit(newCommit, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVISION_INFO__COMMIT, newCommit,
					newCommit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, FileInfo> getFiles() {
		if (files == null) {
			files = new EcoreEMap<String, FileInfo>(ModelPackage.Literals.STRING_TO_FILE_INFO,
					StringToFileInfoImpl.class, this, ModelPackage.REVISION_INFO__FILES);
		}
		return files;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, ActionInfo> getActions() {
		if (actions == null) {
			actions = new EcoreEMap<String, ActionInfo>(ModelPackage.Literals.STRING_TO_ACTION_INFO,
					StringToActionInfoImpl.class, this, ModelPackage.REVISION_INFO__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReviewed() {
		return reviewed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReviewed(boolean newReviewed) {
		boolean oldReviewed = reviewed;
		reviewed = newReviewed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVISION_INFO__REVIEWED, oldReviewed,
					reviewed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCommentsLoaded() {
		return commentsLoaded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommentsLoaded(boolean newCommentsLoaded) {
		boolean oldCommentsLoaded = commentsLoaded;
		commentsLoaded = newCommentsLoaded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVISION_INFO__COMMENTS_LOADED,
					oldCommentsLoaded, commentsLoaded));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ChangeInfo getChangeInfo() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isActionAllowed(String action) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.REVISION_INFO__FETCH:
			return ((InternalEList<?>) getFetch()).basicRemove(otherEnd, msgs);
		case ModelPackage.REVISION_INFO__COMMIT:
			return basicSetCommit(null, msgs);
		case ModelPackage.REVISION_INFO__FILES:
			return ((InternalEList<?>) getFiles()).basicRemove(otherEnd, msgs);
		case ModelPackage.REVISION_INFO__ACTIONS:
			return ((InternalEList<?>) getActions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.REVISION_INFO__DRAFT:
			return isDraft();
		case ModelPackage.REVISION_INFO__HAS_DRAFT_COMMENTS:
			return isHas_draft_comments();
		case ModelPackage.REVISION_INFO__NUMBER:
			return get_number();
		case ModelPackage.REVISION_INFO__REF:
			return getRef();
		case ModelPackage.REVISION_INFO__FETCH:
			if (coreType)
				return getFetch();
			else
				return getFetch().map();
		case ModelPackage.REVISION_INFO__COMMIT:
			return getCommit();
		case ModelPackage.REVISION_INFO__FILES:
			if (coreType)
				return getFiles();
			else
				return getFiles().map();
		case ModelPackage.REVISION_INFO__ACTIONS:
			if (coreType)
				return getActions();
			else
				return getActions().map();
		case ModelPackage.REVISION_INFO__REVIEWED:
			return isReviewed();
		case ModelPackage.REVISION_INFO__COMMENTS_LOADED:
			return isCommentsLoaded();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ModelPackage.REVISION_INFO__DRAFT:
			setDraft((Boolean) newValue);
			return;
		case ModelPackage.REVISION_INFO__HAS_DRAFT_COMMENTS:
			setHas_draft_comments((Boolean) newValue);
			return;
		case ModelPackage.REVISION_INFO__NUMBER:
			set_number((Integer) newValue);
			return;
		case ModelPackage.REVISION_INFO__REF:
			setRef((String) newValue);
			return;
		case ModelPackage.REVISION_INFO__FETCH:
			((EStructuralFeature.Setting) getFetch()).set(newValue);
			return;
		case ModelPackage.REVISION_INFO__COMMIT:
			setCommit((CommitInfo) newValue);
			return;
		case ModelPackage.REVISION_INFO__FILES:
			((EStructuralFeature.Setting) getFiles()).set(newValue);
			return;
		case ModelPackage.REVISION_INFO__ACTIONS:
			((EStructuralFeature.Setting) getActions()).set(newValue);
			return;
		case ModelPackage.REVISION_INFO__REVIEWED:
			setReviewed((Boolean) newValue);
			return;
		case ModelPackage.REVISION_INFO__COMMENTS_LOADED:
			setCommentsLoaded((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ModelPackage.REVISION_INFO__DRAFT:
			setDraft(DRAFT_EDEFAULT);
			return;
		case ModelPackage.REVISION_INFO__HAS_DRAFT_COMMENTS:
			setHas_draft_comments(HAS_DRAFT_COMMENTS_EDEFAULT);
			return;
		case ModelPackage.REVISION_INFO__NUMBER:
			set_number(_NUMBER_EDEFAULT);
			return;
		case ModelPackage.REVISION_INFO__REF:
			setRef(REF_EDEFAULT);
			return;
		case ModelPackage.REVISION_INFO__FETCH:
			getFetch().clear();
			return;
		case ModelPackage.REVISION_INFO__COMMIT:
			setCommit((CommitInfo) null);
			return;
		case ModelPackage.REVISION_INFO__FILES:
			getFiles().clear();
			return;
		case ModelPackage.REVISION_INFO__ACTIONS:
			getActions().clear();
			return;
		case ModelPackage.REVISION_INFO__REVIEWED:
			setReviewed(REVIEWED_EDEFAULT);
			return;
		case ModelPackage.REVISION_INFO__COMMENTS_LOADED:
			setCommentsLoaded(COMMENTS_LOADED_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ModelPackage.REVISION_INFO__DRAFT:
			return draft != DRAFT_EDEFAULT;
		case ModelPackage.REVISION_INFO__HAS_DRAFT_COMMENTS:
			return has_draft_comments != HAS_DRAFT_COMMENTS_EDEFAULT;
		case ModelPackage.REVISION_INFO__NUMBER:
			return _number != _NUMBER_EDEFAULT;
		case ModelPackage.REVISION_INFO__REF:
			return REF_EDEFAULT == null ? ref != null : !REF_EDEFAULT.equals(ref);
		case ModelPackage.REVISION_INFO__FETCH:
			return fetch != null && !fetch.isEmpty();
		case ModelPackage.REVISION_INFO__COMMIT:
			return commit != null;
		case ModelPackage.REVISION_INFO__FILES:
			return files != null && !files.isEmpty();
		case ModelPackage.REVISION_INFO__ACTIONS:
			return actions != null && !actions.isEmpty();
		case ModelPackage.REVISION_INFO__REVIEWED:
			return reviewed != REVIEWED_EDEFAULT;
		case ModelPackage.REVISION_INFO__COMMENTS_LOADED:
			return commentsLoaded != COMMENTS_LOADED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case ModelPackage.REVISION_INFO___IS_ACTION_ALLOWED__STRING:
			return isActionAllowed((String) arguments.get(0));
		case ModelPackage.REVISION_INFO___GET_ID:
			return getId();
		case ModelPackage.REVISION_INFO___GET_CHANGE_INFO:
			return getChangeInfo();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (draft: "); //$NON-NLS-1$
		result.append(draft);
		result.append(", has_draft_comments: "); //$NON-NLS-1$
		result.append(has_draft_comments);
		result.append(", _number: "); //$NON-NLS-1$
		result.append(_number);
		result.append(", ref: "); //$NON-NLS-1$
		result.append(ref);
		result.append(", reviewed: "); //$NON-NLS-1$
		result.append(reviewed);
		result.append(", commentsLoaded: "); //$NON-NLS-1$
		result.append(commentsLoaded);
		result.append(')');
		return result.toString();
	}

} //RevisionInfoImpl
