package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.carEvent;
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
    private ResourcesHolder resources;

	public ResourceService(String name) {
		super(name);
		resources = ResourcesHolder.getInstance();
	}

	@Override
	protected void initialize() {
		subscribeEvent(carEvent.class, cur -> {
			Future<DeliveryVehicle> ft= resources.acquireVehicle();
			DeliveryVehicle car = ft.get();
			complete(cur , car);
		});
		
	}

}
