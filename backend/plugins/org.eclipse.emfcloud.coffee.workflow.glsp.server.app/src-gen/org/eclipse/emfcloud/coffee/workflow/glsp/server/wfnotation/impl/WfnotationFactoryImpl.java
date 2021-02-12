/**
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WfnotationFactoryImpl extends EFactoryImpl
		implements org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationFactory init() {
		try {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationFactory theWfnotationFactory = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationFactory) EPackage.Registry.INSTANCE
					.getEFactory(WfnotationPackage.eNS_URI);
			if (theWfnotationFactory != null) {
				return theWfnotationFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WfnotationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WfnotationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM:
			return createDiagram();
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SEMANTIC_PROXY:
			return createSemanticProxy();
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE:
			return createShape();
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.EDGE:
			return createEdge();
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.POINT:
			return createPoint();
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIMENSION:
			return createDimension();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram createDiagram() {
		DiagramImpl diagram = new DiagramImpl();
		return diagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy createSemanticProxy() {
		SemanticProxyImpl semanticProxy = new SemanticProxyImpl();
		return semanticProxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape createShape() {
		ShapeImpl shape = new ShapeImpl();
		return shape;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge createEdge() {
		EdgeImpl edge = new EdgeImpl();
		return edge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point createPoint() {
		PointImpl point = new PointImpl();
		return point;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension createDimension() {
		DimensionImpl dimension = new DimensionImpl();
		return dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage getWfnotationPackage() {
		return (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage getPackage() {
		return org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.eINSTANCE;
	}

} //WfnotationFactoryImpl
