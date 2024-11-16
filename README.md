
<h1 align="center">
  <br>
 <img src="https://github.com/ChesterCalog/PMS---SpotSmart/blob/main/images/pms_logo.png" alt="SpotSmart" width="200"></a>
  <br>
  PMS - SpotSmart
  <br>
</h1>

<h4 align="center">A Simple Parking Management Main w/ Database using Java and MySql </a>.</h4>

## I. A Brief Project Overview

# Vehicle Management:
 - Add Vehicle: Users can register vehicles by entering the license plate, owner's name, and 
   type (car or motorcycle).
 - Remove Vehicle: Users can remove vehicles from the parking lot by providing the vehicle’s 
   license plate.
 - Find Vehicle: Allows users to search for a vehicle by license plate and retrieve its parking 
   details.

# Parking Spot Management and Dynamic Configuration:
 - Parking spots are divided into separate sections for cars and motorcycles.
   Users can view available spots and parking details in real time.
 - The system allows administrators to set the number of parking spots available for both cars 
   and motorcycles.

# Parking History:
 - Every entry and exit of vehicles is logged with details such as license plate, owner, vehicle type, parking spot, and timestamp.
 - Users can view and clear historical records for transparency and audit purposes.

# Database Integration:
 - The system integrates with an SQL database to store and manage parking and history data.
The database contains tables for CarParking, MotorcycleParking, and History, with vehicle data stored along with timestamps of entries and exits.

## II. OOP Principles Applied
# Encapsulation
  - All fields in classes are private, with public getters and setters for controlled access.
  - The **Vehicle** superclass encapsulates shared fields (**licensePlate** and **owner**) with private     
  - access modifiers and provides public getter methods.
  - Subclasses (Car and Motorcycle) inherit these encapsulated fields and methods, maintaining      data privacy.
  - Organized code into multiple classes each handling a specific responsibility
├── main/
│   ├── Main.java
│   ├── DatabaseConnection.java
├── parking/
│   ├── ParkingLot.java
│   ├── ParkingSpot.java
│   ├── Vehicle.java
│   ├── Car.java
│   ├── Motorcycle.java

# Inheritance and Polymorphism
 - **Car** and **Motorcycle** inherit from the abstract class **Vehicle**, reusing its shared 
   attributes and methods.
 - This design adheres to DRY (Don't Repeat Yourself) principles by centralizing shared logic 
   in **Vehicle**.
 - **Vehicle** serves as the base class, extended by **Car** and **Motorcycle**, allowing the 
   system to treat vehicles uniformly while enabling vehicle-specific behavior.
 - The abstract method **getVehicleType** in **Vehicle** is overridden in both **Car** and 
   **Motorcycle**
   
# Abstraction
 - The **Vehicle** class is abstract, focusing only on common properties and behaviors, leaving 
   specific details to its subclasses.
 - This separation allows higher-level concepts (like a generic "vehicle") to be defined while 
   delegating specific details to the respective subclasses.
 - **System.java** abstracts the concept of parking, with a focus on the spot number and 
   occupancy state.

## III. Details of the Chosen SDG and its Integration

## IV. Instructions for Running the Program





