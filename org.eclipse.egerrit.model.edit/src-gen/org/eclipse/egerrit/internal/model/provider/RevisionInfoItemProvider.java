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

import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;

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
 * This is the item provider adapter for a {@link org.eclipse.egerrit.internal.model.RevisionInfo} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RevisionInfoItemProvider extends ItemProviderAdapter
		implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, ITableItemLabelProvider, ITableItemColorProvider, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionInfoItemProvider(AdapterFactory adapterFactory) {
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

			addDraftPropertyDescriptor(object);
			addHas_draft_commentsPropertyDescriptor(object);
			add_numberPropertyDescriptor(object);
			addRefPropertyDescriptor(object);
			addReviewedPropertyDescriptor(object);
			addCommentsLoadedPropertyDescriptor(object);
			addSubmitablePropertyDescriptor(object);
			addRebaseablePropertyDescriptor(object);
			addCherrypickablePropertyDescriptor(object);
			addDeleteablePropertyDescriptor(object);
			addPublishablePropertyDescriptor(object);
			addFilesLoadedPropertyDescriptor(object);
			addCommentedPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Draft feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDraftPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_draft_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_draft_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__DRAFT, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Has draft comments feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHas_draft_commentsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_has_draft_comments_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_has_draft_comments_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__HAS_DRAFT_COMMENTS, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
						getResourceLocator(), getString("_UI_RevisionInfo__number_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo__number_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__NUMBER, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Ref feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRefPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_ref_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_ref_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__REF, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
						getResourceLocator(), getString("_UI_RevisionInfo_reviewed_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_reviewed_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__REVIEWED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Comments Loaded feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCommentsLoadedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_commentsLoaded_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_commentsLoaded_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__COMMENTS_LOADED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Submitable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSubmitablePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_submitable_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_submitable_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__SUBMITABLE, false, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Rebaseable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRebaseablePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_rebaseable_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_rebaseable_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__REBASEABLE, false, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Cherrypickable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCherrypickablePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_cherrypickable_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_cherrypickable_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__CHERRYPICKABLE, false, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Deleteable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDeleteablePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_deleteable_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_deleteable_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__DELETEABLE, false, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Publishable feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPublishablePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_publishable_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_publishable_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__PUBLISHABLE, false, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Files Loaded feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFilesLoadedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_filesLoaded_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_filesLoaded_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__FILES_LOADED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Commented feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCommentedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_commented_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_commented_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__COMMENTED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
			childrenFeatures.add(ModelPackage.Literals.REVISION_INFO__FETCH);
			childrenFeatures.add(ModelPackage.Literals.REVISION_INFO__COMMIT);
			childrenFeatures.add(ModelPackage.Literals.REVISION_INFO__FILES);
			childrenFeatures.add(ModelPackage.Literals.REVISION_INFO__ACTIONS);
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
	 * This returns RevisionInfo.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/RevisionInfo")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		RevisionInfo revisionInfo = (RevisionInfo) object;
		return getString("_UI_RevisionInfo_type") + " " + revisionInfo.isDraft(); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(RevisionInfo.class)) {
		case ModelPackage.REVISION_INFO__DRAFT:
		case ModelPackage.REVISION_INFO__HAS_DRAFT_COMMENTS:
		case ModelPackage.REVISION_INFO__NUMBER:
		case ModelPackage.REVISION_INFO__REF:
		case ModelPackage.REVISION_INFO__REVIEWED:
		case ModelPackage.REVISION_INFO__COMMENTS_LOADED:
		case ModelPackage.REVISION_INFO__SUBMITABLE:
		case ModelPackage.REVISION_INFO__REBASEABLE:
		case ModelPackage.REVISION_INFO__CHERRYPICKABLE:
		case ModelPackage.REVISION_INFO__DELETEABLE:
		case ModelPackage.REVISION_INFO__PUBLISHABLE:
		case ModelPackage.REVISION_INFO__FILES_LOADED:
		case ModelPackage.REVISION_INFO__COMMENTED:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case ModelPackage.REVISION_INFO__FETCH:
		case ModelPackage.REVISION_INFO__COMMIT:
		case ModelPackage.REVISION_INFO__FILES:
		case ModelPackage.REVISION_INFO__ACTIONS:
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

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.REVISION_INFO__FETCH,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_FETCH_INFO)));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.REVISION_INFO__COMMIT,
				ModelFactory.eINSTANCE.createCommitInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.REVISION_INFO__FILES,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_FILE_INFO)));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.REVISION_INFO__ACTIONS,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_ACTION_INFO)));
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

}
