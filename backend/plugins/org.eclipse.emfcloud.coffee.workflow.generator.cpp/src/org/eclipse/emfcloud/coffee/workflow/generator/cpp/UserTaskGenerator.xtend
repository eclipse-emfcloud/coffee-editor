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
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class UserTaskGenerator {
	String sourceDirectory
	String includeDirectory
	
	new(String sourceDirectory, String includeDirectory) {
		this.sourceDirectory = sourceDirectory
		this.includeDirectory = includeDirectory
	}

	def String toHeaderFileName(String taskName) {
		'''«includeDirectory»/«FileUtil.getCppHeaderFileName(taskName)»'''
	}

	def String toHeaderFileContent(String sourceFileName, Task task) {
		'''
			// auto-generated from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			
			#include "Abstract«FileUtil.normalize(task.name)».h"
			
			class «FileUtil.normalize(task.name)»: public Abstract«FileUtil.normalize(task.name)» {
				public:
			        void preExecute();	
			        void postExecute();
			};
		'''
	}

	def String toClassFileName(String taskName) {
		'''«sourceDirectory»/«FileUtil.getCppClassFileName(taskName)»'''
	}

	def String toClassFileContent(String sourceFileName, Task task) {
		'''
			// auto-generated from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»

			#include "«FileUtil.normalize(task.name)».h"
			
			void «FileUtil.normalize(task.name)»::preExecute() {
				// TODO Add custom implementation here
			}
			
			void «FileUtil.normalize(task.name)»::postExecute() {
				// TODO Add custom implementation here
			}
		'''
	}
}
