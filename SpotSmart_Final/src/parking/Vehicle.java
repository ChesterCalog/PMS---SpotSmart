package parking;

//Abstract class representing a generic vehicle.
//Encapsulates common properties (license plate and owner)
public abstract class Vehicle {
    private String licensePlate;
    private String owner;

    public Vehicle(String licensePlate, String owner) {
        this.licensePlate = licensePlate;
        this.owner = owner;
    }

    //Getter for license plate and owner
    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    //Abstract method to be implemented by subclasses (define the vehicle type)
    public abstract String getVehicleType();
}
