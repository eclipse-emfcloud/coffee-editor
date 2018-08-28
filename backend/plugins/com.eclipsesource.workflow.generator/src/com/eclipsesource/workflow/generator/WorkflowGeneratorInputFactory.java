package com.eclipsesource.workflow.generator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IResource;

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
		// could be more elegant with OSGi services and factories
		if(UML_FILE_EXTENSION.contains(resource.getFileExtension())) {
			return Optional.of(new UMLWorkflowGeneratorInput(resource));
		}
		if(SPROTTYWF_FILE_EXTENSION.contains(resource.getFileExtension())) {
			return Optional.of(new SprottyWFWorkflowGeneratorInput(resource));	
		}
		return Optional.empty();
	}
	
	public static boolean canHandle(IResource resource) {
		return resource != null && SUPPORTED_EXTENSIONS.contains(resource.getFileExtension());
	}
	
	public static WorkflowGeneratorInputFactory getInstance() {
		return INSTANCE;
	}
}
