package com.eclipsesource.workflow.dsl.ide.^extension

import org.eclipse.lsp4j.ExecuteCommandParams
import org.eclipse.xtext.ide.server.ILanguageServerAccess
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService
import org.eclipse.xtext.util.CancelIndicator

class CommandService implements IExecutableCommandService {
	public static final String SUCCESS_PREIFX = "success://"
	public static final String ERROR_PREFIX = "error://"

	override execute(ExecuteCommandParams params, ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		val command = CommandRegistry.instance.getCommand(params.command)
		if (command === null)
			return CommandService.toJSON("error", "BadCommand")
		else
			return command.execute(params.arguments.map[toString])
	}

	override initialize() {
		CommandRegistry.instance.registeredCommands
	}

	def static toJSON(String object, String value) {
		return String.format("{ \"%s\":\"%s\"}", object, value);
	}

}
