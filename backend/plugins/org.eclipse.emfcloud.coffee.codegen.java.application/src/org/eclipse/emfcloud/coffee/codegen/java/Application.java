/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.codegen.java;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.common.ModelServerClientUtil;
import org.eclipse.emfcloud.coffee.workflow.generator.GeneratedFile;
import org.eclipse.emfcloud.coffee.workflow.generator.java.JavaWorkflowGenerator;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

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
			throws IllegalArgumentException, Exception {
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

	private static Machine parse(URI uri) throws IllegalArgumentException, Exception {
		return ModelServerClientUtil.loadResource(uri, Machine.class).orElseThrow(IllegalArgumentException::new);
	}
}
