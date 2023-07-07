package TravelBuddyGUI;
 

import Trips.GetWeather;
import Trips.Trip;
import Trips.Trips;
import Trips.WF;
import Trips.WeatherObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
 
public class TravelBuddy implements ItemListener, ActionListener {
    JPanel cards; //a panel that uses CardLayout
    //Create and set up the window for each method
    static JFrame frame = new JFrame("TravelBuddy Program");
    final static String LOGIN = "Login";
    final static String MENU = "Menu";
    final static String CREATE = "Create Trip";
    final static String FIND = "Find Trip";
    final static String ADD = "Add Interest";
    final static String CHECK = "Check Interest";
     
    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { LOGIN, MENU, CREATE, FIND, ADD, CHECK };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        //Exit button panel
        JPanel exit = new JPanel();

        JButton ExitBtn = new JButton( new AbstractAction("Exit") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    });
        exit.add(ExitBtn);
        
        //Login Card
        JPanel Login = new JPanel();
        JLabel NameLabel = new JLabel("Name:");
        Login.add(NameLabel);
        JTextField NameTF = new JTextField("",20);
        Login.add(NameTF);
        JButton UserBtn = new JButton( new AbstractAction("Enter") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            if (!NameTF.getText().equals("")) {
                String Name = NameTF.getText(); 
                getInfo.setName(Name);
                try {
                    newUser();
                } catch (IOException ex) {
                }
            } 
        }
    });
        Login.add(UserBtn);
         
        //Menu Card
        JPanel Menu = new JPanel();
        JLabel ShowNameLabel = new JLabel("");
        Menu.add(ShowNameLabel);
        //New Trip Button
        JButton CreateTripBtn = new JButton( new AbstractAction("Create a Trip") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, CREATE);
        }
    });
        Menu.add(CreateTripBtn);
        //Find Trips Button
        JButton FindTripsBtn = new JButton( new AbstractAction("Find Trips") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, FIND);
        }
    });
        Menu.add(FindTripsBtn);
        //Add interest Button
        JButton AddInterestBtn = new JButton( new AbstractAction("Add Interest") {
        @Override
        public void actionPerformed( ActionEvent e ) {
           CardLayout cl = (CardLayout)(cards.getLayout());
           cl.show(cards, ADD);
        }
    });
        Menu.add(AddInterestBtn);
        //Check interest Button
        JButton CheckInterestBtn = new JButton( new AbstractAction("Check Interest") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, CHECK);
        }
    });
        Menu.add(CheckInterestBtn);
        String identifier = "Welcome";
        //Label to show name and UUID
        ShowNameLabel.setText(identifier);
        
        
        //New Trip Card
        JPanel Info = new JPanel();
        Info.add(new JLabel("Trip Info:"));
        //Configuring output field
        JTextArea TripDetailsLabel = new JTextArea("");
        TripDetailsLabel.setLineWrap(true);
        TripDetailsLabel.setWrapStyleWord(true);
        TripDetailsLabel.setOpaque(false);
        TripDetailsLabel.setEditable(false);
        Info.add(TripDetailsLabel);
        JPanel Create = new JPanel();
        Create.add(Info);
        Create.add(new JLabel("Location:"));
        JTextField LocationTF = new JTextField("", 20);
        Create.add(LocationTF);
        Create.add(new JLabel("Date:"));
        JTextField DateTF = new JTextField("", 20);
        Create.add(DateTF);
        JLabel errors = new JLabel("");
        Create.add(errors);
        //back to menu button
        JButton MenuBtn = new JButton( new AbstractAction("Menu") {
        @Override
        public void actionPerformed( ActionEvent e ) {
         CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, MENU);   
        }
        });
        Create.add(MenuBtn);
        MenuBtn.hide();
        //SubmitButton
        JButton SubmitBtn = new JButton( new AbstractAction("Submit") {
        @Override
        //Action when button is pressed
        public void actionPerformed( ActionEvent e ) {
            //Check if the input field contents is valid
            if (!LocationTF.getText().equals("") || !DateTF.getText().equals("")) {
                if (LocationTF.getText().length() > 0 && LocationTF.getText().length() < 21 && LocationTF.getText().matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$")){
                 String Location = LocationTF.getText();
                 try{
                    int Input = Integer.parseInt(DateTF.getText());
                    if (DateTF.getText().length() == 8) {
                        //if valid get contents and call new trip method
                        String Date = DateTF.getText();
                        String[] output = new String[2];
                        output = newTrip(Location, Date);
                        String TripInfo = " TripUUID: " + output[0]+ " \nLocation: "+ Location+" \nDate: "+Date+" \nWeather: "+output[1];
                        TripDetailsLabel.setText(TripInfo);
                        //show menu button
                        MenuBtn.show();
                        LocationTF.setText("");
                        DateTF.setText("");
                    } else {
                        errors.setText("Date input invalid");
                    }
                } 
                catch (NumberFormatException ex){
                    errors.setText("Date input invalid");
                }   catch (IOException ex) { 
                        Logger.getLogger(TravelBuddy.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                } else { 
                    errors.setText("Location input invalid");
                }                
            }
        }
    });
        Create.add(SubmitBtn);
        //Find trips card
        JPanel Find = new JPanel();
        JTextArea outputLabel = new JTextArea("");
        //outputLabel.setLineWrap(true);
        outputLabel.setWrapStyleWord(true);
        outputLabel.setOpaque(false);
        outputLabel.setEditable(false);
        
        Find.add(outputLabel);
        JLabel LocationLabel = new JLabel("Location");
        Find.add(LocationLabel);
        JTextField FLocationFT = new JTextField("", 20);
        Find.add(FLocationFT);
        JLabel errorsFind = new JLabel("");
        Find.add(errorsFind);
        //back to menu button
        JButton FMenuBtn = new JButton( new AbstractAction("Menu") {
        @Override
        public void actionPerformed( ActionEvent e ) {
         CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, MENU);   
        }
        });
        Find.add(FMenuBtn);
        FMenuBtn.hide();
        //Find Trip Button
        JButton FindTripBtn = new JButton( new AbstractAction("Find") {
        @Override
        //Action when button is pressed
        public void actionPerformed( ActionEvent e ) {
            //Check if the input field contents is valid
            if (FLocationFT.getText().length() > 0 && FLocationFT.getText().length() < 21 && FLocationFT.getText().matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$")){
                //if input is valid call find trip method
                String Location = FLocationFT.getText();
                String output = findTrip(Location);
                outputLabel.setText(output);
                //Reset input boxes and show menu button
                FMenuBtn.show();
                FLocationFT.setText("");
            }else {
                errorsFind.setText("Input is invalid");
                errorsFind.setForeground(Color.red);
            }
        }
    });
        Find.add(FindTripBtn);
        //Add interest Card
        JPanel Add = new JPanel();
        JTextArea AoutputLabel = new JTextArea("");
        AoutputLabel.setLineWrap(true);
        AoutputLabel.setWrapStyleWord(true);
        AoutputLabel.setOpaque(false);
        AoutputLabel.setEditable(false);
        Add.add(AoutputLabel);
        JLabel TripUUIDLabel = new JLabel("Trip UUID:");
        Add.add(TripUUIDLabel);
        JTextField TripUUIDFT = new JTextField("", 20);
        Add.add(TripUUIDFT);
        JLabel errorsAdd = new JLabel("");
        Add.add(errorsAdd);
        //back to menu button
        JButton AMenuBtn = new JButton( new AbstractAction("Menu") {
        @Override
        public void actionPerformed( ActionEvent e ) {
         CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, MENU);   
        }
        });
        Add.add(AMenuBtn);
        AMenuBtn.hide();
        //Add interest Button
        JButton AddBtn = new JButton( new AbstractAction("Add") {
        @Override
        //Action when button is pressed
        public void actionPerformed( ActionEvent e ) {
            //Check if the input field contents is valid
            if (TripUUIDFT.getText().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")){
                //If the input is correct call the add interest method
                String tripUUID = TripUUIDFT.getText();
                String output = addInterest(tripUUID);
                AoutputLabel.setText(output);
                //Reset input boxes and show menu button
                AMenuBtn.show();
                TripUUIDFT.setText("");
            }else {
                errorsAdd.setText("Input is invalid");
                errorsAdd.setForeground(Color.red);
            }
        }
    });
        Add.add(AddBtn);
        //Check interest Card
        JPanel Check = new JPanel();
        JTextArea CoutputLabel = new JTextArea("");
        CoutputLabel.setLineWrap(true);
        CoutputLabel.setWrapStyleWord(true);
        CoutputLabel.setOpaque(false);
        CoutputLabel.setEditable(false);
        Check.add(CoutputLabel);
        JLabel CTripUUIDLabel = new JLabel("Trip UUID:");
        Check.add(CTripUUIDLabel);
        JTextField CTripUUIDFT = new JTextField("", 20);
        Check.add(CTripUUIDFT);
        JLabel errorsCheck = new JLabel("");
        Check.add(errorsCheck);
        //back to menu button
        JButton CMenuBtn = new JButton( new AbstractAction("Menu") {
        @Override
        public void actionPerformed( ActionEvent e ) {
         CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, MENU);   
        }
        });
        Check.add(CMenuBtn);
        CMenuBtn.hide();
        //Check interest Button
        JButton CheckBtn = new JButton( new AbstractAction("Check") {
        @Override
        //Action when button is pressed
        public void actionPerformed( ActionEvent e ) {
            //Check if the input field contents is valid
            if (CTripUUIDFT.getText().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")){
                //If the input is correct call the add interest method
                String tripUUID = CTripUUIDFT.getText();
                String output = checkInterest(tripUUID);
                CoutputLabel.setText(output);
                //Reset input boxes and show menu button
                CMenuBtn.show();
                CTripUUIDFT.setText("");
            }else {
                errorsCheck.setText("Input is invalid");
                errorsCheck.setForeground(Color.red);
            }
        }
    });
        Check.add(CheckBtn);
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(Login, LOGIN);
        cards.add(Menu, MENU);
        cards.add(Create, CREATE);
        cards.add(Find, FIND);
        cards.add(Add, ADD);
        cards.add(Check, CHECK);
         
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(exit, BorderLayout.PAGE_END);
    }
     
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        TravelBuddy demo = new TravelBuddy();
        demo.addComponentToPane(frame.getContentPane());
         
        //Display the window.
        frame.setSize(450, 500);
        //frame.pack();
        frame.setVisible(true);
    }
    public void newUser() throws ProtocolException, IOException{
        System.out.println("in newuser method");
        //Creating Gson object
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String newName = getInfo.getName();
        //String UUID = "3c1f6efd-f6fa-4ad1-9b4b-5351968cdbca";
      
        String UserString = "";
        //get Users from JSON file
        java.util.List<String> Lines = Files.readAllLines(Paths.get("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\testGUI\\src\\TravelBuddyGUI\\Users.json"));
        for (String Line : Lines) {
            UserString += Line;
        }
        //Creating users object
        Users User = gson.fromJson(UserString, Users.class);
        User[] Users = User.getUsers();
        //Check whether the new name already exists
        String Dupe = null;
        for (int i=0; i<Users.length; i++) {  
            if (Users[i].getName().toLowerCase().equals(newName.toLowerCase())) {
                Dupe = "True";
                System.out.println("Found user");
                currentUser();
                break;
            }else {
                Dupe = "False";
            }
        }
        if ("False".equals(Dupe)){
            String UUID = "00000000-0000-0000-0000-000000000000";
            try {
                //Getting random UUID
                String uuidString = getRandom();
                getUUID uuid = gson.fromJson(uuidString, getUUID.class);
                UUID = uuid.getUUID();
            } catch (ProtocolException ex) {
                System.out.println("Error connecting to server, using default UUID");
            } catch (IOException ex) {
                System.out.println("Error connecting to server, using default UUID");
            }
            System.out.println("Got ID: "+UUID);
            getInfo.setUUID(UUID);
            String TotalUser = "{\"Users\":"+Arrays.toString(Users);
            TotalUser = TotalUser.substring(0,TotalUser.length() - 1);
            String TotalUsers = TotalUser+",{\"name\":\""+newName+"\",\"uuid\":\""+UUID+"\"}]}";
            
            FileWriter myWriter = new FileWriter("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\testGUI\\src\\TravelBuddyGUI\\Users.json");
            myWriter.write(TotalUsers);
            myWriter.close();
            currentUser();
        }      
    }
    public void currentUser() throws IOException{
        System.out.println("Inside Current user method");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String name = getInfo.getName();
        System.out.println("Name: " +name);
  
        String UserString = "";
        //get Users from JSON file
        java.util.List<String> Lines = Files.readAllLines(Paths.get("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\testGUI\\src\\TravelBuddyGUI\\Users.json"));
        for (String Line : Lines) {
            UserString += Line;
            }
            //Creating users object
            Users User = gson.fromJson(UserString, Users.class);
            User[] Users = User.getUsers();
            String UUID = "";
            //Getting user UUID
            for (int i=0; i<Users.length; i++) {  
                if (Users[i].getName().toLowerCase().equals(name.toLowerCase())) {
                    UUID = Users[i].getUUID();
                    getInfo.setUUID(UUID);
                    break;
                } 
            }
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, MENU);
    }
    public static String[] newTrip(String location, String date) throws ProtocolException, IOException{
        //Method for new trip
        //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        //Setting variables
        String Location = location;
        String Date = date;
        String Weather = "";
        String UserInt = getInfo.getUUID();
        String TripID = "00000000-0000-0000-0000-000000000000";
        try {
            //Getting random trip UUID
            String uuidString = getRandom();
            getUUID uuid = gson.fromJson(uuidString, getUUID.class);
            TripID = uuid.getUUID();
        } catch (ProtocolException ex) {
            System.out.println("Error connecting to server, using default UUID");
        } catch (IOException ex) {
        }

       
        //getting weather forecast
        System.out.println("Here is the weather forecast for your location...");
        double lon = -1.204350;
        double lat = 52.770771;
        String WeatherString = getWeather(lon,lat);
        
        //Converting Weather JSON into object array
        GetWeather wf = gson.fromJson(WeatherString, GetWeather.class);
        WF[] forecast = wf.getWF();
        WeatherObj WeatherList[] = new WeatherObj[forecast.length];
        String weather = "Weather Forecast: ";
        for (int i=0; i<forecast.length; i++) {
            WeatherList[i] = new WeatherObj(forecast[i].getDate(), forecast[i].getWeather());
            //System.out.println("Date: "+forecast[i].getDate()+ " Weather: "+forecast[i].getWeather());
            weather = weather + ("\nDate: "+forecast[i].getDate()+ " Weather: "+forecast[i].getWeather());
        }
        //Converting formatted Weather JSON into JSON
        Weather = new Gson().toJson(WeatherList);
        Weather = "[{\"WF\":" + Weather;
        Weather = Weather.substring(2, Weather.length() - 0);
        //Format data to JSON to send to server
        String JSON = "{\"Trips\":[{\"tripID\":\""+TripID+"\",\"location\":\""+Location+"\",\"date\":\""+Date+"\","+Weather+",\"usersInterested\":{\"userID\":[\""+UserInt+"\"]}}]}";
        System.out.println(JSON);
        URL url;
        try {
            url = new URL("http://localhost:8080/AzureService/webresources/NewTrip");
        
        //Get reply and convert to Java object
        String reply = send(JSON,url);
        System.out.println("Received message:");
        //checks contents of reply to see if its trip content or a msg
        if (reply.contains("msg")) {
            getMsg Msg = gson.fromJson(reply, getMsg.class);
            String msg = Msg.getMsg();
            System.out.println(msg);
        } else {
            System.out.println("Did not get a reply from the server, please try again later...");
        }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        String[] reply = new String[2];
        reply[1] = weather;
        reply[0] = TripID;
        return reply;
    }
    public static String findTrip(String location)  {
    //Method for finding trips with location
    //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
    String Trip = "";
    String Location = location;

    String JSON = ("{\"Trips\":[{\"location\":\""+Location+"\"} ]}");
    //send post to server
    //Capture reply and check what reply it is
    try {  
        URL url = new URL("http://localhost:8080/AzureService/webresources/GetTrips");
        //Get reply and convert to Java object
        String reply = send(JSON,url);
        //checks contents of reply to see if its trip content or a msg
        if (reply.contains("msg")) {
            //if reply is a msg
            getMsg Msg = gson.fromJson(reply, getMsg.class);
            Trip = Msg.getMsg();
        }
        if (reply.contains("\"Trips\"")){
            //if reply is a json of trips
            Trips TripsInterested = gson.fromJson(reply, Trips.class);
            Trip[] TTripUserInterest = TripsInterested.getTrips();
            System.out.println("Here are the trips we found at that location:");
            for (int i=0; i<TTripUserInterest.length; i++) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Trip ID: "+TTripUserInterest[i].getTripID()+"\nDate: "+TTripUserInterest[i].getDate()+
                                   "\nNumber of people interested: "+TTripUserInterest[i].getUsersInterested().getUserID().length+
                                   "\nWeather forecast: ");
                 Trip = ("Trip ID: "+TTripUserInterest[i].getTripID()+"\nDate: "+TTripUserInterest[i].getDate()+
                                   "\nNumber of people interested: "+TTripUserInterest[i].getUsersInterested().getUserID().length+
                                   "\nWeather forecast: ");
                //gets weather forecast
                WF[] forecast = TTripUserInterest[i].getWF();
                WeatherObj WeatherList[] = new WeatherObj[forecast.length];
                for (int l=0; l<forecast.length; l++) {
                    WeatherList[l] = new WeatherObj(forecast[l].getDate(), forecast[l].getWeather());
                    System.out.println("Date: "+forecast[l].getDate()+ " Weather: "+forecast[l].getWeather());
                    Trip = Trip + "\nDate: "+forecast[l].getDate()+ " Weather: "+forecast[l].getWeather();
                    }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }  
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return Trip;
    }
    public static String addInterest(String TripUUID)  { 
    //Add interest to an existing trip
    //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
    String userUUID = getInfo.getUUID();
    String tripUUID = TripUUID;
    String msg = ""; 
        String JSON = "{\"Trips\":[{\"tripID\":\""+tripUUID+"\",\"usersInterested\":{\"userID\":[\""+userUUID+"\"]}} ]}";
        try {
            URL url = new URL("http://localhost:8080/AzureService/webresources/AddInterest");
        //Get reply and convert to Java object
        String reply = send(JSON,url);
        System.out.println("Received message: \n");
        //checks contents of reply to see if its trip content or a msg
        if (reply.contains("msg")) {
            getMsg Msg = gson.fromJson(reply, getMsg.class);
            msg = Msg.getMsg();
            System.out.println(msg);
        } else {
            System.out.println("Did not get a reply from the server, please try again later...");
        }
        } catch (MalformedURLException ex) {
            System.out.println("An error occured please try again later..");
        } catch (IOException ex) {
            System.out.println("An error occured please try again later..");
        }
        return msg;
    }
    public static String checkInterest(String TripUUID) {
    //Check interst of proposed trip
    //Code to set up GSON  
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
    String userUUID = getInfo.getUUID();
    String tripUUID = TripUUID;
    String msg = "";

        String JSON = "{\"Trips\":[{\"tripID\":\""+tripUUID+"\",\"usersInterested\":{\"userID\":[\""+userUUID+"\"]}} ]}";
        //Get reply and check msg
        try {
            URL url = new URL("http://localhost:8080/AzureService/webresources/CheckInterest");
        //Get reply and convert to Java object
        String reply = send(JSON,url);
        //checks contents of reply to see if its trip content or a msg
        if (reply.contains("msg")) {
            getMsg Msg = gson.fromJson(reply, getMsg.class);
            msg = Msg.getMsg();
            System.out.println(msg);
        }
        if (reply.contains("Trips")){
            //if reply is a json of trips
            Trips TripsInterested = gson.fromJson(reply, Trips.class);
            Trip[] TTripUserInterest = TripsInterested.getTrips();
            System.out.println("These users are interested in your trip: ");
            for (int i=0; i<TTripUserInterest.length; i++) {
                String[] CurIDs = new String[TTripUserInterest[i].getUsersInterested().getUserID().length];
                CurIDs = TTripUserInterest[i].getUsersInterested().getUserID();
                //Searches user list to find names from UUIDs
                for (int l=0; l<CurIDs.length; l++) {
                    String UserString = "";
                    //get Users from JSON file
                    java.util.List<String> Lines = Files.readAllLines(Paths.get("C:\\Users\\xzims\\Documents\\uniwork\\Year 3\\Cloud\\CourseWork\\testGUI\\src\\TravelBuddyGUI\\Users.json"));
                    for (String Line : Lines) {
                        UserString += Line;
                        }
                    //Creating users object
                    Users User = gson.fromJson(UserString, Users.class);
                    User[] Users = User.getUsers();
                    //Getting users name
                    for (int x=0; x<Users.length; x++) {  
                    if (Users[x].getUUID().toLowerCase().equals(CurIDs[l].toLowerCase())) {
                        System.out.println(Users[x].getName());
                        msg = msg+Users[x].getName() + "\n";
                        break;
                        } 
                    }
                }
            }
        }
        } catch (MalformedURLException ex) {
            System.out.println("An error occured please try again later..");
        } catch (IOException ex) {
            System.out.println("An error occured please try again later..");
        }
        return msg;
    }
    public static String getRandom() throws MalformedURLException, ProtocolException, IOException {
            URL url = new URL("http://localhost:8080/AzureService/webresources/getRandom");
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
        return answer;
        }
    public static String getWeather(double lon, double lat) throws MalformedURLException, ProtocolException, IOException {
            URL url = new URL("http://localhost:8080/AzureService/webresources/GetWeather?lon="+lon+"&lat="+lat+"");
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
        return answer;
        }
    public static String send(String JSON, URL url) throws MalformedURLException, ProtocolException, IOException {
        //Method to send POST requests to servuice
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("POST");
        c.setRequestProperty("Accept", "application/json");
        c.setRequestProperty("Content-Type", "application/json");
        c.setDoOutput(true);
        
        //Formats JSON msg to send to server and sends it
        OutputStream os = c.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(JSON);
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
        return answer;
    }
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}