/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafficmangementsystem;

import java.util.List;

/**
 *
 * @author bassant
 */
public abstract class User 
{
 protected int id; 
 protected String name;  
 protected String contact_info;

    public User() {
    }

    public User (int id, String name, String contact_info )
  {
      this.id=id;
      this.name=name;
      this.contact_info=contact_info;
  }
    abstract public void viewViolation(List<TrafficViolation> allviolations);
}
