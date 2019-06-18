package com.eclipsesource.coffee.codegen.java;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.java.JavaWorkflowGenerator;
import com.eclipsesource.workflow.generator.json.SprottyWFWorkflowGeneratorInput;

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

		IWorkflowGeneratorInput createInput = new SprottyWFWorkflowGeneratorInput(packageName, new URI(source));
		try {
			GenerateJavaCode.generate(URI.create(targetFolder), createInput, new JavaWorkflowGenerator());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return -20;
		} catch (IOException e) {
			e.printStackTrace();
			return -30;
		}

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}
}
