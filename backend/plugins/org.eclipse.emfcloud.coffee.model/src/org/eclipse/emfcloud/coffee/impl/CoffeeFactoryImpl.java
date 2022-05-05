/**
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package org.eclipse.emfcloud.coffee.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfcloud.coffee.AutomaticTask;
import org.eclipse.emfcloud.coffee.BrewingUnit;
import org.eclipse.emfcloud.coffee.CoffeeFactory;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.ControlUnit;
import org.eclipse.emfcloud.coffee.Decision;
import org.eclipse.emfcloud.coffee.Dimension;
import org.eclipse.emfcloud.coffee.DipTray;
import org.eclipse.emfcloud.coffee.Display;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Fork;
import org.eclipse.emfcloud.coffee.Join;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.ManualTask;
import org.eclipse.emfcloud.coffee.ManufactoringProcess;
import org.eclipse.emfcloud.coffee.Merge;
import org.eclipse.emfcloud.coffee.Probability;
import org.eclipse.emfcloud.coffee.Processor;
import org.eclipse.emfcloud.coffee.RAM;
import org.eclipse.emfcloud.coffee.RamType;
import org.eclipse.emfcloud.coffee.SocketConnectorType;
import org.eclipse.emfcloud.coffee.WaterTank;
import org.eclipse.emfcloud.coffee.WeightedFlow;
import org.eclipse.emfcloud.coffee.Workflow;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class CoffeeFactoryImpl extends EFactoryImpl implements CoffeeFactory {
   /**
    * Creates the default factory implementation. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    *
    * @generated
    */
   public static CoffeeFactory init() {
      try {
         CoffeeFactory theCoffeeFactory = (CoffeeFactory) EPackage.Registry.INSTANCE
            .getEFactory(CoffeePackage.eNS_URI);
         if (theCoffeeFactory != null) {
            return theCoffeeFactory;
         }
      } catch (Exception exception) {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new CoffeeFactoryImpl();
   }

   /**
    * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    *
    * @generated
    */
   public CoffeeFactoryImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public EObject create(final EClass eClass) {
      switch (eClass.getClassifierID()) {
         case CoffeePackage.MACHINE:
            return createMachine();
         case CoffeePackage.CONTROL_UNIT:
            return createControlUnit();
         case CoffeePackage.BREWING_UNIT:
            return createBrewingUnit();
         case CoffeePackage.DIP_TRAY:
            return createDipTray();
         case CoffeePackage.WATER_TANK:
            return createWaterTank();
         case CoffeePackage.PROCESSOR:
            return createProcessor();
         case CoffeePackage.DIMENSION:
            return createDimension();
         case CoffeePackage.RAM:
            return createRAM();
         case CoffeePackage.DISPLAY:
            return createDisplay();
         case CoffeePackage.WORKFLOW:
            return createWorkflow();
         case CoffeePackage.AUTOMATIC_TASK:
            return createAutomaticTask();
         case CoffeePackage.MANUAL_TASK:
            return createManualTask();
         case CoffeePackage.FORK:
            return createFork();
         case CoffeePackage.JOIN:
            return createJoin();
         case CoffeePackage.DECISION:
            return createDecision();
         case CoffeePackage.MERGE:
            return createMerge();
         case CoffeePackage.FLOW:
            return createFlow();
         case CoffeePackage.WEIGHTED_FLOW:
            return createWeightedFlow();
         default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Object createFromString(final EDataType eDataType, final String initialValue) {
      switch (eDataType.getClassifierID()) {
         case CoffeePackage.SOCKET_CONNECTOR_TYPE:
            return createSocketConnectorTypeFromString(eDataType, initialValue);
         case CoffeePackage.MANUFACTORING_PROCESS:
            return createManufactoringProcessFromString(eDataType, initialValue);
         case CoffeePackage.RAM_TYPE:
            return createRamTypeFromString(eDataType, initialValue);
         case CoffeePackage.PROBABILITY:
            return createProbabilityFromString(eDataType, initialValue);
         case CoffeePackage.TASK_NAME:
            return createTaskNameFromString(eDataType, initialValue);
         default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public String convertToString(final EDataType eDataType, final Object instanceValue) {
      switch (eDataType.getClassifierID()) {
         case CoffeePackage.SOCKET_CONNECTOR_TYPE:
            return convertSocketConnectorTypeToString(eDataType, instanceValue);
         case CoffeePackage.MANUFACTORING_PROCESS:
            return convertManufactoringProcessToString(eDataType, instanceValue);
         case CoffeePackage.RAM_TYPE:
            return convertRamTypeToString(eDataType, instanceValue);
         case CoffeePackage.PROBABILITY:
            return convertProbabilityToString(eDataType, instanceValue);
         case CoffeePackage.TASK_NAME:
            return convertTaskNameToString(eDataType, instanceValue);
         default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Machine createMachine() {
      MachineImpl machine = new MachineImpl();
      return machine;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public ControlUnit createControlUnit() {
      ControlUnitImpl controlUnit = new ControlUnitImpl();
      return controlUnit;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public BrewingUnit createBrewingUnit() {
      BrewingUnitImpl brewingUnit = new BrewingUnitImpl();
      return brewingUnit;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public DipTray createDipTray() {
      DipTrayImpl dipTray = new DipTrayImpl();
      return dipTray;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public WaterTank createWaterTank() {
      WaterTankImpl waterTank = new WaterTankImpl();
      return waterTank;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Processor createProcessor() {
      ProcessorImpl processor = new ProcessorImpl();
      return processor;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Dimension createDimension() {
      DimensionImpl dimension = new DimensionImpl();
      return dimension;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public RAM createRAM() {
      RAMImpl ram = new RAMImpl();
      return ram;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Display createDisplay() {
      DisplayImpl display = new DisplayImpl();
      return display;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Workflow createWorkflow() {
      WorkflowImpl workflow = new WorkflowImpl();
      return workflow;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public AutomaticTask createAutomaticTask() {
      AutomaticTaskImpl automaticTask = new AutomaticTaskImpl();
      return automaticTask;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public ManualTask createManualTask() {
      ManualTaskImpl manualTask = new ManualTaskImpl();
      return manualTask;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Fork createFork() {
      ForkImpl fork = new ForkImpl();
      return fork;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Join createJoin() {
      JoinImpl join = new JoinImpl();
      return join;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Decision createDecision() {
      DecisionImpl decision = new DecisionImpl();
      return decision;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Merge createMerge() {
      MergeImpl merge = new MergeImpl();
      return merge;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public Flow createFlow() {
      FlowImpl flow = new FlowImpl();
      return flow;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public WeightedFlow createWeightedFlow() {
      WeightedFlowImpl weightedFlow = new WeightedFlowImpl();
      return weightedFlow;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public SocketConnectorType createSocketConnectorTypeFromString(final EDataType eDataType,
      final String initialValue) {
      SocketConnectorType result = SocketConnectorType.get(initialValue);
      if (result == null) {
         throw new IllegalArgumentException(
            "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
      }
      return result;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public String convertSocketConnectorTypeToString(final EDataType eDataType, final Object instanceValue) {
      return instanceValue == null ? null : instanceValue.toString();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public ManufactoringProcess createManufactoringProcessFromString(final EDataType eDataType,
      final String initialValue) {
      ManufactoringProcess result = ManufactoringProcess.get(initialValue);
      if (result == null) {
         throw new IllegalArgumentException(
            "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
      }
      return result;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public String convertManufactoringProcessToString(final EDataType eDataType, final Object instanceValue) {
      return instanceValue == null ? null : instanceValue.toString();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public RamType createRamTypeFromString(final EDataType eDataType, final String initialValue) {
      RamType result = RamType.get(initialValue);
      if (result == null) {
         throw new IllegalArgumentException(
            "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
      }
      return result;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public String convertRamTypeToString(final EDataType eDataType, final Object instanceValue) {
      return instanceValue == null ? null : instanceValue.toString();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public Probability createProbabilityFromString(final EDataType eDataType, final String initialValue) {
      Probability result = Probability.get(initialValue);
      if (result == null) {
         throw new IllegalArgumentException(
            "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
      }
      return result;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public String convertProbabilityToString(final EDataType eDataType, final Object instanceValue) {
      return instanceValue == null ? null : instanceValue.toString();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public String createTaskNameFromString(final EDataType eDataType, final String initialValue) {
      return (String) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   public String convertTaskNameToString(final EDataType eDataType, final Object instanceValue) {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   @Override
   public CoffeePackage getCoffeePackage() { return (CoffeePackage) getEPackage(); }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @deprecated
    * @generated
    */
   @Deprecated
   public static CoffeePackage getPackage() { return CoffeePackage.eINSTANCE; }

} // CoffeeFactoryImpl
