/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.generator.cpp

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class ManualWorkflowTaskGenerator {
	String includeDirectory
	
	new(String includeDirectory) {
		this.includeDirectory = includeDirectory
	}
	
	def String toHeaderFileName() {
		'''«includeDirectory»/«FileUtil.getCppHeaderFileName("ManualWorkflowTask")»'''
	}

	def String toHeaderFileContent() {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			#include <string>
			#include "WorkflowTask.h"
			
			#ifndef _ManualWorkflowTask_
			#define _ManualWorkflowTask_
			
			class ManualWorkflowTask: public WorkflowTask {
				public:
					std::string getActor();
			};
			
			#endif
		'''
	}
}
