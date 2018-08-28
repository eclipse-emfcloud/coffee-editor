package com.eclipsesource.workflow.generator.java

import com.eclipsesource.workflow.generator.IWorkflowTask
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

class DynamicCodeGenerator {
	protected String SRC_DIR = "src"
	
	def String toFileContent(String packageName, String sourceFileName, IWorkflowTask task) {
		'''	
		// auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
		package «packageName»;
			
		import «packageName».Abstract«task.name.normalize»;
				
		public class «task.name.normalize» extends Abstract«task.name.normalize» {
		
			@Override
			protected void preExecute() {
				// TODO Add custom implementation here
		
			}
		
			@Override
			protected void postExecute() {
				// TODO Add custom implementation here
		
			}
		
		}
		'''
	}
	
	def toFileName(String packageName, String taskName) {
		'''«SRC_DIR»/«packageName.filePath»«taskName.javaName»'''
	}

	private def normalize(String string) {
		// camelCase+ only alphanumeric
		string.split(" ").map[toFirstUpper].join("").replaceAll("[^A-Za-z0-9]", "")
	}
	
	private def String getJavaName(String name) {
		'''«name.normalize».java'''
	}

	private def getFilePath(String packageName) {
		packageName.split("\\.").join("/") + "/"
	}
}
