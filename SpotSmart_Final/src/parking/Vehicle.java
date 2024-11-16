package parking;

public abstract class Vehicle {
    private String licensePlate;
    private String owner;

    public Vehicle(String licensePlate, String owner) {
        this.licensePlate = licensePlate;
        this.owner = owner;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    public abstract String getVehicleType();
}
