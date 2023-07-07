/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TravelBuddyGUI;

/**
 *
 * @author xzims
 */
public class UserObj {
    private String name;
    private String uuid;
    
    public UserObj(String name, String uuid){
        this.name = name;
      this.uuid = uuid;
    }
    public String getName()
    {
        return name;
    }
 
    public void setname(String name)
    {
        this.name = name;
    }
    public String getUuid()
    {
        return uuid;
    }
 
    public void setuuid(String uuid)
    {
        this.uuid = uuid;
    }
    @Override
    public String toString()
    {
        return "name:"+ name 
                + "uuid" + uuid;
    }
}
