package com.eclipsesource.workflow.generator.java

import com.eclipsesource.modelserver.coffee.model.coffee.AutomaticTask
import com.eclipsesource.modelserver.coffee.model.coffee.ManualTask
import com.eclipsesource.modelserver.coffee.model.coffee.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.List

class MainMethodUserTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
	
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»tests/«JavaUtil.getJavaFileName(packageName.toRunnerClassName)»'''
	}

	def String toFileContent(String packageName, String sourceFileName, List<Task> tasks) {
		'''
			// auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».tests;
			
			«FOR task : tasks»
			import «packageName».«task.name.toClassName»;
			«ENDFOR»
			
			public class «packageName.toRunnerClassName» {
				
				public static void main(String[] args) {
					«FOR task : tasks»
					run«task.name.toClassName»();
					«ENDFOR»
			    }
			
				«FOR task:tasks»
				«var className = task.name.toClassName»
				private static void run«className» () {
					System.out.println("Run methods of «className»()...");
					«className» task = new «className»();
					// verify initial state
					System.out.println(String.format("«className» duration %s.", task.getDuration()));
					«IF task instanceof ManualTask»
						System.out.println(String.format("«className» actor %s.", task.getActor()));
					«ELSEIF task instanceof AutomaticTask»
						System.out.println(String.format("«className» component %s.", task.getComponent()));
					«ENDIF»
			
					// verify execute
					try {
						task.doExecute();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(String.format("«className» finished: %s.", task.hasFinished()));
				}
				
				«ENDFOR»
			}
		'''
	}
		
	private def String toRunnerClassName(String packageName) {
		toClassName(packageName + "Runner");
	}
	
	private def String toClassName(String packageName) {
		JavaUtil.normalize(packageName);
	}
}
