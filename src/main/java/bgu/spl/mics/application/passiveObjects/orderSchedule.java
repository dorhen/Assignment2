package bgu.spl.mics.application.passiveObjects;

import java.io.Serializable;

public class orderSchedule implements Serializable{
	
	
	private String bookTitle;
	private int tick;
	
	public orderSchedule(String bookTitle, int tick)
	{
		this.bookTitle = bookTitle;
		this.tick = tick;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public int getTick() {
		return tick;
	}
	

}
