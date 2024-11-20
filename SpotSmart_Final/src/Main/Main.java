package Main;
import parking.Car;
import parking.Motorcycle;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(java.lang.System.in);

        // Default parking spot counts
        int carSpotsCount = 5;
        int motorcycleSpotsCount = 5;

        String asciiArt =
                """
                         _____         _   _____               _  \s
                        |   __|___ ___| |_|   __|_____ ___ ___| |_\s
                        |__   | . | . |  _|__   |     | .'|  _|  _|
                        |_____|  _|___|_| |_____|_|_|_|__,|_| |_| \s
                              |_|                                \s""";
        java.lang.System.out.println(asciiArt);
        System system = new System(carSpotsCount, motorcycleSpotsCount);

        while (true) {
            java.lang.System.out.println("\nSystem.Main Menu:");
            java.lang.System.out.println("1. Use System");
            java.lang.System.out.println("2. Set Parking Spots");
            java.lang.System.out.println("3. Exit");
            java.lang.System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    useSystem(scanner, system);
                    break;
                case 2:
                    java.lang.System.out.print("Enter new number of car parking spots: ");
                    carSpotsCount = scanner.nextInt();
                    java.lang.System.out.print("Enter new number of motorcycle parking spots: ");
                    motorcycleSpotsCount = scanner.nextInt();
                    system = new System(carSpotsCount, motorcycleSpotsCount);
                    java.lang.System.out.println("Parking spot counts updated.");
                    break;
                case 3:
                    java.lang.System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    java.lang.System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void useSystem(Scanner scanner, System system) throws SQLException {
        while (true) {
            java.lang.System.out.println("\nParking Lot System:");
            java.lang.System.out.println("1. Add Vehicle");
            java.lang.System.out.println("2. Remove Vehicle");
            java.lang.System.out.println("3. Find Vehicle");
            java.lang.System.out.println("4. Display Parking");
            java.lang.System.out.println("5. View Parking History");
            java.lang.System.out.println("6. Clear All History");
            java.lang.System.out.println("7. Clear All Vehicles");
            java.lang.System.out.println("8. Main Menu");
            java.lang.System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    java.lang.System.out.print("Enter license plate: ");
                    String licensePlate = scanner.nextLine();
                    java.lang.System.out.print("Enter owner name: ");
                    String owner = scanner.nextLine();
                    java.lang.System.out.print("Enter vehicle type (1 for car, 2 for motorcycle): ");
                    String typeInput = scanner.nextLine();
                    String vehicleType;

                    if (typeInput.equals("1")) {
                        vehicleType = "car";
                    } else if (typeInput.equals("2")) {
                        vehicleType = "motorcycle";
                    } else {
                        java.lang.System.out.println("Invalid vehicle type!");
                        return;
                    }
                    if (vehicleType.equals("car")) {
                        system.addVehicle(new Car(licensePlate, owner));
                    } else {
                        system.addVehicle(new Motorcycle(licensePlate, owner));
                    }
                    break;
                case 2:
                    java.lang.System.out.print("Enter license plate to remove: ");
                    String plateToRemove = scanner.nextLine();
                    system.removeVehicle(plateToRemove);
                    break;
                case 3:
                    java.lang.System.out.print("Enter license plate to find: ");
                    String plateToFind = scanner.nextLine();
                    system.findVehicle(plateToFind);
                    break;
                case 4:
                    system.displayParking();
                    break;
                case 5:
                    system.viewHistory();
                    break;
                case 6:
                    system.deleteHistory();
                    break;
                case 7:
                    try {
                        system.clearAllVehicles();
                    } catch (SQLException e) {
                        java.lang.System.out.println("Error clearing vehicles: " + e.getMessage());
                    }
                    break;
                case 8:
                    return;
                default:
                    java.lang.System.out.println("Invalid choice, try again.");
            }
        }
    }
}
