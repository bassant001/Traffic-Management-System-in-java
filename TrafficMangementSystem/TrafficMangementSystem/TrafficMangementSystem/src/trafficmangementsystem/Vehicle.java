package trafficmangementsystem;

import java.util.ArrayList;
/**
 * The vehicle in the traffic system
 */
public class Vehicle {
    private int vehicle_id;
    private String type;
    private String license_plate;
    private Owner owner ;
    private String vehicle_status = "Active";
    private static File_Manger fmanger = new File_Manger();

    public Vehicle (){}

    public Vehicle(int vehicle_id, Owner owner ,String type, String license_plate, String vehicle_status) {
        this.vehicle_id = vehicle_id;
        this.owner = owner;
        this.type = type;
        this.license_plate = license_plate;
        this.vehicle_status = vehicle_status;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }


    public String getType() {
        return type;
    }


    public String getLicense_plate() {
        return license_plate;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getVehicle_status() {
        return vehicle_status;
    }

    public void setVehicle_status(String vehicle_status) {
        this.vehicle_status = vehicle_status;
    }


    public String getDate (){
        return this.vehicle_id + "#" + owner.getName() + "#" + owner.getId() + "#" + this.type + "#" + this.license_plate + "#" +this.vehicle_status;
    }
    public static void write (ArrayList<Vehicle> vehicles){
        fmanger.write(vehicles.get(0).getDate(),"Vehicle.txt",false);
        for (int i =1 ; i < vehicles.size(); i ++){
            fmanger.write(vehicles.get(i).getDate(),"Vehicle.txt",true);
        }
    }

}