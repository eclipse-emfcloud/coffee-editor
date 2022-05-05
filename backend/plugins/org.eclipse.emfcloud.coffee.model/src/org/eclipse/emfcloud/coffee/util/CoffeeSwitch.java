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
package org.eclipse.emfcloud.coffee.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emfcloud.coffee.AutomaticTask;
import org.eclipse.emfcloud.coffee.BrewingUnit;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.Component;
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
import org.eclipse.emfcloud.coffee.Merge;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Processor;
import org.eclipse.emfcloud.coffee.RAM;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.WaterTank;
import org.eclipse.emfcloud.coffee.WeightedFlow;
import org.eclipse.emfcloud.coffee.Workflow;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.emfcloud.coffee.CoffeePackage
 * @generated
 */
public class CoffeeSwitch<T> extends Switch<T> {
   /**
    * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @generated
    */
   protected static CoffeePackage modelPackage;

   /**
    * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    *
    * @generated
    */
   public CoffeeSwitch() {
      if (modelPackage == null) {
         modelPackage = CoffeePackage.eINSTANCE;
      }
   }

   /**
    * Checks whether this is a switch for the given package. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
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
    * Calls <code>caseXXX</code> for each class of the model until one returns a
    * non null result; it yields that result. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    *
    * @return the first non-null result returned by a <code>caseXXX</code> call.
    * @generated
    */
   @Override
   protected T doSwitch(final int classifierID, final EObject theEObject) {
      switch (classifierID) {
         case CoffeePackage.COMPONENT: {
            Component component = (Component) theEObject;
            T result = caseComponent(component);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.MACHINE: {
            Machine machine = (Machine) theEObject;
            T result = caseMachine(machine);
            if (result == null) {
               result = caseComponent(machine);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.CONTROL_UNIT: {
            ControlUnit controlUnit = (ControlUnit) theEObject;
            T result = caseControlUnit(controlUnit);
            if (result == null) {
               result = caseComponent(controlUnit);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.BREWING_UNIT: {
            BrewingUnit brewingUnit = (BrewingUnit) theEObject;
            T result = caseBrewingUnit(brewingUnit);
            if (result == null) {
               result = caseComponent(brewingUnit);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.DIP_TRAY: {
            DipTray dipTray = (DipTray) theEObject;
            T result = caseDipTray(dipTray);
            if (result == null) {
               result = caseComponent(dipTray);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.WATER_TANK: {
            WaterTank waterTank = (WaterTank) theEObject;
            T result = caseWaterTank(waterTank);
            if (result == null) {
               result = caseComponent(waterTank);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.PROCESSOR: {
            Processor processor = (Processor) theEObject;
            T result = caseProcessor(processor);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.DIMENSION: {
            Dimension dimension = (Dimension) theEObject;
            T result = caseDimension(dimension);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.RAM: {
            RAM ram = (RAM) theEObject;
            T result = caseRAM(ram);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.DISPLAY: {
            Display display = (Display) theEObject;
            T result = caseDisplay(display);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.WORKFLOW: {
            Workflow workflow = (Workflow) theEObject;
            T result = caseWorkflow(workflow);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.NODE: {
            Node node = (Node) theEObject;
            T result = caseNode(node);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.TASK: {
            Task task = (Task) theEObject;
            T result = caseTask(task);
            if (result == null) {
               result = caseNode(task);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.AUTOMATIC_TASK: {
            AutomaticTask automaticTask = (AutomaticTask) theEObject;
            T result = caseAutomaticTask(automaticTask);
            if (result == null) {
               result = caseTask(automaticTask);
            }
            if (result == null) {
               result = caseNode(automaticTask);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.MANUAL_TASK: {
            ManualTask manualTask = (ManualTask) theEObject;
            T result = caseManualTask(manualTask);
            if (result == null) {
               result = caseTask(manualTask);
            }
            if (result == null) {
               result = caseNode(manualTask);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.FORK: {
            Fork fork = (Fork) theEObject;
            T result = caseFork(fork);
            if (result == null) {
               result = caseNode(fork);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.JOIN: {
            Join join = (Join) theEObject;
            T result = caseJoin(join);
            if (result == null) {
               result = caseNode(join);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.DECISION: {
            Decision decision = (Decision) theEObject;
            T result = caseDecision(decision);
            if (result == null) {
               result = caseNode(decision);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.MERGE: {
            Merge merge = (Merge) theEObject;
            T result = caseMerge(merge);
            if (result == null) {
               result = caseNode(merge);
            }
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.FLOW: {
            Flow flow = (Flow) theEObject;
            T result = caseFlow(flow);
            if (result == null) {
               result = defaultCase(theEObject);
            }
            return result;
         }
         case CoffeePackage.WEIGHTED_FLOW: {
            WeightedFlow weightedFlow = (WeightedFlow) theEObject;
            T result = caseWeightedFlow(weightedFlow);
            if (result == null) {
               result = caseFlow(weightedFlow);
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
    * Returns the result of interpreting the object as an instance of
    * '<em>Component</em>'. <!-- begin-user-doc --> This implementation returns
    * null; returning a non-null result will terminate the switch. <!--
    * end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Component</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseComponent(final Component object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Machine</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Machine</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseMachine(final Machine object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Control
    * Unit</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Control
    *         Unit</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseControlUnit(final ControlUnit object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Brewing
    * Unit</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Brewing
    *         Unit</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseBrewingUnit(final BrewingUnit object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Dip
    * Tray</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Dip
    *         Tray</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseDipTray(final DipTray object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Water
    * Tank</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Water
    *         Tank</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseWaterTank(final WaterTank object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Processor</em>'. <!-- begin-user-doc --> This implementation returns
    * null; returning a non-null result will terminate the switch. <!--
    * end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Processor</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseProcessor(final Processor object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Dimension</em>'. <!-- begin-user-doc --> This implementation returns
    * null; returning a non-null result will terminate the switch. <!--
    * end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Dimension</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseDimension(final Dimension object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>RAM</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>RAM</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseRAM(final RAM object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Display</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Display</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseDisplay(final Display object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Workflow</em>'. <!-- begin-user-doc --> This implementation returns
    * null; returning a non-null result will terminate the switch. <!--
    * end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Workflow</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseWorkflow(final Workflow object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Node</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Node</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseNode(final Node object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Task</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Task</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseTask(final Task object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Automatic Task</em>'. <!-- begin-user-doc --> This implementation
    * returns null; returning a non-null result will terminate the switch. <!--
    * end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Automatic Task</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAutomaticTask(final AutomaticTask object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Manual
    * Task</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Manual
    *         Task</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseManualTask(final ManualTask object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Fork</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Fork</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseFork(final Fork object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Join</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Join</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseJoin(final Join object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Decision</em>'. <!-- begin-user-doc --> This implementation returns
    * null; returning a non-null result will terminate the switch. <!--
    * end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Decision</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseDecision(final Decision object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Merge</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Merge</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseMerge(final Merge object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>Flow</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>Flow</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseFlow(final Flow object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Weighted
    * Flow</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Weighted
    *         Flow</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseWeightedFlow(final WeightedFlow object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of
    * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null;
    * returning a non-null result will terminate the switch, but this is the last
    * case anyway. <!-- end-user-doc -->
    *
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of
    *         '<em>EObject</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject)
    * @generated
    */
   @Override
   public T defaultCase(final EObject object) {
      return null;
   }

} // CoffeeSwitch
