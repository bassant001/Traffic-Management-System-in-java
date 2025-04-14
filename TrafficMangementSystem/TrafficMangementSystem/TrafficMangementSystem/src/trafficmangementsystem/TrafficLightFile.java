package trafficmangementsystem;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class TrafficLightFile {

    public static ArrayList<TrafficLight> read() {

        String filepath = "TrafficLights.txt";
        Scanner reader = null;
        ArrayList<TrafficLight> TLdata = new ArrayList<>();

        try {
            reader = new Scanner(new File(filepath));
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] sep = line.split("#");
                TrafficLight TL = new TrafficLight(Integer.parseInt(sep[0]), sep[1], sep[2],sep[3], Integer.parseInt(sep[4]), sep[5], LocalTime.parse(sep[6]),Integer.parseInt(sep[7]));
                TLdata.add(TL);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } finally {
            if(reader!=null)
                reader.close();
        }
        return TLdata;
    }

    public static boolean saveTrafficLights(ArrayList<TrafficLight> allTrafficLight) {
        boolean success = true;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("TrafficLights.txt", false));
                    for (TrafficLight tl : allTrafficLight) {
                    writer.println(tl.getTLdata());
                }
        } catch (IOException e) {
            System.out.println("Error saving traffic lights: " + e.getMessage());
            success = false;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return success;
    }


}
