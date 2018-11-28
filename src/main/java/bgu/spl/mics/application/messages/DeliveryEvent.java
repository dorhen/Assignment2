package bgu.spl.mics.application.messages;

public class DeliveryEvent implements Event<DeliveryVehicle>{
    
    String address;
    int distance;
    
    public DeliveryEvent(String address, int distance){
        this.address = address;
        this.distance = distance;
    }
    
}
    
