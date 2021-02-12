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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage
 * @generated
 */
public class WfnotationSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WfnotationSwitch() {
		if (modelPackage == null) {
			modelPackage = org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT: {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement diagramElement = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement) theEObject;
			T result = caseDiagramElement(diagramElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM: {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram diagram = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram) theEObject;
			T result = caseDiagram(diagram);
			if (result == null)
				result = caseDiagramElement(diagram);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SEMANTIC_PROXY: {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy semanticProxy = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy) theEObject;
			T result = caseSemanticProxy(semanticProxy);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE: {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape shape = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape) theEObject;
			T result = caseShape(shape);
			if (result == null)
				result = caseDiagramElement(shape);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.EDGE: {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge edge = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge) theEObject;
			T result = caseEdge(edge);
			if (result == null)
				result = caseDiagramElement(edge);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.POINT: {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point point = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point) theEObject;
			T result = casePoint(point);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIMENSION: {
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension dimension = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension) theEObject;
			T result = caseDimension(dimension);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Diagram Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diagram Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiagramElement(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiagram(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Semantic Proxy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Semantic Proxy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSemanticProxy(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shape</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shape</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShape(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEdge(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Point</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePoint(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDimension(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //WfnotationSwitch
