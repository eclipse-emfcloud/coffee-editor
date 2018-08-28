package com.eclipsesource.workflow.dsl.ide.^extension

import java.util.HashMap
import java.util.List
import java.util.Map
import com.eclipsesource.workflow.dsl.ide.^extension.commands.BuildCommand
import com.eclipsesource.workflow.dsl.ide.^extension.commands.AnalyzeWorkflowCommand

class CommandRegistry {
	private static CommandRegistry instance;
	private Map<String, ICommand> idMap

	def static CommandRegistry getInstance() {
		if (instance === null) {
			instance = new CommandRegistry
			instance.initialize();
		}
		instance
	}

	def private initialize() {
		registerCommand(new BuildCommand)
		registerCommand(new AnalyzeWorkflowCommand)
	}

	private new() {
		idMap = new HashMap
	}

	def ICommand getCommand(String id) {
		return idMap.get(id)
	}

	def List<String> getRegisteredCommands() {
		return idMap.keySet.toList
	}

	def void registerCommand(ICommand command) {
		idMap.put(command.id, command)
	}

}
