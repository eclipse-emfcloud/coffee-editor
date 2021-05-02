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
package org.eclipse.emfcloud.coffee.workflow.generator.java

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.eclipse.emfcloud.coffee.Task
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class UserTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
		
	def String toFileContent(String packageName, String sourceFileName, Task task) {
		'''	
		// auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
		package «packageName»;
			
		import «packageName».Abstract«FileUtil.normalize(task.name)»;
				
		public class «FileUtil.normalize(task.name)» extends Abstract«FileUtil.normalize(task.name)» {
		
			@Override
			public void preExecute() {
				// TODO Add custom implementation here
		
			}
		
			@Override
			public void postExecute() {
				// TODO Add custom implementation here
		
			}
		
		}
		'''
	}
	
	def String toFileName(String packageName, String taskName) {
		'''«sourceDirectory»/«FileUtil.getFilePath(packageName)»«FileUtil.getJavaFileName(taskName)»'''
	}
}
