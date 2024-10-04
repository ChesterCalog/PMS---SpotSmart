
import java.util.ArrayList;

public class CarPark
{
    private ArrayList<ParkingSlot> ParkingSlots;

    public CarPark()
    {
        ParkingSlots = new ArrayList<ParkingSlot>();
    }

    public void addNewParkingSlot(ParkingSlot aParkingSlot)
    {
        // put your code here
        ParkingSlots.add(aParkingSlot);
    }
    
    public boolean removeAParkingSlot(String spotId)
    {
        for(ParkingSlot aParkingSlot : ParkingSlots)
        {
            if(aParkingSlot.getParkingSlotId().equalsIgnoreCase(spotId) && !aParkingSlot.getIsOccupied())
            {
                ParkingSlots.remove(aParkingSlot);
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<ParkingSlot> getAvailableParkingSlots()
    {
        ArrayList<ParkingSlot> availParkingSlots = new ArrayList<ParkingSlot>();
        for(ParkingSlot aParkingSlot : ParkingSlots)
        {
            if(!aParkingSlot.getIsOccupied())
                availParkingSlots.add(aParkingSlot);
        }
        return availParkingSlots;
    }

    public ArrayList<ParkingSlot> findVehicleOwner(String ownerName)
    {
        ArrayList<ParkingSlot> foundParkingSlots = new ArrayList<ParkingSlot>();
        for(ParkingSlot aParkingSlot : ParkingSlots)
        {
            if(aParkingSlot.getVehicle()!=null )
            {  
                if(aParkingSlot.getVehicle().getOwner().equals(ownerName) && aParkingSlot.getIsOccupied())
                {
                    foundParkingSlots.add(aParkingSlot);
                    
                }    
            }
        }   
            return foundParkingSlots;
    
    }

    public ArrayList<ParkingSlot> findVehicleRegNum(String RegNum)
    {
        ArrayList<ParkingSlot> foundParkingSlots = new ArrayList<ParkingSlot>();
        for(ParkingSlot aParkingSlot : ParkingSlots)
        {
            if(aParkingSlot.getVehicle() instanceof Car )
            {  
                if(((Car)aParkingSlot.getVehicle()).getregNum().equals(RegNum) && aParkingSlot.getIsOccupied())
                {
                    foundParkingSlots.add(aParkingSlot);
                    
                }
            }
        }   
            return foundParkingSlots;
    
    }

    public ParkingSlot findParkingbyID(String Id)
    {
        ParkingSlot foundParkingSlot = new ParkingSlot("-1");
        for(ParkingSlot aParkingSlot : ParkingSlots)
        {
            if(aParkingSlot.getParkingSlotId().equals(Id))
            {
                foundParkingSlot = aParkingSlot;
                break;
            }    
        }
        return foundParkingSlot;
    }

    public ArrayList<ParkingSlot> getOccupiedParkingSlots()
    {
        ArrayList<ParkingSlot> occupiedParkingSlots = new ArrayList<ParkingSlot>();
        for(ParkingSlot aParkingSlot : ParkingSlots)
        {
            if(aParkingSlot.getIsOccupied())
                {occupiedParkingSlots.add(aParkingSlot);}
        }
        return occupiedParkingSlots;
    }

    public ArrayList<ParkingSlot> getStaffParkingSlots()
    {
        ArrayList<ParkingSlot> staffParkingSlots = new ArrayList<ParkingSlot>();
        for(ParkingSlot aParkingSlot : ParkingSlots)
        {
            if(aParkingSlot.getIsStaff())
                {
                    staffParkingSlots.add(aParkingSlot);
                }
        }
        return staffParkingSlots;
    }
  
    public ArrayList<ParkingSlot> getAllParkingSlots()
    {
        return ParkingSlots;
    }
}