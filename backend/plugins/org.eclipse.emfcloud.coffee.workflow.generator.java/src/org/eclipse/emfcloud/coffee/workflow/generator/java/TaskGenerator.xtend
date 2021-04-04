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
import org.eclipse.emfcloud.coffee.MenuSelectionTask

class TaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}

	def String toFileName(String packageName, String taskName) {
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»Abstract«JavaUtil.getJavaFileName(taskName)»'''
	}

	def String toFileContent(String packageName, String sourceFileName, Task task) {
		'''	
			// auto-generated from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName»;
			
			«task.classHeader(packageName)»
			
				«task.specificMethods»
			
				@Override
				public int getDuration() {
					return «task.duration»;
				}
			
			}
		'''
	}
	
	protected def dispatch String classHeader(ManualTask task, String packageName) '''
		import «packageName».library.ManualWorkflowTask;
		
		public abstract class Abstract«JavaUtil.normalize(task.name)» extends ManualWorkflowTask {
	'''
		
	protected def dispatch String classHeader(MenuSelectionTask task, String packageName) '''
		import java.util.List;
		import «packageName».library.MenuSelectionWorkflowTask;
		
		public abstract class Abstract«JavaUtil.normalize(task.name)» extends MenuSelectionWorkflowTask {
	'''
	
	protected def dispatch String classHeader(AutomaticTask task, String packageName) '''
		import «packageName».library.AutomaticWorkflowTask;
		
		public abstract class Abstract«JavaUtil.normalize(task.name)» extends AutomaticWorkflowTask {
	'''
	
	protected def dispatch String specificMethods(ManualTask task) {
		task.manualTaskMethods
	}

	private def String manualTaskMethods(ManualTask task) '''
		public String getActor() {
			return "«task.actor»";
		}
	'''
	
	protected def dispatch String specificMethods(MenuSelectionTask task) '''
		public List<String> getMenu() {
			return List.of(«FOR item : task.menu SEPARATOR ", "»"«item»"«ENDFOR»);
		}
		
		public int getTimeout() {
			«IF task.isSetTimeout»
				return «task.timeout»;
			«ELSE»
				return 0;
			«ENDIF»
		}
		
		«task.manualTaskMethods»
	'''
	
	protected def dispatch String specificMethods(AutomaticTask task) '''
		public String getComponent() {
			return "«task.component»";
		}
	'''
	
}
