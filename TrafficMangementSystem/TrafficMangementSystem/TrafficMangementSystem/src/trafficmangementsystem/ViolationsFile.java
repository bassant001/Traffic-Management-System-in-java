package trafficmangementsystem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ViolationsFile {

    private static final String FILE_NAME = "violations.txt";

    public static void saveViolations(List<TrafficViolation> violations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (TrafficViolation violation : violations) {
                writer.write(violation.toString()); // Use a custom toString format for saving
                writer.newLine();
            }
            //System.out.println("Violations saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving violations: " + e.getMessage());
        }
    }

    public static List<TrafficViolation> loadViolations() {
        List<TrafficViolation> violations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                TrafficViolation violation = TrafficViolation.fromString(line); // Custom parsing method
                violations.add(violation);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No violations file found. Returning empty list.");
        } catch (IOException e) {
            System.out.println("Error loading violations: " + e.getMessage());
        }
        return violations;
    }

    public static void addViolation(TrafficViolation violation) {
        List<TrafficViolation> violations = loadViolations();
        violations.add(violation);
        saveViolations(violations);
    }

    public static void viewViolations(int vehicleId) {
        List<TrafficViolation> violations = loadViolations();
        boolean found = false;

        for (TrafficViolation violation : violations) {
            if (violation.getVehicle_id() == vehicleId) {
                System.out.println(violation);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No violations found for vehicle ID: " + vehicleId);
        }
    }
}