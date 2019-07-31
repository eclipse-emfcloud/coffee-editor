// auto-generated stub from '/home/eugen/Git/coffee-editor/backend/examples/SuperBrewer3000/SuperBrewer3000.xmi' at 2019/07/31 16:58:08
package SuperBrewer3000.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import SuperBrewer3000.Preheat;
import SuperBrewer3000.Brew;

public class SuperBrewer3000Test {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// setup resources before all test runs
		System.out.println("========== Class Setup of SuperBrewer3000Test ==========");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// tear down resources after all test runs
		System.out.println("========== Class Tear Down of SuperBrewer3000Test ==========");
	}

	@Before
	public void setUp() throws Exception {
		// setup resources before each test run
	}

	@After
	public void tearDown() throws Exception {
		// tear down resources after each test run
	}
	
	@Test
	public void testPreheat() throws InterruptedException {
		System.out.println("Run testPreheat()...");
		Preheat task = new Preheat();
		
		// verify initial state
		assertEquals("Unexpected task duration.", 0, task.getDuration());
		assertEquals("Unexpected task component.", "", task.getComponent());
		
		// verify preExecute
		task.preExecute();
		
		// verify execute
		task.execute();
		assertTrue("Task should have finished.", task.hasFinished());
		
		// verify postExecute
		task.postExecute();
	}
	
	@Test
	public void testBrew() throws InterruptedException {
		System.out.println("Run testBrew()...");
		Brew task = new Brew();
		
		// verify initial state
		assertEquals("Unexpected task duration.", 0, task.getDuration());
		assertEquals("Unexpected task component.", "", task.getComponent());
		
		// verify preExecute
		task.preExecute();
		
		// verify execute
		task.execute();
		assertTrue("Task should have finished.", task.hasFinished());
		
		// verify postExecute
		task.postExecute();
	}
}
