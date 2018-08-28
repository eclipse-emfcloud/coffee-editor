package com.eclipsesource.workflow.generator.java

import com.eclipsesource.workflow.generator.IWorkflowTask
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.SubMonitor

class DynamicCodeGenerator {
	protected String SRC_DIR = "src"

	protected String packageName = ""

	def init(IProject project, IProgressMonitor monitor) {
		val subMonitor = SubMonitor.convert(monitor, 1)
		packageName = project.name
		val file = project.getFile(
			'''«SRC_DIR»/«packageName.filePath»'''
		)
		var folder = file.getLocation().toFile()
		if (!folder.exists) {
			folder.mkdirs
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, subMonitor.split(1));

	}

	def generate(
		IProject project,
		IResource resource,
		IWorkflowTask task,		
		IProgressMonitor monitor
	) {
		val subMonitor = SubMonitor.convert(monitor, 1)
		val file = project.getFile('''«SRC_DIR»/«packageName.filePath»«task.name.tofileName»''')

		if (!file.exists) {
			file.create(inputStream(task.toFileContents(resource)), true, subMonitor.split(1))
		}

	}

	def CharSequence toFileContents(IWorkflowTask task, IResource resource) {
		'''	
		// stub generated from '«resource.name»'
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

	def ByteArrayInputStream inputStream(CharSequence fileContents) {
		new ByteArrayInputStream(fileContents.toString.getBytes(StandardCharsets.UTF_8.name()))
	}

	def String tofileName(String name) {
		'''/«name.normalize».java'''
	}

	def normalize(String string) {
		// camelCase+ only alphanumeric
		string.split(" ").map[toFirstUpper].join("").replaceAll("[^A-Za-z0-9]", "")
	}

	def getFilePath(String packageName) {
		packageName.split("\\.").join("/") + "/"
	}

}
