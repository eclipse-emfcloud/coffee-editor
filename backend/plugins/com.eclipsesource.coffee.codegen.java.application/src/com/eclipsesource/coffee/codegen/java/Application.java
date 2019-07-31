package com.eclipsesource.coffee.codegen.java;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.eclipsesource.modelserver.client.ModelServerClient;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.workflow.generator.GeneratedFile;
import com.eclipsesource.workflow.generator.java.JavaWorkflowGenerator;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		context.applicationRunning();
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String targetFolder = null;
		String source = null;
		String packageName = null;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
			case "-targetFolder":
				i++;
				targetFolder = args[i];
				break;
			case "-source":
				i++;
				source = args[i];
				break;
			case "-packageName":
				i++;
				packageName = args[i];
				break;
			}
		}
		if (targetFolder == null) {
			return -10;
		}
		if (source == null) {
			return -11;
		}
		if (packageName == null) {
			return -12;
		}

		try {
			generate(URI.create(targetFolder), packageName, URI.create(source));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(System.err);
			return -20;
		} catch (IOException e) {
			e.printStackTrace(System.err);
			return -30;
		}

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}
	
	
	private static void generate(URI targetLocation, String packageName, URI sourceUri)
			throws UnsupportedEncodingException, IOException, InterruptedException, ExecutionException {
		JavaWorkflowGenerator generator = new JavaWorkflowGenerator();
		
		Collection<GeneratedFile> files = generator.generateClasses(parse(sourceUri), packageName, sourceUri.getPath());
		Path targetFolder = Paths.get(targetLocation);

		for (GeneratedFile generatedFile : files) {
			Path file = targetFolder.resolve(generatedFile.getFileName());
			if (Files.notExists(file) || generatedFile.shouldOverwrite()) {
				Files.createDirectories(file.getParent());
				Files.write(file, generatedFile.getContent().getBytes(StandardCharsets.UTF_8.name()));
			}
		}
	}
	
	private static Machine parse (URI uri) throws InterruptedException, ExecutionException, IOException {
		ModelServerClient msc = new ModelServerClient("http://localhost:8081/api/v1/");
		String content = msc.get(Paths.get(uri).getFileName().toString()+"?format=xmi")
	      .get().body();
		
		//TODO remove
		System.out.println(content);
		
		ResourceSet rs = new ResourceSetImpl();
		
		Resource resource = rs.createResource(org.eclipse.emf.common.util.URI.createURI("VIRTUAL"));
		resource.load(new ByteArrayInputStream(content.getBytes()), null);

		return (Machine) resource.getContents().get(0);
	}
}
