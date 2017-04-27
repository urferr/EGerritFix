/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.internal.core.command;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

class EMFTypeAdapterFactory implements TypeAdapterFactory {
	private static final Logger logger = LoggerFactory.getLogger(EMFTypeAdapterFactory.class);

	@SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		if (!EObject.class.isAssignableFrom(typeToken.getRawType())) {
			return null;
		}

		return (TypeAdapter<T>) new EObjectTypeAdapter(gson, typeToken.getType());
	}

	private static class EObjectTypeAdapter extends TypeAdapter<EObject> {

		private Type expectedType;

		private Gson gson;

		private EObjectTypeAdapter(Gson gson, Type expectedType) {
			this.expectedType = expectedType;
			this.gson = gson;
		}

		private EClass toEClass() {
			EClassifier classifier = ModelPackage.eINSTANCE.getEClassifier(
					expectedType.getTypeName().substring(expectedType.getTypeName().lastIndexOf('.') + 1));
			return (EClass) classifier;
		}

		private EStructuralFeature getAttributeWithName(EClass eclass, String name) {
			return eclass.getEStructuralFeature(name);
		}

		private TypeAdapter<?> getTypeAdapter(EObject instance, EStructuralFeature feature) {
			if (!feature.isMany()) {
				return gson.getAdapter(feature.getEType().getInstanceClass());
			}
			if (EMap.class.isAssignableFrom(instance.eGet(feature).getClass())) {
				Type keyType = ((org.eclipse.emf.ecore.EReference) feature).getEReferenceType()
						.getEStructuralFeature("key") //$NON-NLS-1$
						.getEType()
						.getInstanceClass();
				EStructuralFeature valueFeature = ((org.eclipse.emf.ecore.EReference) feature).getEReferenceType()
						.getEStructuralFeature("value"); //$NON-NLS-1$
				Type valueType = null;
				if (!valueFeature.isMany()) {
					valueType = valueFeature.getEType().getInstanceClass();
				} else {
					//Deal with the case where the value of the map is a list
					valueType = TypeToken.get(
							new MapType(BasicEList.class, new Type[] { valueFeature.getEType().getInstanceClass() }))
							.getType();
				}

				return gson.getAdapter(TypeToken.get(new MapType(Map.class, new Type[] { keyType, valueType })));

			}
			if (EList.class.isAssignableFrom(instance.eGet(feature).getClass())) {
				return gson.getAdapter(TypeToken.get(new MapType(List.class,
						new Type[] { feature.getEGenericType().getEClassifier().getInstanceClass() })));

			}
			return gson.getAdapter(instance.eGet(feature).getClass());
		}

		@Override
		public EObject read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}

			EObject instance = EcoreUtil.create(toEClass());

			try {
				in.beginObject();
				while (in.hasNext()) {
					String name = in.nextName();
//					logger.debug("Deserializing " + name); //$NON-NLS-1$
					EStructuralFeature feature = getAttributeWithName(instance.eClass(), name);
					if (feature == null) {
//						logger.warn("Unknown attribute " + name); //$NON-NLS-1$
						in.skipValue();
						continue;
					}
					Object value = getTypeAdapter(instance, feature).read(in);
					instance.eSet(feature, value);
				}
			} catch (IllegalStateException e) {
				throw new JsonSyntaxException(e);
			}
			in.endObject();
			return instance;
		}

		@Override
		public void write(JsonWriter out, EObject value) throws IOException {
			throw new UnsupportedOperationException("Serialization is not supported"); //$NON-NLS-1$
		}
	}

	private static class MapType implements Type, ParameterizedType {
		private Type[] actualTypes;

		private Type rawType;

		private MapType(Type raw, Type[] actualTypes) {
			rawType = raw;
			this.actualTypes = actualTypes;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return actualTypes;
		}

		@Override
		public Type getRawType() {
			return rawType;
		}

		@Override
		public Type getOwnerType() {
			// ignore
			return null;
		}
	}
}
