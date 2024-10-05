package pms;

public class Car extends Vehicle
{
    private String regNum;
    
    public Car(String regNum, String ownerName)
    {
        super(ownerName);
        this.regNum = regNum;
    }
    
    public String getregNum()
    {
        return regNum;
    }

     public String toString()
    {
        return "Car"+","+owner+","+getArrivalTime()+","+regNum;
    }
}