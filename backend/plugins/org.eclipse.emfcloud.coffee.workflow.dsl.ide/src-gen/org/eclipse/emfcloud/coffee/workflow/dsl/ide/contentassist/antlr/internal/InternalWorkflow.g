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
grammar InternalWorkflow;

options {
	superClass=AbstractInternalContentAssistParser;
}

@lexer::header {
package org.eclipse.emfcloud.coffee.workflow.dsl.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package org.eclipse.emfcloud.coffee.workflow.dsl.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess;

}
@parser::members {
	private WorkflowGrammarAccess grammarAccess;

	public void setGrammarAccess(WorkflowGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		return tokenName;
	}
}

// Entry rule entryRuleWorkflowConfiguration
entryRuleWorkflowConfiguration
:
{ before(grammarAccess.getWorkflowConfigurationRule()); }
	 ruleWorkflowConfiguration
{ after(grammarAccess.getWorkflowConfigurationRule()); } 
	 EOF 
;

// Rule WorkflowConfiguration
ruleWorkflowConfiguration 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWorkflowConfigurationAccess().getGroup()); }
		(rule__WorkflowConfiguration__Group__0)
		{ after(grammarAccess.getWorkflowConfigurationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAssertion
entryRuleAssertion
:
{ before(grammarAccess.getAssertionRule()); }
	 ruleAssertion
{ after(grammarAccess.getAssertionRule()); } 
	 EOF 
;

// Rule Assertion
ruleAssertion 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAssertionAccess().getGroup()); }
		(rule__Assertion__Group__0)
		{ after(grammarAccess.getAssertionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProbabilityConfiguration
entryRuleProbabilityConfiguration
:
{ before(grammarAccess.getProbabilityConfigurationRule()); }
	 ruleProbabilityConfiguration
{ after(grammarAccess.getProbabilityConfigurationRule()); } 
	 EOF 
;

// Rule ProbabilityConfiguration
ruleProbabilityConfiguration 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProbabilityConfigurationAccess().getGroup()); }
		(rule__ProbabilityConfiguration__Group__0)
		{ after(grammarAccess.getProbabilityConfigurationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFloat
entryRuleFloat
:
{ before(grammarAccess.getFloatRule()); }
	 ruleFloat
{ after(grammarAccess.getFloatRule()); } 
	 EOF 
;

// Rule Float
ruleFloat 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFloatAccess().getGroup()); }
		(rule__Float__Group__0)
		{ after(grammarAccess.getFloatAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__0__Impl
	rule__WorkflowConfiguration__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getMachineKeyword_0()); }
	'machine'
	{ after(grammarAccess.getWorkflowConfigurationAccess().getMachineKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__1__Impl
	rule__WorkflowConfiguration__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getColonKeyword_1()); }
	':'
	{ after(grammarAccess.getWorkflowConfigurationAccess().getColonKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__2__Impl
	rule__WorkflowConfiguration__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getMachineAssignment_2()); }
	(rule__WorkflowConfiguration__MachineAssignment_2)
	{ after(grammarAccess.getWorkflowConfigurationAccess().getMachineAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__3__Impl
	rule__WorkflowConfiguration__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getWorkflowKeyword_3()); }
	'workflow'
	{ after(grammarAccess.getWorkflowConfigurationAccess().getWorkflowKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__4__Impl
	rule__WorkflowConfiguration__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getColonKeyword_4()); }
	':'
	{ after(grammarAccess.getWorkflowConfigurationAccess().getColonKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__5__Impl
	rule__WorkflowConfiguration__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getModelAssignment_5()); }
	(rule__WorkflowConfiguration__ModelAssignment_5)
	{ after(grammarAccess.getWorkflowConfigurationAccess().getModelAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__6__Impl
	rule__WorkflowConfiguration__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getProbConfAssignment_6()); }
	(rule__WorkflowConfiguration__ProbConfAssignment_6)?
	{ after(grammarAccess.getWorkflowConfigurationAccess().getProbConfAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getGroup_7()); }
	(rule__WorkflowConfiguration__Group_7__0)?
	{ after(grammarAccess.getWorkflowConfigurationAccess().getGroup_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WorkflowConfiguration__Group_7__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group_7__0__Impl
	rule__WorkflowConfiguration__Group_7__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getAssertionsKeyword_7_0()); }
	'assertions'
	{ after(grammarAccess.getWorkflowConfigurationAccess().getAssertionsKeyword_7_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group_7__1__Impl
	rule__WorkflowConfiguration__Group_7__2
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssignment_7_1()); }
	(rule__WorkflowConfiguration__AssertionsAssignment_7_1)
	{ after(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssignment_7_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group_7__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getGroup_7_2()); }
	(rule__WorkflowConfiguration__Group_7_2__0)*
	{ after(grammarAccess.getWorkflowConfigurationAccess().getGroup_7_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WorkflowConfiguration__Group_7_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group_7_2__0__Impl
	rule__WorkflowConfiguration__Group_7_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getCommaKeyword_7_2_0()); }
	','
	{ after(grammarAccess.getWorkflowConfigurationAccess().getCommaKeyword_7_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WorkflowConfiguration__Group_7_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__Group_7_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssignment_7_2_1()); }
	(rule__WorkflowConfiguration__AssertionsAssignment_7_2_1)
	{ after(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssignment_7_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Assertion__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Assertion__Group__0__Impl
	rule__Assertion__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAssertionAccess().getBeforeAssignment_0()); }
	(rule__Assertion__BeforeAssignment_0)
	{ after(grammarAccess.getAssertionAccess().getBeforeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Assertion__Group__1__Impl
	rule__Assertion__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAssertionAccess().getEqualsSignGreaterThanSignKeyword_1()); }
	'=>'
	{ after(grammarAccess.getAssertionAccess().getEqualsSignGreaterThanSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Assertion__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAssertionAccess().getAfterAssignment_2()); }
	(rule__Assertion__AfterAssignment_2)
	{ after(grammarAccess.getAssertionAccess().getAfterAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProbabilityConfiguration__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group__0__Impl
	rule__ProbabilityConfiguration__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getGroup_0()); }
	(rule__ProbabilityConfiguration__Group_0__0)
	{ after(grammarAccess.getProbabilityConfigurationAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group__1__Impl
	rule__ProbabilityConfiguration__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getGroup_1()); }
	(rule__ProbabilityConfiguration__Group_1__0)
	{ after(grammarAccess.getProbabilityConfigurationAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getGroup_2()); }
	(rule__ProbabilityConfiguration__Group_2__0)
	{ after(grammarAccess.getProbabilityConfigurationAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProbabilityConfiguration__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_0__0__Impl
	rule__ProbabilityConfiguration__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getProbabilitiesKeyword_0_0()); }
	'probabilities'
	{ after(grammarAccess.getProbabilityConfigurationAccess().getProbabilitiesKeyword_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_0__1__Impl
	rule__ProbabilityConfiguration__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getLowKeyword_0_1()); }
	'low'
	{ after(grammarAccess.getProbabilityConfigurationAccess().getLowKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_0__2__Impl
	rule__ProbabilityConfiguration__Group_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_0_2()); }
	':'
	{ after(grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_0__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getLowAssignment_0_3()); }
	(rule__ProbabilityConfiguration__LowAssignment_0_3)
	{ after(grammarAccess.getProbabilityConfigurationAccess().getLowAssignment_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProbabilityConfiguration__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_1__0__Impl
	rule__ProbabilityConfiguration__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getMediumKeyword_1_0()); }
	'medium'
	{ after(grammarAccess.getProbabilityConfigurationAccess().getMediumKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_1__1__Impl
	rule__ProbabilityConfiguration__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_1_1()); }
	':'
	{ after(grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getMediumAssignment_1_2()); }
	(rule__ProbabilityConfiguration__MediumAssignment_1_2)
	{ after(grammarAccess.getProbabilityConfigurationAccess().getMediumAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProbabilityConfiguration__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_2__0__Impl
	rule__ProbabilityConfiguration__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getHighKeyword_2_0()); }
	'high'
	{ after(grammarAccess.getProbabilityConfigurationAccess().getHighKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_2__1__Impl
	rule__ProbabilityConfiguration__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_2_1()); }
	':'
	{ after(grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProbabilityConfiguration__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProbabilityConfigurationAccess().getHighAssignment_2_2()); }
	(rule__ProbabilityConfiguration__HighAssignment_2_2)
	{ after(grammarAccess.getProbabilityConfigurationAccess().getHighAssignment_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Float__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Float__Group__0__Impl
	rule__Float__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Float__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFloatAccess().getINTTerminalRuleCall_0()); }
	RULE_INT
	{ after(grammarAccess.getFloatAccess().getINTTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Float__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Float__Group__1__Impl
	rule__Float__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Float__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFloatAccess().getFullStopKeyword_1()); }
	'.'
	{ after(grammarAccess.getFloatAccess().getFullStopKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Float__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Float__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Float__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFloatAccess().getINTTerminalRuleCall_2()); }
	RULE_INT
	{ after(grammarAccess.getFloatAccess().getINTTerminalRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WorkflowConfiguration__MachineAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWorkflowConfigurationAccess().getMachineSTRINGTerminalRuleCall_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getWorkflowConfigurationAccess().getMachineSTRINGTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__ModelAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWorkflowConfigurationAccess().getModelSTRINGTerminalRuleCall_5_0()); }
		RULE_STRING
		{ after(grammarAccess.getWorkflowConfigurationAccess().getModelSTRINGTerminalRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__ProbConfAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWorkflowConfigurationAccess().getProbConfProbabilityConfigurationParserRuleCall_6_0()); }
		ruleProbabilityConfiguration
		{ after(grammarAccess.getWorkflowConfigurationAccess().getProbConfProbabilityConfigurationParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__AssertionsAssignment_7_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssertionParserRuleCall_7_1_0()); }
		ruleAssertion
		{ after(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssertionParserRuleCall_7_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WorkflowConfiguration__AssertionsAssignment_7_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssertionParserRuleCall_7_2_1_0()); }
		ruleAssertion
		{ after(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssertionParserRuleCall_7_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__BeforeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAssertionAccess().getBeforeSTRINGTerminalRuleCall_0_0()); }
		RULE_STRING
		{ after(grammarAccess.getAssertionAccess().getBeforeSTRINGTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__AfterAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAssertionAccess().getAfterSTRINGTerminalRuleCall_2_0()); }
		RULE_STRING
		{ after(grammarAccess.getAssertionAccess().getAfterSTRINGTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__LowAssignment_0_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProbabilityConfigurationAccess().getLowFloatParserRuleCall_0_3_0()); }
		ruleFloat
		{ after(grammarAccess.getProbabilityConfigurationAccess().getLowFloatParserRuleCall_0_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__MediumAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProbabilityConfigurationAccess().getMediumFloatParserRuleCall_1_2_0()); }
		ruleFloat
		{ after(grammarAccess.getProbabilityConfigurationAccess().getMediumFloatParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProbabilityConfiguration__HighAssignment_2_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProbabilityConfigurationAccess().getHighFloatParserRuleCall_2_2_0()); }
		ruleFloat
		{ after(grammarAccess.getProbabilityConfigurationAccess().getHighFloatParserRuleCall_2_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
