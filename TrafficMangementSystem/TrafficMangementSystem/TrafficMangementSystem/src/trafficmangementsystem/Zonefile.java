package trafficmangementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Zonefile {

    static ArrayList<Zone> zones = new ArrayList<>();

    public static ArrayList<Zone> readzones() {
        File file = new File("Zones.txt");
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                parcline(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return zones;
    }

    static void parcline(String line) {
        Scanner sc = new Scanner(line);
        sc.useDelimiter(",");
        if (sc.hasNext()) {
            int id = Integer.parseInt(sc.next());
            String name = sc.next();
            String location = sc.next();
            boolean density = Boolean.parseBoolean(sc.next());
            zones.add(new Zone(id, name, location, density));
        }
    }

    public static void savezones(ArrayList<Zone> zones) {
        File file = new File("Zones.txt");
        try (PrintWriter pr = new PrintWriter(file)) {
            for (Zone zone : zones) {
                pr.println(zone.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

    }
}