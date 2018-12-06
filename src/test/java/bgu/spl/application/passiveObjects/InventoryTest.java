package bgu.spl.application.passiveObjects;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.OrderResult;

public class InventoryTest {
	
	private Inventory inv;

	@Before
	public void setUp() throws Exception {
		inv = inv.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		assertTrue(inv != null);
	}

	@Test
	public void testLoad() {
		BookInventoryInfo a = new BookInventoryInfo("Test book", 2, 50);
		BookInventoryInfo[] arr = {a};
		inv.load(arr);
	}

	@Test
	public void testTake() {
		BookInventoryInfo a = new BookInventoryInfo("Test book", 2, 50);
		BookInventoryInfo[] arr = {a};
		inv.load(arr);
		OrderResult res = inv.take("Test Book");
		assertEquals(res, "SUCCESSFULLY_TAKEN");
		OrderResult res1 = inv.take("Test Book");
		assertEquals(res1, "NOT_IN_STOCK");

	}

	@Test
	public void testCheckAvailabiltyAndGetPrice() {
		BookInventoryInfo a = new BookInventoryInfo("Test book", 2, 50);
		BookInventoryInfo[] arr = {a};
		inv.load(arr);
		int res = inv.checkAvailabiltyAndGetPrice("Test Book");
		assertEquals(res, 50);
		int res1 = inv.checkAvailabiltyAndGetPrice("Non Existing");
		assertEquals(res1, -1);
	}

	@Test
	public void testPrintInventoryToFile() {
		
	}

}
