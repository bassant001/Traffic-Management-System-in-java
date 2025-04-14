package trafficmangementsystem;

import java.util.ArrayList;

/**
 * The zone in the traffic system
 */
public class Zone
{
    private int ID;
    private String name;
    private String location;
    private boolean high_density = false;
    public ArrayList<TrafficLight> trafficlights;

    public Zone() {
    }
    public Zone(int zone_id) {
        this.ID=zone_id;
    }


    public Zone(int ID, String name, String location, boolean high_density) {
        this.ID = ID;
        this.location = location;
        this.name = name;
        this.high_density = high_density;
        this.trafficlights = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public boolean isHigh_density() {
        return high_density;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHigh_density(boolean high_density) {
        this.high_density = high_density;
    }

    @Override
    public String toString() {
        return ID + "," + name + "," + location +"," + high_density;
    }
}