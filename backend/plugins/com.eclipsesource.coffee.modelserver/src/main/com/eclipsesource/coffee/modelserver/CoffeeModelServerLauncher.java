package com.eclipsesource.coffee.modelserver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.eclipsesource.modelserver.emf.launch.CLIParser;
import com.eclipsesource.modelserver.emf.launch.ModelServerLauncher;
import com.google.common.collect.Lists;

public class CoffeeModelServerLauncher {
	private static Logger LOG = Logger.getLogger(CoffeeModelServerLauncher.class.getSimpleName());
	private static String PROCESS_NAME = "java -jar com.eclipsesource.coffee.modelserver--X.X.X";

	public static void main(String[] args) throws ParseException {
		BasicConfigurator.configure();
		try {
			CLIParser.create(args, CLIParser.getDefaultCLIOptions());
		} catch (UnrecognizedOptionException e) {
			LOG.error("Unrecognized command line argument(s) used!\n");
			CLIParser.printHelp(PROCESS_NAME, CLIParser.getDefaultCLIOptions());
			return;
		}

		if (!CLIParser.getInstance().optionExists("r")) {
			// No workspace root was specified, use test workspace
			final File workspaceRoot = new File(".temp/root/");
			clean(workspaceRoot);
			args = Arrays.copyOf(args, args.length + 1);
			args[args.length - 1] = "--root=" + workspaceRoot.toURI();
			CLIParser.create(args, CLIParser.getDefaultCLIOptions());
		}

		if (CLIParser.getInstance().optionExists("h"))

		{
			CLIParser.getInstance().printHelp(PROCESS_NAME);
			return;
		}

		final ModelServerLauncher launcher = new ModelServerLauncher(args);
		launcher.addEPackageConfigurations(Lists.newArrayList(CoffeePackageConfiguration.class));
		launcher.start();

	}

	private static void clean(final File workspaceRoot) {
		try {
			if (workspaceRoot.exists()) {
				FileUtils.deleteDirectory(workspaceRoot);
			} else {
				workspaceRoot.mkdirs();
			}
		} catch (IOException e) {
			LOG.warn(e);
		}
	}

}
