package com.eclipsesource.workflow.generator.java

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import org.apache.commons.io.FileUtils
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.SubMonitor
import org.eclipse.uml2.uml.Action
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Stereotype

import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_MANUAL_TASK
import static com.eclipsesource.workflow.architecture.internal.utils.WorkflowExtensionUtil.*

class StaticCodeGenerator {
	protected String SRC_DIR = "src-gen"
	private String packageName = ""

	def init(IProject project, IProgressMonitor monitor) {
		val subMonitor = SubMonitor.convert(monitor, 2)
		packageName = project.name;
		var file = project.getFile('''«SRC_DIR»/«packageName.filePath»''')
		var folder = file.getLocation().toFile()
		if (!folder.exists) {
			folder.mkdirs
		} else {
			FileUtils.cleanDirectory(folder)
		}
		file = project.getFile('''«SRC_DIR»/«packageName.filePath»/library''')
		folder = file.getLocation().toFile()
		if (!folder.exists) {
			folder.mkdirs
		} else {
			FileUtils.cleanDirectory(folder)
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, subMonitor.split(1))
		generateBaseClasses(project, subMonitor.split(1))
	}

	private def generateBaseClasses(IProject project, IProgressMonitor monitor) {
		val subMonitor = SubMonitor.convert(monitor, 1)
		var file = project.getFile('''«SRC_DIR»/«packageName.filePath»/library/«"WorkflowTask".tofileName»''')
		if (!file.exists) {
			file.create(inputStream(workflowTaskClass), true, subMonitor.split(1))
		}
		file = project.getFile('''«SRC_DIR»/«packageName.filePath»/library/«"AutomaticWorkflowTask".tofileName»''')

		if (!file.exists) {
			file.create(inputStream(automaticWorkflowClass), true, subMonitor.split(1))
		}

		file = project.getFile('''«SRC_DIR»/«packageName.filePath»/library/«"ManualWorkflowTask".tofileName»''')

		file.create(inputStream(manualWorkflowClass), true, subMonitor.split(1))

	}

	def CharSequence workflowTaskClass() {
		'''
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

	def CharSequence automaticWorkflowClass() {
		'''
			package «packageName».library;
			
			public abstract class AutomaticWorkflowTask extends WorkflowTask {
				public abstract String getComponent();
			}
		'''
	}

	def CharSequence manualWorkflowClass() {
		'''
			package «packageName».library;
			
			public abstract class ManualWorkflowTask extends WorkflowTask {
				public abstract String getActor();
			}
		'''
	}

	def generate(
		Action action,
		Stereotype stereotype,
		IProject project,
		IProgressMonitor monitor
	) {
		val subMonitor = SubMonitor.convert(monitor, 1)
		val file = project.getFile('''«SRC_DIR»/«packageName.filePath»Abstract«action.name.tofileName»''')

		if (!file.exists) {
			file.create(inputStream(action.toFileContents(stereotype)), true, subMonitor.split(1))
		}

	}

	def CharSequence toFileContents(Action action, Stereotype stereotype) {
		'''	
			«var isManual=stereotype.qualifiedName.equals(STEREOTYPE_MANUAL_TASK)»
			package «packageName»;
			
			«IF isManual»
			import «packageName».library.ManualWorkflowTask;
				
			public abstract class Abstract«action.name.normalize» extends ManualWorkflowTask {
			«ELSE»
			import «packageName».library.AutomaticWorkflowTask;
				
			public abstract class Abstract«action.name.normalize» extends AutomaticWorkflowTask {
			«ENDIF»
				@Override
				«IF isManual»
				public String getActor() {
					return "«getStringProperty(action,"actor",stereotype)»";
				}
				«ELSE»
				public String getComponent() {
					return "«getStringProperty(action,"component",stereotype)»";
				}
				«ENDIF»
			
			
				@Override
				public int getDuration() {
					return «getStereotypePropertyValue(action,"duration",stereotype)»;
				}
			
				@Override
				public String getId() {
					return "«action.activity.name+"::"+action.name»";
				}
			
				@Override
				protected void preExecute() {
					// TODO Auto-generated method stub
			
				}
			
				@Override
				protected void postExecute() {
					// TODO Auto-generated method stub
			
				}
			
			}
		'''
	}

	def getStringProperty(Action action, String property, Stereotype stereotype) {
		val element = getStereotypePropertyValue(action, property, stereotype)
		if (element instanceof NamedElement)
			(element as NamedElement).name
		else
			""
	}

	def ByteArrayInputStream inputStream(CharSequence fileContents) {
		new ByteArrayInputStream(fileContents.toString.getBytes(StandardCharsets.UTF_8.name()))
	}

	def String tofileName(String name) {
		'''«name.normalize».java'''
	}

	def normalize(String string) {
		// camelCase+ only alphanumeric
		string.split(" ").map[toFirstUpper].join("").replaceAll("[^A-Za-z0-9]", "")
	}

	def getFilePath(String packageName) {
		packageName.split("\\.").join("/") + "/"
	}

}
