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
import org.eclipse.emfcloud.coffee.Task
import org.eclipse.emfcloud.coffee.ManualTask
import org.eclipse.emfcloud.coffee.AutomaticTask
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class TaskGenerator {
	String includeDirectory
	
	new(String includeDirectory) {
		this.includeDirectory = includeDirectory
	}
	
	def String toHeaderFileName(String taskName) {
		'''«includeDirectory»/Abstract«FileUtil.getCppHeaderFileName(taskName)»'''
	}

	def String toHeaderFileContent(String sourceFileName, Task task) {
		'''
			// auto-generated from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			#include <string>
			«IF task instanceof ManualTask»
				#include "ManualWorkflowTask.h"
					
				class Abstract«FileUtil.normalize(task.name)»: public ManualWorkflowTask {
			«ELSE»
				#include "AutomaticWorkflowTask.h"
					
				class Abstract«FileUtil.normalize(task.name)»: public AutomaticWorkflowTask {
			«ENDIF»
				public:
					«IF task instanceof ManualTask»
					std::string getActor() {
						return "«(task as ManualTask).actor»";
					}
					«ELSEIF task instanceof AutomaticTask»
					std::string getComponent() {
						return "«(task as AutomaticTask).component»";
					}
					«ENDIF»
			
					int getDuration() {
						return «task.duration»;
					}
			};
		'''
	}
}
