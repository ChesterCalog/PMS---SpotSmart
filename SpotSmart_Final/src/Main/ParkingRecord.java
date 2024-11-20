package Main;
import java.sql.Timestamp;

//Represents a record of a parked vehicle.
public class ParkingRecord {
    private int parkingSpot;
    private String licensePlate;
    private String owner;
    private Timestamp entryTime;


    //Constructor to create a ParkingRecord.
    public ParkingRecord(int parkingSpot, String licensePlate, String owner, Timestamp entryTime) {
        this.parkingSpot = parkingSpot;
        this.licensePlate = licensePlate;
        this.owner = owner;
        this.entryTime = entryTime;
    }

    //Getters for the parking spot number, license plate, owner and entry time.
    public int getParkingSpot() {
        return parkingSpot;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    //Returns a string representation of the ParkingRecord.
    @Override
    public String toString() {
        return String.format("ParkingSpot: %d, LicensePlate: %s, Owner: %s, EntryTime: %s",
                parkingSpot, licensePlate, owner, entryTime);
    }
}
