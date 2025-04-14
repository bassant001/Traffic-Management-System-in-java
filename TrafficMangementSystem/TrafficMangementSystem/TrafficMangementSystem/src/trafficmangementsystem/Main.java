package trafficmangementsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.exit;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args)  {
        Scanner input = new Scanner(System.in);
        List<TrafficViolation> allviolations = ViolationsFile.loadViolations(); // load all violations from file
        ArrayList<TrafficOfficer> allOfficers = FileOfficer.read();// load all officers
        ArrayList<Zone> allzones = Zonefile.readzones();
        File_Manger manger = new File_Manger();
        ArrayList<Vehicle> allVehicles = (ArrayList<Vehicle>) (Object) manger.read("Vehicle.txt"); // read all vehicls
        ArrayList<Object> loginData = manger.read("Owner.txt"); // read all owners
        ArrayList<TrafficLight> allTrafficLight = TrafficLightFile.read();// load all tl
        String choose;
        do {
                System.out.println("\t\t\tWelcome to the traffic system");
                System.out.println("=====================================================");
                System.out.println("Login as Admin : Enter 1");
                System.out.println("Login as Officer : Enter 2");
                System.out.println("Login as user : Enter 3");
                System.out.println("Sign Up new user : Enter 4");
                System.out.println("Exit : Enter 5");
                choose = input.next();
                switch (choose) {
                    case "1":
                        Admin a = new Admin();
                        a.Admin_menu(allzones,allviolations,allTrafficLight,allOfficers);
                        break;
                    case "2":
                        TrafficOfficer.menu(allOfficers, allviolations,allVehicles,loginData,allzones);// i use all vehicls and owner in a notification system
                        break;
                    case "3":
                        Owner.menu(allVehicles,loginData,allviolations);
                        break;
                    case "4":
                        Owner.standaloneSignup(loginData);
                        break;
                    case "5":
                        System.out.println("Have a nice day.");
                        Owner.writeAllData(loginData,allVehicles);
                        Zonefile.savezones(allzones);
                        ViolationsFile.saveViolations(allviolations);
                        TrafficLightFile.saveTrafficLights(allTrafficLight);
                        exit(0);
                    default:
                        System.out.println("Invalid input.Please try again.");
                }
        } while (true) ;
    }
}