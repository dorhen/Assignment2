package bgu.spl.mics.application.passiveObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * Passive data-object representing a customer of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Customer {
	private int id;
	private String name;
	private String address;
	private List<OrderReceipt> List;
	private int distance;
	private int creditCard;
	private int availableAmountInCreditCard;
	
	public Customer(int id, String name, String address, int distance, int creditCard, int credit){
		this.id=id;
		this.name=name;
		this.address=address;
		List=new LinkedList<OrderReceipt>();
		this.distance=distance;
		this.creditCard=credit;
		availableAmountInCreditCard=credit;
	}
	
	/**
     * Retrieves the name of the customer.
     */
	public String getName() {
		return name;
	}

	/**
     * Retrieves the ID of the customer  . 
     */
	public int getId() {
		return id;
	}
	
	/**
     * Retrieves the address of the customer.  
     */
	public String getAddress() {
		return address;
	}
	
	/**
     * Retrieves the distance of the customer from the store.  
     */
	public int getDistance() {
		return distance;
	}

	
	/**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     * @return A list of receipts.
     */
	public List<OrderReceipt> getCustomerReceiptList() {
		return List;
	}
	
	/**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     * @return Amount of money left.   
     */
	public int getAvailableCreditAmount() {
		return availableAmountInCreditCard;
	}
	
	/**
     * Retrieves this customers credit card serial number.    
     */
	public int getCreditNumber() {
		return creditCard;
	}
	
	/**
	* charge the customer credit card if possible
	*/
	public boolean charge(int amount){
		if(amount>availableAmountInCreditCard)
			return false;
		availableAmountInCreditCard -= amount;
		return true;
	}
	
	/**
	* add receipt to the list
	*/
	public void addReceipt(OrderReceipt r){
		List.add(r);
	}
	
}
