/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Azure.Trips;

import java.util.Arrays;
import java.util.UUID;

public class Trip {
    private UUID tripID;
    private String location;
    private String date;
    private WF[] WF;
    private UsersInterested usersInterested;

    public UUID getTripID() { return tripID; }
    public void setTripID(UUID value) { this.tripID = value; }

    public String getLocation() { return location; }
    public void setLocation(String value) { this.location = value; }

    public String getDate() { return date; }
    public void setDate(String value) { this.date = value; }

    public WF[] getWF() { return WF; }
    public void setWF(WF[] value) { this.WF = value; }

    public UsersInterested getUsersInterested() { return usersInterested; }
    public void setUsersInterested(UsersInterested value) { this.usersInterested = value; }
    @Override
    public String toString()
    {
        return "tripID:"+ tripID 
                + "location:" + location
                + "date:" + date 
                + "WF:" + Arrays.toString(WF) 
                + "usersInterested:" + usersInterested;
    }
}