package com.eclipsesource.workflow.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.statushandlers.StatusManager;

import com.eclipsesource.workflow.generator.GeneratorUtil;
import com.eclipsesource.workflow.generator.IWorkflowGenerator;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.WorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.java.JavaWorkflowGenerator;

@SuppressWarnings("restriction")
public class WorkflowBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.eclipsesource.workflow.builder.workflowBuilder";
	private static final String MARKER_TYPE = "com.eclipsesource.workflow.builder.workflowProblem";
	protected static final String PLUGIN_ID = "com.eclipsesource.workflow.builder";

	public WorkflowBuilder() {
		super();
	}

	protected void addMarker(IFile file, String message, int lineNumber, int severity) {
		try {
			IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		} catch (CoreException e) {
			StatusManager.getManager().handle(e, PLUGIN_ID);
		}
	}

	protected void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			StatusManager.getManager().handle(e, PLUGIN_ID);
		}
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 4);
		createSourceStructure("src", subMonitor);
		createSourceStructure("src-gen", subMonitor);
		if (kind == FULL_BUILD) {
			fullBuild(subMonitor.split(1));
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(subMonitor.split(1));
			} else {
				incrementalBuild(delta, subMonitor.split(1));
			}
		}

		return null;
	}

	protected void createSourceStructure(String folder, IProgressMonitor monitor)
			throws OperationCanceledException, CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 2);
		IFolder sourceFolder = getProject().getFolder(folder);
		if (sourceFolder.exists()) {
			return;
		}

		sourceFolder.create(false, false, subMonitor.split(1));
		IJavaProject javaProject = JavaCore.create(getProject());
		IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(sourceFolder);

		if (Arrays.stream(javaProject.getRawClasspath()).noneMatch(ce -> ce.getPath().equals(root.getPath()))) {
			IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
			IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
			System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
			newEntries[oldEntries.length] = JavaCore.newSourceEntry(root.getPath());
			javaProject.setRawClasspath(newEntries, subMonitor.split(1));
		}

	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		getProject().deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		GeneratorUtil.cleanGeneratedFolder(getProject(), monitor);
	}

	protected boolean isUMLResource(IResource resource) {
		return "uml".equals(resource.getFileExtension()); //$NON-NLS-1$
	}

	protected void fullBuild(IProgressMonitor monitor) throws CoreException {
		UMLResourceVisitor umlResourceCollectingVisitor = new UMLResourceVisitor();
		getProject().accept(umlResourceCollectingVisitor);
		List<IResource> umlResources = umlResourceCollectingVisitor.getCollectedResources();
		build(umlResources, monitor);
	}

	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		UMLResourceDeltaVisitor umlFileCollectingVisitor = new UMLResourceDeltaVisitor();
		delta.accept(umlFileCollectingVisitor);
		List<IResource> umlResources = umlFileCollectingVisitor.getCollectedResources();
		build(umlResources, monitor);
	}

	private void build(List<IResource> umlResources, IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, umlResources.size() * 2);
		IWorkflowGenerator generator = getWorkflowGenerator();
		for (IResource umlResource : umlResources) {
			if (subMonitor.isCanceled()) {
				return;
			}
			if (umlResource.exists() && umlResource instanceof IFile) {
				IFile umlFile = (IFile) umlResource;
				deleteMarkers(umlFile);
				IWorkflowGeneratorInput input = createGeneratorInput(umlFile);
				IStatus validationStatus = validateModel(input, subMonitor.split(1));
				if (!validationStatus.isOK()) {
					String errorMessage = "There are model validation errors in " + umlResource.getName();
					addMarker(umlFile, errorMessage, 1, IMarker.SEVERITY_ERROR);
				}
				generator.generate(input, subMonitor.split(1));
				input.dispose();
			}
		}
		generator.dispose();
	}

	private IStatus validateModel(IWorkflowGeneratorInput input, IProgressMonitor monitor) {
		// TODO validate model before the build
		return Status.OK_STATUS;
	}

	protected IWorkflowGeneratorInput createGeneratorInput(IResource umlResource) {
		return new WorkflowGeneratorInput(umlResource);
	}

	protected IWorkflowGenerator getWorkflowGenerator() {
		// TODO maybe decide which generator to take based on builder parameters
		// (future)
		return new JavaWorkflowGenerator();
	}

	abstract class UMLResourceCollector {
		private List<IResource> collectedResources = new ArrayList<>();

		public List<IResource> getCollectedResources() {
			return collectedResources;
		}
	}

	class UMLResourceDeltaVisitor extends UMLResourceCollector implements IResourceDeltaVisitor {
		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			if (isUMLResource(resource)) {
				getCollectedResources().add(resource);
			}
			return true;
		}
	}

	class UMLResourceVisitor extends UMLResourceCollector implements IResourceVisitor {
		public boolean visit(IResource resource) {
			if (isUMLResource(resource)) {
				getCollectedResources().add(resource);
			}
			return true;
		}
	}
}