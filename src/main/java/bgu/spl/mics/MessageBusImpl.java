package bgu.spl.mics;

import java.util.Vector;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	
	private static MessageBusImpl instance = null;
	private Vector<Object[]> microServicesQueue;
	
	private MessageBusImpl() {
		microServicesQueue = new Vector<Object[]>();
	}
	
	public static MessageBusImpl getInstance() {
		if (instance == null) 
			instance = new MessageBusImpl();
		return instance;
	
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m)
				arr[2] = type;
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
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		Queue<Message> q = new LinkedList<>();
		Object[] arr = new Object[4];
		arr[0] = q;
		arr[1] = m;
		microServicesQueue.add(arr);
	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
