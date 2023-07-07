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
@Path("CheckInterest")
public class CheckInterestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SetTripInterestResource
     */
    public CheckInterestResource() {
    }

    /**
     * Retrieves representation of an instance of Azure.AddTripInterestResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "This application requires the use of a POST request in the JSON format. \nPlease try again...";
    }

    /**
     * PUT method for updating or creating an instance of AddTripInterestResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String  posthtml(String content) throws IOException {
        String JSON = content;
        String reply = "{\"msg\":\"Error could not complete action\"}";;
         reply = checkInterest(JSON);

        return reply;
}
 public String checkInterest(String JSON) throws IOException  {
     String URL = "C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\Trips\\Trips.json";
     String reply = "{\"msg\":\"Could not find any Trips with that ID.\"}";
     
        //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        //Received User ID
        Trips TripCheckInterest = gson.fromJson(JSON, Trips.class);
        Trip[] TTripCheckInterest = TripCheckInterest.getTrips();
        
        String TripsString = "";
        //Current trips
        List<String> Lines = Files.readAllLines(Paths.get(URL));
        for (String Line : Lines) {
            TripsString += Line;
            }
        //Converting JSON files to java objects
        Trips TripsObj = gson.fromJson(TripsString, Trips.class);
        Trip[] cTrip = TripsObj.getTrips();
        //Finding Trips with the same Trip ID
        for (int i=0; i<cTrip.length; i++) {
            if (cTrip[i].getTripID() == null ? TTripCheckInterest[0].getTripID() == null : cTrip[i].getTripID().equals(TTripCheckInterest[0].getTripID())) {
                int n = cTrip[i].getUsersInterested().getUserID().length;
                n = n + TTripCheckInterest[0].getUsersInterested().getUserID().length;
                //Gets old and new trip user ids and insert them into new string arrays
                String[] CurIDs = new String[cTrip[i].getUsersInterested().getUserID().length];
                CurIDs = cTrip[i].getUsersInterested().getUserID();
                String[] NewID = new String[TTripCheckInterest[0].getUsersInterested().getUserID().length];
                NewID = TTripCheckInterest[0].getUsersInterested().getUserID();
                //Checks if user ID is the one who proposed trip
                if (CurIDs[0].equals(NewID[0])) {
                    //Gets Trips user IDs who are interested and converts to JSON to send
                        String UserIDsJSON = new Gson().toJson(cTrip[i].getUsersInterested());
                        UserIDsJSON = UserIDsJSON.substring(1, UserIDsJSON.length() - 1);
                        String JSONIDs = "{\"Trips\":[{\"usersInterested\":{" +UserIDsJSON + "}}]}";
                        reply = JSONIDs;
                } else {
                     reply = "{\"msg\":\"You did not propose this trip\"}";
                }
            }
        }
        return reply;
    }   
}