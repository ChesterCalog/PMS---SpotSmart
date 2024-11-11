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

            // Display car parking information
            System.out.println("Car Parking:");
            System.out.println("License Plate | Parking Spot | Owner | Entry Time");
            ResultSet rs = stmt.executeQuery("SELECT * FROM CarParking");
            while (rs.next()) {
                System.out.println(rs.getString("licensePlate") + " | " + rs.getInt("parkingSpot") + " | " +
                        rs.getString("owner") + " | " + rs.getTimestamp("entryTime"));
            }

            System.out.println("\nMotorcycle Parking:");
            System.out.println("License Plate | Parking Spot | Owner | Entry Time");
            rs = stmt.executeQuery("SELECT * FROM MotorcycleParking");
            while (rs.next()) {
                System.out.println(rs.getString("licensePlate") + " | " + rs.getInt("parkingSpot") + " | " +
                        rs.getString("owner") + " | " + rs.getTimestamp("entryTime"));
            }
        }
    }

    public void addVehicle(String licensePlate, String owner, String vehicleType) throws SQLException {
        String findAvailableSpotQuery;
        String insertQuery;
        String tableName;

        if (vehicleType.equals("car")) {
            findAvailableSpotQuery =
                    "SELECT COALESCE(MIN(parkingSpot + 1), 1) AS nextAvailableSpot " +
                            "FROM CarParking WHERE (parkingSpot + 1) NOT IN (SELECT parkingSpot FROM CarParking)";
            insertQuery = "INSERT INTO CarParking (licensePlate, owner, parkingSpot) VALUES (?, ?, ?)";
            tableName = "CarParking";
        } else if (vehicleType.equals("motorcycle")) {
            findAvailableSpotQuery =
                    "SELECT COALESCE(MIN(parkingSpot + 1), 1) AS nextAvailableSpot " +
                            "FROM MotorcycleParking WHERE (parkingSpot + 1) NOT IN (SELECT parkingSpot FROM MotorcycleParking)";
            insertQuery = "INSERT INTO MotorcycleParking (licensePlate, owner, parkingSpot) VALUES (?, ?, ?)";
            tableName = "MotorcycleParking";
        } else {
            System.out.println("Invalid vehicle type!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            ResultSet rs = stmt.executeQuery(findAvailableSpotQuery);
            int parkingSpot = 1;

            if (rs.next()) {
                parkingSpot = rs.getInt("nextAvailableSpot");
                if (rs.wasNull()) {
                    // No gaps, so assign the next spot after the maximum spot number
                    String maxSpotQuery = "SELECT IFNULL(MAX(parkingSpot), 0) + 1 AS nextSpot FROM " + tableName;
                    ResultSet maxRs = stmt.executeQuery(maxSpotQuery);
                    if (maxRs.next()) {
                        parkingSpot = maxRs.getInt("nextSpot");
                    }
                }
            }

            // Insert the new vehicle record with the assigned parking spot
            pstmt.setString(1, licensePlate);
            pstmt.setString(2, owner);
            pstmt.setInt(3, parkingSpot);
            pstmt.executeUpdate();

            System.out.println("Vehicle added successfully to " + vehicleType + " parking at spot: " + parkingSpot);
        }
    }

    public void removeVehicle(String licensePlate) throws SQLException {
        String vehicleType = findVehicleType(licensePlate);
        if (vehicleType == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        String sql = vehicleType.equalsIgnoreCase("car") ?
                "DELETE FROM CarParking WHERE licensePlate = ?" :
                "DELETE FROM MotorcycleParking WHERE licensePlate = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, licensePlate);
            pstmt.executeUpdate();
            System.out.println("Vehicle removed successfully.");

            // Free the parking spot in memory
            int parkingSpot = findParkingSpot(vehicleType, licensePlate);
            freeSpot(vehicleType, parkingSpot);
        }
    }

    // Helper function to determine if a license plate belongs to a car or motorcycle
    private String findVehicleType(String licensePlate) throws SQLException {
        String[] tables = {"CarParking", "MotorcycleParking"};
        for (String table : tables) {
            String sql = "SELECT * FROM " + table + " WHERE licensePlate = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, licensePlate);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return table.equals("CarParking") ? "car" : "motorcycle";
                }
            }
        }
        return null; // Vehicle not found
    }

    public void findVehicle(String licensePlate) throws SQLException {
        String sqlCar = "SELECT * FROM CarParking WHERE licensePlate = ?";
        String sqlMotorcycle = "SELECT * FROM MotorcycleParking WHERE licensePlate = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check in CarParking table
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCar)) {
                pstmt.setString(1, licensePlate);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Vehicle found in Car Parking:");
                    System.out.println("License Plate: " + rs.getString("licensePlate"));
                    System.out.println("Owner: " + rs.getString("owner"));
                    System.out.println("Parking Spot: " + rs.getInt("parkingSpot"));
                    System.out.println("Entry Time: " + rs.getTimestamp("entryTime"));
                    return; // Exit after finding in CarParking
                }
            }

            // Check in MotorcycleParking table
            try (PreparedStatement pstmt = conn.prepareStatement(sqlMotorcycle)) {
                pstmt.setString(1, licensePlate);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Vehicle found in Motorcycle Parking:");
                    System.out.println("License Plate: " + rs.getString("licensePlate"));
                    System.out.println("Owner: " + rs.getString("owner"));
                    System.out.println("Parking Spot: " + rs.getInt("parkingSpot"));
                    System.out.println("Entry Time: " + rs.getTimestamp("entryTime"));
                    return; // Exit after finding in MotorcycleParking
                }
            }

            // If not found in either table
            System.out.println("Vehicle with license plate " + licensePlate + " not found.");
        }
    }


    // Finds the parking spot for a vehicle by license plate in the appropriate table
    private int findParkingSpot(String vehicleType, String licensePlate) throws SQLException {
        String table = vehicleType.equalsIgnoreCase("car") ? "CarParking" : "MotorcycleParking";
        String sql = "SELECT parkingSpot FROM " + table + " WHERE licensePlate = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("parkingSpot");
            }
        }
        return -1; // Spot not found
    }

    private int findAvailableSpot(String vehicleType) {
        ArrayList<ParkingSpot> spots = vehicleType.equalsIgnoreCase("car") ? carSpots : motorcycleSpots;
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied()) {
                return spot.getSpotNumber();
            }
        }
        return -1; // No available spot
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

    private void freeSpot(String vehicleType, int spotNumber) {
        ArrayList<ParkingSpot> spots = vehicleType.equalsIgnoreCase("car") ? carSpots : motorcycleSpots;
        for (ParkingSpot spot : spots) {
            if (spot.getSpotNumber() == spotNumber) {
                spot.free();
                break;
            }
        }
    }
}
