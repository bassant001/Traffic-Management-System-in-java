package trafficmangementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class File_Manger {
    // Define relative paths for the data files
    private static final String VEHICLE_FILE = "Vehicle.txt";

    public void write(String text, String file_path, boolean append) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(new File(file_path), append));
            writer.println(text);
            return;
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (writer != null) writer.close();
        }
        return;
    }
    public ArrayList<Object> read(String file_path) {
        Scanner reader = null;
        ArrayList<Object> data = new ArrayList<>();
        try {
            reader = new Scanner(new File(file_path));
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] sep = line.split("#");

                // If reading vehicles file, parse lines into Vehicle objects
                if (file_path.equals("Vehicle.txt")) {
                    Owner owner = new Owner(sep[1],Integer.parseInt(sep[2]));
                    Vehicle v = new Vehicle(
                            Integer.parseInt(sep[0]),  // vehicle_id
                            owner,                     // owner_id
                            sep[3],                    // type
                            sep[4],                    // license_plate
                            sep[5]                     // vehicle_status
                    );
                    data.add(v);
                } else {
                    // For other files (e.g., Login), store raw data as strings
                    data.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } finally {
            if (reader != null) reader.close();
        }
        return data;
    }

}