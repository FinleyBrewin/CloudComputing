/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package Azure;

import Azure.Weather.Datasery;
import Azure.Weather.GetWeather;
import Azure.Weather.WeatherObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("GetWeather")
public class GetWeatherResource {

    @Context
    private UriInfo context;

    public GetWeatherResource() {
    }

    /**
     * Retrieves GET request and sends API call to find weather using lon and lat params
     * @param lon
     * @param lat
     * @return next 7 dates weather in JSON format
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHTML(@QueryParam("lon")double lon,@QueryParam("lat")double lat ) {
        String reply = "{\"msg\":\"Error... could not communicate with 7timer\"}";;
        try {
            getWeather(lon, lat);
        } catch (ProtocolException ex) {
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        
        try {
        //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        //Reading the content of the JSON file(Weather.JSON)
        String jsonString = "";
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\Weather.json"));
        for (String line : lines) {
            jsonString += line;
            }
        //Converts JSON into java strings
        GetWeather wf = gson.fromJson(jsonString, GetWeather.class);
        Datasery[] forecast = wf.getDataseries();
        WeatherObj WeatherList[] = new WeatherObj[forecast.length];
        //Selects date and weather data from formatted json 
        for (int i=0; i<forecast.length; i++) {
            WeatherList[i] = new WeatherObj(forecast[i].getDate(), forecast[i].getWeather());
        }
        //converts data back to JSON
        reply = new Gson().toJson(WeatherList);
        reply = "{\"WF\":" + reply + "}";
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }

        return reply;
    }

    /**
     * PUT method for updating or creating an instance of GetWeatherResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }
    
    public void getWeather(double lon, double lat) throws UnsupportedEncodingException, ProtocolException, MalformedURLException, IOException {
        //Method to call GET request to Weather website using lon lat params   
        URL url = new URL("https://www.7timer.info/bin/api.pl?lon="+lon+"&lat="+lat+"&product=civillight&output=json");
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("GET");
        c.connect();
        
        //Input stream reader to get reply from api call
        BufferedReader reply = new BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
        
        String line;
        String answer = "";
        while ((line = reply.readLine()) != null) {
                answer = answer + line;
        }
        //Saves JSON to JSON file
        try {
               FileWriter myWriter = new FileWriter("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\Weather.json");
               myWriter.write(answer);
               myWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
