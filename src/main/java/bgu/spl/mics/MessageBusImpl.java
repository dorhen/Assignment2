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
	private Map<Class<? extends Event<?>>, Queue<MicroService>> roundRobinMap;
	private Map<Event<?>,Future<?>> futureMap;
	
	private MessageBusImpl() {
		microServicesQueue = new Vector<Object[]>();
		roundRobinMap = new HashMap<Class<? extends Event<?>>, Queue<MicroService>>();
		futureMap = new HashMap<Event<?>,Future<?>>();
	}
	
	public static MessageBusImpl getInstance() {
		if (instance == null) 
			instance = new MessageBusImpl();
		return instance;
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m && !((Vector<Class<? extends Event<?>>>)arr[2]).contains(type)) {
				((Vector<Class<? extends Event<T>>>)arr[2]).add(type);
				if(roundRobinMap.get(type) == null)
					roundRobinMap.put(type, new LinkedList<>());
				roundRobinMap.get(type).add(m);
			}
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m)
				((Vector<Class<? extends Broadcast>>)arr[3]).add(type);
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> ft = (Future<T>) futureMap.get(e);
		
		ft.resolve(result);
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		boolean wakeUp = false;
		for(Object[] arr: microServicesQueue) {
			if(((Vector<Class<? extends Broadcast>>)arr[3]).contains(b.getClass())) {
				((Queue<Message>)arr[0]).add(b);
				wakeUp = true;
			}
		}
		if(wakeUp) notifyAll();
	}

	
	@Override
	public synchronized <T> Future<T> sendEvent(Event<T> e) {
		Future<T> ft = new Future<T>();
		futureMap.put(e, ft);
		Queue<MicroService> msQ = roundRobinMap.get(e.getClass());;
		if(msQ == null) return null;
		MicroService m = msQ.remove();
		if (m == null) return null;
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m) {
				Queue<Message> q = (Queue<Message>)arr[0];
				q.add(e);
				notifyAll();
			}
		}
		msQ.add(m);
		return ft;
	}

	@Override
	public void register(MicroService m) {
		Queue<Message> q = new LinkedList<>();
		Object[] arr = new Object[4];//arr[0] holds the queue, arr[1] holds the micro-service, arr[2] holds the event type, arr[3] holds the broadcast type.
		arr[0] = q;
		arr[1] = m;
		arr[2] = new Vector<Class<? extends Event>>();
		arr[3] = new Vector<Class<? extends Broadcast>>();
		microServicesQueue.add(arr);
	}

	@Override
	public void unregister(MicroService m) {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m) {
				microServicesQueue.remove(arr);
				for(Class<? extends Event<?>> type : ((Vector<Class<? extends Event<?>>>)arr[2])) {
					Queue<MicroService> msQ = roundRobinMap.get(type);;
					if(msQ != null) msQ.remove(m);
				}
				return;
			}
		}

	}

	@Override
	public synchronized Message awaitMessage(MicroService m) throws InterruptedException {
		for(Object[] arr: microServicesQueue) {
			if(arr[1] == m) {
				Queue<Message> q = (Queue<Message>)arr[0];
				while(q.isEmpty())
					wait();
				Message msg = q.remove();
				return msg;
			}
		}
		throw new IllegalStateException("Micro-Service was never registered");
	}

	

}
