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
package org.eclipse.emfcloud.coffee.workflow.dsl.ide.contentassist

import org.eclipse.emfcloud.coffee.workflow.dsl.index.IWorkflowIndex
import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry

class WorkflowIdeContentProposalProvider extends IdeContentProposalProvider {
	@Inject extension WorkflowGrammarAccess
	@Inject IWorkflowIndex index

	override dispatch createProposals(Assignment assignment, ContentAssistContext context,
		IIdeContentProposalAcceptor acceptor) {
		val model = context.currentModel
		switch (assignment) {
			case workflowConfigurationAccess.machineAssignment_2:
				completeWorkflowConfigurationMachine(model, context, acceptor)
			case workflowConfigurationAccess.modelAssignment_5:
				completeWorkflowConfigurationModel(model, context, acceptor)
				
			case assertionAccess.beforeAssignment_0,
			case assertionAccess.afterAssignment_2:
				completeAssertionBeforeAfter(model, context, acceptor)
			
			default: {
				super._createProposals(assignment, context, acceptor)
			}
		}
	}

	def completeWorkflowConfigurationMachine(EObject model, ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		index.machines.forEach[graph | 
			val proposal = proposalCreator.createProposal(graph.name.quoteString, "", context, ContentAssistEntry.KIND_UNKNOWN, null)
			acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal))
		]
	}
	
	def completeWorkflowConfigurationModel(EObject model, ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		index.getWorkflows(model.workflowConfiguration.machine).forEach[workflow | 
			val proposal = proposalCreator.createProposal(workflow.name.quoteString, "", context, ContentAssistEntry.KIND_UNKNOWN, null)
			acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal))
		]
	}
	
	def completeAssertionBeforeAfter(EObject model, ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		index.getTasks(model.workflowConfiguration.machine,model.workflowConfiguration.model).forEach[task | 
			val proposal = proposalCreator.createProposal(task.name.quoteString, "", context, ContentAssistEntry.KIND_UNKNOWN, null)
			acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal))
		]
	}
	
	def WorkflowConfiguration workflowConfiguration(EObject model) {
		if(model instanceof WorkflowConfiguration) {
			return model
		}
		if(model instanceof Assertion) {
			return model.eContainer as WorkflowConfiguration
		}
		if(model instanceof ProbabilityConfiguration) {
			return model.eContainer as WorkflowConfiguration
		}
	}

	def static quoteString(String text) {
		return "\"" + text + "\"";
	}
}
