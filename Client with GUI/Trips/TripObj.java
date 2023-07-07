/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Trips;

import TravelBuddyGUI.UsersInterested;
import java.util.UUID;

/**
 *
 * @author xzims
 */
public class TripObj {
    private String tripID;
    private String location;
    private String date;
    private String weather;
    private UsersInterested usersInterested;
    
    public TripObj(String tripID, String location,String date,String weather,UsersInterested usersInterested ){
      this.tripID = tripID;
      this.location = location;
      this.date = date;
      this.weather = weather;
      this.usersInterested = usersInterested;
   }
    
    public String getTripID()
    {
        return tripID;
    }
 
    public void setTripID(String tripID)
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
    public void setWeather(String weather)
    {
        this.weather = weather;
    }
    public String getWeather()
    {
            return weather;
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
                + "weather:" + weather 
                + "usersInterested:" + usersInterested;
    }
}
