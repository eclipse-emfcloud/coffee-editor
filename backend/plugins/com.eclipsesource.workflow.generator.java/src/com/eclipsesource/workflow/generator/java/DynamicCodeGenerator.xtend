package com.eclipsesource.workflow.generator.java

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.SubMonitor
import org.eclipse.uml2.uml.Action
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Stereotype

import static com.eclipsesource.workflow.architecture.internal.utils.WorkflowExtensionUtil.*

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
		Action action,
		Stereotype stereotype,
		IProject project,
		IProgressMonitor monitor
	) {
		val subMonitor = SubMonitor.convert(monitor, 1)
		val file = project.getFile('''«SRC_DIR»/«packageName.filePath»«action.name.tofileName»''')

		if (!file.exists) {
			file.create(inputStream(action.toFileContents(stereotype)), true, subMonitor.split(1))
		}

	}

	def CharSequence toFileContents(Action action, Stereotype stereotype) {
		'''	
		package «packageName»;
			
		import «packageName».Abstract«action.name.normalize»;
				
		public class «action.name.normalize» extends Abstract«action.name.normalize» {
		
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

	def getStringProperty(Action action, String property, Stereotype stereotype) {
		val element = getStereotypePropertyValue(
			action,
			property,
			stereotype
		)
		if (element instanceof NamedElement)
			(element as NamedElement).name
		else
			""
	}

	def ByteArrayInputStream inputStream(CharSequence fileContents) {
		new ByteArrayInputStream(fileContents.toString.getBytes(StandardCharsets.UTF_8.name()))
	}

	def String tofileName(Action action) {
		tofileName(action.name)
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
