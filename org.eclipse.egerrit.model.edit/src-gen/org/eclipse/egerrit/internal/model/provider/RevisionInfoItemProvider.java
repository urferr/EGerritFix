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
			addIdPropertyDescriptor(object);
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
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RevisionInfo_id_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RevisionInfo_id_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_RevisionInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.REVISION_INFO__ID, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
		String label = ((RevisionInfo) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_RevisionInfo_type") //$NON-NLS-1$
				:
				getString("_UI_RevisionInfo_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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
		case ModelPackage.REVISION_INFO__ID:
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
