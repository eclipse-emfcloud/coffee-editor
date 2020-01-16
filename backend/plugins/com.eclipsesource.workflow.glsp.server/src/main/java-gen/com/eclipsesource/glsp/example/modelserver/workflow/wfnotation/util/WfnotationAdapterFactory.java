/**
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.util;

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage
 * @generated
 */
public class WfnotationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static WfnotationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public WfnotationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = WfnotationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!--
	 * begin-user-doc --> This implementation returns <code>true</code> if the
	 * object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected WfnotationSwitch<Adapter> modelSwitch = new WfnotationSwitch<Adapter>() {
		@Override
		public Adapter caseDiagramElement(DiagramElement object) {
			return createDiagramElementAdapter();
		}

		@Override
		public Adapter caseDiagram(Diagram object) {
			return createDiagramAdapter();
		}

		@Override
		public Adapter caseSemanticProxy(SemanticProxy object) {
			return createSemanticProxyAdapter();
		}

		@Override
		public Adapter caseShape(Shape object) {
			return createShapeAdapter();
		}

		@Override
		public Adapter caseEdge(Edge object) {
			return createEdgeAdapter();
		}

		@Override
		public Adapter casePoint(Point object) {
			return createPointAdapter();
		}

		@Override
		public Adapter caseDimension(Dimension object) {
			return createDimensionAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram
	 * <em>Diagram</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram
	 * @generated
	 */
	public Adapter createDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement
	 * <em>Diagram Element</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement
	 * @generated
	 */
	public Adapter createDiagramElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy
	 * <em>Semantic Proxy</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy
	 * @generated
	 */
	public Adapter createSemanticProxyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape
	 * <em>Shape</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape
	 * @generated
	 */
	public Adapter createShapeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge
	 * <em>Edge</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge
	 * @generated
	 */
	public Adapter createEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point
	 * <em>Point</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point
	 * @generated
	 */
	public Adapter createPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension
	 * <em>Dimension</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // WfnotationAdapterFactory
