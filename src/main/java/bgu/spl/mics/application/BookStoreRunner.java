package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import bgu.spl.mics.application.services.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
    	/*if(args.length!=5) {
    		throw new IllegalArgumentException("");
    	}*/
    	Gson gson = new Gson();
    	try {
			JsonReader reader = new JsonReader(new FileReader(args[0]));
			InputContainer input = gson.fromJson(reader, InputContainer.class);
			Inventory inventory = Inventory.getInstance();
			inventory.load(input.initialInventory);
			ResourcesHolder resourcesHolder = ResourcesHolder.getInstance();
			resourcesHolder.load(input.initialResources[0].vehicles);
			int numOfThreads=input.services.sum();
			Thread[] threads = new Thread[numOfThreads];
			int i=0;
			int index=0;
			for(i=0;i<input.services.selling;i++) {
				threads[index]=new Thread(new SellingService());
				threads[index].start();
				index++;
				}
			for(i=0;i<input.services.inventoryService;i++) {
				threads[index]=new Thread(new InventoryService());
				threads[index].start();
				index++;
				}
			for(i=0;i<input.services.logistics;i++) {
				threads[index]=new Thread(new LogisticsService());
				threads[index].start();
				index++;
			}
			for(i=0;i<input.services.resourcesService;i++) {
				threads[index]=new Thread(new ResourceService());
				threads[index].start();
				index++;	
			}
			for(Customer c: input.services.customers) {
				threads[index]=new Thread(new APIService(c.orderSchedule,c));
				threads[index].start();
				index++;
			}
			threads[index]=new Thread(new TimeService(input.services.time.speed,input.services.time.duration));
			threads[index].start();
			index++;
			for(i=0;i<numOfThreads;i++)
				threads[i].join();
			MoneyRegister.getInstance().printOrderReceipts(args[3]);;
			
    	} catch (FileNotFoundException | InterruptedException e) {
			System.out.println("input not valid or Interrupted thread");
		} 	
    }
}
