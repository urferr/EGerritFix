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
package org.eclipse.egerrit.internal.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.ModelPackage;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemColorProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.egerrit.internal.model.ChangeInfo} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ChangeInfoItemProvider extends ItemProviderAdapter
		implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, ITableItemLabelProvider, ITableItemColorProvider, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeInfoItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addKindPropertyDescriptor(object);
			addIdPropertyDescriptor(object);
			addProjectPropertyDescriptor(object);
			addBranchPropertyDescriptor(object);
			addTopicPropertyDescriptor(object);
			addChange_idPropertyDescriptor(object);
			addSubjectPropertyDescriptor(object);
			addStatusPropertyDescriptor(object);
			addCreatedPropertyDescriptor(object);
			addUpdatedPropertyDescriptor(object);
			addStarredPropertyDescriptor(object);
			addReviewedPropertyDescriptor(object);
			addMergeablePropertyDescriptor(object);
			addInsertionsPropertyDescriptor(object);
			addDeletionsPropertyDescriptor(object);
			add_sortkeyPropertyDescriptor(object);
			add_numberPropertyDescriptor(object);
			addCurrent_revisionPropertyDescriptor(object);
			add_more_changesPropertyDescriptor(object);
			addBase_changePropertyDescriptor(object);
			addHashtagsPropertyDescriptor(object);
			addSameTopicPropertyDescriptor(object);
			addConflictsWithPropertyDescriptor(object);
			addRevisionPropertyDescriptor(object);
			addLatestPatchSetPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Kind feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addKindPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_kind_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_kind_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__KIND, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_id_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_id_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__ID, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Project feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addProjectPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_project_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_project_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__PROJECT, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Branch feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBranchPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_branch_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_branch_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__BRANCH, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Topic feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTopicPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_topic_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_topic_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__TOPIC, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Change id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addChange_idPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_change_id_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_change_id_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__CHANGE_ID, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Subject feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSubjectPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_subject_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_subject_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__SUBJECT, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Status feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStatusPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_status_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_status_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__STATUS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Created feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCreatedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_created_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_created_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__CREATED, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Updated feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUpdatedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_updated_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_updated_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__UPDATED, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Starred feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStarredPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_starred_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_starred_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__STARRED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Reviewed feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReviewedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_reviewed_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_reviewed_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__REVIEWED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Mergeable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMergeablePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_mergeable_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_mergeable_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__MERGEABLE, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Insertions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInsertionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_insertions_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_insertions_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__INSERTIONS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Deletions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDeletionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_deletions_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_deletions_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__DELETIONS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the sortkey feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void add_sortkeyPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo__sortkey_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo__sortkey_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__SORTKEY, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the number feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void add_numberPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo__number_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo__number_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__NUMBER, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Current revision feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCurrent_revisionPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_current_revision_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_current_revision_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__CURRENT_REVISION, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the more changes feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void add_more_changesPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo__more_changes_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo__more_changes_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__MORE_CHANGES, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Base change feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBase_changePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_base_change_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_base_change_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__BASE_CHANGE, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Hashtags feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHashtagsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_hashtags_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_hashtags_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__HASHTAGS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Same Topic feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSameTopicPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_sameTopic_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_sameTopic_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__SAME_TOPIC, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Conflicts With feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addConflictsWithPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_conflictsWith_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_conflictsWith_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__CONFLICTS_WITH, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Revision feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRevisionPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_revision_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_revision_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__REVISION, false, false, false, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Latest Patch Set feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLatestPatchSetPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ChangeInfo_latestPatchSet_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ChangeInfo_latestPatchSet_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ChangeInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.CHANGE_INFO__LATEST_PATCH_SET, true, false, true, null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__OWNER);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__ACTIONS);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__LABELS);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__PERMITTED_LABELS);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__REMOVABLE_REVIEWERS);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__MESSAGES);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__REVISIONS);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__PROBLEMS);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__INCLUDED_IN);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__RELATED_CHANGES);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__REVIEWERS);
			childrenFeatures.add(ModelPackage.Literals.CHANGE_INFO__MERGEABLE_INFO);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ChangeInfo.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ChangeInfo")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ChangeInfo) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_ChangeInfo_type") : //$NON-NLS-1$
				getString("_UI_ChangeInfo_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ChangeInfo.class)) {
		case ModelPackage.CHANGE_INFO__KIND:
		case ModelPackage.CHANGE_INFO__ID:
		case ModelPackage.CHANGE_INFO__PROJECT:
		case ModelPackage.CHANGE_INFO__BRANCH:
		case ModelPackage.CHANGE_INFO__TOPIC:
		case ModelPackage.CHANGE_INFO__CHANGE_ID:
		case ModelPackage.CHANGE_INFO__SUBJECT:
		case ModelPackage.CHANGE_INFO__STATUS:
		case ModelPackage.CHANGE_INFO__CREATED:
		case ModelPackage.CHANGE_INFO__UPDATED:
		case ModelPackage.CHANGE_INFO__STARRED:
		case ModelPackage.CHANGE_INFO__REVIEWED:
		case ModelPackage.CHANGE_INFO__MERGEABLE:
		case ModelPackage.CHANGE_INFO__INSERTIONS:
		case ModelPackage.CHANGE_INFO__DELETIONS:
		case ModelPackage.CHANGE_INFO__SORTKEY:
		case ModelPackage.CHANGE_INFO__NUMBER:
		case ModelPackage.CHANGE_INFO__CURRENT_REVISION:
		case ModelPackage.CHANGE_INFO__MORE_CHANGES:
		case ModelPackage.CHANGE_INFO__BASE_CHANGE:
		case ModelPackage.CHANGE_INFO__HASHTAGS:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case ModelPackage.CHANGE_INFO__OWNER:
		case ModelPackage.CHANGE_INFO__ACTIONS:
		case ModelPackage.CHANGE_INFO__LABELS:
		case ModelPackage.CHANGE_INFO__PERMITTED_LABELS:
		case ModelPackage.CHANGE_INFO__REMOVABLE_REVIEWERS:
		case ModelPackage.CHANGE_INFO__MESSAGES:
		case ModelPackage.CHANGE_INFO__REVISIONS:
		case ModelPackage.CHANGE_INFO__PROBLEMS:
		case ModelPackage.CHANGE_INFO__INCLUDED_IN:
		case ModelPackage.CHANGE_INFO__RELATED_CHANGES:
		case ModelPackage.CHANGE_INFO__REVIEWERS:
		case ModelPackage.CHANGE_INFO__MERGEABLE_INFO:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__OWNER,
				ModelFactory.eINSTANCE.createAccountInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__ACTIONS,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_ACTION_INFO)));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__LABELS,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_LABEL_INFO)));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__PERMITTED_LABELS,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_LIST_OF_STRING)));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__REMOVABLE_REVIEWERS,
				ModelFactory.eINSTANCE.createAccountInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__MESSAGES,
				ModelFactory.eINSTANCE.createChangeMessageInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__REVISIONS,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_REVISION_INFO)));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__PROBLEMS,
				ModelFactory.eINSTANCE.createProblemInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__INCLUDED_IN,
				ModelFactory.eINSTANCE.createIncludedInInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__RELATED_CHANGES,
				ModelFactory.eINSTANCE.createRelatedChangesInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__REVIEWERS,
				ModelFactory.eINSTANCE.createReviewerInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.CHANGE_INFO__MERGEABLE_INFO,
				ModelFactory.eINSTANCE.createMergeableInfo()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == ModelPackage.Literals.CHANGE_INFO__OWNER
				|| childFeature == ModelPackage.Literals.CHANGE_INFO__REMOVABLE_REVIEWERS;

		if (qualify) {
			return getString("_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return EgerritEditPlugin.INSTANCE;
	}

	@Override
	public String getColumnText(Object object, int columnIndex) {
		System.out.println("blabla");
		// TODO Auto-generated method stub
		return super.getColumnText(object, columnIndex);
	}
}
