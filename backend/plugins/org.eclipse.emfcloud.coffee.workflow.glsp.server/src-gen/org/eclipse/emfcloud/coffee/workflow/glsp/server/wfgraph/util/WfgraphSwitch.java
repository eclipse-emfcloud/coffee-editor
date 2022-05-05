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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GCompartment;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GEdgeLayoutable;
import org.eclipse.glsp.graph.GLayouting;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GShapeElement;

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
 *
 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage
 * @generated
 */
public class WfgraphSwitch<T> extends Switch<T> {
   /**
    * The cached model package
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   protected static org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage modelPackage;

   /**
    * Creates an instance of the switch.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   public WfgraphSwitch() {
      if (modelPackage == null) {
         modelPackage = org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.eINSTANCE;
      }
   }

   /**
    * Checks whether this is a switch for the given package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @param ePackage the package in question.
    * @return whether this is a switch for the given package.
    * @generated
    */
   @Override
   protected boolean isSwitchFor(final EPackage ePackage) {
      return ePackage == modelPackage;
   }

   /**
    * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @return the first non-null result returned by a <code>caseXXX</code> call.
    * @generated
    */
   @Override
   protected T doSwitch(final int classifierID, final EObject theEObject) {
      switch (classifierID) {
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.ACTIVITY_NODE: {
            org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode activityNode = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode) theEObject;
            T result = caseActivityNode(activityNode);
            if (result == null) {
               result = caseGNode(activityNode);
            }
            if (result == null) {
               result = caseGShapeElement(activityNode);
            }
            if (result == null) {
               result = caseGEdgeLayoutable(activityNode);
            }
            if (result == null) {
               result = caseGLayouting(activityNode);
            }
            if (result == null) {
               result = caseGModelElement(activityNode);
            }
            if (result == null) {
               result = caseGBoundsAware(activityNode);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.TASK_NODE: {
            org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode taskNode = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode) theEObject;
            T result = caseTaskNode(taskNode);
            if (result == null) {
               result = caseGNode(taskNode);
            }
            if (result == null) {
               result = caseGShapeElement(taskNode);
            }
            if (result == null) {
               result = caseGEdgeLayoutable(taskNode);
            }
            if (result == null) {
               result = caseGLayouting(taskNode);
            }
            if (result == null) {
               result = caseGModelElement(taskNode);
            }
            if (result == null) {
               result = caseGBoundsAware(taskNode);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.ICON: {
            org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon icon = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon) theEObject;
            T result = caseIcon(icon);
            if (result == null) {
               result = caseGCompartment(icon);
            }
            if (result == null) {
               result = caseGShapeElement(icon);
            }
            if (result == null) {
               result = caseGLayouting(icon);
            }
            if (result == null) {
               result = caseGModelElement(icon);
            }
            if (result == null) {
               result = caseGBoundsAware(icon);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.WEIGHTED_EDGE: {
            org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge weightedEdge = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge) theEObject;
            T result = caseWeightedEdge(weightedEdge);
            if (result == null) {
               result = caseGEdge(weightedEdge);
            }
            if (result == null) {
               result = caseGModelElement(weightedEdge);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         default:
            return defaultCase(theEObject);
      }
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Activity Node</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Activity Node</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseActivityNode(final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Task Node</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Task Node</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseTaskNode(final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Icon</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Icon</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseIcon(final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Weighted Edge</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Weighted Edge</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseWeightedEdge(final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GModel Element</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GModel Element</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGModelElement(final GModelElement object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GBounds Aware</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GBounds Aware</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGBoundsAware(final GBoundsAware object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GShape Element</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GShape Element</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGShapeElement(final GShapeElement object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GEdge Layoutable</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GEdge Layoutable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGEdgeLayoutable(final GEdgeLayoutable object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GLayouting</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GLayouting</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGLayouting(final GLayouting object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GNode</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GNode</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGNode(final GNode object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GCompartment</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GCompartment</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGCompartment(final GCompartment object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GEdge</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GEdge</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGEdge(final GEdge object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch, but this is the last case anyway.
    * <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject)
    * @generated
    */
   @Override
   public T defaultCase(final EObject object) {
      return null;
   }

} // WfgraphSwitch
