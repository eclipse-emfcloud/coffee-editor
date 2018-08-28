package com.eclipsesource.workflow.dsl.ide.^extension

interface ICommand {

	def String getId()

	def Object execute(String[] args)
}
