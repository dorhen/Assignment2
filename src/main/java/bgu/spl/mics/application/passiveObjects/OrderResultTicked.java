package bgu.spl.mics.application.passiveObjects;

public class OrderResultTicked {
	private OrderResult result;
	private int tick;
	
	public OrderResultTicked(OrderResult r,int t) {
		result=r;
		tick=t;
	}
	
	public int getTick() {
		return tick;
	}
	
	public OrderResult getResult() {
		return result;
	}
	

}
