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
package org.eclipse.emfcloud.coffee.workflow.dsl.formatting2

import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration
import com.google.inject.Inject
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument

class WorkflowFormatter extends AbstractFormatter2 {

	@Inject extension WorkflowGrammarAccess access

	def dispatch void format(WorkflowConfiguration wfConfig, extension IFormattableDocument document) {
		wfConfig.regionFor.keyword(":").prepend[noSpace].append[oneSpace]
		wfConfig.regionFor.keyword(",").append[newLine]
		wfConfig.probConf.format
		wfConfig.probConf.interior[indent]
		wfConfig.assertions.head.interior[indent]

		wfConfig.regionFor.keyword("assertions").prepend[newLines = 2].append[newLine]
		wfConfig.assertions.head.prepend[indent]
		wfConfig.assertions.last.append[indent]

	}

	def dispatch void format(ProbabilityConfiguration probConf, extension IFormattableDocument document) {
		probConf.prepend[newLines=2]
		probConf.regionFor.keyword("low").prepend[newLine]
		probConf.regionFor.keyword("medium").prepend[newLine]
		probConf.regionFor.keyword("high").prepend[newLine]
	}

	def dispatch void format(Assertion assert, extension IFormattableDocument document) {
//		assert.before.append[newLine]
//		assert.after.prepend[newLine]
	}
}
