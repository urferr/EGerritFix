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
package org.eclipse.egerrit.internal.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Action Constants</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getActionConstants()
 * @model
 * @generated
 */
public enum ActionConstants implements Enumerator {
	/**
	 * The '<em><b>Abandon</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABANDON_VALUE
	 * @generated
	 * @ordered
	 */
	ABANDON(0, "abandon", "Abandon"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Checkout</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHECKOUT_VALUE
	 * @generated
	 * @ordered
	 */
	CHECKOUT(1, "checkout", "Checkout"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Cherrypick</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHERRYPICK_VALUE
	 * @generated
	 * @ordered
	 */
	CHERRYPICK(2, "cherrypick", "Cherry-Pick"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Draft</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DRAFT_VALUE
	 * @generated
	 * @ordered
	 */
	DRAFT(3, "draft", "Draft ..."), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Followup</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FOLLOWUP_VALUE
	 * @generated
	 * @ordered
	 */
	FOLLOWUP(4, "followup", "Followup"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Publish</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PUBLISH_VALUE
	 * @generated
	 * @ordered
	 */
	PUBLISH(5, "publish", "Publish"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Rebase</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REBASE_VALUE
	 * @generated
	 * @ordered
	 */
	REBASE(6, "rebase", "Rebase"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Refresh</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REFRESH_VALUE
	 * @generated
	 * @ordered
	 */
	REFRESH(7, "refresh", "Refresh"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Reply</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLY_VALUE
	 * @generated
	 * @ordered
	 */
	REPLY(8, "reply", "Reply ..."), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Restore</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESTORE_VALUE
	 * @generated
	 * @ordered
	 */
	RESTORE(9, "restore", "Restore"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Revert</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REVERT_VALUE
	 * @generated
	 * @ordered
	 */
	REVERT(10, "revert", "Revert"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Submit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUBMIT_VALUE
	 * @generated
	 * @ordered
	 */
	SUBMIT(11, "submit", "Submit"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Topic</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TOPIC_VALUE
	 * @generated
	 * @ordered
	 */
	TOPIC(12, "topic", "Topic"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Abandon</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Abandon</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ABANDON
	 * @model name="abandon" literal="Abandon"
	 * @generated
	 * @ordered
	 */
	public static final int ABANDON_VALUE = 0;

	/**
	 * The '<em><b>Checkout</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Checkout</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHECKOUT
	 * @model name="checkout" literal="Checkout"
	 * @generated
	 * @ordered
	 */
	public static final int CHECKOUT_VALUE = 1;

	/**
	 * The '<em><b>Cherrypick</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cherrypick</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHERRYPICK
	 * @model name="cherrypick" literal="Cherry-Pick"
	 * @generated
	 * @ordered
	 */
	public static final int CHERRYPICK_VALUE = 2;

	/**
	 * The '<em><b>Draft</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Draft</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DRAFT
	 * @model name="draft" literal="Draft ..."
	 * @generated
	 * @ordered
	 */
	public static final int DRAFT_VALUE = 3;

	/**
	 * The '<em><b>Followup</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Followup</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FOLLOWUP
	 * @model name="followup" literal="Followup"
	 * @generated
	 * @ordered
	 */
	public static final int FOLLOWUP_VALUE = 4;

	/**
	 * The '<em><b>Publish</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Publish</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PUBLISH
	 * @model name="publish" literal="Publish"
	 * @generated
	 * @ordered
	 */
	public static final int PUBLISH_VALUE = 5;

	/**
	 * The '<em><b>Rebase</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rebase</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REBASE
	 * @model name="rebase" literal="Rebase"
	 * @generated
	 * @ordered
	 */
	public static final int REBASE_VALUE = 6;

	/**
	 * The '<em><b>Refresh</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Refresh</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REFRESH
	 * @model name="refresh" literal="Refresh"
	 * @generated
	 * @ordered
	 */
	public static final int REFRESH_VALUE = 7;

	/**
	 * The '<em><b>Reply</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Reply</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLY
	 * @model name="reply" literal="Reply ..."
	 * @generated
	 * @ordered
	 */
	public static final int REPLY_VALUE = 8;

	/**
	 * The '<em><b>Restore</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Restore</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RESTORE
	 * @model name="restore" literal="Restore"
	 * @generated
	 * @ordered
	 */
	public static final int RESTORE_VALUE = 9;

	/**
	 * The '<em><b>Revert</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Revert</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REVERT
	 * @model name="revert" literal="Revert"
	 * @generated
	 * @ordered
	 */
	public static final int REVERT_VALUE = 10;

	/**
	 * The '<em><b>Submit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Submit</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SUBMIT
	 * @model name="submit" literal="Submit"
	 * @generated
	 * @ordered
	 */
	public static final int SUBMIT_VALUE = 11;

	/**
	 * The '<em><b>Topic</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Topic</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TOPIC
	 * @model name="topic" literal="Topic"
	 * @generated
	 * @ordered
	 */
	public static final int TOPIC_VALUE = 12;

	/**
	 * An array of all the '<em><b>Action Constants</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ActionConstants[] VALUES_ARRAY = new ActionConstants[] { ABANDON, CHECKOUT, CHERRYPICK, DRAFT,
			FOLLOWUP, PUBLISH, REBASE, REFRESH, REPLY, RESTORE, REVERT, SUBMIT, TOPIC, };

	/**
	 * A public read-only list of all the '<em><b>Action Constants</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ActionConstants> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Action Constants</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ActionConstants get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ActionConstants result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Action Constants</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ActionConstants getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ActionConstants result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Action Constants</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ActionConstants get(int value) {
		switch (value) {
		case ABANDON_VALUE:
			return ABANDON;
		case CHECKOUT_VALUE:
			return CHECKOUT;
		case CHERRYPICK_VALUE:
			return CHERRYPICK;
		case DRAFT_VALUE:
			return DRAFT;
		case FOLLOWUP_VALUE:
			return FOLLOWUP;
		case PUBLISH_VALUE:
			return PUBLISH;
		case REBASE_VALUE:
			return REBASE;
		case REFRESH_VALUE:
			return REFRESH;
		case REPLY_VALUE:
			return REPLY;
		case RESTORE_VALUE:
			return RESTORE;
		case REVERT_VALUE:
			return REVERT;
		case SUBMIT_VALUE:
			return SUBMIT;
		case TOPIC_VALUE:
			return TOPIC;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ActionConstants(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} //ActionConstants
