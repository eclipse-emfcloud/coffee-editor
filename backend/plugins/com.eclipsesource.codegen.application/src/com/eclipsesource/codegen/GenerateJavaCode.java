package com.eclipsesource.codegen;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import com.eclipsesource.workflow.generator.IWorkflowGenerator;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorOutput;

public class GenerateJavaCode {

	public static IStatus generate(URI targetLocation, IWorkflowGeneratorInput input, IWorkflowGenerator generator)
			throws UnsupportedEncodingException, IOException {
		IWorkflowGeneratorOutput output = generator.generateClasses(input, new NullProgressMonitor());
		Path targetFolder = Paths.get(targetLocation);

		for (IWorkflowGeneratorOutput.IGeneratedFile generatedFile : output.getGeneratedFiles()) {
			Path file = targetFolder.resolve(generatedFile.getFileName());
			if (Files.notExists(file) || generatedFile.shouldOverwrite()) {
				Files.createDirectories(file.getParent());
				Files.write(file, generatedFile.getContent().getBytes(StandardCharsets.UTF_8.name()));
			}
		}
		return Status.OK_STATUS;
	}
}
