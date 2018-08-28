package com.eclipsesource.workflow.dsl.ide.^extension.commands

import com.eclipsesource.workflow.dsl.ide.^extension.ICommand
import java.io.File
import java.io.FileInputStream
import java.util.HashMap
import java.util.Map
import org.apache.commons.io.FileUtils
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.resources.WorkspaceJob
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.ui.dialogs.IOverwriteQuery
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider
import org.eclipse.ui.wizards.datatransfer.ImportOperation
import static com.eclipsesource.workflow.dsl.ide.^extension.CommandService.toJSON;
import java.util.Arrays

class BuildCommand implements ICommand {

	private IProgressMonitor monitor

	new() {
		this.monitor = new NullProgressMonitor
	}

	override getId() {
		"workflow.project.build"
	}

	override execute(String[] args) {
		val projects = loadProjects(args.head.replace("file://", ""))
		val cleanJob = new WorkspaceJob(IDEWorkbenchMessages.CleanDialog_cleanSelectedTaskName) {

			override runInWorkspace(IProgressMonitor monitor) throws CoreException {
				projects.keySet.forEach[build(IncrementalProjectBuilder.AUTO_BUILD, monitor)]
				projects.forEach[p, f|copyGeneratedFiles(p, f)]
				projects.keySet.forEach[p|p.delete(true, monitor)]
				return Status.OK_STATUS;
			}

		};
		cleanJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule())
		cleanJob.setUser(true)
		cleanJob.schedule()

		return toJSON("success", "success");
	}

	def void setProgressMonitor(IProgressMonitor monitor) {
		this.monitor = monitor
	}

	def void copyGeneratedFiles(IProject project, File file) {
		"src".copyFolder(project, file)
		"src-gen".copyFolder(project, file)
	}

	def copyFolder(String name, IProject project, File file) {

		val src = project.getFolder(name).location.toFile
		if (!src.exists)
			return;
		val dest = new File(file.path.replace(".project", "") + name);
		if (!dest.exists) {
			dest.mkdir
		}
		FileUtils.copyDirectory(src, dest);
	}

	private def Map<IProject, File> loadProjects(String path) {
		val rootFile = new File(path)
		val map = new HashMap<IProject, File>
		FileUtils.listFiles(rootFile, #["project"], true).forEach[file|map.put(loadProject(file), file)]
		return map;
	}

	private def IProject loadProject(File projectFile) {
		val workspace = ResourcesPlugin.workspace
		val projectDescription = workspace.loadProjectDescription(new FileInputStream(projectFile))
		var project = workspace.root.getProject(projectDescription.name)
		if (project.exists) {
			project.delete(true, true, monitor)
		}
		importProject(project, projectFile.path.replaceAll(".project", ""))
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor)
		if (!project.open) {
			project.open(monitor)
		}
		return project
	}

	def importProject(IProject project, String path) {
		val overwriteQuery = new IOverwriteQuery() {
			override queryOverwrite(String pathString) {
				ALL
			}
		}
		val importOperation = new ImportOperation(project.getFullPath(), new File(path),
			FileSystemStructureProvider.INSTANCE, overwriteQuery)
		importOperation.setCreateContainerStructure(false)
		importOperation.run(monitor);
	}

}
