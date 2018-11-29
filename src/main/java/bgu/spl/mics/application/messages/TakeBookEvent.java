package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderResult;


public class TakeBookEvent implements Event<OrderResult>{
	
	private String title;
	
	public TakeBookEvent(String s) {
		title = s;
	}
	
	public String getBookName() {
		return title;
	}

}
