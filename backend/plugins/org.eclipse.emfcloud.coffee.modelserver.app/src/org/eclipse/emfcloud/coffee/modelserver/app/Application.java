package org.eclipse.emfcloud.coffee.modelserver.app;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.emf.di.DefaultModelServerModule;
import org.eclipse.emfcloud.modelserver.emf.launch.ModelServerLauncher;
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
		
		startServer(args).get();
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}
	
	private Future<Void> startServer(String[] args) {
		ModelServerLauncher.configureLogger();
		final ModelServerLauncher launcher = new ModelServerLauncher(new CoffeeModelServerModule());
		launcher.run();
		return new CompletableFuture<>();
	}
}
