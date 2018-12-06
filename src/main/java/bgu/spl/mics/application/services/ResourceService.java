package bgu.spl.mics.application.services;

import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CarEvent;
import bgu.spl.mics.application.messages.RealeseCarEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.*;


/**
 * ResourceService is in charge of the store resources - the delivery vehicles.
 * Holds a reference to the {@link ResourceHolder} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ResourceService extends MicroService{
	private static AtomicInteger counter = new AtomicInteger(0);
    private ResourcesHolder resources;

	public ResourceService() {
		super("ResourcesService"+counter.getAndIncrement());
		resources = ResourcesHolder.getInstance();
	}

	@Override
	protected void initialize() {
		System.out.println(this.getName()+" initialize");
		subscribeBroadcast(TerminateBroadcast.class, tB -> {
			terminate();			
		});
		subscribeEvent(CarEvent.class, cur -> {
			Future<DeliveryVehicle> ft= resources.acquireVehicle();
			DeliveryVehicle car = ft.get();
			complete(cur , car);
		});
		subscribeEvent(RealeseCarEvent.class, cur -> {
			resources.releaseVehicle(cur.getVehicle());
		});
		
	}

}
