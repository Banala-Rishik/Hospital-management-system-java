-- ---------------------------------------------------------
-- Database: hospital
-- Description: Schema for Hospital Management System (HMS)
-- ---------------------------------------------------------

CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

-- 1. Patients Table
-- Stores demographic information for all registered patients
CREATE TABLE IF NOT EXISTS patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL
);

-- 2. Doctors Table
-- Stores doctor details and their medical specializations
CREATE TABLE IF NOT EXISTS doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL
);

-- 3. Appointments Table
-- Relational bridge linking Patients and Doctors with a Date constraint
CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

-- 4. Initial Seed Data
-- Pre-populating doctors so the system is usable immediately
INSERT INTO doctors (name, specialization) VALUES 
('Dr. Pankaj Sharma', 'Physician'),
('Dr. Ananya Reddy', 'Cardiologist'),
('Dr. Vikrant Mehta', 'Neurologist');
