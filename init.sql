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