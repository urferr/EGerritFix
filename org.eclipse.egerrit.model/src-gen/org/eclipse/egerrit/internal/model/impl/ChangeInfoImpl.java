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
import java.util.Collection;
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.eclipse.egerrit.internal.model.ApprovalInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.IncludedInInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.ProblemInfo;
import org.eclipse.egerrit.internal.model.RelatedChangesInfo;
import org.eclipse.egerrit.internal.model.ReviewerInfo;
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
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getProject <em>Project</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getBranch <em>Branch</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getTopic <em>Topic</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getChange_id <em>Change id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getCreated <em>Created</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getUpdated <em>Updated</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#isStarred <em>Starred</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#isReviewed <em>Reviewed</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#isMergeable <em>Mergeable</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getInsertions <em>Insertions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getDeletions <em>Deletions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#get_sortkey <em>sortkey</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#get_number <em>number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getLabels <em>Labels</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getPermitted_labels <em>Permitted labels</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getRemovable_reviewers <em>Removable reviewers</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getCurrent_revision <em>Current revision</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getRevisions <em>Revisions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#is_more_changes <em>more changes</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getProblems <em>Problems</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getBase_change <em>Base change</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getIncludedIn <em>Included In</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getHashtags <em>Hashtags</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getRelatedChanges <em>Related Changes</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getReviewers <em>Reviewers</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getSameTopic <em>Same Topic</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getConflictsWith <em>Conflicts With</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getMergeableInfo <em>Mergeable Info</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getRevision <em>Revision</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getLatestPatchSet <em>Latest Patch Set</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#getUserSelectedRevision <em>User Selected Revision</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#isRevertable <em>Revertable</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#isAbandonable <em>Abandonable</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#isRestoreable <em>Restoreable</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl#isDeleteable <em>Deleteable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChangeInfoImpl extends MinimalEObjectImpl.Container implements ChangeInfo {
	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final String KIND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected String kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected String project = PROJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBranch() <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranch()
	 * @generated
	 * @ordered
	 */
	protected static final String BRANCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBranch() <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranch()
	 * @generated
	 * @ordered
	 */
	protected String branch = BRANCH_EDEFAULT;

	/**
	 * The default value of the '{@link #getTopic() <em>Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopic()
	 * @generated
	 * @ordered
	 */
	protected static final String TOPIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTopic() <em>Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopic()
	 * @generated
	 * @ordered
	 */
	protected String topic = TOPIC_EDEFAULT;

	/**
	 * The default value of the '{@link #getChange_id() <em>Change id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange_id()
	 * @generated
	 * @ordered
	 */
	protected static final String CHANGE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getChange_id() <em>Change id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange_id()
	 * @generated
	 * @ordered
	 */
	protected String change_id = CHANGE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected String subject = SUBJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected String status = STATUS_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreated() <em>Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreated()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreated() <em>Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreated()
	 * @generated
	 * @ordered
	 */
	protected String created = CREATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdated() <em>Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdated()
	 * @generated
	 * @ordered
	 */
	protected static final String UPDATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdated() <em>Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdated()
	 * @generated
	 * @ordered
	 */
	protected String updated = UPDATED_EDEFAULT;

	/**
	 * The default value of the '{@link #isStarred() <em>Starred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStarred()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STARRED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStarred() <em>Starred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStarred()
	 * @generated
	 * @ordered
	 */
	protected boolean starred = STARRED_EDEFAULT;

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
	 * The default value of the '{@link #isMergeable() <em>Mergeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMergeable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MERGEABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMergeable() <em>Mergeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMergeable()
	 * @generated
	 * @ordered
	 */
	protected boolean mergeable = MERGEABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInsertions() <em>Insertions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertions()
	 * @generated
	 * @ordered
	 */
	protected static final int INSERTIONS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getInsertions() <em>Insertions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertions()
	 * @generated
	 * @ordered
	 */
	protected int insertions = INSERTIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeletions() <em>Deletions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletions()
	 * @generated
	 * @ordered
	 */
	protected static final int DELETIONS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDeletions() <em>Deletions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletions()
	 * @generated
	 * @ordered
	 */
	protected int deletions = DELETIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #get_sortkey() <em>sortkey</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_sortkey()
	 * @generated
	 * @ordered
	 */
	protected static final String _SORTKEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #get_sortkey() <em>sortkey</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_sortkey()
	 * @generated
	 * @ordered
	 */
	protected String _sortkey = _SORTKEY_EDEFAULT;

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
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected AccountInfo owner;

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
	 * The cached value of the '{@link #getLabels() <em>Labels</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabels()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, LabelInfo> labels;

	/**
	 * The cached value of the '{@link #getPermitted_labels() <em>Permitted labels</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPermitted_labels()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, EList<String>> permitted_labels;

	/**
	 * The cached value of the '{@link #getRemovable_reviewers() <em>Removable reviewers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemovable_reviewers()
	 * @generated
	 * @ordered
	 */
	protected EList<AccountInfo> removable_reviewers;

	/**
	 * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessages()
	 * @generated
	 * @ordered
	 */
	protected EList<ChangeMessageInfo> messages;

	/**
	 * The default value of the '{@link #getCurrent_revision() <em>Current revision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrent_revision()
	 * @generated
	 * @ordered
	 */
	protected static final String CURRENT_REVISION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCurrent_revision() <em>Current revision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrent_revision()
	 * @generated
	 * @ordered
	 */
	protected String current_revision = CURRENT_REVISION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRevisions() <em>Revisions</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevisions()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, RevisionInfo> revisions;

	/**
	 * The default value of the '{@link #is_more_changes() <em>more changes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #is_more_changes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean _MORE_CHANGES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #is_more_changes() <em>more changes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #is_more_changes()
	 * @generated
	 * @ordered
	 */
	protected boolean _more_changes = _MORE_CHANGES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProblems() <em>Problems</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProblems()
	 * @generated
	 * @ordered
	 */
	protected EList<ProblemInfo> problems;

	/**
	 * The default value of the '{@link #getBase_change() <em>Base change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_change()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_CHANGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBase_change() <em>Base change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_change()
	 * @generated
	 * @ordered
	 */
	protected String base_change = BASE_CHANGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIncludedIn() <em>Included In</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludedIn()
	 * @generated
	 * @ordered
	 */
	protected IncludedInInfo includedIn;

	/**
	 * The cached value of the '{@link #getHashtags() <em>Hashtags</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashtags()
	 * @generated
	 * @ordered
	 */
	protected EList<String> hashtags;

	/**
	 * The cached value of the '{@link #getRelatedChanges() <em>Related Changes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelatedChanges()
	 * @generated
	 * @ordered
	 */
	protected RelatedChangesInfo relatedChanges;

	/**
	 * The cached value of the '{@link #getReviewers() <em>Reviewers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewers()
	 * @generated
	 * @ordered
	 */
	protected EList<ReviewerInfo> reviewers;

	/**
	 * The cached value of the '{@link #getSameTopic() <em>Same Topic</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSameTopic()
	 * @generated
	 * @ordered
	 */
	protected EList<ChangeInfo> sameTopic;

	/**
	 * The cached value of the '{@link #getConflictsWith() <em>Conflicts With</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConflictsWith()
	 * @generated
	 * @ordered
	 */
	protected EList<ChangeInfo> conflictsWith;

	/**
	 * The cached value of the '{@link #getMergeableInfo() <em>Mergeable Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMergeableInfo()
	 * @generated
	 * @ordered
	 */
	protected MergeableInfo mergeableInfo;

	/**
	 * The cached value of the '{@link #getUserSelectedRevision() <em>User Selected Revision</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserSelectedRevision()
	 * @generated
	 * @ordered
	 */
	protected RevisionInfo userSelectedRevision;

	/**
	 * The default value of the '{@link #isRevertable() <em>Revertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRevertable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REVERTABLE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isAbandonable() <em>Abandonable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbandonable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABANDONABLE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isRestoreable() <em>Restoreable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRestoreable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RESTOREABLE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isDeleteable() <em>Deleteable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeleteable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DELETEABLE_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.CHANGE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKind(String newKind) {
		String oldKind = kind;
		kind = newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProject(String newProject) {
		String oldProject = project;
		project = newProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__PROJECT, oldProject,
					project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBranch() {
		return branch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBranch(String newBranch) {
		String oldBranch = branch;
		branch = newBranch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__BRANCH, oldBranch, branch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTopic() {
		return topic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTopic(String newTopic) {
		String oldTopic = topic;
		topic = newTopic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__TOPIC, oldTopic, topic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getChange_id() {
		return change_id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setChange_id(String newChange_id) {
		String oldChange_id = change_id;
		change_id = newChange_id;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__CHANGE_ID, oldChange_id,
					change_id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSubject() {
		return subject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubject(String newSubject) {
		String oldSubject = subject;
		subject = newSubject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__SUBJECT, oldSubject,
					subject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus(String newStatus) {
		String oldStatus = status;
		status = newStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__STATUS, oldStatus, status));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreated() {
		return created;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreated(String newCreated) {
		String oldCreated = created;
		created = newCreated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__CREATED, oldCreated,
					created));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUpdated() {
		return updated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUpdated(String newUpdated) {
		String oldUpdated = updated;
		updated = newUpdated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__UPDATED, oldUpdated,
					updated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStarred() {
		return starred;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStarred(boolean newStarred) {
		boolean oldStarred = starred;
		starred = newStarred;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__STARRED, oldStarred,
					starred));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__REVIEWED, oldReviewed,
					reviewed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMergeable() {
		return mergeable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMergeable(boolean newMergeable) {
		boolean oldMergeable = mergeable;
		mergeable = newMergeable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__MERGEABLE, oldMergeable,
					mergeable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getInsertions() {
		return insertions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInsertions(int newInsertions) {
		int oldInsertions = insertions;
		insertions = newInsertions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__INSERTIONS, oldInsertions,
					insertions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDeletions() {
		return deletions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeletions(int newDeletions) {
		int oldDeletions = deletions;
		deletions = newDeletions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__DELETIONS, oldDeletions,
					deletions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String get_sortkey() {
		return _sortkey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_sortkey(String new_sortkey) {
		String old_sortkey = _sortkey;
		_sortkey = new_sortkey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__SORTKEY, old_sortkey,
					_sortkey));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__NUMBER, old_number,
					_number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccountInfo getOwner() {
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(AccountInfo newOwner, NotificationChain msgs) {
		AccountInfo oldOwner = owner;
		owner = newOwner;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.CHANGE_INFO__OWNER, oldOwner, newOwner);
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
	public void setOwner(AccountInfo newOwner) {
		if (newOwner != owner) {
			NotificationChain msgs = null;
			if (owner != null)
				msgs = ((InternalEObject) owner).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__OWNER, null, msgs);
			if (newOwner != null)
				msgs = ((InternalEObject) newOwner).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__OWNER, null, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__OWNER, newOwner, newOwner));
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
					StringToActionInfoImpl.class, this, ModelPackage.CHANGE_INFO__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, LabelInfo> getLabels() {
		if (labels == null) {
			labels = new EcoreEMap<String, LabelInfo>(ModelPackage.Literals.STRING_TO_LABEL_INFO,
					StringToLabelInfoImpl.class, this, ModelPackage.CHANGE_INFO__LABELS);
		}
		return labels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, EList<String>> getPermitted_labels() {
		if (permitted_labels == null) {
			permitted_labels = new EcoreEMap<String, EList<String>>(ModelPackage.Literals.STRING_TO_LIST_OF_STRING,
					StringToListOfStringImpl.class, this, ModelPackage.CHANGE_INFO__PERMITTED_LABELS);
		}
		return permitted_labels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AccountInfo> getRemovable_reviewers() {
		if (removable_reviewers == null) {
			removable_reviewers = new EObjectContainmentEList<AccountInfo>(AccountInfo.class, this,
					ModelPackage.CHANGE_INFO__REMOVABLE_REVIEWERS);
		}
		return removable_reviewers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ChangeMessageInfo> getMessages() {
		if (messages == null) {
			messages = new EObjectContainmentEList<ChangeMessageInfo>(ChangeMessageInfo.class, this,
					ModelPackage.CHANGE_INFO__MESSAGES);
		}
		return messages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCurrent_revision() {
		return current_revision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCurrent_revision(String newCurrent_revision) {
		String oldCurrent_revision = current_revision;
		current_revision = newCurrent_revision;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__CURRENT_REVISION,
					oldCurrent_revision, current_revision));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, RevisionInfo> getRevisions() {
		if (revisions == null) {
			revisions = new EcoreEMap<String, RevisionInfo>(ModelPackage.Literals.STRING_TO_REVISION_INFO,
					StringToRevisionInfoImpl.class, this, ModelPackage.CHANGE_INFO__REVISIONS);
		}
		return revisions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean is_more_changes() {
		return _more_changes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_more_changes(boolean new_more_changes) {
		boolean old_more_changes = _more_changes;
		_more_changes = new_more_changes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__MORE_CHANGES,
					old_more_changes, _more_changes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ProblemInfo> getProblems() {
		if (problems == null) {
			problems = new EObjectContainmentEList<ProblemInfo>(ProblemInfo.class, this,
					ModelPackage.CHANGE_INFO__PROBLEMS);
		}
		return problems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBase_change() {
		return base_change;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBase_change(String newBase_change) {
		String oldBase_change = base_change;
		base_change = newBase_change;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__BASE_CHANGE, oldBase_change,
					base_change));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IncludedInInfo getIncludedIn() {
		return includedIn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIncludedIn(IncludedInInfo newIncludedIn, NotificationChain msgs) {
		IncludedInInfo oldIncludedIn = includedIn;
		includedIn = newIncludedIn;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.CHANGE_INFO__INCLUDED_IN, oldIncludedIn, newIncludedIn);
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
	public void setIncludedIn(IncludedInInfo newIncludedIn) {
		if (newIncludedIn != includedIn) {
			NotificationChain msgs = null;
			if (includedIn != null)
				msgs = ((InternalEObject) includedIn).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__INCLUDED_IN, null, msgs);
			if (newIncludedIn != null)
				msgs = ((InternalEObject) newIncludedIn).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__INCLUDED_IN, null, msgs);
			msgs = basicSetIncludedIn(newIncludedIn, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__INCLUDED_IN, newIncludedIn,
					newIncludedIn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getHashtags() {
		if (hashtags == null) {
			hashtags = new EDataTypeUniqueEList<String>(String.class, this, ModelPackage.CHANGE_INFO__HASHTAGS);
		}
		return hashtags;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RelatedChangesInfo getRelatedChanges() {
		return relatedChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelatedChanges(RelatedChangesInfo newRelatedChanges, NotificationChain msgs) {
		RelatedChangesInfo oldRelatedChanges = relatedChanges;
		relatedChanges = newRelatedChanges;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.CHANGE_INFO__RELATED_CHANGES, oldRelatedChanges, newRelatedChanges);
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
	public void setRelatedChanges(RelatedChangesInfo newRelatedChanges) {
		if (newRelatedChanges != relatedChanges) {
			NotificationChain msgs = null;
			if (relatedChanges != null)
				msgs = ((InternalEObject) relatedChanges).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__RELATED_CHANGES, null, msgs);
			if (newRelatedChanges != null)
				msgs = ((InternalEObject) newRelatedChanges).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__RELATED_CHANGES, null, msgs);
			msgs = basicSetRelatedChanges(newRelatedChanges, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__RELATED_CHANGES,
					newRelatedChanges, newRelatedChanges));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ReviewerInfo> getReviewers() {
		if (reviewers == null) {
			reviewers = new EObjectContainmentEList<ReviewerInfo>(ReviewerInfo.class, this,
					ModelPackage.CHANGE_INFO__REVIEWERS);
		}
		return reviewers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ChangeInfo> getSameTopic() {
		if (sameTopic == null) {
			sameTopic = new EObjectResolvingEList<ChangeInfo>(ChangeInfo.class, this,
					ModelPackage.CHANGE_INFO__SAME_TOPIC);
		}
		return sameTopic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ChangeInfo> getConflictsWith() {
		if (conflictsWith == null) {
			conflictsWith = new EObjectResolvingEList<ChangeInfo>(ChangeInfo.class, this,
					ModelPackage.CHANGE_INFO__CONFLICTS_WITH);
		}
		return conflictsWith;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MergeableInfo getMergeableInfo() {
		return mergeableInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMergeableInfo(MergeableInfo newMergeableInfo, NotificationChain msgs) {
		MergeableInfo oldMergeableInfo = mergeableInfo;
		mergeableInfo = newMergeableInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.CHANGE_INFO__MERGEABLE_INFO, oldMergeableInfo, newMergeableInfo);
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
	public void setMergeableInfo(MergeableInfo newMergeableInfo) {
		if (newMergeableInfo != mergeableInfo) {
			NotificationChain msgs = null;
			if (mergeableInfo != null)
				msgs = ((InternalEObject) mergeableInfo).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__MERGEABLE_INFO, null, msgs);
			if (newMergeableInfo != null)
				msgs = ((InternalEObject) newMergeableInfo).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_INFO__MERGEABLE_INFO, null, msgs);
			msgs = basicSetMergeableInfo(newMergeableInfo, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__MERGEABLE_INFO,
					newMergeableInfo, newMergeableInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RevisionInfo getRevision() {
		RevisionInfo revision = basicGetRevision();
		return revision != null && revision.eIsProxy() ? (RevisionInfo) eResolveProxy((InternalEObject) revision)
				: revision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionInfo basicGetRevision() {
		// TODO: implement this method to return the 'Revision' reference
		// -> do not perform proxy resolution
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RevisionInfo getLatestPatchSet() {
		RevisionInfo latestPatchSet = basicGetLatestPatchSet();
		return latestPatchSet != null && latestPatchSet.eIsProxy()
				? (RevisionInfo) eResolveProxy((InternalEObject) latestPatchSet) : latestPatchSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionInfo basicGetLatestPatchSet() {
		// TODO: implement this method to return the 'Latest Patch Set' reference
		// -> do not perform proxy resolution
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLatestPatchSet(RevisionInfo newLatestPatchSet) {
		// TODO: implement this method to set the 'Latest Patch Set' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetLatestPatchSet() {
		// TODO: implement this method to unset the 'Latest Patch Set' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetLatestPatchSet() {
		// TODO: implement this method to return whether the 'Latest Patch Set' reference is set
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RevisionInfo getUserSelectedRevision() {
		if (userSelectedRevision != null && userSelectedRevision.eIsProxy()) {
			InternalEObject oldUserSelectedRevision = (InternalEObject) userSelectedRevision;
			userSelectedRevision = (RevisionInfo) eResolveProxy(oldUserSelectedRevision);
			if (userSelectedRevision != oldUserSelectedRevision) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ModelPackage.CHANGE_INFO__USER_SELECTED_REVISION, oldUserSelectedRevision,
							userSelectedRevision));
			}
		}
		return userSelectedRevision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionInfo basicGetUserSelectedRevision() {
		return userSelectedRevision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUserSelectedRevision(RevisionInfo newUserSelectedRevision) {
		RevisionInfo oldUserSelectedRevision = userSelectedRevision;
		userSelectedRevision = newUserSelectedRevision;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_INFO__USER_SELECTED_REVISION,
					oldUserSelectedRevision, userSelectedRevision));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RevisionInfo getRevisionByNumber(int revisionId) {
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
	public ApprovalInfo getMostRelevantVote(String label) {
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
	public boolean isRevertable() {
		// TODO: implement this method to return the 'Revertable' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbandonable() {
		// TODO: implement this method to return the 'Abandonable' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRestoreable() {
		// TODO: implement this method to return the 'Restoreable' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeleteable() {
		// TODO: implement this method to return the 'Deleteable' attribute
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
		case ModelPackage.CHANGE_INFO__OWNER:
			return basicSetOwner(null, msgs);
		case ModelPackage.CHANGE_INFO__ACTIONS:
			return ((InternalEList<?>) getActions()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__LABELS:
			return ((InternalEList<?>) getLabels()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__PERMITTED_LABELS:
			return ((InternalEList<?>) getPermitted_labels()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__REMOVABLE_REVIEWERS:
			return ((InternalEList<?>) getRemovable_reviewers()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__MESSAGES:
			return ((InternalEList<?>) getMessages()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__REVISIONS:
			return ((InternalEList<?>) getRevisions()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__PROBLEMS:
			return ((InternalEList<?>) getProblems()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__INCLUDED_IN:
			return basicSetIncludedIn(null, msgs);
		case ModelPackage.CHANGE_INFO__RELATED_CHANGES:
			return basicSetRelatedChanges(null, msgs);
		case ModelPackage.CHANGE_INFO__REVIEWERS:
			return ((InternalEList<?>) getReviewers()).basicRemove(otherEnd, msgs);
		case ModelPackage.CHANGE_INFO__MERGEABLE_INFO:
			return basicSetMergeableInfo(null, msgs);
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
		case ModelPackage.CHANGE_INFO__KIND:
			return getKind();
		case ModelPackage.CHANGE_INFO__ID:
			return getId();
		case ModelPackage.CHANGE_INFO__PROJECT:
			return getProject();
		case ModelPackage.CHANGE_INFO__BRANCH:
			return getBranch();
		case ModelPackage.CHANGE_INFO__TOPIC:
			return getTopic();
		case ModelPackage.CHANGE_INFO__CHANGE_ID:
			return getChange_id();
		case ModelPackage.CHANGE_INFO__SUBJECT:
			return getSubject();
		case ModelPackage.CHANGE_INFO__STATUS:
			return getStatus();
		case ModelPackage.CHANGE_INFO__CREATED:
			return getCreated();
		case ModelPackage.CHANGE_INFO__UPDATED:
			return getUpdated();
		case ModelPackage.CHANGE_INFO__STARRED:
			return isStarred();
		case ModelPackage.CHANGE_INFO__REVIEWED:
			return isReviewed();
		case ModelPackage.CHANGE_INFO__MERGEABLE:
			return isMergeable();
		case ModelPackage.CHANGE_INFO__INSERTIONS:
			return getInsertions();
		case ModelPackage.CHANGE_INFO__DELETIONS:
			return getDeletions();
		case ModelPackage.CHANGE_INFO__SORTKEY:
			return get_sortkey();
		case ModelPackage.CHANGE_INFO__NUMBER:
			return get_number();
		case ModelPackage.CHANGE_INFO__OWNER:
			return getOwner();
		case ModelPackage.CHANGE_INFO__ACTIONS:
			if (coreType)
				return getActions();
			else
				return getActions().map();
		case ModelPackage.CHANGE_INFO__LABELS:
			if (coreType)
				return getLabels();
			else
				return getLabels().map();
		case ModelPackage.CHANGE_INFO__PERMITTED_LABELS:
			if (coreType)
				return getPermitted_labels();
			else
				return getPermitted_labels().map();
		case ModelPackage.CHANGE_INFO__REMOVABLE_REVIEWERS:
			return getRemovable_reviewers();
		case ModelPackage.CHANGE_INFO__MESSAGES:
			return getMessages();
		case ModelPackage.CHANGE_INFO__CURRENT_REVISION:
			return getCurrent_revision();
		case ModelPackage.CHANGE_INFO__REVISIONS:
			if (coreType)
				return getRevisions();
			else
				return getRevisions().map();
		case ModelPackage.CHANGE_INFO__MORE_CHANGES:
			return is_more_changes();
		case ModelPackage.CHANGE_INFO__PROBLEMS:
			return getProblems();
		case ModelPackage.CHANGE_INFO__BASE_CHANGE:
			return getBase_change();
		case ModelPackage.CHANGE_INFO__INCLUDED_IN:
			return getIncludedIn();
		case ModelPackage.CHANGE_INFO__HASHTAGS:
			return getHashtags();
		case ModelPackage.CHANGE_INFO__RELATED_CHANGES:
			return getRelatedChanges();
		case ModelPackage.CHANGE_INFO__REVIEWERS:
			return getReviewers();
		case ModelPackage.CHANGE_INFO__SAME_TOPIC:
			return getSameTopic();
		case ModelPackage.CHANGE_INFO__CONFLICTS_WITH:
			return getConflictsWith();
		case ModelPackage.CHANGE_INFO__MERGEABLE_INFO:
			return getMergeableInfo();
		case ModelPackage.CHANGE_INFO__REVISION:
			if (resolve)
				return getRevision();
			return basicGetRevision();
		case ModelPackage.CHANGE_INFO__LATEST_PATCH_SET:
			if (resolve)
				return getLatestPatchSet();
			return basicGetLatestPatchSet();
		case ModelPackage.CHANGE_INFO__USER_SELECTED_REVISION:
			if (resolve)
				return getUserSelectedRevision();
			return basicGetUserSelectedRevision();
		case ModelPackage.CHANGE_INFO__REVERTABLE:
			return isRevertable();
		case ModelPackage.CHANGE_INFO__ABANDONABLE:
			return isAbandonable();
		case ModelPackage.CHANGE_INFO__RESTOREABLE:
			return isRestoreable();
		case ModelPackage.CHANGE_INFO__DELETEABLE:
			return isDeleteable();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ModelPackage.CHANGE_INFO__KIND:
			setKind((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__ID:
			setId((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__PROJECT:
			setProject((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__BRANCH:
			setBranch((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__TOPIC:
			setTopic((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__CHANGE_ID:
			setChange_id((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__SUBJECT:
			setSubject((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__STATUS:
			setStatus((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__CREATED:
			setCreated((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__UPDATED:
			setUpdated((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__STARRED:
			setStarred((Boolean) newValue);
			return;
		case ModelPackage.CHANGE_INFO__REVIEWED:
			setReviewed((Boolean) newValue);
			return;
		case ModelPackage.CHANGE_INFO__MERGEABLE:
			setMergeable((Boolean) newValue);
			return;
		case ModelPackage.CHANGE_INFO__INSERTIONS:
			setInsertions((Integer) newValue);
			return;
		case ModelPackage.CHANGE_INFO__DELETIONS:
			setDeletions((Integer) newValue);
			return;
		case ModelPackage.CHANGE_INFO__SORTKEY:
			set_sortkey((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__NUMBER:
			set_number((Integer) newValue);
			return;
		case ModelPackage.CHANGE_INFO__OWNER:
			setOwner((AccountInfo) newValue);
			return;
		case ModelPackage.CHANGE_INFO__ACTIONS:
			((EStructuralFeature.Setting) getActions()).set(newValue);
			return;
		case ModelPackage.CHANGE_INFO__LABELS:
			((EStructuralFeature.Setting) getLabels()).set(newValue);
			return;
		case ModelPackage.CHANGE_INFO__PERMITTED_LABELS:
			((EStructuralFeature.Setting) getPermitted_labels()).set(newValue);
			return;
		case ModelPackage.CHANGE_INFO__REMOVABLE_REVIEWERS:
			getRemovable_reviewers().clear();
			getRemovable_reviewers().addAll((Collection<? extends AccountInfo>) newValue);
			return;
		case ModelPackage.CHANGE_INFO__MESSAGES:
			getMessages().clear();
			getMessages().addAll((Collection<? extends ChangeMessageInfo>) newValue);
			return;
		case ModelPackage.CHANGE_INFO__CURRENT_REVISION:
			setCurrent_revision((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__REVISIONS:
			((EStructuralFeature.Setting) getRevisions()).set(newValue);
			return;
		case ModelPackage.CHANGE_INFO__MORE_CHANGES:
			set_more_changes((Boolean) newValue);
			return;
		case ModelPackage.CHANGE_INFO__PROBLEMS:
			getProblems().clear();
			getProblems().addAll((Collection<? extends ProblemInfo>) newValue);
			return;
		case ModelPackage.CHANGE_INFO__BASE_CHANGE:
			setBase_change((String) newValue);
			return;
		case ModelPackage.CHANGE_INFO__INCLUDED_IN:
			setIncludedIn((IncludedInInfo) newValue);
			return;
		case ModelPackage.CHANGE_INFO__HASHTAGS:
			getHashtags().clear();
			getHashtags().addAll((Collection<? extends String>) newValue);
			return;
		case ModelPackage.CHANGE_INFO__RELATED_CHANGES:
			setRelatedChanges((RelatedChangesInfo) newValue);
			return;
		case ModelPackage.CHANGE_INFO__REVIEWERS:
			getReviewers().clear();
			getReviewers().addAll((Collection<? extends ReviewerInfo>) newValue);
			return;
		case ModelPackage.CHANGE_INFO__SAME_TOPIC:
			getSameTopic().clear();
			getSameTopic().addAll((Collection<? extends ChangeInfo>) newValue);
			return;
		case ModelPackage.CHANGE_INFO__CONFLICTS_WITH:
			getConflictsWith().clear();
			getConflictsWith().addAll((Collection<? extends ChangeInfo>) newValue);
			return;
		case ModelPackage.CHANGE_INFO__MERGEABLE_INFO:
			setMergeableInfo((MergeableInfo) newValue);
			return;
		case ModelPackage.CHANGE_INFO__LATEST_PATCH_SET:
			setLatestPatchSet((RevisionInfo) newValue);
			return;
		case ModelPackage.CHANGE_INFO__USER_SELECTED_REVISION:
			setUserSelectedRevision((RevisionInfo) newValue);
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
		case ModelPackage.CHANGE_INFO__KIND:
			setKind(KIND_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__ID:
			setId(ID_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__PROJECT:
			setProject(PROJECT_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__BRANCH:
			setBranch(BRANCH_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__TOPIC:
			setTopic(TOPIC_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__CHANGE_ID:
			setChange_id(CHANGE_ID_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__SUBJECT:
			setSubject(SUBJECT_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__STATUS:
			setStatus(STATUS_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__CREATED:
			setCreated(CREATED_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__UPDATED:
			setUpdated(UPDATED_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__STARRED:
			setStarred(STARRED_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__REVIEWED:
			setReviewed(REVIEWED_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__MERGEABLE:
			setMergeable(MERGEABLE_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__INSERTIONS:
			setInsertions(INSERTIONS_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__DELETIONS:
			setDeletions(DELETIONS_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__SORTKEY:
			set_sortkey(_SORTKEY_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__NUMBER:
			set_number(_NUMBER_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__OWNER:
			setOwner((AccountInfo) null);
			return;
		case ModelPackage.CHANGE_INFO__ACTIONS:
			getActions().clear();
			return;
		case ModelPackage.CHANGE_INFO__LABELS:
			getLabels().clear();
			return;
		case ModelPackage.CHANGE_INFO__PERMITTED_LABELS:
			getPermitted_labels().clear();
			return;
		case ModelPackage.CHANGE_INFO__REMOVABLE_REVIEWERS:
			getRemovable_reviewers().clear();
			return;
		case ModelPackage.CHANGE_INFO__MESSAGES:
			getMessages().clear();
			return;
		case ModelPackage.CHANGE_INFO__CURRENT_REVISION:
			setCurrent_revision(CURRENT_REVISION_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__REVISIONS:
			getRevisions().clear();
			return;
		case ModelPackage.CHANGE_INFO__MORE_CHANGES:
			set_more_changes(_MORE_CHANGES_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__PROBLEMS:
			getProblems().clear();
			return;
		case ModelPackage.CHANGE_INFO__BASE_CHANGE:
			setBase_change(BASE_CHANGE_EDEFAULT);
			return;
		case ModelPackage.CHANGE_INFO__INCLUDED_IN:
			setIncludedIn((IncludedInInfo) null);
			return;
		case ModelPackage.CHANGE_INFO__HASHTAGS:
			getHashtags().clear();
			return;
		case ModelPackage.CHANGE_INFO__RELATED_CHANGES:
			setRelatedChanges((RelatedChangesInfo) null);
			return;
		case ModelPackage.CHANGE_INFO__REVIEWERS:
			getReviewers().clear();
			return;
		case ModelPackage.CHANGE_INFO__SAME_TOPIC:
			getSameTopic().clear();
			return;
		case ModelPackage.CHANGE_INFO__CONFLICTS_WITH:
			getConflictsWith().clear();
			return;
		case ModelPackage.CHANGE_INFO__MERGEABLE_INFO:
			setMergeableInfo((MergeableInfo) null);
			return;
		case ModelPackage.CHANGE_INFO__LATEST_PATCH_SET:
			unsetLatestPatchSet();
			return;
		case ModelPackage.CHANGE_INFO__USER_SELECTED_REVISION:
			setUserSelectedRevision((RevisionInfo) null);
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
		case ModelPackage.CHANGE_INFO__KIND:
			return KIND_EDEFAULT == null ? kind != null : !KIND_EDEFAULT.equals(kind);
		case ModelPackage.CHANGE_INFO__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case ModelPackage.CHANGE_INFO__PROJECT:
			return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
		case ModelPackage.CHANGE_INFO__BRANCH:
			return BRANCH_EDEFAULT == null ? branch != null : !BRANCH_EDEFAULT.equals(branch);
		case ModelPackage.CHANGE_INFO__TOPIC:
			return TOPIC_EDEFAULT == null ? topic != null : !TOPIC_EDEFAULT.equals(topic);
		case ModelPackage.CHANGE_INFO__CHANGE_ID:
			return CHANGE_ID_EDEFAULT == null ? change_id != null : !CHANGE_ID_EDEFAULT.equals(change_id);
		case ModelPackage.CHANGE_INFO__SUBJECT:
			return SUBJECT_EDEFAULT == null ? subject != null : !SUBJECT_EDEFAULT.equals(subject);
		case ModelPackage.CHANGE_INFO__STATUS:
			return STATUS_EDEFAULT == null ? status != null : !STATUS_EDEFAULT.equals(status);
		case ModelPackage.CHANGE_INFO__CREATED:
			return CREATED_EDEFAULT == null ? created != null : !CREATED_EDEFAULT.equals(created);
		case ModelPackage.CHANGE_INFO__UPDATED:
			return UPDATED_EDEFAULT == null ? updated != null : !UPDATED_EDEFAULT.equals(updated);
		case ModelPackage.CHANGE_INFO__STARRED:
			return starred != STARRED_EDEFAULT;
		case ModelPackage.CHANGE_INFO__REVIEWED:
			return reviewed != REVIEWED_EDEFAULT;
		case ModelPackage.CHANGE_INFO__MERGEABLE:
			return mergeable != MERGEABLE_EDEFAULT;
		case ModelPackage.CHANGE_INFO__INSERTIONS:
			return insertions != INSERTIONS_EDEFAULT;
		case ModelPackage.CHANGE_INFO__DELETIONS:
			return deletions != DELETIONS_EDEFAULT;
		case ModelPackage.CHANGE_INFO__SORTKEY:
			return _SORTKEY_EDEFAULT == null ? _sortkey != null : !_SORTKEY_EDEFAULT.equals(_sortkey);
		case ModelPackage.CHANGE_INFO__NUMBER:
			return _number != _NUMBER_EDEFAULT;
		case ModelPackage.CHANGE_INFO__OWNER:
			return owner != null;
		case ModelPackage.CHANGE_INFO__ACTIONS:
			return actions != null && !actions.isEmpty();
		case ModelPackage.CHANGE_INFO__LABELS:
			return labels != null && !labels.isEmpty();
		case ModelPackage.CHANGE_INFO__PERMITTED_LABELS:
			return permitted_labels != null && !permitted_labels.isEmpty();
		case ModelPackage.CHANGE_INFO__REMOVABLE_REVIEWERS:
			return removable_reviewers != null && !removable_reviewers.isEmpty();
		case ModelPackage.CHANGE_INFO__MESSAGES:
			return messages != null && !messages.isEmpty();
		case ModelPackage.CHANGE_INFO__CURRENT_REVISION:
			return CURRENT_REVISION_EDEFAULT == null ? current_revision != null
					: !CURRENT_REVISION_EDEFAULT.equals(current_revision);
		case ModelPackage.CHANGE_INFO__REVISIONS:
			return revisions != null && !revisions.isEmpty();
		case ModelPackage.CHANGE_INFO__MORE_CHANGES:
			return _more_changes != _MORE_CHANGES_EDEFAULT;
		case ModelPackage.CHANGE_INFO__PROBLEMS:
			return problems != null && !problems.isEmpty();
		case ModelPackage.CHANGE_INFO__BASE_CHANGE:
			return BASE_CHANGE_EDEFAULT == null ? base_change != null : !BASE_CHANGE_EDEFAULT.equals(base_change);
		case ModelPackage.CHANGE_INFO__INCLUDED_IN:
			return includedIn != null;
		case ModelPackage.CHANGE_INFO__HASHTAGS:
			return hashtags != null && !hashtags.isEmpty();
		case ModelPackage.CHANGE_INFO__RELATED_CHANGES:
			return relatedChanges != null;
		case ModelPackage.CHANGE_INFO__REVIEWERS:
			return reviewers != null && !reviewers.isEmpty();
		case ModelPackage.CHANGE_INFO__SAME_TOPIC:
			return sameTopic != null && !sameTopic.isEmpty();
		case ModelPackage.CHANGE_INFO__CONFLICTS_WITH:
			return conflictsWith != null && !conflictsWith.isEmpty();
		case ModelPackage.CHANGE_INFO__MERGEABLE_INFO:
			return mergeableInfo != null;
		case ModelPackage.CHANGE_INFO__REVISION:
			return basicGetRevision() != null;
		case ModelPackage.CHANGE_INFO__LATEST_PATCH_SET:
			return isSetLatestPatchSet();
		case ModelPackage.CHANGE_INFO__USER_SELECTED_REVISION:
			return userSelectedRevision != null;
		case ModelPackage.CHANGE_INFO__REVERTABLE:
			return isRevertable() != REVERTABLE_EDEFAULT;
		case ModelPackage.CHANGE_INFO__ABANDONABLE:
			return isAbandonable() != ABANDONABLE_EDEFAULT;
		case ModelPackage.CHANGE_INFO__RESTOREABLE:
			return isRestoreable() != RESTOREABLE_EDEFAULT;
		case ModelPackage.CHANGE_INFO__DELETEABLE:
			return isDeleteable() != DELETEABLE_EDEFAULT;
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
		case ModelPackage.CHANGE_INFO___GET_REVISION_BY_NUMBER__INT:
			return getRevisionByNumber((Integer) arguments.get(0));
		case ModelPackage.CHANGE_INFO___IS_ACTION_ALLOWED__STRING:
			return isActionAllowed((String) arguments.get(0));
		case ModelPackage.CHANGE_INFO___GET_MOST_RELEVANT_VOTE__STRING:
			return getMostRelevantVote((String) arguments.get(0));
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
		result.append(" (kind: "); //$NON-NLS-1$
		result.append(kind);
		result.append(", id: "); //$NON-NLS-1$
		result.append(id);
		result.append(", project: "); //$NON-NLS-1$
		result.append(project);
		result.append(", branch: "); //$NON-NLS-1$
		result.append(branch);
		result.append(", topic: "); //$NON-NLS-1$
		result.append(topic);
		result.append(", change_id: "); //$NON-NLS-1$
		result.append(change_id);
		result.append(", subject: "); //$NON-NLS-1$
		result.append(subject);
		result.append(", status: "); //$NON-NLS-1$
		result.append(status);
		result.append(", created: "); //$NON-NLS-1$
		result.append(created);
		result.append(", updated: "); //$NON-NLS-1$
		result.append(updated);
		result.append(", starred: "); //$NON-NLS-1$
		result.append(starred);
		result.append(", reviewed: "); //$NON-NLS-1$
		result.append(reviewed);
		result.append(", mergeable: "); //$NON-NLS-1$
		result.append(mergeable);
		result.append(", insertions: "); //$NON-NLS-1$
		result.append(insertions);
		result.append(", deletions: "); //$NON-NLS-1$
		result.append(deletions);
		result.append(", _sortkey: "); //$NON-NLS-1$
		result.append(_sortkey);
		result.append(", _number: "); //$NON-NLS-1$
		result.append(_number);
		result.append(", current_revision: "); //$NON-NLS-1$
		result.append(current_revision);
		result.append(", _more_changes: "); //$NON-NLS-1$
		result.append(_more_changes);
		result.append(", base_change: "); //$NON-NLS-1$
		result.append(base_change);
		result.append(", hashtags: "); //$NON-NLS-1$
		result.append(hashtags);
		result.append(')');
		return result.toString();
	}

} //ChangeInfoImpl
