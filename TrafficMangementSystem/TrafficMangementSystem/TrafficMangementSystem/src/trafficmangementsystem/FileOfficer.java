/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafficmangementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author bassant
 */
public class FileOfficer {
    private static final String key = "asd123";
    
    public static boolean write (String text , boolean append){
    String  file_path="Officers.txt";
    PrintWriter writer = null;
    try {
        writer = new PrintWriter(new FileWriter(new File(file_path), append));
        writer.println(text); // print any data typr
        return true;
    } catch (IOException e) {
        System.out.println(e);
    }
    finally {
        if (writer!=null)
        writer.close();
    }
    return false;
    }
    
    public static ArrayList<TrafficOfficer> read (){
        String  file_path="Officers.txt";
        Scanner reader = null;
        ArrayList <TrafficOfficer> data = new ArrayList<>();
        try{
            reader = new Scanner(new File(file_path));
            while (reader.hasNext()){
                String line = reader.nextLine();
                String [] sep = line.split("#");
                TrafficOfficer o = new TrafficOfficer(Integer.parseInt(sep[0]),sep[1],sep[2],Integer.parseInt(sep[3]),sep[4],sep[5]);
                data.add(o);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);}
        catch (NumberFormatException e) {
             System.out.println("Error: Invalid number format.");}
        finally {
            if(reader!=null)
            reader.close();
        }
        return data;
    }
    public static void addOfficerToFile(ArrayList<TrafficOfficer> officers, String name, String contact,int zoneid, String zone, String ZoneLocaion)
    {
        try {
        String accountData = officers.size()+1 + "#" + name + "#" + contact+ "#" +zoneid+ "#" +zone+ "#" +ZoneLocaion;
        boolean success = FileOfficer.write(accountData, true);
         if (success) {
                System.out.println("Account created successfully!");
            } else {
                System.out.println("An error occurred while saving your account. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    public static String getKey() {
        return key;
    }

 
}