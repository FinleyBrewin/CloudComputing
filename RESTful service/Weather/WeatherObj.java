/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Azure.Weather;


/**
 *
 * @author xzims
 */
public class WeatherObj {
    private long Date;
    private String Weather;
    
    public WeatherObj(Long date, String Weather){
      this.Date = date;
      this.Weather = Weather;
   }
    
    public long getDate()
    {
        return Date;
    }
 
    public void setDate(long Date)
    {
        this.Date = Date;
    }
    public String getWeather()
    {
            return Weather;
    }
 
    public void setWeather(String Weather)
    {
        this.Weather = Weather;
    }
    @Override
    public String toString()
    {
        return "Weather [Date:"
            + Date + ", Weather:"
            + Weather +
            "]";
    }
}

