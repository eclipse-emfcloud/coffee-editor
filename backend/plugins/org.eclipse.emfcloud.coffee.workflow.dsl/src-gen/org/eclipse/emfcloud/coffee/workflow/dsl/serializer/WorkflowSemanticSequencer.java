/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.dsl.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class WorkflowSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private WorkflowGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == WorkflowPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case WorkflowPackage.ASSERTION:
				sequence_Assertion(context, (Assertion) semanticObject); 
				return; 
			case WorkflowPackage.PROBABILITY_CONFIGURATION:
				sequence_ProbabilityConfiguration(context, (ProbabilityConfiguration) semanticObject); 
				return; 
			case WorkflowPackage.WORKFLOW_CONFIGURATION:
				sequence_WorkflowConfiguration(context, (WorkflowConfiguration) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     Assertion returns Assertion
	 *
	 * Constraint:
	 *     (before=STRING after=STRING)
	 */
	protected void sequence_Assertion(ISerializationContext context, Assertion semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, WorkflowPackage.Literals.ASSERTION__BEFORE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WorkflowPackage.Literals.ASSERTION__BEFORE));
			if (transientValues.isValueTransient(semanticObject, WorkflowPackage.Literals.ASSERTION__AFTER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WorkflowPackage.Literals.ASSERTION__AFTER));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAssertionAccess().getBeforeSTRINGTerminalRuleCall_0_0(), semanticObject.getBefore());
		feeder.accept(grammarAccess.getAssertionAccess().getAfterSTRINGTerminalRuleCall_2_0(), semanticObject.getAfter());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ProbabilityConfiguration returns ProbabilityConfiguration
	 *
	 * Constraint:
	 *     (low=Float medium=Float high=Float)
	 */
	protected void sequence_ProbabilityConfiguration(ISerializationContext context, ProbabilityConfiguration semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__LOW) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__LOW));
			if (transientValues.isValueTransient(semanticObject, WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__MEDIUM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__MEDIUM));
			if (transientValues.isValueTransient(semanticObject, WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__HIGH) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WorkflowPackage.Literals.PROBABILITY_CONFIGURATION__HIGH));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getProbabilityConfigurationAccess().getLowFloatParserRuleCall_0_3_0(), semanticObject.getLow());
		feeder.accept(grammarAccess.getProbabilityConfigurationAccess().getMediumFloatParserRuleCall_1_2_0(), semanticObject.getMedium());
		feeder.accept(grammarAccess.getProbabilityConfigurationAccess().getHighFloatParserRuleCall_2_2_0(), semanticObject.getHigh());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     WorkflowConfiguration returns WorkflowConfiguration
	 *
	 * Constraint:
	 *     (machine=STRING model=STRING probConf=ProbabilityConfiguration? (assertions+=Assertion assertions+=Assertion*)?)
	 */
	protected void sequence_WorkflowConfiguration(ISerializationContext context, WorkflowConfiguration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
