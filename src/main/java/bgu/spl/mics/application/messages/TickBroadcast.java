package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
	
	private int currentTick;
	private boolean terminate;
	
	public TickBroadcast(int time, boolean t) {
		currentTick = time;
		terminate = t;
	}
	
	public int getCurrentTime() {
		return currentTick;
	}
	
	public boolean getTermination() {
		return terminate;
	}

}
