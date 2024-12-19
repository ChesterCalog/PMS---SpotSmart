CREATE DATABASE ParkingManagement;
CREATE SCHEMA ParkingDB;
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
    vehicleType VARCHAR(20), -- "Car" or "Motorcycle"
    parkingSpot INT,
    action VARCHAR(10),      -- "Entry" or "Exit"
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_Car FOREIGN KEY (licensePlate) REFERENCES CarParking(licensePlate) ON DELETE SET NULL,
    CONSTRAINT FK_Motorcycle FOREIGN KEY (licensePlate) REFERENCES MotorcycleParking(licensePlate) ON DELETE SET NULL
);
