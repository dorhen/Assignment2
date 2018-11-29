package bgu.spl.mics.application.services;

import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.CheckAvailabilityEvent;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.passiveObjects.OrderResult;

/**
 * Selling service in charge of taking orders from customers.
 * Holds a reference to the {@link MoneyRegister} singleton of the store.
 * Handles {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class SellingService extends MicroService{

	private static AtomicInteger counter = new AtomicInteger(0);
	private MoneyRegister moneyRegRef;
	private int currentTick;

	public SellingService() {
		super("SellingService"+counter.getAndIncrement());
		moneyRegRef = MoneyRegister.getInstance();
		currentTick = -1;
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, tB -> {
			if(tB.getTermination())terminate();
			currentTick = tB.getCurrentTime();
			
		});
		subscribeEvent(BookOrderEvent.class, current ->{
			int balance = current.getCustomer().getAvailableCreditAmount();
			String title = current.getBookTitle();
				Future<Integer> ft = (Future<Integer>) sendEvent(new CheckAvailabilityEvent(title));
				int price = ft.get();
				Future<OrderResult> ft2 = null;
				Customer c = current.getCustomer();
				synchronized(c) {
					if(price > -1 && balance >= price) {
					ft2 = (Future<OrderResult>) sendEvent(new TakeBookEvent(title));
					OrderResult result = ft2.get();
					if(result == OrderResult.SUCCESSFULLY_TAKEN)
						current.getCustomer().charge(price);
						sendEvent(new DeliveryEvent(title, c.getDistance()));
						OrderReceipt receipt = new OrderReceipt(0, getName(), c.getId(), title, price, current.getOrderTick(), 0 ,0);
						moneyRegRef.file(receipt);
						complete(current, receipt);
					}
					else
						complete(current,null);
				}
				
				
				
		});
		
	}

}
