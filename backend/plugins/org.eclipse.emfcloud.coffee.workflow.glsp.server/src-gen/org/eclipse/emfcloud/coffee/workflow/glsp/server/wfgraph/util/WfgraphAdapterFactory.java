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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage
 * @generated
 */
public class WfgraphAdapterFactory extends AdapterFactoryImpl {
   /**
    * The cached model package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   protected static org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage modelPackage;

   /**
    * Creates an instance of the adapter factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   public WfgraphAdapterFactory() {
      if (modelPackage == null) {
         modelPackage = org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.eINSTANCE;
      }
   }

   /**
    * Returns whether this factory is applicable for the type of the object.
    * <!-- begin-user-doc -->
    * This implementation returns <code>true</code> if the object is either the model's package or is an instance object
    * of the model.
    * <!-- end-user-doc -->
    *
    * @return whether this factory is applicable for the type of the object.
    * @generated
    */
   @Override
   public boolean isFactoryForType(final Object object) {
      if (object == modelPackage) {
         return true;
      }
      if (object instanceof EObject) {
         return ((EObject) object).eClass().getEPackage() == modelPackage;
      }
      return false;
   }

   /**
    * The switch that delegates to the <code>createXXX</code> methods.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   protected WfgraphSwitch<Adapter> modelSwitch = new WfgraphSwitch<>() {
      @Override
      public Adapter caseActivityNode(
         final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode object) {
         return createActivityNodeAdapter();
      }

      @Override
      public Adapter caseTaskNode(final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode object) {
         return createTaskNodeAdapter();
      }

      @Override
      public Adapter caseIcon(final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon object) {
         return createIconAdapter();
      }

      @Override
      public Adapter caseWeightedEdge(
         final org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge object) {
         return createWeightedEdgeAdapter();
      }

      @Override
      public Adapter caseGModelElement(final GModelElement object) {
         return createGModelElementAdapter();
      }

      @Override
      public Adapter caseGBoundsAware(final GBoundsAware object) {
         return createGBoundsAwareAdapter();
      }

      @Override
      public Adapter caseGShapeElement(final GShapeElement object) {
         return createGShapeElementAdapter();
      }

      @Override
      public Adapter caseGEdgeLayoutable(final GEdgeLayoutable object) {
         return createGEdgeLayoutableAdapter();
      }

      @Override
      public Adapter caseGLayouting(final GLayouting object) {
         return createGLayoutingAdapter();
      }

      @Override
      public Adapter caseGNode(final GNode object) {
         return createGNodeAdapter();
      }

      @Override
      public Adapter caseGCompartment(final GCompartment object) {
         return createGCompartmentAdapter();
      }

      @Override
      public Adapter caseGEdge(final GEdge object) {
         return createGEdgeAdapter();
      }

      @Override
      public Adapter defaultCase(final EObject object) {
         return createEObjectAdapter();
      }
   };

   /**
    * Creates an adapter for the <code>target</code>.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @param target the object to adapt.
    * @return the adapter for the <code>target</code>.
    * @generated
    */
   @Override
   public Adapter createAdapter(final Notifier target) {
      return modelSwitch.doSwitch((EObject) target);
   }

   /**
    * Creates a new adapter for an object of class
    * '{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode <em>Activity Node</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode
    * @generated
    */
   public Adapter createActivityNodeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class
    * '{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode <em>Task Node</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode
    * @generated
    */
   public Adapter createTaskNodeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon
    * <em>Icon</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon
    * @generated
    */
   public Adapter createIconAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class
    * '{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge <em>Weighted Edge</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge
    * @generated
    */
   public Adapter createWeightedEdgeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GModelElement <em>GModel
    * Element</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GModelElement
    * @generated
    */
   public Adapter createGModelElementAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GBoundsAware <em>GBounds Aware</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GBoundsAware
    * @generated
    */
   public Adapter createGBoundsAwareAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GShapeElement <em>GShape
    * Element</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GShapeElement
    * @generated
    */
   public Adapter createGShapeElementAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GEdgeLayoutable <em>GEdge
    * Layoutable</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GEdgeLayoutable
    * @generated
    */
   public Adapter createGEdgeLayoutableAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GLayouting <em>GLayouting</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GLayouting
    * @generated
    */
   public Adapter createGLayoutingAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GNode <em>GNode</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GNode
    * @generated
    */
   public Adapter createGNodeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GCompartment <em>GCompartment</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GCompartment
    * @generated
    */
   public Adapter createGCompartmentAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.glsp.graph.GEdge <em>GEdge</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @see org.eclipse.glsp.graph.GEdge
    * @generated
    */
   public Adapter createGEdgeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for the default case.
    * <!-- begin-user-doc -->
    * This default implementation returns null.
    * <!-- end-user-doc -->
    *
    * @return the new adapter.
    * @generated
    */
   public Adapter createEObjectAdapter() {
      return null;
   }

} // WfgraphAdapterFactory
