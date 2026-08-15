package com.hims.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.hims.controller.AppointmentController;
import com.hims.controller.PatientController;
import com.hims.dto.AppointmentDTO;
import com.hims.dto.PatientDTO;
import com.hims.dto.UserDTO;
import com.hims.enums.Gender;

public class ReceptionistDashboard {

    private final UserDTO               receptionist;
    private final PatientController     patientController     = new PatientController();
    private final AppointmentController appointmentController = new AppointmentController();
    private final BufferedReader        br = new BufferedReader(new InputStreamReader(System.in));

    public ReceptionistDashboard(UserDTO receptionist) {
        this.receptionist = receptionist;
    }

    public void showDashboard() {
        try {
            boolean running = true;
            while (running) {
                System.out.println("\n========== RECEPTIONIST DASHBOARD ==========");
                System.out.println("Logged in as: " + receptionist.getEmail());
                System.out.println("--------------------------------------------");
                System.out.println("1. Register Patient");
                System.out.println("2. Book Appointment");
                System.out.println("3. View All Appointments");
                System.out.println("4. View All Patients");
                System.out.println("5. Logout");
                System.out.println("--------------------------------------------");
                System.out.print("Enter Choice: ");

                int choice = readInt();

                switch (choice) {
                    case 1: registerPatient();     break;
                    case 2: bookAppointment();     break;
                    case 3: viewAllAppointments(); break;
                    case 4: viewAllPatients();     break;
                    case 5:
                        System.out.println("Logged Out Successfully.");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid Choice! Please enter 1-5.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------

    private void registerPatient() throws Exception {
        System.out.println("\n--- Register New Patient ---");
        System.out.print("First Name   : ");
        String firstName = br.readLine().trim();
        System.out.print("Last Name    : ");
        String lastName = br.readLine().trim();

        Gender gender = null;
        while (gender == null) {
            System.out.print("Gender (MALE/FEMALE/OTHER): ");
            try {
                gender = Gender.valueOf(br.readLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("  Invalid gender. Please enter MALE, FEMALE, or OTHER.");
            }
        }

        System.out.print("Date of Birth (YYYY-MM-DD) [e.g. 1995-05-20]: ");
        String dob = br.readLine().trim();
        if (dob.isEmpty()) {
            dob = "2000-01-01";
        }

        System.out.print("Phone        : ");
        String phone = br.readLine().trim();
        System.out.print("Blood Group (A+, B+, O+, AB+, etc.): ");
        String bloodGroup = br.readLine().trim();
        System.out.print("Address      : ");
        String address = br.readLine().trim();

        PatientDTO patient = new PatientDTO();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setGender(gender);
        patient.setDob(dob);
        patient.setPhone(phone);
        patient.setBloodGroup(bloodGroup);
        patient.setAddress(address);

        boolean success = patientController.registerPatient(patient);
        System.out.println(success ? "Patient registered successfully!" : "Failed to register patient.");
    }

    private void bookAppointment() throws Exception {
        System.out.println("\n--- Book Appointment ---");
        System.out.print("Doctor ID                      : ");
        int doctorId = readInt();
        System.out.print("Patient ID                     : ");
        int patientId = readInt();
        System.out.print("Appointment Date (YYYY-MM-DD)  : ");
        String date = br.readLine().trim();
        System.out.print("Appointment Time (HH:MM:SS)    : ");
        String time = br.readLine().trim();
        if (time.isEmpty()) {
            time = "10:00:00";
        }
        System.out.print("Reason for Visit               : ");
        String reason = br.readLine().trim();

        AppointmentDTO appointment = new AppointmentDTO();
        appointment.setDoctorId(doctorId);
        appointment.setPatientId(patientId);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setReason(reason);

        boolean success = appointmentController.bookAppointment(appointment);
        System.out.println(success ? "Appointment booked successfully!" : "Failed to book appointment.");
    }

    private void viewAllAppointments() {
        System.out.println("\n--- All Appointments ---");
        List<AppointmentDTO> appointments = appointmentController.getAllAppointments();
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.printf("%-5s %-10s %-10s %-12s %-10s %-15s %-12s%n",
                "ID", "Doctor ID", "Patient ID", "Date", "Time", "Reason", "Status");
        System.out.println("----------------------------------------------------------------------------------");
        for (AppointmentDTO a : appointments) {
            System.out.printf("%-5d %-10d %-10d %-12s %-10s %-15s %-12s%n",
                    a.getAppointmentId(), a.getDoctorId(), a.getPatientId(),
                    a.getAppointmentDate(), a.getAppointmentTime(),
                    (a.getReason() != null ? a.getReason() : "-"),
                    a.getStatus());
        }
    }

    private void viewAllPatients() {
        System.out.println("\n--- All Patients ---");
        List<PatientDTO> patients = patientController.getAllPatients();
        if (patients == null || patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.printf("%-5s %-20s %-8s %-12s %-14s %-10s%n",
                "ID", "Name", "Gender", "DOB", "Phone", "Blood Grp");
        System.out.println("-------------------------------------------------------------------------------");
        for (PatientDTO p : patients) {
            System.out.printf("%-5d %-20s %-8s %-12s %-14s %-10s%n",
                    p.getPatientId(), p.getFullName(),
                    p.getGender(), p.getDob(), p.getPhone(), p.getBloodGroup());
        }
    }

    // -----------------------------------------------------------------------

    private int readInt() {
        try {
            return Integer.parseInt(br.readLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }
}
