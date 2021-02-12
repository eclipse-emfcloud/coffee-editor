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
package org.eclipse.emfcloud.coffee.workflow.dsl.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class WorkflowGrammarAccess extends AbstractElementFinder.AbstractGrammarElementFinder {
	
	public class WorkflowConfigurationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.WorkflowConfiguration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cMachineKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cColonKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cMachineAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cMachineSTRINGTerminalRuleCall_2_0 = (RuleCall)cMachineAssignment_2.eContents().get(0);
		private final Keyword cWorkflowKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Keyword cColonKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cModelAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cModelSTRINGTerminalRuleCall_5_0 = (RuleCall)cModelAssignment_5.eContents().get(0);
		private final Assignment cProbConfAssignment_6 = (Assignment)cGroup.eContents().get(6);
		private final RuleCall cProbConfProbabilityConfigurationParserRuleCall_6_0 = (RuleCall)cProbConfAssignment_6.eContents().get(0);
		private final Group cGroup_7 = (Group)cGroup.eContents().get(7);
		private final Keyword cAssertionsKeyword_7_0 = (Keyword)cGroup_7.eContents().get(0);
		private final Assignment cAssertionsAssignment_7_1 = (Assignment)cGroup_7.eContents().get(1);
		private final RuleCall cAssertionsAssertionParserRuleCall_7_1_0 = (RuleCall)cAssertionsAssignment_7_1.eContents().get(0);
		private final Group cGroup_7_2 = (Group)cGroup_7.eContents().get(2);
		private final Keyword cCommaKeyword_7_2_0 = (Keyword)cGroup_7_2.eContents().get(0);
		private final Assignment cAssertionsAssignment_7_2_1 = (Assignment)cGroup_7_2.eContents().get(1);
		private final RuleCall cAssertionsAssertionParserRuleCall_7_2_1_0 = (RuleCall)cAssertionsAssignment_7_2_1.eContents().get(0);
		
		//WorkflowConfiguration:
		//	'machine' ':' machine=STRING
		//	'workflow' ':' model=STRING
		//	probConf=ProbabilityConfiguration? ('assertions' assertions+=Assertion (',' assertions+=Assertion)*)?;
		@Override public ParserRule getRule() { return rule; }
		
		//'machine' ':' machine=STRING 'workflow' ':' model=STRING probConf=ProbabilityConfiguration? ('assertions'
		//assertions+=Assertion (',' assertions+=Assertion)*)?
		public Group getGroup() { return cGroup; }
		
		//'machine'
		public Keyword getMachineKeyword_0() { return cMachineKeyword_0; }
		
		//':'
		public Keyword getColonKeyword_1() { return cColonKeyword_1; }
		
		//machine=STRING
		public Assignment getMachineAssignment_2() { return cMachineAssignment_2; }
		
		//STRING
		public RuleCall getMachineSTRINGTerminalRuleCall_2_0() { return cMachineSTRINGTerminalRuleCall_2_0; }
		
		//'workflow'
		public Keyword getWorkflowKeyword_3() { return cWorkflowKeyword_3; }
		
		//':'
		public Keyword getColonKeyword_4() { return cColonKeyword_4; }
		
		//model=STRING
		public Assignment getModelAssignment_5() { return cModelAssignment_5; }
		
		//STRING
		public RuleCall getModelSTRINGTerminalRuleCall_5_0() { return cModelSTRINGTerminalRuleCall_5_0; }
		
		//probConf=ProbabilityConfiguration?
		public Assignment getProbConfAssignment_6() { return cProbConfAssignment_6; }
		
		//ProbabilityConfiguration
		public RuleCall getProbConfProbabilityConfigurationParserRuleCall_6_0() { return cProbConfProbabilityConfigurationParserRuleCall_6_0; }
		
		//('assertions' assertions+=Assertion (',' assertions+=Assertion)*)?
		public Group getGroup_7() { return cGroup_7; }
		
		//'assertions'
		public Keyword getAssertionsKeyword_7_0() { return cAssertionsKeyword_7_0; }
		
		//assertions+=Assertion
		public Assignment getAssertionsAssignment_7_1() { return cAssertionsAssignment_7_1; }
		
		//Assertion
		public RuleCall getAssertionsAssertionParserRuleCall_7_1_0() { return cAssertionsAssertionParserRuleCall_7_1_0; }
		
		//(',' assertions+=Assertion)*
		public Group getGroup_7_2() { return cGroup_7_2; }
		
		//','
		public Keyword getCommaKeyword_7_2_0() { return cCommaKeyword_7_2_0; }
		
		//assertions+=Assertion
		public Assignment getAssertionsAssignment_7_2_1() { return cAssertionsAssignment_7_2_1; }
		
		//Assertion
		public RuleCall getAssertionsAssertionParserRuleCall_7_2_1_0() { return cAssertionsAssertionParserRuleCall_7_2_1_0; }
	}
	public class AssertionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.Assertion");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cBeforeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cBeforeSTRINGTerminalRuleCall_0_0 = (RuleCall)cBeforeAssignment_0.eContents().get(0);
		private final Keyword cEqualsSignGreaterThanSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cAfterAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cAfterSTRINGTerminalRuleCall_2_0 = (RuleCall)cAfterAssignment_2.eContents().get(0);
		
		//Assertion:
		//	before=STRING '=>' after=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//before=STRING '=>' after=STRING
		public Group getGroup() { return cGroup; }
		
		//before=STRING
		public Assignment getBeforeAssignment_0() { return cBeforeAssignment_0; }
		
		//STRING
		public RuleCall getBeforeSTRINGTerminalRuleCall_0_0() { return cBeforeSTRINGTerminalRuleCall_0_0; }
		
		//'=>'
		public Keyword getEqualsSignGreaterThanSignKeyword_1() { return cEqualsSignGreaterThanSignKeyword_1; }
		
		//after=STRING
		public Assignment getAfterAssignment_2() { return cAfterAssignment_2; }
		
		//STRING
		public RuleCall getAfterSTRINGTerminalRuleCall_2_0() { return cAfterSTRINGTerminalRuleCall_2_0; }
	}
	public class ProbabilityConfigurationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.ProbabilityConfiguration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Keyword cProbabilitiesKeyword_0_0 = (Keyword)cGroup_0.eContents().get(0);
		private final Keyword cLowKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Keyword cColonKeyword_0_2 = (Keyword)cGroup_0.eContents().get(2);
		private final Assignment cLowAssignment_0_3 = (Assignment)cGroup_0.eContents().get(3);
		private final RuleCall cLowFloatParserRuleCall_0_3_0 = (RuleCall)cLowAssignment_0_3.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cMediumKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Keyword cColonKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		private final Assignment cMediumAssignment_1_2 = (Assignment)cGroup_1.eContents().get(2);
		private final RuleCall cMediumFloatParserRuleCall_1_2_0 = (RuleCall)cMediumAssignment_1_2.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cHighKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Keyword cColonKeyword_2_1 = (Keyword)cGroup_2.eContents().get(1);
		private final Assignment cHighAssignment_2_2 = (Assignment)cGroup_2.eContents().get(2);
		private final RuleCall cHighFloatParserRuleCall_2_2_0 = (RuleCall)cHighAssignment_2_2.eContents().get(0);
		
		//ProbabilityConfiguration:
		//	('probabilities' 'low' ':' low=Float) ('medium' ':' medium=Float) ('high' ':' high=Float);
		@Override public ParserRule getRule() { return rule; }
		
		//('probabilities' 'low' ':' low=Float) ('medium' ':' medium=Float) ('high' ':' high=Float)
		public Group getGroup() { return cGroup; }
		
		//('probabilities' 'low' ':' low=Float)
		public Group getGroup_0() { return cGroup_0; }
		
		//'probabilities'
		public Keyword getProbabilitiesKeyword_0_0() { return cProbabilitiesKeyword_0_0; }
		
		//'low'
		public Keyword getLowKeyword_0_1() { return cLowKeyword_0_1; }
		
		//':'
		public Keyword getColonKeyword_0_2() { return cColonKeyword_0_2; }
		
		//low=Float
		public Assignment getLowAssignment_0_3() { return cLowAssignment_0_3; }
		
		//Float
		public RuleCall getLowFloatParserRuleCall_0_3_0() { return cLowFloatParserRuleCall_0_3_0; }
		
		//('medium' ':' medium=Float)
		public Group getGroup_1() { return cGroup_1; }
		
		//'medium'
		public Keyword getMediumKeyword_1_0() { return cMediumKeyword_1_0; }
		
		//':'
		public Keyword getColonKeyword_1_1() { return cColonKeyword_1_1; }
		
		//medium=Float
		public Assignment getMediumAssignment_1_2() { return cMediumAssignment_1_2; }
		
		//Float
		public RuleCall getMediumFloatParserRuleCall_1_2_0() { return cMediumFloatParserRuleCall_1_2_0; }
		
		//('high' ':' high=Float)
		public Group getGroup_2() { return cGroup_2; }
		
		//'high'
		public Keyword getHighKeyword_2_0() { return cHighKeyword_2_0; }
		
		//':'
		public Keyword getColonKeyword_2_1() { return cColonKeyword_2_1; }
		
		//high=Float
		public Assignment getHighAssignment_2_2() { return cHighAssignment_2_2; }
		
		//Float
		public RuleCall getHighFloatParserRuleCall_2_2_0() { return cHighFloatParserRuleCall_2_2_0; }
	}
	public class FloatElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.emfcloud.coffee.workflow.dsl.Workflow.Float");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cFullStopKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		//Float ecore::EFloat:
		//	INT '.' INT;
		@Override public ParserRule getRule() { return rule; }
		
		//INT '.' INT
		public Group getGroup() { return cGroup; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_0() { return cINTTerminalRuleCall_0; }
		
		//'.'
		public Keyword getFullStopKeyword_1() { return cFullStopKeyword_1; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_2() { return cINTTerminalRuleCall_2; }
	}
	
	
	private final WorkflowConfigurationElements pWorkflowConfiguration;
	private final AssertionElements pAssertion;
	private final ProbabilityConfigurationElements pProbabilityConfiguration;
	private final FloatElements pFloat;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public WorkflowGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pWorkflowConfiguration = new WorkflowConfigurationElements();
		this.pAssertion = new AssertionElements();
		this.pProbabilityConfiguration = new ProbabilityConfigurationElements();
		this.pFloat = new FloatElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.emfcloud.coffee.workflow.dsl.Workflow".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//WorkflowConfiguration:
	//	'machine' ':' machine=STRING
	//	'workflow' ':' model=STRING
	//	probConf=ProbabilityConfiguration? ('assertions' assertions+=Assertion (',' assertions+=Assertion)*)?;
	public WorkflowConfigurationElements getWorkflowConfigurationAccess() {
		return pWorkflowConfiguration;
	}
	
	public ParserRule getWorkflowConfigurationRule() {
		return getWorkflowConfigurationAccess().getRule();
	}
	
	//Assertion:
	//	before=STRING '=>' after=STRING;
	public AssertionElements getAssertionAccess() {
		return pAssertion;
	}
	
	public ParserRule getAssertionRule() {
		return getAssertionAccess().getRule();
	}
	
	//ProbabilityConfiguration:
	//	('probabilities' 'low' ':' low=Float) ('medium' ':' medium=Float) ('high' ':' high=Float);
	public ProbabilityConfigurationElements getProbabilityConfigurationAccess() {
		return pProbabilityConfiguration;
	}
	
	public ParserRule getProbabilityConfigurationRule() {
		return getProbabilityConfigurationAccess().getRule();
	}
	
	//Float ecore::EFloat:
	//	INT '.' INT;
	public FloatElements getFloatAccess() {
		return pFloat;
	}
	
	public ParserRule getFloatRule() {
		return getFloatAccess().getRule();
	}
	
	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' |
	//	"'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
