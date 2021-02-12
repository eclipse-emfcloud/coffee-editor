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
package org.eclipse.emfcloud.coffee.workflow.dsl.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.emfcloud.coffee.workflow.dsl.parser.antlr.internal.InternalWorkflowParser;
import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class WorkflowParser extends AbstractAntlrParser {

	@Inject
	private WorkflowGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalWorkflowParser createParser(XtextTokenStream stream) {
		return new InternalWorkflowParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "WorkflowConfiguration";
	}

	public WorkflowGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(WorkflowGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
