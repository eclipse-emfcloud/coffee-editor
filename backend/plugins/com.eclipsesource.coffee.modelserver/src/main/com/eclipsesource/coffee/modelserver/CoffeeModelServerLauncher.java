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
package com.eclipsesource.coffee.modelserver;

import org.apache.commons.cli.ParseException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.emf.launch.ModelServerLauncher;

import com.google.common.collect.Lists;

public class CoffeeModelServerLauncher {

	public static void main(String[] args) throws ParseException {
		ModelServerLauncher.configureLogger();
		Logger.getRootLogger().setLevel(Level.INFO);
		final ModelServerLauncher launcher = new ModelServerLauncher(args);
		launcher.addEPackageConfigurations(Lists.newArrayList(CoffeePackageConfiguration.class));
		launcher.start();
	}
}
