package parking;

//Represents a parking spot with a specific number and occupancy status.
//Encapsulates spot details and provides methods to manage occupancy
public class ParkingSpot {
    private int spotNumber;
    private boolean occupied;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.occupied = false;
    }

    //Getter for parking spot number
    public int getSpotNumber() {
        return spotNumber;
    }

    //frees the parking spot, marking it as unoccupied
    public void free() {
        occupied = false;
    }
}
