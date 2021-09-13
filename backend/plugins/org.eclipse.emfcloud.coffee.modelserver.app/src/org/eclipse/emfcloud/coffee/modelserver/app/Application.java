package org.eclipse.emfcloud.coffee.modelserver.app;

import org.eclipse.emfcloud.coffee.modelserver.CoffeeModelServerLauncher;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
		System.setProperty("org.eclipse.jetty.LEVEL", "WARN");
		String[] args = getArgs(context);
		CoffeeModelServerLauncher.main(args);
		System.in.read();
		return null;
	}

	private String[] getArgs(IApplicationContext context) {
		Object object = context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		if (object instanceof String[]) {
			return (String[]) object;
		}
		return new String[0];
	}

	@Override
	public void stop() {
		// Nothing
	}
}
