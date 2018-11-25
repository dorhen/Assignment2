package bgu.spl.mics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bgu.spl.mics.example.messages.ExampleResult;

public class FutureTest {
	
	private Future<ExampleResult> ft;
	private ExampleResult result;

	@Before
	public void setUp() throws Exception {
		ft = new Future<ExampleResult>();
		result= new ExampleResult();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFuture() {
		asserNull(ft);
	}

	@Test
	public void testGet() {
		ft.resolve(result);
		assertEquals(result, ft.get());		
	}

	@Test
	public void testResolve() {
		ft.resolve(result);
		assertEquals(result,ft.get());
	}

	@Test
	public void testIsDone(){
		assertFalse(ft.isDone());
		ft.resolve(result);
		assertTrue(ft.isDone());
	}

	@Test
	public void testGetLongTimeUnit() {
		fail("Not yet implemented");
	}

}
