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

            System.out.println("\nCar Parking:");
            System.out.printf("%-5s %-15s %-25s %-20s%n", "No.", "License Plate", "Owner", "Entry Time");
            ResultSet rs = stmt.executeQuery("SELECT * FROM CarParking ORDER BY parkingSpot");
            ArrayList<ParkingRecord> carRecords = new ArrayList<>();
            while (rs.next()) {
                int parkingSpot = rs.getInt("parkingSpot");
                String licensePlate = rs.getString("licensePlate");
                String owner = rs.getString("owner");
                Timestamp entryTime = rs.getTimestamp("entryTime");
                carRecords.add(new ParkingRecord(parkingSpot, licensePlate, owner, entryTime));
            }

            for (int i = 1; i <= maxCarSpots; i++) {
                boolean found = false;
                for (ParkingRecord record : carRecords) {
                    if (record.getParkingSpot() == i) {
                        System.out.printf("%-5d %-15s %-20s %-20s%n", i,
                                record.getLicensePlate(), record.getOwner(), record.getEntryTime());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.printf("%-5d %-15s %-20s %-20s%n", i, "[empty slot]", "", "");
                }
            }

            System.out.println("\nMotorcycle Parking:");
            System.out.printf("%-5s %-15s %-20s %-20s%n", "No.", "License Plate", "Owner", "Entry Time");
            rs = stmt.executeQuery("SELECT * FROM MotorcycleParking ORDER BY parkingSpot");
            ArrayList<ParkingRecord> motorcycleRecords = new ArrayList<>();
            while (rs.next()) {
                int parkingSpot = rs.getInt("parkingSpot");
                String licensePlate = rs.getString("licensePlate");
                String owner = rs.getString("owner");
                Timestamp entryTime = rs.getTimestamp("entryTime");
                motorcycleRecords.add(new ParkingRecord(parkingSpot, licensePlate, owner, entryTime));
            }

            for (int i = 1; i <= maxMotorcycleSpots; i++) {
                boolean found = false;
                for (ParkingRecord record : motorcycleRecords) {
                    if (record.getParkingSpot() == i) {
                        System.out.printf("%-5d %-15s %-20s %-20s%n", i,
                                record.getLicensePlate(), record.getOwner(), record.getEntryTime());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.printf("%-5d %-15s %-20s %-20s%n", i, "[empty slot]", "", "");
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

                recordHistory(licensePlate, owner, vehicleType, parkingSpot, "Entry");
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

            int parkingSpot = findParkingSpot(vehicleType, licensePlate);

            String owner = findOwner(licensePlate);
            pstmt.setString(1, licensePlate);
            pstmt.executeUpdate();

            recordHistory(licensePlate, owner, vehicleType, parkingSpot, "Exit");
            System.out.println("Vehicle removed successfully.");
            freeSpot(vehicleType, parkingSpot);
        }
    }

    private String findOwner(String licensePlate) throws SQLException {
        String[] tables = {"CarParking", "MotorcycleParking"};

        for (String table : tables) {
            String sql = "SELECT owner FROM " + table + " WHERE licensePlate = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, licensePlate);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return rs.getString("owner");
                }
            }
        }
        return null;
    }

    public void clearAllVehicles() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you sure you want to clear all vehicles? (1 = Yes, 2 = No): ");
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
                    return;
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
                    return;
                }
            }

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
        return -1;
    }

    public void recordHistory(String licensePlate, String owner, String vehicleType, int parkingSpot, String action) throws SQLException {
        String sql = "INSERT INTO History (licensePlate, owner, vehicleType, parkingSpot, action) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            pstmt.setString(2, owner);
            pstmt.setString(3, vehicleType);
            pstmt.setInt(4, parkingSpot);
            pstmt.setString(5, action);
            pstmt.executeUpdate();
        }
    }

    public void viewHistory() throws SQLException {
        String sql = "SELECT * FROM History ORDER BY timestamp DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nParking History:");
            System.out.printf("%-15s %-20s %-15s %-10s %-20s%n", "License Plate", "Owner", "Vehicle Type", "Action", "Timestamp");

            while (rs.next()) {
                System.out.printf("%-15s %-20s %-15s %-10s %-20s%n",
                        rs.getString("licensePlate"),
                        rs.getString("owner"),
                        rs.getString("vehicleType"),
                        rs.getString("action"),
                        rs.getTimestamp("timestamp").toString());
            }
        }
    }

    public void deleteHistory() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to delete all history records? (1 = Yes, 2 = No)");
        int choice = scanner.nextInt();

        if (choice == 1) {
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM history");
                System.out.println("All history records have been deleted.");
            }
        } else if (choice == 2) {
            System.out.println("Delete history operation canceled.");
        } else {
            System.out.println("Invalid choice. Operation canceled.");
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
