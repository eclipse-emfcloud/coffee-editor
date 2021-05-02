/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.generator.cpp


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.List
import org.eclipse.emfcloud.coffee.ManualTask
import org.eclipse.emfcloud.coffee.AutomaticTask
import org.eclipse.emfcloud.coffee.Task
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class MainMethodUserTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
	
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«FileUtil.getCppClassFileName(packageName.toRunnerClassName)»'''
	}

	def String toFileContent(String packageName, String sourceFileName, List<Task> tasks) {
		'''
			// auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			
			#include <iostream>
			«FOR task : tasks»
				#include "«task.name.toClassName».h"
			«ENDFOR»
			
			
			«FOR task:tasks»
				«var className = task.name.toClassName»
				void run«className» () {
					std::cout << "Run methods of «className» ..." << std::endl;
					«className» task;
					// verify initial state
					std::cout << "«className» duration " << task.getDuration() << "." << std::endl;
					«IF task instanceof ManualTask»
						std::cout << "«className» actor " << task.getActor() << "." << std::endl;
					«ELSEIF task instanceof AutomaticTask»
						std::cout << "«className» component " << task.getComponent() << "." << std::endl;
					«ENDIF»

					// verify execute
					task.doExecute();
					
					std::cout << "«className» finished: " << task.hasFinished() << "." << std::endl;
				}
			«ENDFOR»
			
			int main () {
				«FOR task : tasks»
					run«task.name.toClassName»();
				«ENDFOR»
			}
		'''
	}
		
	private def String toRunnerClassName(String packageName) {
		toClassName(packageName + "Runner");
	}
	
	private def String toClassName(String packageName) {
		FileUtil.normalize(packageName);
	}
}
