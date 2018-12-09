package bgu.spl.mics.application.passiveObjects;

import java.io.Serializable;

public class CreditCard implements Serializable{
	public int number;
	public int amount;
	
	public CreditCard(int number,int amount) {
		this.number=number;
		this.amount=amount;
	}
}
