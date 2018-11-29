package bgu.spl.mics.application;

import bgu.spl.mics.application.services.*;

/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
    	Thread t2 = new Thread(new APIService(null,null));
    	Thread t3 = new Thread(new APIService(null,null));

    	Thread t4 = new Thread(new APIService(null,null));

    	Thread t1 = new Thread(new TimeService(2000, 5));
    	//Thread t3 = new Thread(new APIService(null));
    	t3.start();
    	t4.start();
    	t2.start();
    	t1.start();

    	
    }
}
