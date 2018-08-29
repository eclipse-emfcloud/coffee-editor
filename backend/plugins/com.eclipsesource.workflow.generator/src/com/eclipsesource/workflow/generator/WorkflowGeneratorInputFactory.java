package com.eclipsesource.workflow.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.eclipsesource.workflow.generator.json.SprottyWFWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.uml.UMLWorkflowGeneratorInput;

public class WorkflowGeneratorInputFactory {
	private static WorkflowGeneratorInputFactory INSTANCE = new WorkflowGeneratorInputFactory();
	
	private static Set<String> UML_FILE_EXTENSION = new HashSet<>(Arrays.asList("uml"));
	private static Set<String> SPROTTYWF_FILE_EXTENSION = new HashSet<>(Arrays.asList("json", "wf"));
	
	private static Set<String> SUPPORTED_EXTENSIONS = Stream.of(SPROTTYWF_FILE_EXTENSION)
			.flatMap(Set::stream).collect(Collectors.toSet());

	private WorkflowGeneratorInputFactory() {
	}
	
	public Optional<IWorkflowGeneratorInput> createInput(IResource resource) {
		if(!(resource instanceof IFile)) {
			return Optional.empty();
		}
		if(!UML_FILE_EXTENSION.contains(resource.getFileExtension()) &&
			!SPROTTYWF_FILE_EXTENSION.contains(resource.getFileExtension())) {
			return Optional.empty();
		}
		
		IFile file = (IFile) resource;
		Optional<String> content = getStringContent(file);
		if(!content.isPresent()) {
			return Optional.empty();
		}
		return createInput(file.getProject().getName(), file.getProjectRelativePath().toString(), content.get());
	}
	
	public Optional<IWorkflowGeneratorInput> createInput(String packageName, String sourceFileName, String content) {
		String fileExtension = getFileExtension(sourceFileName);
		if(UML_FILE_EXTENSION.contains(fileExtension)) {
			return Optional.of(new UMLWorkflowGeneratorInput(packageName, sourceFileName, content));
		}
		if(SPROTTYWF_FILE_EXTENSION.contains(fileExtension)) {
			return Optional.of(new SprottyWFWorkflowGeneratorInput(packageName, sourceFileName, content));
		}
		return Optional.empty();
	}
	
	public static boolean canHandle(IResource resource) {
		return resource != null && SUPPORTED_EXTENSIONS.contains(resource.getFileExtension());
	}
	
	public static boolean canHandle(String sourceFileName) {
		return sourceFileName != null && SUPPORTED_EXTENSIONS.contains(getFileExtension(sourceFileName));
	}
	
	public static WorkflowGeneratorInputFactory getInstance() {
		return INSTANCE;
	}
	
	public static String getFileExtension(String name) {
		int index = name.lastIndexOf('.');
		if (index == -1)
			return null;
		if (index == (name.length() - 1))
			return ""; //$NON-NLS-1$
		return name.substring(index + 1);
	}
	
	public static Optional<String> getStringContent(IFile file) {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getContents(), file.getCharset()))) {
			String line = null;
		    StringBuilder sb = new StringBuilder();
		    while ((line = reader.readLine()) != null) {
		    	sb.append(line+'\n');
		    }
			return Optional.of(sb.toString());
		} catch (IOException | CoreException e) {
			return Optional.empty();
		}
	}
}
