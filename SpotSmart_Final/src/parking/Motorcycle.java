package parking;

//Represents a motorcycle, Inherits common properties from Vehicle superclass.
public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate, String owner) {
        super(licensePlate, owner);
    }

    //Overrides the abstract method from Vehicle to specify the type as "motorcycle"
    @Override
    public String getVehicleType() {
        return "motorcycle";
    }
}