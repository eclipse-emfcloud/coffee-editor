/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.generator.java

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class WorkflowTaskGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
	
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«FileUtil.getFilePath(packageName)»library/«FileUtil.getJavaFileName("WorkflowTask")»'''
	}

	def String toFileContent(String packageName) {
		'''
			// auto-generated at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».library;
			
			public abstract class WorkflowTask {
			
				private boolean finished;
			
				public abstract int getDuration();
			
				public void doExecute() throws InterruptedException {
					preExecute();
					execute();
					postExecute();
				}
			
				public void execute() throws InterruptedException {
					Thread.sleep(getDuration());
					finished = true;
				}
			
				public abstract void preExecute();
			
				public abstract void postExecute();
			
				public boolean hasFinished() {
					return finished;
				}
			
			}
		'''
	}
}
