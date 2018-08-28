package com.eclipsesource.workflow.dsl.ide.extension.commands;

import static com.eclipsesource.workflow.dsl.ide.extension.CommandService.toJSON;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Model;

import com.eclipsesource.workflow.analyzer.ui.WorkflowAnalysis;
import com.eclipsesource.workflow.dsl.ide.extension.ICommand;

public class AnalyzeWorkflowCommand implements ICommand {
	@Override
	public String getId() {
		return "workflow.analyze";
	}

	@Override
	public Object execute(String[] args) {
		if (args.length != 1) {
			return toJSON("error", "InvalidArguments");
		}
		try {
			File analysisFile = performAnalysis(args[0]);
			if (analysisFile != null) {
				return toJSON("name", analysisFile.getAbsoluteFile().toString());
			}
		} catch (IOException | CoreException e) {
			e.printStackTrace();
			return toJSON("error", e.getMessage());
		}

		return toJSON("error", "ErrorDuringCommandExecution");
	}

	public File performAnalysis(String configPath) throws IOException, CoreException {
		String configurationPath = configPath;
		if (configurationPath.startsWith("\"")) {
			configurationPath = configurationPath.substring(1);
		}
		if (configurationPath.endsWith("\"")) {
			configurationPath = configurationPath.substring(0, configurationPath.length()-1);
		}
		configurationPath = configurationPath.replaceAll(" ", "%20");

		File rootDir = new File(configurationPath).getParentFile();
		URI umlURI = retrieveUMLURI(rootDir);
		if (umlURI == null)
			return null;
		ResourceSet resourceSet = new ResourceSetImpl();

		Resource umlResource = getResource(resourceSet, umlURI);

		Optional<Activity> activity = ((Model) umlResource.getContents().get(0)).getOwnedElements().stream()
				.filter(Activity.class::isInstance).map(Activity.class::cast).findFirst();
		if (activity.isPresent()) {
			return new WorkflowAnalysis(activity.get()).persistIn(rootDir, new NullProgressMonitor());

		}
		return null;
	}

	private Resource getResource(ResourceSet rs, URI uri) {
		Resource res = rs.getResource(uri, true);
		try {
			res.load(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	private static URI retrieveUMLURI(File rootDir) {

		Optional<File> file = Arrays.stream(rootDir.listFiles()).filter(f -> f.getName().endsWith(".uml")).findFirst();
		if (file.isPresent()) {
			return URI.createFileURI(file.get().toString());
		}
		return null;
	}

}
