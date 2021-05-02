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
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class WorkflowTaskGenerator {
	String sourceDirectory
	String includeDirectory
	
	new(String sourceDirectory, String includeDirectory) {
		this.sourceDirectory = sourceDirectory
		this.includeDirectory = includeDirectory
	}
	
	def String toHeaderFileName() {
		'''«includeDirectory»/«FileUtil.getCppHeaderFileName("WorkflowTask")»'''
	}

	def String toHeaderFileContent() {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			#ifndef _WorkflowTask_
			#define _WorkflowTask_
			class WorkflowTask {
				protected:
					bool finished;
					void execute();
				public:
					virtual int getDuration() = 0;
					void doExecute();
					virtual void preExecute() = 0;
					virtual void postExecute() = 0;
					bool hasFinished() {
						return finished;
					};
			};
			#endif
		'''
	}
	
	def String toClassFileName() {
		'''«sourceDirectory»/«FileUtil.getCppClassFileName("WorkflowTask")»'''
	}

	def String toClassFileContent() {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			#include <chrono>
			#include <thread>
			#include "WorkflowTask.h"
			
			void WorkflowTask::doExecute() {
				preExecute();
				execute();
				postExecute();
			}
			
			void WorkflowTask::execute() {
				std::this_thread::sleep_for(std::chrono::milliseconds(getDuration()));
				finished = true;
			}
		'''
	}
}
