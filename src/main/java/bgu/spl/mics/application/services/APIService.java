package bgu.spl.mics.application.services;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.passiveObjects.orderSchedule;


/**
 * APIService is in charge of the connection between a client and the store.
 * It informs the store about desired purchases using {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class APIService extends MicroService{
	
	private static AtomicInteger counter = new AtomicInteger(0);
	private Map<Integer,Vector<String>> tickMap;
	private Vector<Future<OrderReceipt>> futures;
	

	public APIService(List<orderSchedule> oSchedule) {
		
		super("APIService"+counter.getAndIncrement());
		tickMap = new HashMap<>();
		if(oSchedule!=null)
		for(orderSchedule od : oSchedule) {
			if(tickMap.get(od.getTick()) == null) tickMap.put(od.getTick(), new Vector<>());
			tickMap.get(od.getTick()).add(od.getBookTitle());
		}
	}

	@Override
	protected void initialize() {
		System.out.println("Cutomer service "+ getName() + " has started.");
		subscribeBroadcast(TickBroadcast.class,current -> {
			int tick = current.getCurrentTime();
			System.out.println(getName() + " got a message from the tick broadcast, current time: "+ tick);
			if(tickMap.get(tick) != null)
				for(String s : tickMap.get(tick))
					futures.add((Future<OrderReceipt>)sendEvent(new BookOrderEvent(s)));
		});
		
	}

}
