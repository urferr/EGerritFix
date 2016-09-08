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

import org.eclipse.egerrit.internal.model.LabelInfo;
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
 * This is the item provider adapter for a {@link org.eclipse.egerrit.internal.model.LabelInfo} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class LabelInfoItemProvider extends ItemProviderAdapter
		implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, ITableItemLabelProvider, ITableItemColorProvider, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LabelInfoItemProvider(AdapterFactory adapterFactory) {
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

			addOptionalPropertyDescriptor(object);
			addBlockingPropertyDescriptor(object);
			addValuePropertyDescriptor(object);
			addDefault_valuePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Optional feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOptionalPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_LabelInfo_optional_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_LabelInfo_optional_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_LabelInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.LABEL_INFO__OPTIONAL, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Blocking feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBlockingPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_LabelInfo_blocking_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_LabelInfo_blocking_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_LabelInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.LABEL_INFO__BLOCKING, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Value feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_LabelInfo_value_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_LabelInfo_value_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_LabelInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.LABEL_INFO__VALUE, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Default value feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDefault_valuePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_LabelInfo_default_value_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_LabelInfo_default_value_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_LabelInfo_type"), //$NON-NLS-1$
						ModelPackage.Literals.LABEL_INFO__DEFAULT_VALUE, true, false, false,
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
			childrenFeatures.add(ModelPackage.Literals.LABEL_INFO__APPROVED);
			childrenFeatures.add(ModelPackage.Literals.LABEL_INFO__REJECTED);
			childrenFeatures.add(ModelPackage.Literals.LABEL_INFO__RECOMMENDED);
			childrenFeatures.add(ModelPackage.Literals.LABEL_INFO__DISLIKED);
			childrenFeatures.add(ModelPackage.Literals.LABEL_INFO__ALL);
			childrenFeatures.add(ModelPackage.Literals.LABEL_INFO__VALUES);
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
	 * This returns LabelInfo.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/LabelInfo")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		LabelInfo labelInfo = (LabelInfo) object;
		return getString("_UI_LabelInfo_type") + " " + labelInfo.isOptional(); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(LabelInfo.class)) {
		case ModelPackage.LABEL_INFO__OPTIONAL:
		case ModelPackage.LABEL_INFO__BLOCKING:
		case ModelPackage.LABEL_INFO__VALUE:
		case ModelPackage.LABEL_INFO__DEFAULT_VALUE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case ModelPackage.LABEL_INFO__APPROVED:
		case ModelPackage.LABEL_INFO__REJECTED:
		case ModelPackage.LABEL_INFO__RECOMMENDED:
		case ModelPackage.LABEL_INFO__DISLIKED:
		case ModelPackage.LABEL_INFO__ALL:
		case ModelPackage.LABEL_INFO__VALUES:
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

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__APPROVED,
				ModelFactory.eINSTANCE.createAccountInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__APPROVED,
				ModelFactory.eINSTANCE.createApprovalInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__REJECTED,
				ModelFactory.eINSTANCE.createAccountInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__REJECTED,
				ModelFactory.eINSTANCE.createApprovalInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__RECOMMENDED,
				ModelFactory.eINSTANCE.createAccountInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__RECOMMENDED,
				ModelFactory.eINSTANCE.createApprovalInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__DISLIKED,
				ModelFactory.eINSTANCE.createAccountInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__DISLIKED,
				ModelFactory.eINSTANCE.createApprovalInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__ALL,
				ModelFactory.eINSTANCE.createApprovalInfo()));

		newChildDescriptors.add(createChildParameter(ModelPackage.Literals.LABEL_INFO__VALUES,
				ModelFactory.eINSTANCE.create(ModelPackage.Literals.STRING_TO_STRING)));
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

		boolean qualify = childFeature == ModelPackage.Literals.LABEL_INFO__APPROVED
				|| childFeature == ModelPackage.Literals.LABEL_INFO__REJECTED
				|| childFeature == ModelPackage.Literals.LABEL_INFO__RECOMMENDED
				|| childFeature == ModelPackage.Literals.LABEL_INFO__DISLIKED
				|| childFeature == ModelPackage.Literals.LABEL_INFO__ALL;

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

}
