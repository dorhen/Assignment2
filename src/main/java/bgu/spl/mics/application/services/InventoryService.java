package bgu.spl.mics.application.services;

import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.application.messages.CheckAvailabilityEvent;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.OrderResultTicked;
import bgu.spl.mics.MicroService;

/**
 * InventoryService is in charge of the book inventory and stock.
 * Holds a reference to the {@link Inventory} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class InventoryService extends MicroService{

	private static AtomicInteger counter = new AtomicInteger(0);
	private Inventory inventoryRef;
	private int currentTick;
	public InventoryService() {
		super("InventoryService"+counter.getAndIncrement());
		inventoryRef = Inventory.getInstance();
	}

	@Override
	protected void initialize() {
		System.out.println("Inventory service "+ getName() + " has started.");
		subscribeBroadcast(TerminateBroadcast.class, tB -> {
			terminate();			
		});
		subscribeBroadcast(TickBroadcast.class, tB -> {
			currentTick=tB.getCurrentTime();		
		});
		subscribeEvent(CheckAvailabilityEvent.class, ev->{
			String s = ev.getBookName();
			complete(ev, inventoryRef.checkAvailabiltyAndGetPrice(s));
		});
		subscribeEvent(TakeBookEvent.class, ev->{
			String s = ev.getBookName();
			OrderResultTicked result=new OrderResultTicked(inventoryRef.take(s),currentTick);
			complete(ev, result);
		});
		
	}

}
