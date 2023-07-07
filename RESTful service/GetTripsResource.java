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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import Azure.Trips.Trip;
import Azure.Trips.TripObj;
import Azure.Trips.Trips;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/**
 * REST Web Service
 *
 * @author xzims
 */
@Path("GetTrips")
public class GetTripsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetTripsResource
     */
    public GetTripsResource() {
    }

    /**
     * Retrieves representation of an instance of Azure.GetTripsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "This application requires the use of a POST request in the JSON format. \nPlease try again...";
    }

    /**
     * PUT method for updating or creating an instance of GetTripsResource
     * @param content representation for the resource
     * @return 
     * @throws java.io.IOException 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String  posthtml(String content) throws IOException {
        String JSON = content;
        String reply = "{\"msg\":\"Error could not complete action\"}";;
         reply = getTrip(JSON);

        
        return reply;
}
     public String getTrip(String JSON) throws IOException  {
         String json = JSON;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        //Receive wanted Trip Location
        Trips TripLoc = gson.fromJson(json, Trips.class);
        Trip[] TTripLoc = TripLoc.getTrips();

        String TripsString = "";
        //Current trips
        List<String> Lines = Files.readAllLines(Paths.get("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\Trips\\Trips.json"));
        for (String Line : Lines) {
            TripsString += Line;
           }
        //Converting JSON files to java objects
        Trips TripsObj = gson.fromJson(TripsString, Trips.class);
        Trip[] cTrip = TripsObj.getTrips();
        String reply = "{\"msg\":\"Could not find any Trips at that location.\"}";
        for (int i=0; i<cTrip.length; i++) {
            if (cTrip[i].getLocation().toLowerCase().equals(TTripLoc[0].getLocation().toLowerCase())) {
                //Checks how many values needed to fit in the array
                int n = 0;
                for (int l=0; l<cTrip.length; l++) {
                    if (cTrip[l].getLocation() == null ? TTripLoc[0].getLocation() == null : cTrip[l].getLocation().toLowerCase().equals(TTripLoc[0].getLocation().toLowerCase())) {
                    n=n+1;  
                    }
                }
                TripObj coTrip[] = new TripObj[n];
                //Finding Trips with the TripLoc and adding to array
                int c = 0;
                for (int x=0; x<cTrip.length; x++) {
                    if (cTrip[x].getLocation() == null ? TTripLoc[0].getLocation() == null : cTrip[x].getLocation().toLowerCase().equals(TTripLoc[0].getLocation().toLowerCase())) {
                        coTrip[c] = new TripObj(cTrip[x].getTripID(),cTrip[x].getLocation(),cTrip[x].getDate(),cTrip[x].getWF(),cTrip[x].getUsersInterested());
                        c=c+1;
                    }
                }
                //Formats outputted trips to JSON to reply with
                String SelTrips = new Gson().toJson(coTrip);
                SelTrips = SelTrips.substring(1, SelTrips.length() - 1);
                reply = "{\"Trips\":[" +SelTrips + "]}";
            }
        }
        
        return reply;   
     }
}
