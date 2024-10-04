import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class Vehicle
{
    protected String owner;
    private LocalDateTime arrivalTime;

    public Vehicle(String ownerName)
    {
        // initialise instance variables
       
        owner = ownerName;
        arrivalTime = LocalDateTime.now();
    }
    
    public String getOwner()
    {
        // put your code here
        return owner;
    }

    public LocalDateTime getArrivalTime()
    {
        // put your code here
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