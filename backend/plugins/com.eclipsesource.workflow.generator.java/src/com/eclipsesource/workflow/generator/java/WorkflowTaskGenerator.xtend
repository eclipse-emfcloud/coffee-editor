package com.eclipsesource.workflow.generator.java

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WorkflowTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
	
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»library/«JavaUtil.getJavaFileName("WorkflowTask")»'''
	}

	def String toFileContent(String packageName) {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».library;
			
			public abstract class WorkflowTask {
			
				private boolean finished;
			
				public abstract int getDuration();
			
				public abstract String getId();
			
				public void doExecute() throws InterruptedException {
					preExecute();
					execute();
					postExecute();
				}
			
				public void execute() throws InterruptedException {
					Thread.sleep(getDuration());
					finished = true;
				}
			
				public abstract void preExecute();
			
				public abstract void postExecute();
			
				public boolean hasFinished() {
					return finished;
				}
			
			}
		'''
	}
}
