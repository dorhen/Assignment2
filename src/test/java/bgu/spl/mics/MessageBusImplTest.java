package bgu.spl.mics;
import bgu.spl.mics.application.messages.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageBusImplTest {

	private MessageBusImpl msgBus;
	@Before
	public void setUp() throws Exception {
		msgBus = MessageBusImpl.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSubscribeEvent() {
		
	}

	@Test
	public void testSubscribeBroadcast() {
		fail("Not yet implemented");
	}

	@Test
	public void testComplete() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendBroadcast() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendEvent() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegister() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnregister() {
		fail("Not yet implemented");
	}

	@Test
	public void testAwaitMessage() {
		fail("Not yet implemented");
	}

}
