package trafficmangementsystem;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * The traffic light in the traffic system
 */
public class TrafficLight
{
    private int ID;
    private String location;
    private String status;
    private int duration;
    private LocalTime starttime;
    private boolean incycle;
    private int zoneid;
    private String zonename;



    Scanner input=new Scanner(System.in);

    public String getStatus() {
        return status;
    }

    public TrafficLight(int ID, String zonename, String location, String status, int duration, String cycletype, LocalTime starttime, int zoneid){
        this.ID=ID;
        this.location=location;
        this.status=status;
        this.duration=duration;
        if(cycletype.equalsIgnoreCase("default cycle"))
            incycle=true;
        else incycle=false;
        this.starttime=starttime;
        this.zoneid=zoneid;
        this.zonename=zonename;
    }

    public TrafficLight(int ID ,String zonename,String location , String status ,boolean incycle,int zoneid)
    {
        this.ID=ID;
        this.location=location;
        this.status=status;
        this.incycle=incycle;
        this.starttime=LocalTime.now();
        this.zoneid=zoneid;
        this.zonename=zonename;

        if(incycle)
            this.duration=60;
        else
            setconfiguredduration();

    }
    public void setconfiguredduration (){
        System.out.println("Enter the number of seconds you want for the cycles duration trafficlight number "+this.ID);
        int configuredduration=input.nextInt();
        setDuration(configuredduration);
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Configure the duration of the traffic light cycle
     * @param duration the duration of the cycle in seconds
     */
    public void setDuration(int duration) {
        while (duration<=0){
            System.out.println("Invalid.the duration must be greater than zero ");
            duration=input.nextInt();
        }
        this.duration=duration;
        this.incycle=false;

    }

    public int getZoneid(){
        return zoneid;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public int getID(){
        return ID;
    }

    public void setStatus(String status) {

        this.status = status;
        this.starttime=LocalTime.now();
    }
    /**
     * Update the traffic light status by local time
     */
    public void updatestatus(){
        LocalTime currenttime=LocalTime.now();

        int startposition = 0;
        if (this.status.equalsIgnoreCase("red"))
            startposition=0;
        else if (this.status.equalsIgnoreCase("green"))
            startposition=1;
        else if (this.status.equalsIgnoreCase("yellow"))
            startposition=2;

        int secondspassed=(int)starttime.until(currenttime,ChronoUnit.SECONDS);
        int cyclecount=secondspassed/this.duration;
        int cycleposition=(cyclecount+startposition)%3;
        if (cycleposition==0)
            this.status="red";
        else if (cycleposition==1)
            this.status="green";
        else if (cycleposition==2)
            this.status="yellow";
    }
    /**
     * Display the status of the traffic light
     */
    public void displaystatus(){
        updatestatus();
        System.out.println("This traffic light id is "+ID);
        System.out.println("This traffic light location is "+location);
        System.out.println("This traffic light status is "+status);
        System.out.println("This traffic light status duration is "+duration+" seconds");
        if(incycle)
            System.out.println("This traffic light is in a default duration");
        else
            System.out.println("This traffic light status is in a configured duration");

    }

    public String getcycle(){
        if (incycle)
            return "default cycle";
        else
            return "configured cycle";
    }

    public String getTLdata(){
        return this.ID+"#"+this.zonename+"#"+this.location+"#"+this.status+"#"+this.duration+"#"+getcycle()+"#"+this.starttime.truncatedTo(ChronoUnit.SECONDS)+"#"+this.zoneid;
    }


}