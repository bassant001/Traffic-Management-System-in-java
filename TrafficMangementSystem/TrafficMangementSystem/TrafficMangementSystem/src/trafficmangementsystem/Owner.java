package trafficmangementsystem;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
/**
 * The regular user
 */
public class Owner extends User{
    private double fineamount = 0.0;
    private boolean isLoggedIn = false;
    private static File_Manger manger = new File_Manger();
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static ArrayList<Object> ownerData = new ArrayList<>();

    public String getContact_info() {
        return contact_info;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }

    public Owner(int id, String name, String contact_info) {
        super(id, name, contact_info);
    }

    public Owner() {
    }
    public Owner(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public boolean setFineamount(List<TrafficViolation> allviolations) {
        boolean found = false;
        fineamount =0.0;
        int index = -1;
        for (int i = 0; i < allviolations.size() ;i++)
            for (int j = 0 ; j < vehicles.size(); j ++){
            if ((allviolations.get(i).getVehicle_id()==vehicles.get(j).getVehicle_id() )&& (vehicles.get(j).getOwner().getId() == this.id) ){
                this.fineamount += allviolations.get(i).getFineAmount();
                found = true;
            }
        }
        if (!found)
            this.fineamount = 0.0;
        return  found;
    }

    public double getFineamount() {
        return fineamount;
    }

    public static void standaloneSignup(ArrayList<Object> ownerd) {
        Owner owner = new Owner();
        ownerData = ownerd;
        owner.signup(ownerData);
    }

    private boolean askToContinue() {
        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to perform another action? (yes/no): ");
        String response = input.next().trim();
        input.nextLine();
        return response.equalsIgnoreCase("yes");
    }
    /**
     * Adding a new user to the system
     * @param ownerData contain all saved owners information
     */
    public void signup(ArrayList<Object> ownerData) {
        Scanner input = new Scanner(System.in);
        System.out.println("Sign Up for a New Account");

        try {

            int newId;
            boolean uniqeId;
            System.out.print("Enter your Name: ");
            String newName = input.nextLine();
            do{
                uniqeId = true;
                System.out.print("Enter your ID: ");
                newId = input.nextInt();
                input.nextLine();
                for (int i = 0 ; i < ownerData.size(); i++){
                    String[] details = ownerData.get(i).toString().split("#");
                    if (details.length !=3 || details[0].isEmpty()
                            || !details[1].matches("\\d+")
                            || !details[2].matches("^[a-zA-Z0-9@._-]+$")) {
                        continue;
                    }
                    if (Integer.parseInt(details[1]) == newId){
                        uniqeId = false;
                        System.out.println("This Id is already used");
                        break;
                    }
                }
            }while (!uniqeId);
            System.out.print("Enter your Contact Information: ");
            String newContact = input.nextLine();

            String accountData = newName + "#" + newId + "#" + newContact;

            // Add new owner data to the list
            ownerData.add(accountData);


            System.out.println("Account created successfully! You can now log in.");
        } catch (Exception e) {
            System.out.println("INVALID ID!");
        }
    }

    /**
     * Login to users accounts
     */
    public void login() {
        Scanner input = new Scanner(System.in);

        while (!isLoggedIn) {
            try {
                System.out.print("Enter your name: ");
                String inputName = input.nextLine();

                System.out.print("Enter your ID: ");
                String inputIdString = input.nextLine();

                if (!inputIdString.matches("\\d+")) {
                    System.out.println("Invalid ID format. Please enter a valid numeric ID.");
                    continue;
                }

                int inputId = Integer.parseInt(inputIdString);

                for (Object obj : ownerData) {
                    String[] data = obj.toString().split("#");
                    if (data.length >= 3 && data[0].trim().equals(inputName) && Integer.parseInt(data[1].trim()) == inputId) {
                        this.name = inputName;
                        this.id = inputId;
                        this.isLoggedIn = true;
                        System.out.println("Login successful!");
                        return;
                    }
                }

                System.out.println("Invalid credentials. Please try again.");
                break;
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    /**
     * Making the owner pay his fines
     * @param allviolations contain all saved violations information
     */
    public void payFine(List<TrafficViolation> allviolations) {
        boolean found = setFineamount(allviolations);
        Scanner input = new Scanner(System.in);
        boolean fineFound = false;


        for (int i = 0; i < ownerData.size(); i++) {
            String[] details = ownerData.get(i).toString().split("#");
            if (details.length !=3 || details[0].isEmpty()
                    || !details[1].matches("\\d+")
                    || !details[2].matches("^[a-zA-Z0-9@._-]+$")) {
                continue;
            }

            if (Integer.parseInt(details[1]) == this.id) {
                fineFound = true;
                if (getFineamount() == 0.0 || found == false) {
                    System.out.println("You have no fines.");
                    return;
                } else {
                    System.out.println("Fine Details:");
                    System.out.println("Amount: " + getFineamount());
                    System.out.print("Do you want to pay this fine? (yes/no): ");
                    String choice = input.next();

                    if (choice.equalsIgnoreCase("yes")) {
                        for (int k = 0; k < allviolations.size() ;k++){
                            for (int j = 0 ; j < vehicles.size(); j ++){
                                if ((allviolations.get(k).getVehicle_id()==vehicles.get(j).getVehicle_id() )&& (vehicles.get(j).getOwner().getId() == this.id) ){
                                    allviolations.remove(k);
                                    k = 0;
                                    vehicles.get(j).setVehicle_status("Active");
                                }
                                if(allviolations.isEmpty()){
                                    break;
                                }
                            }
                        }
                        this.fineamount = 0.0;
                        System.out.println("Fine paid successfully!");
                        break;
                    } else {
                        System.out.println("Fine payment cancelled.");
                        break;
                    }
                }
            }
        }

        if (!fineFound) {
            System.out.println("You have no fines.");
        }
    }

    public static void readAllData(ArrayList<Object> ownerData , ArrayList<Vehicle> vehicles) {
        ownerData = manger.read("Owner.txt");
        vehicles = (ArrayList<Vehicle>) (Object) manger.read("Vehicle.txt");
    }

    public static void writeAllData(ArrayList<Object>ownerData , ArrayList<Vehicle> vehicles) {
            manger.write(ownerData.get(0).toString(), "Owner.txt", false);
        for (int i =1; i< ownerData.size() ; i++ ){
            manger.write(ownerData.get(i).toString(), "Owner.txt", true);
        }
        Vehicle.write(vehicles);
    }
    /**
     * The menu of the owner page which contains all his functionalities and call the methods to do these functionalities
     * @param allVehicles contain all saved vehicles information
     * @param allOwners contain all saved owners information
     * @param allviolations contain all saved violations information
     */
    public static void menu(ArrayList<Vehicle> allVehicles, ArrayList<Object> allOwners, List<TrafficViolation> allviolations) {
        Owner owner = new Owner();
        ownerData = allOwners;
        vehicles = allVehicles;
        do {
            if (!(owner.isLoggedIn)){
                owner.login();
            }
            // If the user is logged in
            else if (owner.isLoggedIn) {
                owner.viewNotifications();
                System.out.println("-------------------------------------");
                System.out.println("Welcome, " + owner.getName());
                System.out.println("1- Add Vehicle");
                System.out.println("2- Delete Vehicle");
                System.out.println("3- Check Vehicle Status");
                System.out.println("4- Show Registered Details");
                System.out.println("5- Pay Fine");
                System.out.println("6- View Violations");
                System.out.println("7- Logout");
                System.out.print("Enter Your Choice: ");
                String choice = new Scanner(System.in).nextLine();

                switch (choice) {
                    case "1":
                        owner.addVehicle();
                        break;
                    case "2":
                        owner.delete_Vehicle();
                        break;
                    case "3":
                        owner.checkStatus();
                        break;
                    case "4":
                        owner.registrated_details();
                        break;
                    case "5":
                        owner.payFine(allviolations);
                        break;
                    case "6":
                        owner.viewViolation(allviolations);
                        break;
                    case "7":
                        owner.isLoggedIn = false;
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } while (true);
    }


    public int get_index(int id) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicle_id() == id)
                return i;
        }
        return -1;
    }
    /**
     * Check the status of the vehicle
     */
    public void checkStatus() {
        Scanner input = new Scanner(System.in);
        try {
            do {
                System.out.print("Enter Vehicle ID: ");
                int id = input.nextInt();
                input.nextLine();

                boolean found = false;
                for (Vehicle v : vehicles) {

                    if (v.getVehicle_id() == id && v.getOwner().getId() == this.getId()) {
                        found = true;
                        System.out.println("\nVehicle Status: " + v.getVehicle_status());
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Vehicle not found or does not belong to you.");
                }
            } while (askToContinue());
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID");
            input.nextLine(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }

    public void registrated_details() {
        Scanner input = new Scanner(System.in);
        try {
            do {
                System.out.print("Enter Vehicle ID: ");
                int id = input.nextInt();
                input.nextLine();

                int index = get_index(id);

                if (index != -1) {
                    Vehicle v = vehicles.get(index);

                    if (v.getOwner().getId() == this.getId()) {
                        System.out.printf("- Owner Name: %s\n- Vehicle Type: %s\n- License Plate: %s\n",
                                getName(), v.getType(), v.getLicense_plate());
                    } else {
                        System.out.println("Vehicle not found or does not belong to you.");
                    }
                } else {
                    System.out.println("Vehicle not found or does not belong to you.");
                }
            } while (askToContinue());
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID");
            input.nextLine(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }
    /**
     * View the violation commited by the owner
     * @param allviolations contain all saved violations information
     */
    @Override
    public void viewViolation (List<TrafficViolation> allviolations){
        try {
        boolean found = false;
        boolean header = false;
        String format = "%-15s %-40s %-10s %-10s%n";
        for (int k = 0; k < allviolations.size() ;k++){
            for (int j = 0 ; j < vehicles.size(); j ++){
                if ((allviolations.get(k).getVehicle_id()==vehicles.get(j).getVehicle_id() )&& (vehicles.get(j).getOwner().getId() == this.id) ){
                    found = true ;
                    if (!header){
                        System.out.printf(format,"Vehcile ID", "Violation Type", "Fine", "Date" );
                        header = true;
                    }
                    System.out.printf(format,allviolations.get(k).getVehicle_id(),
                                                allviolations.get(k).getViolation_type(),
                                                allviolations.get(k).getFineAmount(),
                                                allviolations.get(k).getDate());
                }
            }
        }
        if (!found)
            System.out.println("You have no violations");
            Thread.sleep(1000);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public boolean checkLicense (String license){
        for (int i = 0 ; i < vehicles.size(); i++){
            if (vehicles.get(i).getLicense_plate().equalsIgnoreCase(license)){
                System.out.println("This License Plate is already used");
                return false;
            }
        }
        return true;
    }
    /**
     * Add a new vehicle to the owner account
     */
    public void addVehicle (){
        Scanner input = new Scanner(System.in);
       try {
           int vehicle_id = vehicles.size()+1;
           System.out.println("Your Vehicle ID : " + vehicle_id);
           String type;
           do {
               System.out.println("Enter vehicle type: (Car ,Truck ,Bike )");
               type = input.next();
           }while (!type.equalsIgnoreCase("car") && !type.equalsIgnoreCase("Bike") && !type.equalsIgnoreCase("Truck"));
            String license_plate;
           do{
               System.out.println("Enter License Plate:");
               license_plate = input.next();
           }while (!checkLicense(license_plate));
           System.out.println("Added Successfully");
           Thread.sleep(1000);
           Vehicle v = new Vehicle(vehicle_id,this,type,license_plate,"Active");
           vehicles.add(v);
       }catch (InputMismatchException e) {
           System.out.println("Invalid ID");
           input.nextLine(); // Clear invalid input
       } catch (Exception e) {
           System.out.println("Error Occurred: " + e.getMessage());
       }
    }
    /**
     * Delete the vehicle from owner account when it is not his owner
     */
    public void delete_Vehicle(){
        Scanner input = new Scanner(System.in);
        try {
            do {
                System.out.println("Enter Vehicle ID");
                int id = input.nextInt();
                int index = get_index(id);
                if (index != -1){
                    if (vehicles.get(index).getOwner().getId() == this.id){
                        vehicles.remove(index);
                        System.out.println("Deleted Successfully");
                    }else {
                        System.out.println("Vehicle not found or does not belong to you.");
                    }
                }else {
                    System.out.println("Vehicle not found or does not belong to you.");
                }
            }while (askToContinue());
        }catch (InputMismatchException e) {
            System.out.println("Invalid ID");
            input.nextLine(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }

    //notify the owner about violations recorded today and clear all notfications after veiw

    private static ArrayList<TrafficViolation> notifications = new ArrayList<>();

    public static void addNotification(TrafficViolation violation)
    {
        notifications.add(violation);
    }
    /**
     * View notifications to the vehicle owner when committing new violations
     */
    public void viewNotifications() // i called it in menu after log in succssfully
    {
        int index =-1;
        boolean found = false;
        System.out.println("=".repeat(70));
        System.out.println("Notifications for " + name + ":");
        if (notifications.isEmpty()) {
            System.out.println("No new notifications.");
            System.out.println("=".repeat(70));
        }
        else
        {
            for (int i = 0 ; i < notifications.size(); i ++)
            {
                for (int j =0 ; j < vehicles.size(); j ++)
                {
                    if ((notifications.get(i).getVehicle_id() == vehicles.get(j).getVehicle_id())&& (vehicles.get(j).getOwner().getId() == this.id))
                    {
                        notifications.get(i).displayViolation();
                        found = true;
                        index = i;
                        break;
                    }
                }
            }
            if (!found)
            {
                System.out.println("No new notifications.");
                System.out.println("=".repeat(70));
                return;
            }

            //after printing all delete

            clearNotifications(index);
            System.out.println("=".repeat(70));
        }
    }

    public void clearNotifications(int index)
    {
        if (index != -1)
        notifications.remove(index);// Clear notifications after they are viewed
        else return;
    }

}