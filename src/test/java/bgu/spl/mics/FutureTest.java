package bgu.spl.mics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bgu.spl.mics.example.messages.ExampleResult;

public class FutureTest {
	
	private Future<ExampleResult> ft;

	@Before
	public void setUp() throws Exception {
		ft = new Future<ExampleResult>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFuture() {
		
	}

	@Test
	public void testGet() {
		ExampleResult result = ft.get();
		assertFalse(result == null);
		assertTrue(result != null);
		
	}

	@Test
	public void testResolve() {
		ExampleResult result = new ExampleResult();;
		ft.resolve(result);
		ExampleResult result2 = ft.get();
		assertEquals(result,result2);
	}

	@Test
	public void testIsDone() {
		
	}

	@Test
	public void testGetLongTimeUnit() {
		fail("Not yet implemented");
	}

}
