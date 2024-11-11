public class ParkingSpot {
    private int spotNumber;
    private boolean occupied;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.occupied = false; // Initially, the spot is free
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        occupied = true; // Mark spot as occupied
    }

    public void free() {
        occupied = false; // Mark spot as free
    }
}
