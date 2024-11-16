package parking;

public class Car extends Vehicle {
    public Car(String licensePlate, String owner) {
        super(licensePlate, owner);
    }

    @Override
    public String getVehicleType() {
        return "car";
    }
}