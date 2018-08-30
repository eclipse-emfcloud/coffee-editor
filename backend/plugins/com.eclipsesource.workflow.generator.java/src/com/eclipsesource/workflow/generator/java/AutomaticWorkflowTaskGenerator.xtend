package com.eclipsesource.workflow.generator.java

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AutomaticWorkflowTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
		
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»library/«JavaUtil.getJavaFileName("AutomaticWorkflowTask")»'''
	}
	
	def String toFileContent(String packageName) {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».library;
			
			public abstract class AutomaticWorkflowTask extends WorkflowTask {
				public abstract String getComponent();
			}
		'''
	}
}
