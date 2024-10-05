package pms;

import java.time.LocalDateTime;

public class ParkingSlot
{
    private String id;
    private Vehicle vehicleInParkingSlot;
    private boolean isOccupied;
    private boolean isStaff;
    private LocalDateTime arrivalTime;

    public ParkingSlot(String sId)
    {
        this.id = sId;
        isOccupied = false;
        isStaff = false;
    }
    
    public ParkingSlot(String sId, boolean isStaff)
    {
        this.id = sId;
        isOccupied = false;
        this.isStaff = isStaff;
    }

    public void addVisitorVehicle(Vehicle vehicleInParkingSlot)
    {
        this.vehicleInParkingSlot = vehicleInParkingSlot;
        isOccupied = true;
        isStaff = false;
        arrivalTime = LocalDateTime.now();
    }

    public void addStaffVehicle(Vehicle vehicleInParkingSlot)
    {
        this.vehicleInParkingSlot = vehicleInParkingSlot;
        isOccupied = true;
        isStaff = true;
        arrivalTime = LocalDateTime.now();
    }
    
    public String getParkingSlotId()
    {
        return id;
    }
    
    public Vehicle removeVehicle()
    {
        isOccupied = false;
        return vehicleInParkingSlot;
    }

    public Vehicle getVehicle()
    {
        return vehicleInParkingSlot;
    }

    public boolean getIsOccupied()
    {
        return isOccupied;
    }
    
    public boolean getIsStaff()
    {
        return isStaff;
    }
    
    public void setStaff(boolean isStaff)
    {
        this.isStaff = isStaff;
    }
    
    public LocalDateTime getArrivalTime()
    {
        return arrivalTime;
    }
    
    public String toString()
    {
        if(vehicleInParkingSlot != null)
        {  
          /* we check if it is a car instance  */
          if(vehicleInParkingSlot.getClass().isInstance(Car.class))
          { 
            return "ParkingSlot Id : "+id+"\nVehicle In ParkingSlot : "+((Car)vehicleInParkingSlot).getregNum()+"\nStaff Slot : "+isStaff+"\nOccupied : "+(isOccupied?"Yes":"No")+"\nArrivalTime(atParkingSlot) : "+arrivalTime+"\n";
          }
        }         
        
        return"ParkingSlot Id : "+id+"\nStaffSlot : "+isStaff+"\nOccupied? : " + (isOccupied?"Yes\n":"No\n");
        
    }
}