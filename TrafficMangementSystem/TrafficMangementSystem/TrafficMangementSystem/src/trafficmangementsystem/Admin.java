package trafficmangementsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The administrator of the traffic system
 */
public class Admin implements Info {
    private static final int id = 7102023;
    private final String name = "Admin";
    private final String contact = "Admin@gmail.com";
    /*___________________________________________*/

    @Override
    public void View_info() {
        System.out.println("\t\t\t\t Welcome " + name + ".");
        System.out.println("\t\t\t\t " + contact);
        System.out.println("=====================================================");
    }

    /*________________________________________________*/
    /**
     * The menu of the admin page which contains all his functionalities and call the methods to do these functionalities
     * @param zone contain all saved zones information
     * @param allviolations contain all saved violations information
     * @param alltl contain all saved traffic lights information
     */
    void Admin_menu(ArrayList<Zone> zone, List<TrafficViolation> allviolations, ArrayList<TrafficLight> alltl,ArrayList<TrafficOfficer> allOfficers) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter ID: ");
        int ID;
        try {
            ID = input.nextInt();

            if (ID == id) {
                Boolean exit = false;
                do {
                    View_info();
                    System.out.println("Add a traffic light -> 1");
                    System.out.println("Update a traffic light -> 2");
                    System.out.println("Delete a traffic light -> 3");
                    System.out.println("View violations -> 4");
                    System.out.println("Traffic report -> 5");
                    System.out.println("Add a new officer -> 6");
                    System.out.println("Log out -> 7");
                    String ch = input.next();
                    switch (ch) {
                        case "1":
                            this.Add_TL(zone,alltl);
                            break;
                        case "2":
                            this.Update_TL(zone, alltl);
                            break;
                        case "3":
                            this.Delete_TL(zone, alltl);
                            break;
                        case "4":
                            this.View_violation(zone, allviolations);
                            break;
                        case "5":
                            this.Traffic_report(zone, allviolations,alltl);
                            break;
                        case "6":
                            System.out.println("what is the key password:");
                            String password = input.next();
                            try{
                                if(FileOfficer.getKey().equals(password))
                                {
                                    System.out.println("zone id:");
                                    int i= input.nextInt();
                                    System.out.println("officer name:");
                                    String n= input.next();
                                    System.out.println("contact:");
                                    String c= input.next();
                                    System.out.println("zone name:"); // city
                                    String z= input.next();
                                    System.out.println("zone location:"); // city
                                    String l= input.next();
                                    FileOfficer.addOfficerToFile(allOfficers, n, c, i, z, l);}
                                else System.out.println("not allowed wrong key");
                            }
                            catch (InputMismatchException e)
                            {System.out.println("InputMismatchException: Please enter a valid integer.");
                            }catch (NumberFormatException e) {
                                System.out.println("Error parsing ID. Please enter a valid numeric ID."); }
                            catch (Exception e) {
                                System.out.println("Unexpected error: " + e.getMessage());}
                            break;
                        case "7":
                            exit = true;
                            System.out.println("\tGoodbye.");
                            System.out.println("==================");
                            break;
                        default:
                            System.out.println("Invalid input.");
                    }
                } while (!exit);
            } else {
                System.out.println("Wrong id.");
            }
        } catch (InputMismatchException exp) {
            System.out.println("Invalid input.");
        }
    }

    /*___________________________________________________*/
    /**
     * Add traffic light to a specific zone
     * @param zone contain all saved zones information
     * @param tl contain all saved traffic lights information
     */
    private void Add_TL(List<Zone> zone,ArrayList<TrafficLight> tl) {
        try{
        Scanner input = new Scanner(System.in);
        int zone_id, tl_id,i1=-1;
        String location, status,zonename, incycle_choose;
        boolean incycle;
        System.out.println("Enter the zone id you want to add traffic light to : ");
        zone_id = input.nextInt();
        for (int i = 0; i < zone.size(); i++)
            if (zone.get(i).getID() == zone_id) {
                i1 = i;
                break;
            }
        if (i1 == -1)
            System.out.println("No zone with this id.");
        else {

            tl_id=tl.get(tl.size()-1).getID()+1;
            zonename=zone.get(i1).getName();
            location = zone.get(i1).getLocation();
            while (true) {
            System.out.println("Enter traffic light status (red/yellow/green) : ");
            status = input.next();
                if (status.equalsIgnoreCase("red") || status.equalsIgnoreCase("green") || status.equalsIgnoreCase("yellow"))
                {
                    break;
                }
                else {
                    System.out.println("Invalid input.Please try again.");
                }
            }
            while (true) {
            System.out.println("Do you want a default cycle for the traffic light (y/n)?");
            incycle_choose = input.next();
                if (incycle_choose.equalsIgnoreCase("y")){
                    incycle=true;
                    break;
                } else if (incycle_choose.equalsIgnoreCase("n")) {
                    incycle=false;
                    break;
                } else {
                    System.out.println("Invalid input.Please try again.");
                }
            }
            tl.add(new TrafficLight(tl_id,zonename ,location, status, incycle, zone_id));
            System.out.println("Successful operation.");
        }
        }catch (InputMismatchException exp) {
            System.out.println("Invalid input.");
        }
    }

    /*_______________________________________________*/
    /**
     * Update traffic light information at a specific zone
     * @param zone contain all saved zones information
     * @param tl contain all saved traffic lights information
     */
    private void Update_TL(List<Zone> zone, List<TrafficLight> tl) {
        try {
            Scanner input = new Scanner(System.in);
            int zone_id, id, i1 = -1;
            System.out.println("Enter the zone id you want to update traffic light at : ");
            zone_id = input.nextInt();
            for (int i = 0; i < zone.size(); i++)
                if (zone.get(i).getID() == zone_id) {
                    i1 = i;
                    break;
                }
            if (i1 == -1)
                System.out.println("No zone with this id.");
            else {

                for (int i = 0; i < tl.size(); i++)
                    if (zone.get(i1).getID() == tl.get(i).getZoneid())
                        zone.get(i1).trafficlights.add(tl.get(i));


                System.out.println("Enter traffic light id : ");
                id = input.nextInt();
                int choose, i2 = -1;

                for (int i = 0; i < zone.get(i1).trafficlights.size(); i++) {
                    if (zone.get(i1).trafficlights.get(i).getID() == id) {
                        i2 = i;
                        break;
                    }
                }
                if (i2 == -1)
                    System.out.println("No traffic light with this id.");
                else {

                    if (zone.get(i1).getID() != zone.get(i1).trafficlights.get(i2).getZoneid())
                        System.out.println("No traffic light with this id in this zone.");
                    else {

                        System.out.println("What do you want to update?");
                        System.out.println("Location -> 1");
                        System.out.println("Status -> 2");
                        System.out.println("Duration -> 3");
                        choose = input.nextInt();
                        switch (choose) {
                            case 1:
                                System.out.println("Enter the new location : ");
                                zone.get(i1).trafficlights.get(i2).setLocation(input.next());
                                zone.get(i1).trafficlights.get(i2).displaystatus();
                                System.out.println("Successful operation.");
                                break;
                            case 2:
                                System.out.println("Enter the new status : ");
                                zone.get(i1).trafficlights.get(i2).setStatus(input.next());
                                zone.get(i1).trafficlights.get(i2).displaystatus();
                                System.out.println("Successful operation.");
                                break;
                            case 3:
                                System.out.println("Enter the new duration : ");
                                zone.get(i1).trafficlights.get(i2).setDuration(input.nextInt());
                                zone.get(i1).trafficlights.get(i2).displaystatus();
                                System.out.println("Successful operation.");
                                break;
                            default:
                                System.out.println("Invalid input.");
                        }
                    }
                }
            }
        }catch (InputMismatchException exp) {
            System.out.println("Invalid input.");
        }
    }

    /*_____________________________________________*/
    /**
     * Delete traffic light from a specific zone
     * @param zone contain all saved zones information
     * @param tl contain all saved traffic lights information
     */
    private void Delete_TL (List < Zone > zone, List < TrafficLight > tl) {
        try {
            Scanner input = new Scanner(System.in);
            int zone_id, id, i1 = -1;
            System.out.println("Enter the zone id you want to delete traffic light from : ");
            zone_id = input.nextInt();
            for (int i = 0; i < zone.size(); i++)
                if (zone.get(i).getID() == zone_id) {
                    i1 = i;
                    break;
                }
            if (i1 == -1)
                System.out.println("No zone with this id.");
            else {
                int intl=-1;
                for (int i = 0; i < tl.size(); i++)
                    if (zone.get(i1).getID() == tl.get(i).getZoneid()){
                        zone.get(i1).trafficlights.add(tl.get(i));
                    intl=i;
                    }


                System.out.println("Enter traffic light id : ");
                id = input.nextInt();
                int choose, i2 = -1;
                for (int i = 0; i < zone.get(i1).trafficlights.size(); i++) {
                    if (zone.get(i1).trafficlights.get(i).getID() == id) {
                        i2 = i;
                        break;
                    }
                }
                if (i2 == -1)
                    System.out.println("No traffic light with this id.");
                else {

                    if (zone.get(i1).getID() != zone.get(i1).trafficlights.get(i2).getZoneid())
                        System.out.println("No traffic light with this id in this zone.");
                    else {
                        int deleted_index_id=tl.get(intl).getID();
                        tl.remove(intl);
                        int counter=0;
                        for (int j=intl; j<tl.size(); j++){
                            tl.get(j).setID(deleted_index_id+counter);
                            counter++;
                        }
                        System.out.println("Successful operation.");
                    }
                }

            }
        }catch (InputMismatchException exp) {
            System.out.println("Invalid input.");
        }
        }
    /*_______________________________________________________*/
    /**
     * View the violations by zone information or vehicle information
     * @param zone contain all saved zones information
     * @param allviolations contain all saved violations information
     */
    private void View_violation (List < Zone > zone, List < TrafficViolation > allviolations){
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("View violation by vehicle -> 1");
            System.out.println("View violation by zone id -> 2");
            System.out.println("View violation by zone name -> 3");
            int choose = input.nextInt();
            switch (choose) {
                case 1:
                    int index_v = -1;
                    int index_vid = -1;
                    System.out.println("Enter vehicle id : ");
                    int vehicle_id = input.nextInt();
                    for (int i2 = 0; i2 < allviolations.size(); i2++) {
                        if (allviolations.get(i2).getVehicle_id() == vehicle_id) {
                            index_vid = 1;
                            if (allviolations.get(i2).getFineAmount() != 0.0) {
                                index_v = 1;
                                allviolations.get(i2).displayViolation();
                            }
                        }
                    }
                    if (index_v == -1) {
                        System.out.println("There are not violations for this vehicle.");
                    } else if (index_vid == -1) {
                        System.out.println("No vehicle with this id.");
                    } else {
                        System.out.println("==========================================");
                        System.out.println("Successful operation.");
                    }
                    break;
                case 2:
                    int index_id = -1;
                    System.out.println("Enter zone id : ");
                    int zone_id = input.nextInt();
                    for (int i = 0; i < zone.size(); i++) {
                        if (zone.get(i).getID() == zone_id) {
                            index_id = i;
                            break;
                        }
                    }
                    if (index_id == -1) {
                        System.out.println("No zone with this id.");
                    } else {
                        boolean zone_vio = false;
                        for (int i = 0; i < allviolations.size(); i++) {
                            if (allviolations.get(i).getZone_id() == zone_id) {
                                allviolations.get(i).displayViolation();
                                System.out.println("==========================================");
                                zone_vio = true;
                            }
                        }
                        if (!zone_vio) {
                            System.out.println("There are not violations at this zone");
                            System.out.println("==========================================");
                        }
                        System.out.println("Successful operation.");
                        System.out.println("==========================================");
                    }
                    break;
                case 3:
                    int index_name = -1;
                    System.out.println("Enter zone name : ");
                    String zone_name = input.next();
                    for (int i = 0; i < zone.size(); i++) {
                        if (zone.get(i).getName().equals(zone_name)) {
                            index_name = i;
                            break;
                        }
                    }
                    if (index_name == -1) {
                        System.out.println("No zone with this name.");
                    } else {
                        boolean zone_vio = false;
                        for (int i = 0; i < allviolations.size(); i++) {
                            if (allviolations.get(i).getZone_name().equals(zone_name)) {
                                allviolations.get(i).displayViolation();
                                System.out.println("==========================================");
                                zone_vio = true;
                            }
                        }
                        if (!zone_vio) {
                            System.out.println("There are not violations at this zone");
                            System.out.println("==========================================");
                        }
                        System.out.println("Successful operation.");
                        System.out.println("==========================================");
                    }
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }catch (InputMismatchException exp) {
            System.out.println("Invalid input.");
        }
            }

    /*_________________________________________*/
    /**
     * Generate a traffic report for all zones to inform the users the status of the roads by using all the available data of the system
     * @param zone contain all saved zones information
     * @param allviolations contain all saved violations information
     * @param tl contain all saved traffic lights information
     */
     private void Traffic_report (List < Zone > zone, List < TrafficViolation > allviolations,ArrayList<TrafficLight> tl){
         try {
             Scanner input = new Scanner(System.in);
             System.out.println("Do you want a traffic report for");
             System.out.println("one zone -> 1 ");
             System.out.println("all -> 2 ");
             String choice = input.next();
             if (choice.equals("1")) {
                 int zone_id, in = -1;
                 System.out.println("Enter the zone id  : ");
                 zone_id = input.nextInt();
                 for (int i = 0; i < zone.size(); i++)
                     if (zone.get(i).getID() == zone_id) {
                         in = i;
                         break;
                     }
                 if (in == -1)
                     System.out.println("No zone with this id.");
                 else {
                     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                     LocalDateTime now = LocalDateTime.now();
                     System.out.println("\t\t Traffic Report");
                     System.out.println("\t\t " + dtf.format(now));
                     int sum_speeding = 0, sum_influence = 0, sum_reckless = 0, sum_distracted = 0, sum_red = 0;
                     for (int i = 0; i < allviolations.size(); i++) {
                         if (allviolations.get(i).getViolation_type().equals("Speeding") &&
                                 allviolations.get(i).getZone_id() == zone_id) {
                             sum_speeding++;
                         } else if (allviolations.get(i).getViolation_type().equals("Driving under the influence") &&
                                 allviolations.get(i).getZone_id() == zone_id) {
                             sum_influence++;
                         } else if (allviolations.get(i).getViolation_type().equals("Reckless driving") &&
                                 allviolations.get(i).getZone_id() == zone_id) {
                             sum_reckless++;
                         } else if (allviolations.get(i).getViolation_type().equals("Distracted driving") &&
                                 allviolations.get(i).getZone_id() == zone_id) {
                             sum_distracted++;
                         } else if (allviolations.get(i).getViolation_type().equals("Running a red light") &&
                                 allviolations.get(i).getZone_id() == zone_id) {
                             sum_red++;
                         }
                     }
                     System.out.println(zone.get(in).getLocation() + "," + zone.get(in).getName());
                     System.out.println("The frequent violations : ");
                     System.out.println("Speeding has been happened " + sum_speeding + " times.");
                     System.out.println("Driving under the influence has been happened " + sum_influence + " times.");
                     System.out.println("Reckless driving has been happened " + sum_reckless + " times.");
                     System.out.println("Distracted driving has been happened " + sum_distracted + " times.");
                     System.out.println("Running a red light has been happened " + sum_red + " times.");
                     for (int i3 = 0; i3 < tl.size(); i3++) {
                         if (zone.get(in).getID() == tl.get(i3).getZoneid()) {
                             System.out.println("Traffic light id is : " + tl.get(i3).getID() + " .Traffic light status is : " + tl.get(i3).getStatus() + ".");
                         }
                     }
                     if (zone.get(in).isHigh_density()) {
                         System.out.println("This zone is high density.");
                     } else {
                         System.out.println("This zone is low density.");
                     }
                     System.out.println("===================================================================");
                     System.out.println("Successful operation.");
                     System.out.println("==========================================");
                 }
             } else if (choice.equals("2")) {
                 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                 LocalDateTime now = LocalDateTime.now();
                 System.out.println("\t\t Traffic Report");
                 System.out.println("\t\t " + dtf.format(now));

                 int sum_speeding, sum_influence, sum_reckless, sum_distracted, sum_red;
                 for (int i1 = 0; i1 < zone.size(); i1++) {
                     sum_speeding = 0;
                     sum_influence = 0;
                     sum_reckless = 0;
                     sum_distracted = 0;
                     sum_red = 0;
                     for (int i2 = 0; i2 < allviolations.size(); i2++) {
                         if (allviolations.get(i2).getViolation_type().equals("Speeding") &&
                                 allviolations.get(i2).getZone_id() == zone.get(i1).getID()) {
                             sum_speeding++;
                         } else if (allviolations.get(i2).getViolation_type().equals("Driving under the influence") &&
                                 allviolations.get(i2).getZone_id() == zone.get(i1).getID()) {
                             sum_influence++;
                         } else if (allviolations.get(i2).getViolation_type().equals("Reckless driving") &&
                                 allviolations.get(i2).getZone_id() == zone.get(i1).getID()) {
                             sum_reckless++;
                         } else if (allviolations.get(i2).getViolation_type().equals("Distracted driving") &&
                                 allviolations.get(i2).getZone_id() == zone.get(i1).getID()) {
                             sum_distracted++;
                         } else if (allviolations.get(i2).getViolation_type().equals("Running a red light") &&
                                 allviolations.get(i2).getZone_id() == zone.get(i1).getID()) {
                             sum_red++;
                         }
                     }
                     System.out.println(zone.get(i1).getLocation() + "," + zone.get(i1).getName());
                     System.out.println("The frequent violations : ");
                     System.out.println("Speeding has been happened " + sum_speeding + " times.");
                     System.out.println("Driving under the influence has been happened " + sum_influence + " times.");
                     System.out.println("Reckless driving has been happened " + sum_reckless + " times.");
                     System.out.println("Distracted driving has been happened " + sum_distracted + " times.");
                     System.out.println("Running a red light has been happened " + sum_red + " times.");
                     for (int i3 = 0; i3 < tl.size(); i3++) {
                         if (zone.get(i1).getID() == tl.get(i3).getZoneid()) {
                             System.out.println("Traffic light id is : " + tl.get(i3).getID() + " .Traffic light status is : " + tl.get(i3).getStatus() + ".");
                         }
                     }
                     if (zone.get(i1).isHigh_density()) {
                         System.out.println("This zone is high density.");
                     } else {
                         System.out.println("This zone is low density.");
                     }
                     System.out.println("===================================================================");
                 }
                 System.out.println("Successful operation.");
                 System.out.println("==========================================");
             } else {
                 System.out.println("Invalid input");
             }
         }catch (InputMismatchException exp) {
             System.out.println("Invalid input.");
         }
            }


}