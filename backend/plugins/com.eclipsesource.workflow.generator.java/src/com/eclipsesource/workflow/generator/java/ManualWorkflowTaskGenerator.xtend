package com.eclipsesource.workflow.generator.java

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ManualWorkflowTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
	
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»library/«JavaUtil.getJavaFileName("ManualWorkflowTask")»'''
	}

	def String toFileContent(String packageName) {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».library;
			
			public abstract class ManualWorkflowTask extends WorkflowTask {
				public abstract String getActor();
			}
		'''
	}
}
