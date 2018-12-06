package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Customer;

public class ServicesInput {
	public Time time;
	public int selling;
	public int inventoryService;
	public int logistics;
	public int resourcesService;
	public Customer[] customers;

	public int sum() {
		return 1+selling+inventoryService+logistics+resourcesService+customers.length;
	}
}
