<h1 align="center">
  <br>
 <img src="https://github.com/ChesterCalog/PMS---SpotSmart/blob/main/images/pms_logo.png" alt="SpotSmart" width="200"></a>
  <br>
  PMS - SpotSmart
  <br>
</h1>

<h4 align="center">A Simple Parking Management made w/ Database using Java and MySql </a>.</h4>

## I. A Brief Project Overview

### Parking Spot Management and Dynamic Configuration:
 - Parking spots are divided into separate sections for cars and motorcycles.
   Users can view available spots and parking details in real time.
 - The system allows administrators to set the number of parking spots available for both cars 
   and motorcycles.

### Vehicle Management:
 - Add Vehicle: Users can register vehicles by entering the license plate, owner's name, and 
   type (car or motorcycle).
 - Remove Vehicle: Users can remove vehicles from the parking lot by providing the vehicle’s 
   license plate.
 - Find Vehicle: Allows users to search for a vehicle by license plate and retrieve its parking 
   details.

### Parking History:
 - Every entry and exit of vehicles is logged with details such as license plate, owner, vehicle type, parking spot, and timestamp.
 - Users can view and clear historical records for transparency and audit purposes.

### Database Integration:
 - The system integrates with an SQL database to store and manage parking and history data.
The database contains tables for CarParking, MotorcycleParking, and History, with vehicle data stored along with timestamps of entries and exits.

## II. OOP Principles Applied
### Encapsulation
  - All fields in classes are private, with public getters and setters for controlled access.
  - The *Vehicle* superclass encapsulates shared fields (*licensePlate* and *owner*) with private     
  - access modifiers and provides public getter methods.
  - Subclasses (Car and Motorcycle) inherit these encapsulated fields and methods, maintaining data privacy.
  - Organized code into multiple classes each handling a specific responsibility
```
├── main/
│   ├── Main.java
│   ├── DatabaseConnection.java
│   ├── ParkingRecord.java
│   ├── System.java
├── parking/
│   ├── ParkingSpot.java
│   ├── Vehicle.java
│   ├── Car.java
│   ├── Motorcycle.java
```
### Inheritance and Polymorphism
 - *Car* and *Motorcycle* inherit from the abstract class *Vehicle*, reusing its shared 
   attributes and methods.
 - This design adheres to DRY (Don't Repeat Yourself) principles by centralizing shared logic 
   in *Vehicle*.
 - *Vehicle* serves as the base class, extended by *Car* and *Motorcycle*, allowing the 
   system to treat vehicles uniformly while enabling vehicle-specific behavior.
 - The abstract method *getVehicleType* in *Vehicle* is overridden in both *Car* and 
   *Motorcycle*
   
### Abstraction
 - The *Vehicle* class is abstract, focusing only on common properties and behaviors, leaving 
   specific details to its subclasses.
 - This separation allows higher-level concepts (like a generic "vehicle") to be defined while 
   delegating specific details to the respective subclasses.
 - *System.java* abstracts the concept of parking, with a focus on the spot number and 
   occupancy state.

## III. Details of the Chosen SDG and its Integration

### Chosen SDG: SDG 11 - Sustainable Cities and Communities
*Target*: 11.2 - Provide access to safe, affordable, accessible and sustainable transport systems for all, improving road safety, and expanding public transport.
### Integration of SDG 11:
  **Improving Transport Efficiency:**
 - By effectively managing parking spots, SpotSmart helps to reduce the amount of time spent by drivers searching for parking, which in turn reduces traffic congestion. 
 - The system optimizes parking spot usage by tracking occupancy in real-time, making parking more accessible and efficient for all users.
   
  **Promoting Safe Parking:**
 - The system includes detailed vehicle entry and exit logs, contributing to better monitoring of parking lot usage, enhancing safety, and providing transparency for vehicle owners.
 - The integration of historical data allows administrators to maintain a secure and well-organized parking environment, which aligns with SDG 11's emphasis on safety.

  **Accessibility and Inclusivity:**
 - The parking system is designed to handle both cars and motorcycles, ensuring that a diverse range of vehicles is accommodated. This inclusivity contributes to making parking more accessible to all, addressing 
   the needs of different user groups.
 - Furthermore, the system can be customized to cater to people with disabilities by ensuring designated spots are always available, improving access for people in vulnerable situations.

## IV. Instructions for Running the Program
 - Clone the repository in IntelliJ IDEA
 - Open your <a href="https://dev.mysql.com/downloads/installer/">MySql Workbench</a>
 - Setup New Connection
 - Create a new Schema in the connected server
 - Run these Queries or Use the init.sql
```
CREATE DATABASE ParkingManagement;

USE ParkingManagement;

CREATE TABLE CarParking (
    licensePlate VARCHAR(10) PRIMARY KEY,
    owner VARCHAR(50),
    parkingSpot INT UNIQUE,
    entryTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE MotorcycleParking (
    licensePlate VARCHAR(10) PRIMARY KEY,
    owner VARCHAR(50),
    parkingSpot INT UNIQUE,
    entryTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE History (
    id INT AUTO_INCREMENT PRIMARY KEY,
    licensePlate VARCHAR(10),
    owner VARCHAR(50),
    vehicleType VARCHAR(20),
    parkingSpot INT,
    action VARCHAR(10),  -- "Entry" or "Exit"
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
 - Open Intelij IDEA
 - Open Project Structures -> Modules -> Add the <a href="https://dev.mysql.com/downloads/connector/j/">MySql Connector</a> jar file located in lib and apply
 - Run Main.java in SpotSmart_Final/src/Main
 - Enjoy! :>


