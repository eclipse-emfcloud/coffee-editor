package com.eclipsesource.workflow.generator;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

public class GeneratorUtil {

	public static final String GENERATED_FOLDER_NAME = "generated";

	private GeneratorUtil() {
		// hide constructor
	}

	public static void cleanGeneratedFolder(IProject project, IProgressMonitor monitor) throws CoreException {
		IFolder generatedFolder = project.getFolder(GENERATED_FOLDER_NAME); // $NON-NLS-1$
		if (generatedFolder.exists()) {
			ArrayList<IResource> resourceToDelete = new ArrayList<>(Arrays.asList(generatedFolder.members()));
			SubMonitor subMonitor = SubMonitor.convert(monitor, resourceToDelete.size());
			for (IResource resource : resourceToDelete) {
				subMonitor.setTaskName("Deleting " + resource.getFullPath().toOSString());
				if (resource.exists()) {
					resource.delete(true, subMonitor.split(1));
				}

			}
		}
	}

}
