import java.sql.*;
import java.util.ArrayList;

public class ParkingLot {
    private ArrayList<ParkingSpot> carSpots;
    private ArrayList<ParkingSpot> motorcycleSpots;

    public ParkingLot(int carSpotsCount, int motorcycleSpotsCount) {
        carSpots = new ArrayList<>(carSpotsCount);
        motorcycleSpots = new ArrayList<>(motorcycleSpotsCount);
        for (int i = 1; i <= carSpotsCount; i++) {
            carSpots.add(new ParkingSpot(i));
        }
        for (int i = 1; i <= motorcycleSpotsCount; i++) {
            motorcycleSpots.add(new ParkingSpot(i));
        }
    }

    public void displayParking() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("\nCar Parking:");
            System.out.println("License Plate | Parking Spot | Owner | Entry Time");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Parking WHERE vehicleType='car'");
            while (rs.next()) {
                System.out.println(rs.getString("licensePlate") + " | " + rs.getInt("parkingSpot") + " | " + rs.getString("owner") + " | " + rs.getTimestamp("entryTime"));
            }

            System.out.println("\nMotorcycle Parking:");
            System.out.println("License Plate | Parking Spot | Owner | Entry Time");
            rs = stmt.executeQuery("SELECT * FROM Parking WHERE vehicleType='motorcycle'");
            while (rs.next()) {
                System.out.println(rs.getString("licensePlate") + " | " + rs.getInt("parkingSpot") + " | " + rs.getString("owner") + " | " + rs.getTimestamp("entryTime"));
            }
        }
    }

    public void addVehicle(String vehicleType, String licensePlate, String owner) throws SQLException {
        int parkingSpot = findAvailableSpot(vehicleType);
        if (parkingSpot == -1) {
            System.out.println("No available spots for " + vehicleType);
            return;
        }

        String sql = "INSERT INTO Parking (vehicleType, licensePlate, owner, parkingSpot) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehicleType);
            pstmt.setString(2, licensePlate);
            pstmt.setString(3, owner);
            pstmt.setInt(4, parkingSpot);

            pstmt.executeUpdate();
            System.out.println("Vehicle added successfully.");
            occupySpot(vehicleType, parkingSpot);
        }
    }

    public void removeVehicle(String licensePlate) throws SQLException {
        String sql = "DELETE FROM Parking WHERE licensePlate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, licensePlate);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vehicle removed successfully.");
            } else {
                System.out.println("Vehicle not found.");
            }
        }
    }

    public void findVehicle(String licensePlate) throws SQLException {
        String sql = "SELECT * FROM Parking WHERE licensePlate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Vehicle Found: ");
                System.out.println("License Plate: " + rs.getString("licensePlate"));
                System.out.println("Owner: " + rs.getString("owner"));
                System.out.println("Parking Spot: " + rs.getInt("parkingSpot"));
                System.out.println("Vehicle Type: " + rs.getString("vehicleType"));
            } else {
                System.out.println("Vehicle not found.");
            }
        }
    }

    private int findAvailableSpot(String vehicleType) {
        ArrayList<ParkingSpot> spots = vehicleType.equalsIgnoreCase("car") ? carSpots : motorcycleSpots;
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied()) {
                return spot.getSpotNumber();
            }
        }
        return -1;
    }

    private void occupySpot(String vehicleType, int spotNumber) {
        ArrayList<ParkingSpot> spots = vehicleType.equalsIgnoreCase("car") ? carSpots : motorcycleSpots;
        for (ParkingSpot spot : spots) {
            if (spot.getSpotNumber() == spotNumber) {
                spot.occupy();
                break;
            }
        }
    }
}
