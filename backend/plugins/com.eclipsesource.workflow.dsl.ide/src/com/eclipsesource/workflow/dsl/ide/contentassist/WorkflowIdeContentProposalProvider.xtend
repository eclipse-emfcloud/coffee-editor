package com.eclipsesource.workflow.dsl.ide.contentassist

import com.eclipsesource.workflow.dsl.index.IWorkflowIndex
import com.eclipsesource.workflow.dsl.services.WorkflowGrammarAccess
import com.eclipsesource.workflow.dsl.workflow.WorkflowConfiguration
import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider
import com.eclipsesource.workflow.dsl.workflow.Assertion
import com.eclipsesource.workflow.dsl.workflow.ProbabilityConfiguration
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
			val proposal = proposalCreator.createProposal(graph.name, "", context, ContentAssistEntry.KIND_UNKNOWN, null)
			acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal))
		]
	}
	
	def completeWorkflowConfigurationModel(EObject model, ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		index.getWorkflows(model.workflowConfiguration.machine).forEach[workflow | 
			val proposal = proposalCreator.createProposal(workflow.name, "", context, ContentAssistEntry.KIND_UNKNOWN, null)
			acceptor.accept(proposal, proposalPriorities.getDefaultPriority(proposal))
		]
	}
	
	def completeAssertionBeforeAfter(EObject model, ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		index.getTasks(model.workflowConfiguration.machine,model.workflowConfiguration.model).forEach[task | 
			val proposal = proposalCreator.createProposal(task.name, "", context, ContentAssistEntry.KIND_UNKNOWN, null)
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

}
