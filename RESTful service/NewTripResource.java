/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package Azure;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import Azure.Trips.Trip;
import Azure.Trips.TripObj;
import Azure.Trips.Trips;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ws.rs.POST;


/**
 * REST Web Service
 *
 * @author xzims
 */
@Path("NewTrip")
public class NewTripResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of NewTripResource
     */
    public NewTripResource() {
    }

    /**
     * Retrieves representation of an instance of Azure.NewTripResource
     * @return an instance of java.lang.String
     * @throws java.io.IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getText() throws IOException {
        return "This application requires the use of a POST request in the JSON format. \nPlease try again...";
    }

    /**
     * PUT method for updating or creating an instance of NewTripResource
     * @param content representation for the resource
     * @throws java.io.IOException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putText(String content) throws IOException {
        
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String postText(String content) throws IOException {
        String JSON = content;
        String Reply = "Error could not complete action";
        //Check if file trips.json exists
        String FileURL= "C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\Trips\\Trips.json";
        
        Reply = addTrip(JSON, FileURL);
        String finalReply = "{\"msg\":\""+Reply+"\"}";
        return finalReply;
    }
    public static String addTrip(String JSON, String URL) throws IOException {
        String reply = "";
        //Code to set up GSON
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        //Trip to add
        String TripString = JSON;
         String TripsString = "";
        //Current trips
        List<String> Lines = Files.readAllLines(Paths.get(URL));
        for (String Line : Lines) {
            TripsString += Line;
            }
            //Converting JSON files to java objects
            Trips TripsObj = gson.fromJson(TripsString, Trips.class);
            Trip[] cTrip = TripsObj.getTrips();
            TripObj coTrip[] = new TripObj[cTrip.length];
            //Formatting JSON file to add new trip
            for (int i=0; i<cTrip.length; i++) {
                coTrip[i] = new TripObj(cTrip[i].getTripID(),cTrip[i].getLocation(),cTrip[i].getDate(),cTrip[i].getWF(),cTrip[i].getUsersInterested());
            }
            //Formatting new trip JSON
            Trips nTripObj = gson.fromJson(TripString, Trips.class);
            Trip[] nTrip = nTripObj.getTrips();
            TripObj neTrip[] = new TripObj[nTrip.length];
            for (int i=0; i<nTrip.length; i++) {
                neTrip[i] = new TripObj(nTrip[i].getTripID(),nTrip[i].getLocation(),nTrip[i].getDate(),nTrip[i].getWF(),nTrip[i].getUsersInterested());
            }
            //Check whether the new Trip ID already exists
            String Dupe = null;
            for (int i=0; i<cTrip.length; i++) {
                if (cTrip[i].getTripID().equals(nTrip[0].getTripID())) {
                    Dupe = "True";
                    break;
                } else {
                    Dupe = "False";
                }
            }
            //if the dupe check is false then there is no dupe id
            if ("False".equals(Dupe)){
                String ExTrips = new Gson().toJson(coTrip);
                ExTrips = ExTrips.substring(1, ExTrips.length() - 1);
                String NewTrip = new Gson().toJson(neTrip);
                NewTrip = NewTrip.substring(1, NewTrip.length() - 1);
                //Adding new trip to current trips
                String ComTrips = ExTrips +","+ NewTrip;
                ComTrips = "{\"Trips\":[" +ComTrips + "]}";
                
                //Replacing Trip File with updated data
                FileWriter myWriter = new FileWriter(URL);
                myWriter.write(ComTrips);
                myWriter.close();  
                reply = ("Successfully added Trip with Trip ID: "  + neTrip[0].getTripID().toString() ); 
            } else {
                reply = ("The Trip ID: "+neTrip[0].getTripID().toString()+" is already taken");
            }   
        return reply;
    }

}

