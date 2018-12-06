package bgu.spl.mics.application.services;

import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.RealeseCarEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.CarEvent;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

/**
 * Logistic service in charge of delivering books that have been purchased to customers.
 * Handles {@link DeliveryEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LogisticsService extends MicroService {
	private static AtomicInteger counter = new AtomicInteger(0);
	public LogisticsService() {
		super("LogisticService"+counter.getAndIncrement());
	}

	@Override
	protected void initialize() {
		System.out.println(this.getName()+" initialize");
		subscribeBroadcast(TerminateBroadcast.class, tB -> {
			terminate();			
		});
		subscribeEvent(DeliveryEvent.class, cur -> {
			CarEvent cevent = new CarEvent();
			Future<DeliveryVehicle> ft = sendEvent(cevent);
			DeliveryVehicle result=ft.get();
			result.deliver(cur.getAddress(), cur.getDistance());
			sendEvent(new RealeseCarEvent(result));
		});
		
	}

}
