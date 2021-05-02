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
import org.eclipse.emfcloud.coffee.ManualTask
import org.eclipse.emfcloud.coffee.AutomaticTask
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class TaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}

	def String toFileName(String packageName, String taskName) {
		'''«sourceDirectory»/«FileUtil.getFilePath(packageName)»Abstract«FileUtil.getJavaFileName(taskName)»'''
	}

	def String toFileContent(String packageName, String sourceFileName, Task task) {
		'''	
			// auto-generated from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName»;
			
			«IF task instanceof ManualTask»
				import «packageName».library.ManualWorkflowTask;
					
				public abstract class Abstract«FileUtil.normalize(task.name)» extends ManualWorkflowTask {
			«ELSE»
				import «packageName».library.AutomaticWorkflowTask;
					
				public abstract class Abstract«FileUtil.normalize(task.name)» extends AutomaticWorkflowTask {
			«ENDIF»
				@Override
				«IF task instanceof ManualTask»
				public String getActor() {
					return "«(task as ManualTask).actor»";
				}
				«ELSEIF task instanceof AutomaticTask»
				public String getComponent() {
					return "«(task as AutomaticTask).component»";
				}
				«ENDIF»
			
			
				@Override
				public int getDuration() {
					return «task.duration»;
				}
			
			}
		'''
	}
}
