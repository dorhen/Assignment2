package bgu.spl.mics.application.services;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;


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
	
	private static AtomicInteger counter;
	private Map<>
	
	

	public APIService(List) {
		
		super("APIService"+counter.getAndIncrement());
	}

	@Override
	protected void initialize() {
		System.out.println("Cutomer service "+ getName() + " has started.");
		subscribeBroadcast(TickBroadcast.class,current -> {
			System.out.println("Got a message from the tick broadcast, current time: "+ current.getCurrentTime());
			if()
		});
		
		
		
	}

}
