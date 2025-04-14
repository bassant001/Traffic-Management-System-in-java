package trafficmangementsystem;
import java.util.ArrayList;
import java.util.Scanner;

public class TrafficViolation {
    private int violation_id;
    private int vehicle_id;
    private String violation_type;
    private String date;
    private double fineAmount;
    private int officer_id;// extra to track officer violations
    private int zone_id;
    private String zone_name;
    private String zone_location;

    public String getZone_location() {
        return zone_location;
    }

    public void setZone_location(String zone_location) {
        this.zone_location = zone_location;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }



    public TrafficViolation(int violation_id, int vehicle_id, String violation_type, String date,double fineAmount) {
        this.violation_id = violation_id;
        this.vehicle_id = vehicle_id;
        this.violation_type = violation_type;
        this.date = date;
        this.fineAmount=fineAmount;
    }

    public TrafficViolation(int violation_id, int vehicle_id, String violation_type, String date, double fineAmount, int officer_id) {  // i add officer id to track violation
        this(violation_id,vehicle_id,violation_type,date,fineAmount);
        this.officer_id = officer_id;
    }

    public TrafficViolation(int violation_id, int vehicle_id, String violation_type, String date, double fineAmount, int officer_id, int zone_id) {  // i add officer id to track violation
        this(violation_id,vehicle_id,violation_type,date,fineAmount,officer_id);
        this.zone_id=zone_id;
    }

    public TrafficViolation(int violation_id, int vehicle_id, String violation_type, String date, double fineAmount, int officer_id, int zone_id,String zone_name,String zone_location) {  // i add officer id to track violation
        this(violation_id,vehicle_id,violation_type,date,fineAmount,officer_id,zone_id);
        this.zone_name=zone_name;
        this.zone_location=zone_location;
    }

    public int getViolation_id() {
        return violation_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getViolation_type() {
        return violation_type;
    }

    public String getDate() {
        return date;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setViolation_id(int violation_id) {
        this.violation_id = violation_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public void setViolation_type() {
        Scanner input = new Scanner(System.in);
        System.out.println("Speeding -> 1");
        System.out.println("Driving under the influence -> 2");
        System.out.println("Reckless driving -> 3");
        System.out.println("Distracted driving -> 4");
        System.out.println("Running a red light -> 5");
        int choose = input.nextInt();
        switch (choose){
            case 1:
                this.violation_type = "Speeding";
                break;
            case 2:
                this.violation_type = "Driving under the influence";
                break;
            case 3:
                this.violation_type = "Reckless driving";
                break;
            case 4:
                this.violation_type = "Distracted driving";
                break;
            case 5:
                this.violation_type = "Running a red light";
                break;
            default:
                System.out.println("Invalid input");
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public int getofficer_id()//extra
    {
        return officer_id;
    }
    public void setofficer_id(int officer_id)//extra
    {
        this.officer_id = officer_id;
    }
    //notify officer
    public  void Violation_Notification(){
        System.out.println("=".repeat(70));
        System.out.printf(" *** notification *** \n \"%s violation that id is \"%d\" recorded on %s to vehicle %d. Fine: $%.3f\" \n",violation_type,violation_id,date,vehicle_id,fineAmount);
        System.out.println("=".repeat(70));
    }

    public void displayViolation() //extra
    {
        String format = "%-15s %-40s %-10s %-10s%n";
        System.out.printf(format,"Vehcile ID", "Violation Type", "Fine", "Date" );
        System.out.printf(format,vehicle_id,violation_type,fineAmount,date);
    }

    public String toString() {
        return violation_id + "," + vehicle_id + "," + violation_type + "," + date + "," + fineAmount+ "," +officer_id+ "," +zone_id+ "," +zone_name+ "," +zone_location;
    }

    public static TrafficViolation fromString(String line) {
        String[] parts = line.split(",");
        int violationId = Integer.parseInt(parts[0]);
        int vehicleId = Integer.parseInt(parts[1]);
        String violationType = parts[2];
        String date = parts[3];
        double fineAmount = Double.parseDouble(parts[4]);
        int officer_id = Integer.parseInt(parts[5]);
        int zone_id= Integer.parseInt(parts[6]);
        String zone_name = parts[7];
        String zone_location = parts[8];
        return new TrafficViolation(violationId, vehicleId, violationType,date,fineAmount,officer_id,zone_id,zone_name,zone_location);// i add officer id to track violation

    }
    //overloading
    //notify owner
    protected static void Violation_Notification(TrafficViolation violation,ArrayList<Vehicle> allVehicles, ArrayList<Object> owners)
    {
    boolean foundOwner = false;
    // Find the vehicle
        for (int j = 0; j < owners.size() ; j++){
            String[] details = owners.get(j).toString().split("#");
            if (details.length !=3 || details[0].isEmpty()
                    || !details[1].matches("\\d+")
                    || !details[2].matches("^[a-zA-Z0-9@._-]+$")) {
                continue;
            }
        for (int i =0 ; i< allVehicles.size(); i++){
            if ((violation.getVehicle_id() == allVehicles.get(i).getVehicle_id() ) && (allVehicles.get(i).getOwner().getId() == Integer.parseInt(details[1]))){
                Owner owner = allVehicles.get(i).getOwner();
                owner.addNotification(violation);
                System.out.println("Notification sent to owner successfully");
                foundOwner =true;
                break;
            }
        }
        }
        if (!foundOwner){
            System.out.println("Could not send notification, but it is recorded");
        }

    }
}