package bgu.spl.mics;

import java.util.Vector;

import bgu.spl.mics.application.services.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	
	private static MessageBusImpl instance = null;
	private Vector<Object[]> microServicesQueue;
	private Map<Class<? extends MicroService>, Queue<MicroService>> roundRobinMap;
	
	private MessageBusImpl() {
		microServicesQueue = new Vector<Object[]>();
		roundRobinMap = new HashMap();
		roundRobinMap.put(APIService.class, new LinkedList<>());
		roundRobinMap.put(InventoryService.class, new LinkedList<>());
		roundRobinMap.put(LogisticsService.class, new LinkedList<>());
		roundRobinMap.put(ResourceService.class, new LinkedList<>());
		roundRobinMap.put(SellingService.class, new LinkedList<>());
		roundRobinMap.put(TimeService.class, new LinkedList<>());
	}
	
	public static MessageBusImpl getInstance() {
		if (instance == null) 
			instance = new MessageBusImpl();
		return instance;
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m && arr [2] != type) {
				arr[2] = type;
				roundRobinMap.get(m.getClass()).add(m);
			}
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m)
				arr[3] = type;
		}

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		for(Object[] arr: microServicesQueue) {
			if(arr[3] == b.getClass());
				
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		MicroService m = roundRobinMap.get(e.getMS()).remove();
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m) ((Queue<Message>)arr[0]).add(e);
		}
	}

	@Override
	public void register(MicroService m) {
		Queue<Message> q = new LinkedList<>();
		Object[] arr = new Object[4];//arr[0] holds the queue, arr[1] holds the micro-service, arr[2] holds the event type, arr[3] holds the broadcast type.
		arr[0] = q;
		arr[1] = m;
		microServicesQueue.add(arr);
	}

	@Override
	public void unregister(MicroService m) {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m)
				microServicesQueue.remove(arr);
		}

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
