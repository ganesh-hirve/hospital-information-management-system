-- Hospital Management System — Database Schema
-- Run this script to set up the database from scratch.

CREATE DATABASE IF NOT EXISTS hospital_management_system;
USE hospital_management_system;

-- Users (admin, doctors, receptionists)
CREATE TABLE IF NOT EXISTS tbl_user (
    user_id  INT          PRIMARY KEY AUTO_INCREMENT,
    email    VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL,
    status   VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE'
);

-- Doctor profiles (linked to tbl_user)
CREATE TABLE IF NOT EXISTS tbl_doctor (
    doctor_id        INT            PRIMARY KEY AUTO_INCREMENT,
    user_id          INT            NOT NULL,
    first_name       VARCHAR(50)    NOT NULL,
    last_name        VARCHAR(50)    NOT NULL,
    gender           VARCHAR(10)    NOT NULL,
    phone            VARCHAR(15),
    specialization   VARCHAR(100),
    qualification    VARCHAR(100),
    experience       INT            DEFAULT 0,
    consultation_fee DECIMAL(10,2)  DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES tbl_user(user_id)
);

-- Patients
CREATE TABLE IF NOT EXISTS tbl_patient (
    patient_id  INT         PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    age         INT          NOT NULL,
    gender      VARCHAR(10)  NOT NULL,
    phone       VARCHAR(15),
    address     TEXT,
    blood_group VARCHAR(5)
);

-- Appointments
CREATE TABLE IF NOT EXISTS tbl_appointment (
    appointment_id   INT         PRIMARY KEY AUTO_INCREMENT,
    patient_id       INT         NOT NULL,
    doctor_id        INT         NOT NULL,
    appointment_date DATE        NOT NULL,
    status           VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (patient_id) REFERENCES tbl_patient(patient_id),
    FOREIGN KEY (doctor_id)  REFERENCES tbl_doctor(doctor_id)
);

-- Default admin user  (password: admin123)
INSERT IGNORE INTO tbl_user (email, password, role, status)
VALUES ('admin@hospital.com', 'admin123', 'ADMIN', 'ACTIVE');
