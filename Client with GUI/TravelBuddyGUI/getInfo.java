/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TravelBuddyGUI;

public class getInfo {
  private static String Name = "";
  private static String UUID = "";
    
    public static void setName (String name){
        Name = name;
    }
    public static String getName() { 
        return Name;
    }
    public static void setUUID (String uuid){ 
        UUID = uuid;
    }
    public static String getUUID() { 
        return UUID;
    }
}
