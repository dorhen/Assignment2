package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

public class RealeseCarEvent implements Event<DeliveryVehicle>{
	private DeliveryVehicle vehicle;
	
	public RealeseCarEvent(DeliveryVehicle d) {
		vehicle=d;
	}

	public DeliveryVehicle getVehicle() {
		return vehicle;
	}

}
