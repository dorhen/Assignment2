package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Future;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Passive object representing the resource manager.
 * You must not alter any of the given public methods of this class.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class ResourcesHolder {
	private static ResourcesHolder instance = new ResourcesHolder();
	private DeliveryVehicle[] vehicles;
	private boolean[] ready;
	private Queue<Future<DeliveryVehicle>> toBeResolved;
	
	private ResourcesHolder() {
		vehicles = new DeliveryVehicle[0];
		toBeResolved = new LinkedList<Future<DeliveryVehicle>>();
	}
	
	/**
     * Retrieves the single instance of this class.
     */
	public static ResourcesHolder getInstance() {
		return ResourcesHolder.instance;
	}
	
	/**
     * Tries to acquire a vehicle and gives a future object which will
     * resolve to a vehicle.
     * <p>
     * @return 	{@link Future<DeliveryVehicle>} object which will resolve to a 
     * 			{@link DeliveryVehicle} when completed.   
     */
	public Future<DeliveryVehicle> acquireVehicle() {
		Future<DeliveryVehicle> ans = new Future<>();
		synchronized(toBeResolved){
			toBeResolved.add(ans);
		}
		resolve();
		return ans;
	}
	
	/**
     * Releases a specified vehicle, opening it again for the possibility of
     * acquisition.
     * <p>
     * @param vehicle	{@link DeliveryVehicle} to be released.
     */
	public void releaseVehicle(DeliveryVehicle vehicle) {
		for(int i=0; i<vehicles.length ; i++){
			if(vehicles[i]==vehicle){
				ready[i]=true;
				return;
				}
			}
		resolve();
        }   
	
	/**
     * Receives a collection of vehicles and stores them.
     * <p>
     * @param vehicles	Array of {@link DeliveryVehicle} instances to store.
     */
	public synchronized void load(DeliveryVehicle[] vehicles) {
		sort(vehicles);
		this.vehicles = vehicles;
		ready = new boolean[vehicles.length];
		for(int i=0;i<ready.length;i++)
			ready[i]=true;
	}

	private void sort(DeliveryVehicle[] arr) {
		DeliveryVehicle temp;
		for(int i=0;i<arr.length;i++) {
			for(int j=i+1;j<arr.length;j++) {
				if(arr[i].getSpeed()<arr[j].getSpeed()) {
					temp = arr[i];
					arr[i]=arr[j];
					arr[j]=temp;
				}
			}
		}
	}
	
	private void resolve(){
            if(!toBeResolved.isEmpty()){
                for(int i=0; i<ready.length;i++){
                	synchronized(vehicles[i]) {
	                    if(ready[i]){
	                        toBeResolved.remove().resolve(vehicles[i]);
	                        ready[i]=false;
	                        return;
	                    }
                	}
                }
            }
        }
}
