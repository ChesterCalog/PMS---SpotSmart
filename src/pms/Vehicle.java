package pms;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class Vehicle
{
    protected String owner;
    private LocalDateTime arrivalTime;

    public Vehicle(String ownerName)
    {
       
        owner = ownerName;
        arrivalTime = LocalDateTime.now();
    }
    
    public String getOwner()
    {
        return owner;
    }

    public LocalDateTime getArrivalTime()
    {
        return arrivalTime;
    }
    
    public long getTimeIn()
    {
        return arrivalTime.until(LocalDateTime.now(),ChronoUnit.MINUTES);
    }

    public String toString()
    {
     return "Owner: "+owner+" Arrival Time : "+arrivalTime+"\n Time In : "+getTimeIn()+"\n***********";   
    }
}