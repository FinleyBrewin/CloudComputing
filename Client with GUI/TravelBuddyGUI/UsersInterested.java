/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TravelBuddyGUI;

import java.util.Arrays;

public class UsersInterested {
    private String[] userID;

    public String[] getUserID() { return userID; }
    public void setUserID(String[] value) { this.userID = value; }
    
    @Override
    public String toString()
    {
        return "userID:"+ Arrays.toString(userID) 
            ;
    }
}
