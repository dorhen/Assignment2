package bgu.spl.mics.application.services;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	
	private AtomicInteger globalTick;
	private int sleepLength;
	private int terminationTick;
	

	public TimeService(int length, int lifetime) {
		super("Time Service");
		globalTick = new AtomicInteger(1);
		sleepLength = length;
		terminationTick = lifetime;
	}

	@Override
	protected void initialize() {
		System.out.println("TimeService was created, starting to count");
		TimeUnit unit = TimeUnit.MILLISECONDS;
		while(globalTick.getAcquire() != terminationTick) {
			sendBroadcast(new TickBroadcast(globalTick.getAcquire(), false));
			System.out.println("tick");
			try {
				unit.sleep(sleepLength);
			}
			catch(InterruptedException e) {
				
			}
			globalTick.incrementAndGet();
			
		}
		sendBroadcast(new TickBroadcast(globalTick.getAcquire(), true));
		System.out.println("Arrived termination");
		terminate();
	}
}
