package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class CheckAvailabilityEvent implements Event<Integer>{
	
	private String bookName;
	
	public CheckAvailabilityEvent(String s) {
		bookName = s;
	}
	
	public String getBookName() {
		return bookName;
	}
}
