import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Default parking spot counts
        int carSpotsCount = 10;
        int motorcycleSpotsCount = 5;

        ParkingLot parkingLot = new ParkingLot(carSpotsCount, motorcycleSpotsCount);

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Use System");
            System.out.println("2. Set Parking Spots");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    useSystem(scanner, parkingLot);
                    break;
                case 2:
                    System.out.print("Enter new number of car parking spots: ");
                    carSpotsCount = scanner.nextInt();
                    System.out.print("Enter new number of motorcycle parking spots: ");
                    motorcycleSpotsCount = scanner.nextInt();
                    parkingLot = new ParkingLot(carSpotsCount, motorcycleSpotsCount);
                    System.out.println("Parking spot counts updated.");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void useSystem(Scanner scanner, ParkingLot parkingLot) throws SQLException {
        while (true) {
            System.out.println("\nUse System Menu:");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Find Vehicle");
            System.out.println("4. Display Parking");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter license plate: ");
                    String licensePlate = scanner.nextLine();
                    System.out.print("Enter owner name: ");
                    String owner = scanner.nextLine();
                    System.out.print("Enter vehicle type (1 for car, 2 for motorcycle): ");
                    String typeInput = scanner.nextLine();
                    String vehicleType;

                    if (typeInput.equals("1")) {
                        vehicleType = "car";
                    } else if (typeInput.equals("2")) {
                        vehicleType = "motorcycle";
                    } else {
                        System.out.println("Invalid vehicle type!");
                        return;
                    }
                    parkingLot.addVehicle(licensePlate, owner, vehicleType);
                    System.out.println("Vehicle added successfully!");
                    break;
                case 2:
                    System.out.print("Enter license plate to remove: ");
                    String plateToRemove = scanner.nextLine();
                    parkingLot.removeVehicle(plateToRemove);
                    break;
                case 3:
                    System.out.print("Enter license plate to find: ");
                    String plateToFind = scanner.nextLine();
                    parkingLot.findVehicle(plateToFind);
                    break;
                case 4:
                    parkingLot.displayParking();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
