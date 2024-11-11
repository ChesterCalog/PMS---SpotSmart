import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ParkingLot {
    private ArrayList<ParkingSpot> carSpots;
    private ArrayList<ParkingSpot> motorcycleSpots;
    private final int maxCarSpots;
    private final int maxMotorcycleSpots;

    public ParkingLot(int carSpotsCount, int motorcycleSpotsCount) {
        this.maxCarSpots = carSpotsCount;
        this.maxMotorcycleSpots = motorcycleSpotsCount;
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
            System.out.println(String.format("%-5s %-15s %-25s %-20s", "No.", "License Plate", "Owner", "Entry Time"));

            // Fetch all car parking records from the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM CarParking ORDER BY parkingSpot");
            ArrayList<ParkingRecord> carRecords = new ArrayList<>();
            while (rs.next()) {
                int parkingSpot = rs.getInt("parkingSpot");
                String licensePlate = rs.getString("licensePlate");
                String owner = rs.getString("owner");
                Timestamp entryTime = rs.getTimestamp("entryTime");
                carRecords.add(new ParkingRecord(parkingSpot, licensePlate, owner, entryTime));
            }

            // Display parking spots, ensuring all are shown
            for (int i = 1; i <= maxCarSpots; i++) {
                boolean found = false;
                for (ParkingRecord record : carRecords) {
                    if (record.getParkingSpot() == i) {
                        System.out.println(String.format("%-5d %-15s %-20s %-20s", i,
                                record.getLicensePlate(), record.getOwner(), record.getEntryTime()));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println(String.format("%-5d %-15s %-20s %-20s", i, "[empty slot]", "", ""));
                }
            }

            // Display motorcycle parking information
            System.out.println("\nMotorcycle Parking:");
            System.out.println(String.format("%-5s %-15s %-20s %-20s", "No.", "License Plate", "Owner", "Entry Time"));

            // Fetch all motorcycle parking records from the database
            rs = stmt.executeQuery("SELECT * FROM MotorcycleParking ORDER BY parkingSpot");
            ArrayList<ParkingRecord> motorcycleRecords = new ArrayList<>();
            while (rs.next()) {
                int parkingSpot = rs.getInt("parkingSpot");
                String licensePlate = rs.getString("licensePlate");
                String owner = rs.getString("owner");
                Timestamp entryTime = rs.getTimestamp("entryTime");
                motorcycleRecords.add(new ParkingRecord(parkingSpot, licensePlate, owner, entryTime));
            }

            // Display parking spots, ensuring all are shown
            for (int i = 1; i <= maxMotorcycleSpots; i++) {
                boolean found = false;
                for (ParkingRecord record : motorcycleRecords) {
                    if (record.getParkingSpot() == i) {
                        System.out.println(String.format("%-5d %-15s %-20s %-20s", i,
                                record.getLicensePlate(), record.getOwner(), record.getEntryTime()));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println(String.format("%-5d %-15s %-20s %-20s", i, "[empty slot]", "", ""));
                }
            }
        }
    }

    public static class ParkingRecord {
        private int parkingSpot;
        private String licensePlate;
        private String owner;
        private Timestamp entryTime;

        public ParkingRecord(int parkingSpot, String licensePlate, String owner, Timestamp entryTime) {
            this.parkingSpot = parkingSpot;
            this.licensePlate = licensePlate;
            this.owner = owner;
            this.entryTime = entryTime;
        }

        public int getParkingSpot() {
            return parkingSpot;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public String getOwner() {
            return owner;
        }

        public Timestamp getEntryTime() {
            return entryTime;
        }
    }

    public void addVehicle(String licensePlate, String owner, String vehicleType) throws SQLException {
        String countQuery;
        String findAvailableSpotQuery;
        String insertQuery;
        String tableName;
        int maxSpots;

        if (vehicleType.equals("car")) {
            countQuery = "SELECT COUNT(*) FROM CarParking";
            findAvailableSpotQuery = "SELECT MIN(spot) AS nextAvailableSpot FROM (SELECT @row := @row + 1 AS spot " +
                    "FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t1, " +
                    "(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t2, " +
                    "(SELECT @row := 0) t3) temp " +
                    "WHERE spot NOT IN (SELECT parkingSpot FROM CarParking) LIMIT 1";
            insertQuery = "INSERT INTO CarParking (licensePlate, owner, parkingSpot) VALUES (?, ?, ?)";
            tableName = "CarParking";
            maxSpots = maxCarSpots;
        } else if (vehicleType.equals("motorcycle")) {
            countQuery = "SELECT COUNT(*) FROM MotorcycleParking";
            findAvailableSpotQuery = "SELECT MIN(spot) AS nextAvailableSpot FROM (SELECT @row := @row + 1 AS spot " +
                    "FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t1, " +
                    "(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t2, " +
                    "(SELECT @row := 0) t3) temp " +
                    "WHERE spot NOT IN (SELECT parkingSpot FROM MotorcycleParking) LIMIT 1";
            insertQuery = "INSERT INTO MotorcycleParking (licensePlate, owner, parkingSpot) VALUES (?, ?, ?)";
            tableName = "MotorcycleParking";
            maxSpots = maxMotorcycleSpots;
        } else {
            System.out.println("Invalid vehicle type!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Check current count to ensure we haven't exceeded the limit
            ResultSet countRs = stmt.executeQuery(countQuery);
            if (countRs.next() && countRs.getInt(1) >= maxSpots) {
                System.out.println("No available spots for " + vehicleType + ".");
                return;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
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

                pstmt.setString(1, licensePlate);
                pstmt.setString(2, owner);
                pstmt.setInt(3, parkingSpot);
                pstmt.executeUpdate();

                System.out.println("Vehicle added successfully to " + vehicleType + " parking at spot: " + parkingSpot);
            }
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

    public void clearAllVehicles() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Prompt for confirmation
        System.out.println("Are you sure you want to clear all vehicles? (1 for yes / 2 for no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("1")) {
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {

                String deleteCarsQuery = "DELETE FROM CarParking";
                String deleteMotorcyclesQuery = "DELETE FROM MotorcycleParking";

                stmt.executeUpdate(deleteCarsQuery);
                stmt.executeUpdate(deleteMotorcyclesQuery);

                carSpots.clear();
                motorcycleSpots.clear();

                System.out.println("All vehicles have been cleared from the parking lot.");
            } catch (SQLException e) {
                System.out.println("Error occurred while clearing the parking lot: " + e.getMessage());
            }
        } else {
            System.out.println("Operation cancelled. No vehicles were removed.");
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
        return null;
    }

    public void findVehicle(String licensePlate) throws SQLException {
        String sqlCar = "SELECT * FROM CarParking WHERE licensePlate = ?";
        String sqlMotorcycle = "SELECT * FROM MotorcycleParking WHERE licensePlate = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
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
