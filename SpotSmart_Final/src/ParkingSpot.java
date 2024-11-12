public class ParkingSpot {
    private int spotNumber;
    private boolean occupied;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.occupied = false;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void free() {
        occupied = false;
    }
}
