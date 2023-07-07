/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Azure.Trips;

import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author xzims
 */
public class TripObj {
    private UUID tripID;
    private String location;
    private String date;
    private WF[] WF;
    private UsersInterested usersInterested;
    
    public TripObj(UUID tripID, String location,String date,WF[] WF,UsersInterested usersInterested ){
      this.tripID = tripID;
      this.location = location;
      this.date = date;
      this.WF = WF;
      this.usersInterested = usersInterested;
   }
    
    public UUID getTripID()
    {
        return tripID;
    }
 
    public void setTripID(UUID tripID)
    {
        this.tripID = tripID;
    }
    public String getLocation()
    {
            return location;
    }
 
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public String getDate()
    {
            return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public WF[] getWF()
    {
            return WF;
    }
    public void setWf(WF[] WF)
    {
        this.WF = WF;
    }
    public void setUsersInterested(UsersInterested usersInterested)
    {
        this.usersInterested = usersInterested;
    }
    public UsersInterested getUsersInterested()
    {
            return usersInterested;
    }
    @Override
    public String toString()
    {
        return "tripID:"+ tripID 
                + "location:" + location
                + "date:" + date 
                + "WF:" + WF 
                + "usersInterested:" + usersInterested;
    }
}
