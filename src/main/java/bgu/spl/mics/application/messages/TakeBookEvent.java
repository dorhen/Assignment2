package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderResultTicked;


public class TakeBookEvent implements Event<OrderResultTicked>{
	
	private String title;
	
	public TakeBookEvent(String s) {
		title = s;
	}
	
	public String getBookName() {
		return title;
	}

}
