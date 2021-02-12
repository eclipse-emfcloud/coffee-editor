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
	superClass=AbstractInternalAntlrParser;
}

@lexer::header {
package org.eclipse.emfcloud.coffee.workflow.dsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.eclipse.emfcloud.coffee.workflow.dsl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess;

}

@parser::members {

 	private WorkflowGrammarAccess grammarAccess;

    public InternalWorkflowParser(TokenStream input, WorkflowGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "WorkflowConfiguration";
   	}

   	@Override
   	protected WorkflowGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleWorkflowConfiguration
entryRuleWorkflowConfiguration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWorkflowConfigurationRule()); }
	iv_ruleWorkflowConfiguration=ruleWorkflowConfiguration
	{ $current=$iv_ruleWorkflowConfiguration.current; }
	EOF;

// Rule WorkflowConfiguration
ruleWorkflowConfiguration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='machine'
		{
			newLeafNode(otherlv_0, grammarAccess.getWorkflowConfigurationAccess().getMachineKeyword_0());
		}
		otherlv_1=':'
		{
			newLeafNode(otherlv_1, grammarAccess.getWorkflowConfigurationAccess().getColonKeyword_1());
		}
		(
			(
				lv_machine_2_0=RULE_STRING
				{
					newLeafNode(lv_machine_2_0, grammarAccess.getWorkflowConfigurationAccess().getMachineSTRINGTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getWorkflowConfigurationRule());
					}
					setWithLastConsumed(
						$current,
						"machine",
						lv_machine_2_0,
						"org.eclipse.xtext.common.Terminals.STRING");
				}
			)
		)
		otherlv_3='workflow'
		{
			newLeafNode(otherlv_3, grammarAccess.getWorkflowConfigurationAccess().getWorkflowKeyword_3());
		}
		otherlv_4=':'
		{
			newLeafNode(otherlv_4, grammarAccess.getWorkflowConfigurationAccess().getColonKeyword_4());
		}
		(
			(
				lv_model_5_0=RULE_STRING
				{
					newLeafNode(lv_model_5_0, grammarAccess.getWorkflowConfigurationAccess().getModelSTRINGTerminalRuleCall_5_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getWorkflowConfigurationRule());
					}
					setWithLastConsumed(
						$current,
						"model",
						lv_model_5_0,
						"org.eclipse.xtext.common.Terminals.STRING");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getWorkflowConfigurationAccess().getProbConfProbabilityConfigurationParserRuleCall_6_0());
				}
				lv_probConf_6_0=ruleProbabilityConfiguration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWorkflowConfigurationRule());
					}
					set(
						$current,
						"probConf",
						lv_probConf_6_0,
						"org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.ProbabilityConfiguration");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			otherlv_7='assertions'
			{
				newLeafNode(otherlv_7, grammarAccess.getWorkflowConfigurationAccess().getAssertionsKeyword_7_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssertionParserRuleCall_7_1_0());
					}
					lv_assertions_8_0=ruleAssertion
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getWorkflowConfigurationRule());
						}
						add(
							$current,
							"assertions",
							lv_assertions_8_0,
							"org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.Assertion");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_9=','
				{
					newLeafNode(otherlv_9, grammarAccess.getWorkflowConfigurationAccess().getCommaKeyword_7_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssertionParserRuleCall_7_2_1_0());
						}
						lv_assertions_10_0=ruleAssertion
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getWorkflowConfigurationRule());
							}
							add(
								$current,
								"assertions",
								lv_assertions_10_0,
								"org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.Assertion");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
	)
;

// Entry rule entryRuleAssertion
entryRuleAssertion returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAssertionRule()); }
	iv_ruleAssertion=ruleAssertion
	{ $current=$iv_ruleAssertion.current; }
	EOF;

// Rule Assertion
ruleAssertion returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_before_0_0=RULE_STRING
				{
					newLeafNode(lv_before_0_0, grammarAccess.getAssertionAccess().getBeforeSTRINGTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAssertionRule());
					}
					setWithLastConsumed(
						$current,
						"before",
						lv_before_0_0,
						"org.eclipse.xtext.common.Terminals.STRING");
				}
			)
		)
		otherlv_1='=>'
		{
			newLeafNode(otherlv_1, grammarAccess.getAssertionAccess().getEqualsSignGreaterThanSignKeyword_1());
		}
		(
			(
				lv_after_2_0=RULE_STRING
				{
					newLeafNode(lv_after_2_0, grammarAccess.getAssertionAccess().getAfterSTRINGTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAssertionRule());
					}
					setWithLastConsumed(
						$current,
						"after",
						lv_after_2_0,
						"org.eclipse.xtext.common.Terminals.STRING");
				}
			)
		)
	)
;

// Entry rule entryRuleProbabilityConfiguration
entryRuleProbabilityConfiguration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProbabilityConfigurationRule()); }
	iv_ruleProbabilityConfiguration=ruleProbabilityConfiguration
	{ $current=$iv_ruleProbabilityConfiguration.current; }
	EOF;

// Rule ProbabilityConfiguration
ruleProbabilityConfiguration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0='probabilities'
			{
				newLeafNode(otherlv_0, grammarAccess.getProbabilityConfigurationAccess().getProbabilitiesKeyword_0_0());
			}
			otherlv_1='low'
			{
				newLeafNode(otherlv_1, grammarAccess.getProbabilityConfigurationAccess().getLowKeyword_0_1());
			}
			otherlv_2=':'
			{
				newLeafNode(otherlv_2, grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_0_2());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getProbabilityConfigurationAccess().getLowFloatParserRuleCall_0_3_0());
					}
					lv_low_3_0=ruleFloat
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getProbabilityConfigurationRule());
						}
						set(
							$current,
							"low",
							lv_low_3_0,
							"org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.Float");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		(
			otherlv_4='medium'
			{
				newLeafNode(otherlv_4, grammarAccess.getProbabilityConfigurationAccess().getMediumKeyword_1_0());
			}
			otherlv_5=':'
			{
				newLeafNode(otherlv_5, grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getProbabilityConfigurationAccess().getMediumFloatParserRuleCall_1_2_0());
					}
					lv_medium_6_0=ruleFloat
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getProbabilityConfigurationRule());
						}
						set(
							$current,
							"medium",
							lv_medium_6_0,
							"org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.Float");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		(
			otherlv_7='high'
			{
				newLeafNode(otherlv_7, grammarAccess.getProbabilityConfigurationAccess().getHighKeyword_2_0());
			}
			otherlv_8=':'
			{
				newLeafNode(otherlv_8, grammarAccess.getProbabilityConfigurationAccess().getColonKeyword_2_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getProbabilityConfigurationAccess().getHighFloatParserRuleCall_2_2_0());
					}
					lv_high_9_0=ruleFloat
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getProbabilityConfigurationRule());
						}
						set(
							$current,
							"high",
							lv_high_9_0,
							"org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.Float");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleFloat
entryRuleFloat returns [String current=null]:
	{ newCompositeNode(grammarAccess.getFloatRule()); }
	iv_ruleFloat=ruleFloat
	{ $current=$iv_ruleFloat.current.getText(); }
	EOF;

// Rule Float
ruleFloat returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_INT_0=RULE_INT
		{
			$current.merge(this_INT_0);
		}
		{
			newLeafNode(this_INT_0, grammarAccess.getFloatAccess().getINTTerminalRuleCall_0());
		}
		kw='.'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getFloatAccess().getFullStopKeyword_1());
		}
		this_INT_2=RULE_INT
		{
			$current.merge(this_INT_2);
		}
		{
			newLeafNode(this_INT_2, grammarAccess.getFloatAccess().getINTTerminalRuleCall_2());
		}
	)
;

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
