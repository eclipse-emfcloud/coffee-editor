package com.eclipsesource.workflow.generator.java

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.eclipsesource.workflow.generator.IWorkflowTask
import java.util.List
import com.eclipsesource.workflow.generator.IManualWorkflowTask
import com.eclipsesource.workflow.generator.IAutomaticWorkflowTask

class JUnitUserTaskTestGenerator {
	String sourceDirectory
	
	new(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory
	}
	
	def String toFileName(String packageName) {
		'''«sourceDirectory»/«JavaUtil.getFilePath(packageName)»tests/«JavaUtil.getJavaFileName(packageName.toTestClassName)»'''
	}

	def String toFileContent(String packageName, String sourceFileName, List<IWorkflowTask> tasks) {
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
					System.out.println("Setup «packageName.toTestClassName»");
				}
			
				@AfterClass
				public static void tearDownAfterClass() throws Exception {
					System.out.println("Tear down «packageName.toTestClassName»");
				}
			
				@Before
				public void setUp() throws Exception {
					System.out.println("Init before test case");
				}
			
				@After
				public void tearDown() throws Exception {
					System.out.println("Tear down after test case");
				}
				«FOR task : tasks»
				
				@Test
				public void test«task.name.toClassName»() throws InterruptedException {
					«task.name.toClassName» task = new «task.name.toClassName»();
					
					// verify initial state
					assertEquals("Unexpected task id.", "«task.id»", task.getId());
					assertEquals("Unexpected task duration.", «task.duration», task.getDuration());
					«IF task.isManual»
					assertEquals("Unexpected task actor.", "«(task as IManualWorkflowTask).actor»", task.getActor());
					«ELSE»
					assertEquals("Unexpected task component.", "«(task as IAutomaticWorkflowTask).component»", task.getComponent());
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
		JavaUtil.normalize(packageName);
	}
}
