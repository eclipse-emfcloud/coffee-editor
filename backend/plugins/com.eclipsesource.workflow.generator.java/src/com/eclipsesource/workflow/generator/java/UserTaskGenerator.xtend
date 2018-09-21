package com.eclipsesource.workflow.generator.java

import com.eclipsesource.workflow.IWorkflowTask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
		
	def String toFileContent(String packageName, String sourceFileName, IWorkflowTask task) {
		'''	
		// auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
		package «packageName»;
			
		import «packageName».Abstract«JavaUtil.normalize(task.name)»;
				
		public class «JavaUtil.normalize(task.name)» extends Abstract«JavaUtil.normalize(task.name)» {
		
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
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»«JavaUtil.getJavaFileName(taskName)»'''
	}
}
