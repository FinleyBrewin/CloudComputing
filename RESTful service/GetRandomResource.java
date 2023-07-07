/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package Azure;

import Azure.Random.UUIDObj;
import Azure.Random.RandomOrg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;


@Path("getRandom")
public class GetRandomResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GerRandomResource
     */
    public GetRandomResource() {
    }

    /**
     * Retrieves GET request and sends API call to random.org
     * @return JSON formatted UUID 
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        String reply = "{\"msg\":\"Error... could not communicate with random.org\"}";
        try {
            getRandomUUIDs();
        } catch (ProtocolException ex) { 
        } catch (MalformedURLException ex) { 
        } catch (IOException ex) {
        }
        try {
        //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        
        //Reading the content of the JSON file(RandomOrg.JSON)
        String jsonString = "";
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\RandomOrg.json"));
        for (String line : lines) {
            jsonString += line;
            }
        //Converts JSON into java object
        RandomOrg rObj = gson.fromJson(jsonString, RandomOrg.class);
        UUID[] rndm = rObj.getResult().getRandom().getData();
        //Selects UUID from the JSON and converts to object
        UUIDObj data = new UUIDObj();
        data.setUUID(rndm);
        String UUID = data.toString().substring(1, data.toString().length() - 1);
         UUID = "{\"UUID\":\""+UUID+"\"}";
        //Converts UUID back into JSON format to send
        reply = UUID;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reply;
    }

    /**
     * PUT method for updating or creating an instance of GetRandomResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }
public void getRandomUUIDs() throws UnsupportedEncodingException, ProtocolException, MalformedURLException, IOException {
        //Method to send API call to random.org
        int ID = 1;
        String request = "{\n" +
            "    \"jsonrpc\": \"2.0\",\n" +
            "    \"method\": \"generateUUIDs\",\n" +
            "    \"params\": {\n" +
            "        \"apiKey\": \"6bd162cd-955d-49af-9a67-5dd8bb7ad612\",\n" +
            "        \"n\": 1\n" +
            "    },\n" +
            "    \"id\": "+ ID +"\n" +
            "}";
        ID =+1;
        URL url = new URL("https://api.random.org/json-rpc/4/invoke");
        //Setting up HTTP connection to random.org
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("POST");
        c.setRequestProperty("Accept", "application/json");
        c.setRequestProperty("Content-Type", "application/json");
        c.setDoOutput(true);
        
        OutputStream os = c.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(request);
        osw.flush();
        osw.close();
        os.close();
        
        c.connect();
        //Output stream reader to catch reply
        BufferedReader reply = new BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
        
        String line;
        String answer = "";
        while ((line = reply.readLine()) != null) {
                answer = answer + line;
        }
        //Saves reply to a JSON file
        try {
               FileWriter myWriter = new FileWriter("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\AzureService\\src\\java\\Azure\\RandomOrg.json");
               myWriter.write(answer);
               myWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
