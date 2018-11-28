package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
	
	private int currentTick;
	
	public TickBroadcast(int time) {
		currentTick = time;
	}
	
	public int getCurrentTime() {
		return currentTick;
	}

}
