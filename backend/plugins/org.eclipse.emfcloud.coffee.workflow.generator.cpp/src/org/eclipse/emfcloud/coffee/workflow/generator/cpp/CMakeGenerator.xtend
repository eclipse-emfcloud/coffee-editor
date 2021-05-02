package org.eclipse.emfcloud.coffee.workflow.generator.cpp

import java.util.List
import org.eclipse.emfcloud.coffee.Task
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil
import org.apache.commons.lang.StringUtils

class CMakeGenerator {
	
	String rootDirectory
	String sourceDirectory
	
	new(String rootDirectory, String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
		this.rootDirectory = rootDirectory
	}
	
	def String toRootFileName() {
		'''«rootDirectory»/CMakeLists.txt'''
	}

	def String toRootFileContent(String packageName, String sourceFileName, List<Task> tasks) {
		var capitalizedPackage = packageName.capitalize
		'''
			# auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			cmake_minimum_required(VERSION 3.10)
			
			# set the project name and version
			project(«capitalizedPackage» VERSION 1.0)
			
			# specify the C++ standard
			set(CMAKE_CXX_STANDARD 11)
			set(CMAKE_CXX_STANDARD_REQUIRED True)
			
			# Set compile commands for clangd to ON
			set(CMAKE_EXPORT_COMPILE_COMMANDS ON)
			# Turn debugging on
			set(CMAKE_BUILD_TYPE Debug)
			
			include_directories(${«capitalizedPackage»_SOURCE_DIR}/inc)
			
			add_subdirectory(src)
			
			# add the binary tree to the search path for include files
			target_include_directories(«capitalizedPackage» PUBLIC "${PROJECT_BINARY_DIR}" )
		'''
	}
	
	def String toSrcFileName() {
		'''«sourceDirectory»/CMakeLists.txt'''
	}

	def String toSrcFileContent(String packageName, String sourceFileName, List<Task> tasks) {
		var capitalizedPackage = packageName.capitalize
		'''
			# auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			
			# add the executable
			add_executable(«capitalizedPackage» 
			«FOR task:tasks»
				«var className = task.name.toClassName»
					«className».cpp
			«ENDFOR»
				WorkflowTask.cpp
				«capitalizedPackage»Runner.cpp
			)
		'''
	}
	
	private def String toClassName(String packageName) {
		FileUtil.normalize(packageName);
	}
	private def String capitalize(String text) {
		StringUtils.capitalize(text);
	}

}
