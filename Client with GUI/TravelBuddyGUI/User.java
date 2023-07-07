
package TravelBuddyGUI;

public class User {
    private String name;
    private String uuid;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getUUID() { return uuid; }
    public void setUUID(String value) { this.uuid = value; }
    @Override
    public String toString()
    {
        return "{\"name\":\""+name+"\"," 
                +"\"uuid\":\""+uuid+"\"}";
    }
}