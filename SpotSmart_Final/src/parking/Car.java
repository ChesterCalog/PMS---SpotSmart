package parking;

//Represents a car, Inherits common properties from Vehicle superclass.
public class Car extends Vehicle {
    public Car(String licensePlate, String owner) {
        super(licensePlate, owner);
    }

    //Overrides the abstract method from Vehicle to specify the type as "car"
    @Override
    public String getVehicleType() {
        return "car";
    }
}