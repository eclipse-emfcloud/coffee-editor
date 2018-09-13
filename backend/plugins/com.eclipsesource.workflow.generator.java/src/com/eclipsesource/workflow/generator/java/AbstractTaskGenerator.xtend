package com.eclipsesource.workflow.generator.java

import com.eclipsesource.workflow.IAutomaticWorkflowTask
import com.eclipsesource.workflow.IManualWorkflowTask
import com.eclipsesource.workflow.IWorkflowTask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AbstractTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}

	def String toFileName(String packageName, String taskName) {
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»Abstract«JavaUtil.getJavaFileName(taskName)»'''
	}

	def String toFileContent(String packageName, String sourceFileName, IWorkflowTask task) {
		'''	
			// auto-generated from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName»;
			
			«IF task.manual»
				import «packageName».library.ManualWorkflowTask;
					
				public abstract class Abstract«JavaUtil.normalize(task.name)» extends ManualWorkflowTask {
			«ELSE»
				import «packageName».library.AutomaticWorkflowTask;
					
				public abstract class Abstract«JavaUtil.normalize(task.name)» extends AutomaticWorkflowTask {
			«ENDIF»
				@Override
				«IF task.manual»
				public String getActor() {
					return "«(task as IManualWorkflowTask).actor»";
				}
				«ELSE»
				public String getComponent() {
					return "«(task as IAutomaticWorkflowTask).component»";
				}
				«ENDIF»
			
			
				@Override
				public int getDuration() {
					return «task.duration»;
				}
			
				@Override
				public String getId() {
					return "«task.id»";
				}
			
			}
		'''
	}
}
