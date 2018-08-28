package com.eclipsesource.workflow.generator.java

import com.eclipsesource.workflow.generator.IAutomaticWorkflowTask
import com.eclipsesource.workflow.generator.IManualWorkflowTask
import com.eclipsesource.workflow.generator.IWorkflowTask
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import org.apache.commons.io.FileUtils
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.SubMonitor

class StaticCodeGenerator {
	protected String SRC_DIR = "src-gen"
	String packageName = ""

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
		IProject project,
		IResource resource,
		IWorkflowTask task,
		IProgressMonitor monitor
	) {
		val subMonitor = SubMonitor.convert(monitor, 1)
		val file = project.getFile('''«SRC_DIR»/«packageName.filePath»Abstract«task.name.tofileName»''')

		if (!file.exists) {
			file.create(inputStream(task.toFileContents(resource)), true, subMonitor.split(1))
		}

	}

	def CharSequence toFileContents(IWorkflowTask task, IResource resource) {
		'''	
			// auto-generated from '«resource.name»'
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
