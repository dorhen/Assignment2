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
	private Map<MicroService,Queue<Message>> microServiceQueue;
	private Map<Class<? extends Message>, Queue<MicroService>> roundRobinMap;
	private Map<Event<?>,Future<?>> futureMap;
	
	private MessageBusImpl() {
		microServiceQueue = new HashMap<MicroService,Queue<Message>>();
		roundRobinMap = new HashMap<Class<? extends Message>, Queue<MicroService>>();
		futureMap = new HashMap<Event<?>,Future<?>>();
	}
	
	public static MessageBusImpl getInstance() {
		if (instance == null) 
			instance = new MessageBusImpl();
		return instance;
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		synchronized(microServiceQueue) {
			if(roundRobinMap.get(type) == null)
				roundRobinMap.put(type, new LinkedList<MicroService>());
			roundRobinMap.get(type).add(m);
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized(microServiceQueue) {
			if(roundRobinMap.get(type) == null)
				roundRobinMap.put(type, new LinkedList<MicroService>());
			roundRobinMap.get(type).add(m);
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
		Queue<MicroService> q = roundRobinMap.get(b.getClass());
		if(q != null){
			for(MicroService m : q)
				microServiceQueue.get(m).add(b);
			notifyAll();
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> ft = new Future<T>();
		futureMap.put(e, ft);
		Queue<MicroService> msQ = roundRobinMap.get(e.getClass());
		synchronized(msQ){
			if(msQ == null) return null;
			MicroService m = msQ.remove();
			if (m == null) return null;
			microServiceQueue.get(m).add(e);
			msQ.add(m);
		}
		return ft;
	}

	@Override
	public void register(MicroService m) {
		Queue<Message> q = new LinkedList<Message>();
		microServicesQueue.put(m,q);
	}

	@Override
	public void unregister(MicroService m) {
		Queue<Message> q = microServiceQueue.get(m);
		for(Message e : q)
			if(e instanceof Event<?>) complete((Event<?>)e,null);
		for(Map.Entry<Class<? extends Message>, Queue<MicroService>> pair : roundRobinMap.entrySet()){
			pair.getValue().remove(m);
		}
	}

	@Override
	public synchronized Message awaitMessage(MicroService m) throws InterruptedException {
		Queue<Message> q = microServiceQueue.get(m);
		if(q == null) throw new IllegalStateException("Micro-Service was never registered");
		if(q.isEmpty()) wait();
		return q.remove();
	}

	

}
