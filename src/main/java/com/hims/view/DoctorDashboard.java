package com.hims.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.hims.controller.AppointmentController;
import com.hims.dto.AppointmentDTO;
import com.hims.dto.UserDTO;
import com.hims.enums.AppointmentStatus;

public class DoctorDashboard {

    private final UserDTO               doctor;
    private final AppointmentController appointmentController = new AppointmentController();
    private final BufferedReader        br = new BufferedReader(new InputStreamReader(System.in));

    public DoctorDashboard(UserDTO doctor) {
        this.doctor = doctor;
    }

    public void showDashboard() {
        try {
            boolean running = true;
            while (running) {
                System.out.println("\n========== DOCTOR DASHBOARD ==========");
                System.out.println("Logged in as: " + doctor.getEmail());
                System.out.println("--------------------------------------");
                System.out.println("1. View My Appointments");
                System.out.println("2. Update Appointment Status");
                System.out.println("3. Logout");
                System.out.println("--------------------------------------");
                System.out.print("Enter Choice: ");

                int choice = readInt();

                switch (choice) {
                    case 1: viewMyAppointments();      break;
                    case 2: updateAppointmentStatus(); break;
                    case 3:
                        System.out.println("Logged Out Successfully.");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid Choice! Please enter 1-3.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------

    private void viewMyAppointments() {
        System.out.println("\n--- My Appointments ---");
        List<AppointmentDTO> appointments = appointmentController.getAppointmentsByDoctor(doctor.getUserId());
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.printf("%-5s %-12s %-15s %-15s%n", "ID", "Patient ID", "Date", "Status");
        System.out.println("-----------------------------------------------");
        for (AppointmentDTO a : appointments) {
            System.out.printf("%-5d %-12d %-15s %-15s%n",
                    a.getAppointmentId(), a.getPatientId(),
                    a.getAppointmentDate(), a.getStatus());
        }
    }

    private void updateAppointmentStatus() throws Exception {
        System.out.println("\n--- Update Appointment Status ---");
        System.out.print("Appointment ID  : ");
        int aptId = readInt();
        System.out.print("New Status (PENDING/CONFIRMED/COMPLETED/CANCELLED): ");
        String input = br.readLine().trim().toUpperCase();
        try {
            AppointmentStatus status = AppointmentStatus.valueOf(input);
            boolean success = appointmentController.updateAppointmentStatus(aptId, status);
            System.out.println(success
                    ? "Status updated successfully!"
                    : "Update failed. Check the appointment ID.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Use: PENDING, CONFIRMED, COMPLETED, CANCELLED");
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
