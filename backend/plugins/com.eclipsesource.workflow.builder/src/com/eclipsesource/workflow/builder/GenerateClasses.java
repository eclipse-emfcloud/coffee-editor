package com.eclipsesource.workflow.builder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import com.eclipsesource.workflow.generator.IWorkflowGenerator;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorOutput;

public class GenerateClasses {

	public static IStatus generate(IWorkflowGeneratorInput input, IWorkflowGenerator generator,IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 3); // three steps
		try {
			subMonitor.setTaskName("Validating model");
			subMonitor.setTaskName("Creating Classes for all tasks");
			IResource member = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(input.getPackageName())
					.findMember(new Path(input.getSourceFileName()));
			if(member != null) {
				IProject project = member.getProject();
				IWorkflowGeneratorOutput output = generator.generateClasses(input, subMonitor.split(1));
				output.getGeneratedFiles().forEach(generatedFile -> {
					IFile file = project.getFile(generatedFile.getFileName());
					file.getParent().getLocation().toFile().mkdirs();
					if(!file.exists() || generatedFile.shouldOverwrite()) {
						try(ByteArrayInputStream content = new ByteArrayInputStream(generatedFile.getContent().getBytes(StandardCharsets.UTF_8.name()))) {
							file.getParent().refreshLocal(IResource.DEPTH_INFINITE, subMonitor.split(1));
							if(file.exists()) {
								file.setContents(content, true, false, subMonitor.split(1));
							} else {
								file.create(content, true, subMonitor.split(1));								
							}
						} catch (IOException | OperationCanceledException | CoreException e) {
							e.printStackTrace();
						}
					}
				});
				project.refreshLocal(IResource.DEPTH_INFINITE, subMonitor.split(1));
			}					
			return Status.OK_STATUS;
		} catch (CoreException e) {
			return e.getStatus();
		} finally {
			subMonitor.setWorkRemaining(0);
		}
	}
}
