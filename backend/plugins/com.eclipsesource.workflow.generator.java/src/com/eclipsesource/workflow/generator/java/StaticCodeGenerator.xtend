package com.eclipsesource.workflow.generator.java

import com.eclipsesource.workflow.generator.IAutomaticWorkflowTask
import com.eclipsesource.workflow.generator.IManualWorkflowTask
import com.eclipsesource.workflow.generator.IWorkflowTask
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

class StaticCodeGenerator {
	protected String SRC_DIR = "src-gen"
	
	def String toWorkflowTaskName(String packageName) {
		'''«SRC_DIR»/«packageName.filePath»library/«"WorkflowTask".toJavaFileName»'''
	}

	def String toWorkflowTaskContent(String packageName) {
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
			
				protected void execute() throws InterruptedException {
					Thread.sleep(getDuration() * 1000);
					finished = true;
				}
			
				protected abstract void preExecute();
			
				protected abstract void postExecute();
			
				public boolean hasFinished() {
					return finished;
				}
			
			}
		'''
	}
	
	def String toAutomaticWorkflowTaskName(String packageName) {
		'''«SRC_DIR»/«packageName.filePath»library/«"AutomaticWorkflowTask".toJavaFileName»'''
	}
	
	def String toAutomaticWorkflowTaskContent(String packageName) {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».library;
			
			public abstract class AutomaticWorkflowTask extends WorkflowTask {
				public abstract String getComponent();
			}
		'''
	}

	def String toManualWorkflowTaskName(String packageName) {
		'''«SRC_DIR»/«packageName.filePath»library/«"ManualWorkflowTask".toJavaFileName»'''
	}

	def String toManualWorkflowTaskContent(String packageName) {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».library;
			
			public abstract class ManualWorkflowTask extends WorkflowTask {
				public abstract String getActor();
			}
		'''
	}
	
	def String toFileName(String packageName, String taskName) {
		'''«SRC_DIR»/«packageName.filePath»Abstract«taskName.toJavaFileName»'''
	}

	def CharSequence toFileContent(String packageName, String sourceFileName, IWorkflowTask task) {
		'''	
			// auto-generated from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName»;
			
			«IF task.manual»
			import «packageName».library.ManualWorkflowTask;
				
			public abstract class Abstract«task.name.normalize» extends ManualWorkflowTask {
			«ELSE»
			import «packageName».library.AutomaticWorkflowTask;
				
			public abstract class Abstract«task.name.normalize» extends AutomaticWorkflowTask {
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

	private def String toJavaFileName(String name) {
		'''«name.normalize».java'''
	}

	private def normalize(String string) {
		// camelCase+ only alphanumeric
		string.split(" ").map[toFirstUpper].join("").replaceAll("[^A-Za-z0-9]", "")
	}

	private def getFilePath(String packageName) {
		packageName.split("\\.").join("/") + "/"
	}

}
