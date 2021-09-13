package org.eclipse.emfcloud.coffee.modelserver;

import org.eclipse.emfcloud.modelserver.emf.launch.ModelServerLauncher;

public class CoffeeModelServerLauncher {

	public static void main(final String[] args) {
		final ModelServerLauncher launcher = new ModelServerLauncher(new CoffeeModelServerModule());
		launcher.run();
	}

}
