package parking;

public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate, String owner) {
        super(licensePlate, owner);
    }

    @Override
    public String getVehicleType() {
        return "motorcycle";
    }
}