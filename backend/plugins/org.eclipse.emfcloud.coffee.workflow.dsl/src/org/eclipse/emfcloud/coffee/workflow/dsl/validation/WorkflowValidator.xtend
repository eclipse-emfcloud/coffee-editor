/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.dsl.validation

import org.eclipse.emfcloud.coffee.workflow.dsl.index.IWorkflowIndex
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.validation.Check

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class WorkflowValidator extends AbstractWorkflowValidator {

	public static val DUPLICATE_ACTION = 'duplicateAction'
	public static val WRONG_PROBABILITY_ORDER = "propabilityOrder"
	public static val PROBABILITY_NOT_IN_RANGE = "probabilityNotInRange"

	static val PROBABILITY_ORDER_WARNING = "The probability value for \"%s\" is higher than the probability value for \"%s\"";
	static val PROBABILITY_RANGE_ERROR = "The probability value for \"%s\" must be between 0.0 and 1.0"

	static val INVALID_TASK_ID = "invalidTaskID"
	static val INVALID_TASK_ID_MESSAGE = "The ID does not refer to a task of \"%s\" in \"%s\"."

	static val DUPLICATE_TASKS = "duplicateTasks"
	static val DUPLICATE_TASKS_MESSAGE = "Before and After cannot be the same."

	static val INVALID_MODEL_ID = "invalidModelID"
	static val INVALID_MODEL_ID_MESSAGE = "The ID does not refer to an existing model in \"%s\"."
	
	static val INVALID_MACHINE_ID = "invalidMachineID"
	static val INVALID_MACHINE_ID_MESSAGE = "The ID does not refer to an existing machine."
	
	@Inject
	IWorkflowIndex index;

	@Check
	def checkAssertion(WorkflowConfiguration workflowConfiguration) {
		if(!index.getMachine(workflowConfiguration.machine).isPresent) {
			error(INVALID_MACHINE_ID_MESSAGE,
				WorkflowPackage.Literals.WORKFLOW_CONFIGURATION__MACHINE, INVALID_MACHINE_ID);
		}
		if(!index.getWorkflow(workflowConfiguration.machine, workflowConfiguration.model).isPresent) {
			error(String.format(INVALID_MODEL_ID_MESSAGE, workflowConfiguration.machine),
				WorkflowPackage.Literals.WORKFLOW_CONFIGURATION__MODEL, INVALID_MODEL_ID);
		}
	}

	@Check
	def checkAssertion(Assertion assertion) {
		if(!index.getTask(assertion.workflowConfiguration.machine,assertion.workflowConfiguration.model, assertion.before).present) {
			error(String.format(INVALID_TASK_ID_MESSAGE, assertion.workflowConfiguration.model, assertion.workflowConfiguration.machine),
				WorkflowPackage.Literals.ASSERTION__BEFORE, INVALID_TASK_ID);
		}
		if(!index.getTask(assertion.workflowConfiguration.machine,assertion.workflowConfiguration.model, assertion.after).present) {
			error(String.format(INVALID_TASK_ID_MESSAGE, assertion.workflowConfiguration.model, assertion.workflowConfiguration.machine),
				WorkflowPackage.Literals.ASSERTION__AFTER, INVALID_TASK_ID);
		}
		if(assertion.after == assertion.before) {
			error(DUPLICATE_TASKS_MESSAGE, WorkflowPackage.Literals.ASSERTION__AFTER, DUPLICATE_TASKS);
		}
	}

	@Check
	def checkProbabilityRange(ProbabilityConfiguration probConf) {
		if (probConf.low < 0.0 || probConf.low > 1.0) {
			error(String.format(WorkflowValidator.PROBABILITY_RANGE_ERROR, "LOW"),
				WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__LOW, PROBABILITY_NOT_IN_RANGE);
		}
		if (probConf.medium < 0.0 || probConf.medium > 1.0) {
			error(String.format(WorkflowValidator.PROBABILITY_RANGE_ERROR, "MEDIUM"),
				WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__MEDIUM, PROBABILITY_NOT_IN_RANGE);
		}

		if (probConf.high < 0.0 || probConf.high > 1.0) {
			error(String.format(WorkflowValidator.PROBABILITY_RANGE_ERROR, "HIGH"),
				WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__HIGH, PROBABILITY_NOT_IN_RANGE);
		}

	}

	@Check
	def checkProbabilityOrder(ProbabilityConfiguration probabilityConfiguration) {
		if (probabilityConfiguration.low > probabilityConfiguration.medium) {
			warning(String.format(WorkflowValidator.PROBABILITY_ORDER_WARNING, "LOW", "MEDIUM"),
				WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__LOW, WRONG_PROBABILITY_ORDER);
		} else if (probabilityConfiguration.low > probabilityConfiguration.high) {
			warning(String.format(WorkflowValidator.PROBABILITY_ORDER_WARNING, "LOW", "HIGH"),
				WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__LOW, WRONG_PROBABILITY_ORDER);
		} else if (probabilityConfiguration.medium > probabilityConfiguration.high) {
			warning(String.format(WorkflowValidator.PROBABILITY_ORDER_WARNING, "MEDIUM", "HIGH"),
				WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__MEDIUM, WRONG_PROBABILITY_ORDER);
		}
	}
	
	def WorkflowConfiguration workflowConfiguration(EObject assertion) {
		assertion.eContainer as WorkflowConfiguration
	}
}
