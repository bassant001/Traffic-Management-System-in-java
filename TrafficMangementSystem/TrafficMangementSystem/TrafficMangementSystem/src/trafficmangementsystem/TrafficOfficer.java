package trafficmangementsystem;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class TrafficOfficer extends User {
    private int zoneID;
    private String zoneNAME;
    private String ZoneLocaion;
    private static boolean isLoggedIn = false;
    private static Scanner input = new Scanner(System.in);
/*___________________________________________________*/
    // Constructor
    public TrafficOfficer(int id, String name, String contact, int zoneID,String zoneNAME,String ZoneLocation) {
        super(id, name, contact);
        this.zoneID=zoneID;
        this.zoneNAME=zoneNAME;
        this.ZoneLocaion = ZoneLocation;
    }

    public String getZoneNAME() {
        return zoneNAME;
    }
/*___________________________________________________*/

    public String getZoneLocaion() {
        return ZoneLocaion;
    }
/*___________________________________________________*/

    public int getZoneID() {
        return zoneID;
    }
    /*___________________________________________________*/   
    public static boolean setzonedenisty(char d )
    {
        boolean zonedenisty;
        
        if(d == 'y' || d == 'Y' )
        {zonedenisty = true;
        return zonedenisty;}
        else if(d == 'n' || d == 'N' )
        {zonedenisty  = false;
        return zonedenisty;}
        else{
            System.out.println("Invalid input.");
            setzonedenisty(input.next().charAt(0));
            return false; //no_effect
        }
    }      
/*___________________________________________________*/
    public void recordviolation(int violation_id, int vehicle_id, String violation_type, ArrayList<Vehicle> allVehicles, ArrayList<Object> allowner,List<TrafficViolation>allviolations)
      {
          //error preventing 
          if(violation_type.trim().isEmpty()) // to make sure that string is not empty " " to ensure storing meaningfull info
          {
              System.out.println("Invalid violation type details.");
              return;
          }
         
          else if(vehicle_id <= 0)
          {
              System.out.println("Invalid vehicle id details.");
              return;
          }
          //calcfineamount extra
          double fineAmount= calcfineamount(violation_type);
          boolean foundvehicle=false;
          for(Vehicle obj :allVehicles)
          {
              Vehicle v =  obj; 
              if(v.getVehicle_id() == vehicle_id)
              {
                  foundvehicle=true;
                  int countofvechilevio =0;
                  for(TrafficViolation vio : allviolations)
                  {
                      if(v.getVehicle_id()==vio.getVehicle_id())
                      {
                          countofvechilevio++;
                      }
                  }
                  
                      if(countofvechilevio>3)
                      {
                          v.setVehicle_status("Not active");
                      }
              }
          }
          
          if(foundvehicle){
          TrafficViolation v = new TrafficViolation(violation_id, vehicle_id, violation_type, java.time.LocalDate.now().toString(), fineAmount);   
          v.setofficer_id(this.id);
          v.setZone_id(this.zoneID);
          v.setZone_name(this.zoneNAME);
          v.setZone_location(this.ZoneLocaion);
          allviolations.add(v);
          v.Violation_Notification();
         TrafficViolation.Violation_Notification(v, allVehicles, allowner);
          }
          else
          {
              System.out.println("there is no vehcile with this -"+ vehicle_id +"- id"+System.lineSeparator()+"try again");
          } 
      }
/*___________________________________________________*/

    public double calcfineamount(String violation_type) {
        double fineAmount;
        if (violation_type.equalsIgnoreCase("speeding")) {
            fineAmount = 500;
        } else if (violation_type.equalsIgnoreCase("Running a red light")) {
            fineAmount = 300;
        } else {
            fineAmount = 100;
        }
        return fineAmount;
    }
/*___________________________________________________*/
    //view all violations based on officer id
      public void viewViolation (List<TrafficViolation> violations)
      {
          boolean found = false; 
          System.out.println("Traffic Violation recorded by officer " + this.name + ": ");
          for(TrafficViolation v: violations)
          {
             if (v.getofficer_id()==this.id) // filter violations
             {
                 v.displayViolation();
                 System.out.println("_______________________________________________________________________________________");
                 found= true;
             }
          }
             if(!found)
             {
                 System.out.println("no violation recorded by this officer.");
                 System.out.println("_______________________________________________________________________________________");;
             }   
      } 
      /*___________________________________________________*/
    // Login functionality
    public static TrafficOfficer login( ArrayList<TrafficOfficer> Officers ) 
    {
        while (!isLoggedIn) 
        {
            try {
        System.out.print("Enter your Officer ID: ");
        int id = input.nextInt();
        input.nextLine(); // Consume newline
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        for(TrafficOfficer obj : Officers)
        {
            TrafficOfficer officer = obj;
                if (officer.id == id && officer.name.equalsIgnoreCase(name)) 
                {
                    System.out.println("Login successful! Welcome, Officer " + name);
                    System.out.println("_______________________________________________________________________________________");
                    isLoggedIn = true;
                    return officer ;
                }
        }
                System.out.println("Invalid credentials. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Error parsing ID. Please enter a valid numeric ID."); 
            }catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid ID.");
                input.nextLine();
            }
            catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());}
  
        }
        return null;
    }
     
    // Menu for TrafficOfficer
    public static void menu(ArrayList<TrafficOfficer> Officers, List<TrafficViolation> allviolations, ArrayList<Vehicle> allVehicles, ArrayList<Object> allowner, ArrayList<Zone> allzones)
    {
        TrafficOfficer officer = null; // To store the logged-in officer

        do {
            if (!isLoggedIn)
            {
                  officer = login(Officers);
                       
            }
            if (officer != null && isLoggedIn == true )
            {
                System.out.println("**************************MENU**************************");
                System.out.println("press -1- View violations recorded by you");
                System.out.println("press -2- Record violation");
                System.out.println("press -3- set zone denisty");
                System.out.println("press -4- Logout");
                System.out.print("Enter Your Choice: ");
                String choice = input.nextLine();

                switch (choice) {
                    case "1":
                        officer.viewViolation (allviolations);
                        break;
                    case "2":
                        try{
                        System.out.println("Enter  details asked below: ");
                        System.out.print("Vehicle ID: ");
                        int vehicle_id = Integer.parseInt(input.nextLine());
                        System.out.println("Violation Type: ");
//                        Scanner input = new Scanner(System.in);
                        System.out.println("*****choices******");
                        System.out.println("1-Speeding");
                        System.out.println("2-Driving under the influence");
                        System.out.println("3-Reckless driving");
                        System.out.println("4-Distracted driving");
                        System.out.println("5-Running a red light");
                        String violation_type="";
                        int vt=input.nextInt();
                         input.nextLine(); // Consume the leftover newline
                        if(vt==1)
                        {
                           violation_type= "Speeding";
                        }
                        else if(vt==2)
                        {
                           violation_type= "Driving under the influence";
                        }
                        else if(vt==3)
                        {
                           violation_type= "Reckless driving";
                        }
                        else if(vt==4)
                        {
                           violation_type= "Distracted driving";
                        } 
                        else if(vt==5)
                        {
                           violation_type= "Running a red light";
                        }
                        
                        officer.recordviolation(allviolations.size()+1, vehicle_id, violation_type,allVehicles, allowner, allviolations);
                        }
                        catch (InputMismatchException e)
                        {System.out.println("InputMismatchException: Please enter a valid integer.");
                        }catch (NumberFormatException e) {
                          System.out.println("Error parsing ID. Please enter a valid numeric ID."); }
                        catch (Exception e) {
                          System.out.println("Unexpected error: " + e.getMessage());}
                        break;
                    case "3":
                        System.out.println("if heigh press y else n ");
                        char ans = input.next().charAt(0) ;
                       boolean zonedenisty = setzonedenisty( ans );
                        input.nextLine(); // Consume the leftover newline
                        for(Zone obj : allzones)
                        {
                            Zone z = obj;
                            if (officer.zoneID == z.getID())
                            {
                                z.setHigh_density(zonedenisty);
                            }
                        }
                        System.out.println("done!");
                        break;
                    case "4":
                        isLoggedIn = false;
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } while (true);
    }  
}