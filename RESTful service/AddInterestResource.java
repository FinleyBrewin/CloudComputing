/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package Azure;

import Azure.Trips.Trip;
import Azure.Trips.Trips;
import Azure.Trips.UsersInterested;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xzims
 */
@Path("AddInterest")
public class AddInterestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SetTripInterestResource
     */
    public AddInterestResource() {
    }

    /**
     * Retrieves representation of an instance of Azure.AddInterestResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "This application requires the use of a POST request in the JSON format. \nPlease try again...";
    }

    /**
     * PUT method for updating or creating an instance of AddInterestResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String  posthtml(String content) throws IOException {
        String JSON = content;
        String reply = "Error could not complete action";
         reply = setInterest(JSON);
         String finalReply = "{\"msg\":\""+reply+"\"}";
        return finalReply;
}
 public static String setInterest(String JSON) throws IOException  {
     String URL = "C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\Trips\\Trips.json";
     String reply = "Could not find any Trips with that ID.";
        //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        //Received User Interested
        Trips TripUserInterest = gson.fromJson(JSON, Trips.class);
        Trip[] TTripUserInterest = TripUserInterest.getTrips();
        
        String TripsString = "";
        //Current trips
        List<String> Lines = Files.readAllLines(Paths.get(URL));
        for (String Line : Lines) {
            TripsString += Line;
            }
        //Converting JSON files to java objects
        Trips TripsObj = gson.fromJson(TripsString, Trips.class);
        Trip[] cTrip = TripsObj.getTrips();
        //Finding Trips with the same Trip ID and updating User interests
        for (int i=0; i<cTrip.length; i++) {
            if (cTrip[i].getTripID() == null ? TTripUserInterest[0].getTripID() == null : cTrip[i].getTripID().equals(TTripUserInterest[0].getTripID())) {
                int n = cTrip[i].getUsersInterested().getUserID().length;
                n = n + TTripUserInterest[0].getUsersInterested().getUserID().length;
                //Gets old and new trip user ids and insert them into new string arrays
                String[] CurIDs = new String[cTrip[i].getUsersInterested().getUserID().length];
                CurIDs = cTrip[i].getUsersInterested().getUserID();
                String[] NewID = new String[TTripUserInterest[0].getUsersInterested().getUserID().length];
                NewID = TTripUserInterest[0].getUsersInterested().getUserID();
                //Checks if new ID is already in the existing interests
                String Dupe = null;
                for (int j=0; j<CurIDs.length; j++) {
                    if (CurIDs[j].equals(NewID[0])) {
                        Dupe="True";
                        break;
                    } else {
                        Dupe="False";
                    }
                }
                if ("False".equals(Dupe)) {
                    //Adds the new and old ID arrays into one
                    String[] AddedIDs = new String[n];
                    for (int l=0; l<CurIDs.length; l++) {
                        AddedIDs[l] = CurIDs[l];
                    }
                    AddedIDs[n-1] = NewID[0];
                    //Updates the Trip users interested to the updated array
                    UsersInterested NewInterested = new UsersInterested();
                    NewInterested.setUserID(AddedIDs); 
                    cTrip[i].setUsersInterested(NewInterested);
                    //Formats updated trips to JSON to reply with
                    String NewTrips = new Gson().toJson(cTrip);
                    NewTrips = NewTrips.substring(1, NewTrips.length() - 1);
                    String JSONTrips = "{\"Trips\":[" +NewTrips + "]}";
                    //Inserts the updated Trips to the JSON file
                    FileWriter myWriter = new FileWriter(URL);
                    myWriter.write(JSONTrips);
                    myWriter.close();
                    reply = "Successfully added your interest to the selected trips.";
                } else {
                    reply = "Your ID is already recorded as interested for this Trip.";
                } 
            }
        }
        return reply;
    }
}