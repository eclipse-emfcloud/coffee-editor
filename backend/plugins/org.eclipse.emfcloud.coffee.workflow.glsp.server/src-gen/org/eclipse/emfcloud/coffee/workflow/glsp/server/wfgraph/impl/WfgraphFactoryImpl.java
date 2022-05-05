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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class WfgraphFactoryImpl extends EFactoryImpl
   implements org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphFactory {
   /**
    * Creates the default factory implementation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   public static org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphFactory init() {
      try {
         org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphFactory theWfgraphFactory = (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphFactory) EPackage.Registry.INSTANCE
            .getEFactory(WfgraphPackage.eNS_URI);
         if (theWfgraphFactory != null) {
            return theWfgraphFactory;
         }
      } catch (Exception exception) {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new WfgraphFactoryImpl();
   }

   /**
    * Creates an instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   public WfgraphFactoryImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public EObject create(final EClass eClass) {
      switch (eClass.getClassifierID()) {
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.ACTIVITY_NODE:
            return createActivityNode();
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.TASK_NODE:
            return createTaskNode();
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.ICON:
            return createIcon();
         case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.WEIGHTED_EDGE:
            return createWeightedEdge();
         default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode createActivityNode() {
      ActivityNodeImpl activityNode = new ActivityNodeImpl();
      return activityNode;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode createTaskNode() {
      TaskNodeImpl taskNode = new TaskNodeImpl();
      return taskNode;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon createIcon() {
      IconImpl icon = new IconImpl();
      return icon;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge createWeightedEdge() {
      WeightedEdgeImpl weightedEdge = new WeightedEdgeImpl();
      return weightedEdge;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage getWfgraphPackage() {
      return (org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @deprecated
    * @generated
    */
   @Deprecated
   public static org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage getPackage() {
      return org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage.eINSTANCE;
   }

} // WfgraphFactoryImpl
