/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Trips;

public class WF {
    private long Date;
    private String Weather;

    public long getDate() { return Date; }
    public void setDate(long value) { this.Date = value; }

    public String getWeather() { return Weather; }
    public void setWeather(String value) { this.Weather = value; }
    @Override
    public String toString()
    {
        return "Weather [Date:"
            + Date + ", Weather:"
            + Weather +
            "]";
    }
}