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
import java.util.List
import org.eclipse.emfcloud.coffee.Task
import org.eclipse.emfcloud.coffee.ManualTask
import org.eclipse.emfcloud.coffee.AutomaticTask
import org.eclipse.emfcloud.coffee.workflow.generator.FileUtil

class JUnitUserTaskTestGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
	
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«FileUtil.getFilePath(packageName)»tests/«FileUtil.getJavaFileName(packageName.toTestClassName)»'''
	}

	def String toFileContent(String packageName, String sourceFileName, List<Task> tasks) {
		'''
			// auto-generated stub from '«sourceFileName»' at «DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now)»
			package «packageName».tests;
			
			import static org.junit.Assert.assertEquals;
			import static org.junit.Assert.assertTrue;
			
			import org.junit.After;
			import org.junit.AfterClass;
			import org.junit.Before;
			import org.junit.BeforeClass;
			import org.junit.Test;
			
			«FOR task : tasks»
			import «packageName».«task.name.toClassName»;
			«ENDFOR»
			
			public class «packageName.toTestClassName» {
				
				@BeforeClass
				public static void setUpBeforeClass() throws Exception {
					// setup resources before all test runs
					System.out.println("========== Class Setup of «packageName.toTestClassName» ==========");
				}
			
				@AfterClass
				public static void tearDownAfterClass() throws Exception {
					// tear down resources after all test runs
					System.out.println("========== Class Tear Down of «packageName.toTestClassName» ==========");
				}
			
				@Before
				public void setUp() throws Exception {
					// setup resources before each test run
				}
			
				@After
				public void tearDown() throws Exception {
					// tear down resources after each test run
				}
				«FOR task : tasks»
				
				@Test
				public void test«task.name.toClassName»() throws InterruptedException {
					System.out.println("Run test«task.name.toClassName»()...");
					«task.name.toClassName» task = new «task.name.toClassName»();
					
					// verify initial state
					assertEquals("Unexpected task duration.", «task.duration», task.getDuration());
					«IF task instanceof ManualTask»
					assertEquals("Unexpected task actor.", "«(task as ManualTask).actor»", task.getActor());
					«ELSE»
					assertEquals("Unexpected task component.", "«(task as AutomaticTask).component»", task.getComponent());
					«ENDIF»
					
					// verify preExecute
					task.preExecute();
					
					// verify execute
					task.execute();
					assertTrue("Task should have finished.", task.hasFinished());
					
					// verify postExecute
					task.postExecute();
				}
				«ENDFOR»
			}
		'''
	}
	
	private def String toTestClassName(String packageName) {
		toClassName(packageName + "Test");
	}
	
	private def String toClassName(String packageName) {
		FileUtil.normalize(packageName);
	}
}
