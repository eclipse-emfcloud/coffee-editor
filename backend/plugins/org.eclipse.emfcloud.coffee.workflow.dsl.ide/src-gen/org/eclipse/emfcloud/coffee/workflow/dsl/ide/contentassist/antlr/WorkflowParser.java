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
package org.eclipse.emfcloud.coffee.workflow.dsl.ide.contentassist.antlr;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.emfcloud.coffee.workflow.dsl.ide.contentassist.antlr.internal.InternalWorkflowParser;
import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class WorkflowParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(WorkflowGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, WorkflowGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getGroup(), "rule__WorkflowConfiguration__Group__0");
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getGroup_7(), "rule__WorkflowConfiguration__Group_7__0");
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getGroup_7_2(), "rule__WorkflowConfiguration__Group_7_2__0");
			builder.put(grammarAccess.getAssertionAccess().getGroup(), "rule__Assertion__Group__0");
			builder.put(grammarAccess.getProbabilityConfigurationAccess().getGroup(), "rule__ProbabilityConfiguration__Group__0");
			builder.put(grammarAccess.getProbabilityConfigurationAccess().getGroup_0(), "rule__ProbabilityConfiguration__Group_0__0");
			builder.put(grammarAccess.getProbabilityConfigurationAccess().getGroup_1(), "rule__ProbabilityConfiguration__Group_1__0");
			builder.put(grammarAccess.getProbabilityConfigurationAccess().getGroup_2(), "rule__ProbabilityConfiguration__Group_2__0");
			builder.put(grammarAccess.getFloatAccess().getGroup(), "rule__Float__Group__0");
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getMachineAssignment_2(), "rule__WorkflowConfiguration__MachineAssignment_2");
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getModelAssignment_5(), "rule__WorkflowConfiguration__ModelAssignment_5");
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getProbConfAssignment_6(), "rule__WorkflowConfiguration__ProbConfAssignment_6");
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssignment_7_1(), "rule__WorkflowConfiguration__AssertionsAssignment_7_1");
			builder.put(grammarAccess.getWorkflowConfigurationAccess().getAssertionsAssignment_7_2_1(), "rule__WorkflowConfiguration__AssertionsAssignment_7_2_1");
			builder.put(grammarAccess.getAssertionAccess().getBeforeAssignment_0(), "rule__Assertion__BeforeAssignment_0");
			builder.put(grammarAccess.getAssertionAccess().getAfterAssignment_2(), "rule__Assertion__AfterAssignment_2");
			builder.put(grammarAccess.getProbabilityConfigurationAccess().getLowAssignment_0_3(), "rule__ProbabilityConfiguration__LowAssignment_0_3");
			builder.put(grammarAccess.getProbabilityConfigurationAccess().getMediumAssignment_1_2(), "rule__ProbabilityConfiguration__MediumAssignment_1_2");
			builder.put(grammarAccess.getProbabilityConfigurationAccess().getHighAssignment_2_2(), "rule__ProbabilityConfiguration__HighAssignment_2_2");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private WorkflowGrammarAccess grammarAccess;

	@Override
	protected InternalWorkflowParser createParser() {
		InternalWorkflowParser result = new InternalWorkflowParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public WorkflowGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(WorkflowGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}
