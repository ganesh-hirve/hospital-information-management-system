package com.hims.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.hims.controller.AdminController;
import com.hims.dto.DoctorDTO;
import com.hims.dto.UserDTO;
import com.hims.enums.Gender;

public class AdminDashboard {

    private final UserDTO          admin;
    private final AdminController  adminController = new AdminController();
    private final BufferedReader   br = new BufferedReader(new InputStreamReader(System.in));

    public AdminDashboard(UserDTO admin) {
        this.admin = admin;
    }

    public void showDashboard() {
        try {
            boolean running = true;
            while (running) {
                System.out.println("\n========== ADMIN DASHBOARD ==========");
                System.out.println("Logged in as: " + admin.getEmail());
                System.out.println("--------------------------------------");
                System.out.println("1. Register Doctor");
                System.out.println("2. Register Receptionist");
                System.out.println("3. View All Doctors");
                System.out.println("4. View All Receptionists");
                System.out.println("5. Logout");
                System.out.println("--------------------------------------");
                System.out.print("Enter Choice: ");

                int choice = readInt();

                switch (choice) {
                    case 1: registerDoctor();       break;
                    case 2: registerReceptionist(); break;
                    case 3: viewDoctors();          break;
                    case 4: viewReceptionists();    break;
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

    private void registerDoctor() throws Exception {
        System.out.println("\n--- Register New Doctor ---");
        System.out.print("Email          : ");
        String email = br.readLine().trim();
        System.out.print("Password       : ");
        String password = br.readLine();
        System.out.print("First Name     : ");
        String firstName = br.readLine().trim();
        System.out.print("Last Name      : ");
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

        System.out.print("Phone          : ");
        String phone = br.readLine().trim();
        System.out.print("Specialization : ");
        String specialization = br.readLine().trim();
        System.out.print("Qualification  : ");
        String qualification = br.readLine().trim();
        System.out.print("Experience (yrs): ");
        int experience = readInt();
        System.out.print("Consultation Fee: ");
        double fee = readDouble();

        DoctorDTO doctor = new DoctorDTO();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setGender(gender);
        doctor.setPhone(phone);
        doctor.setSpecialization(specialization);
        doctor.setQualification(qualification);
        doctor.setExperience(experience);
        doctor.setConsultationFee(fee);

        boolean success = adminController.createDoctor(email, password, doctor);
        System.out.println(success
                ? "Doctor registered successfully!"
                : "Failed to register doctor. Email may already exist.");
    }

    private void registerReceptionist() throws Exception {
        System.out.println("\n--- Register New Receptionist ---");
        System.out.print("Email    : ");
        String email = br.readLine().trim();
        System.out.print("Password : ");
        String password = br.readLine();

        boolean success = adminController.createReceptionist(email, password);
        System.out.println(success
                ? "Receptionist registered successfully!"
                : "Failed to register receptionist. Email may already exist.");
    }

    private void viewDoctors() {
        System.out.println("\n--- Doctors List ---");
        List<DoctorDTO> doctors = adminController.getDoctors();
        if (doctors == null || doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        System.out.printf("%-5s %-15s %-15s %-20s %-10s %-6s%n",
                "ID", "First Name", "Last Name", "Specialization", "Fee (Rs)", "Exp");
        System.out.println("------------------------------------------------------------------------");
        for (DoctorDTO d : doctors) {
            System.out.printf("%-5d %-15s %-15s %-20s %-10.2f %-6d%n",
                    d.getDoctorId(), d.getFirstName(), d.getLastName(),
                    d.getSpecialization(), d.getConsultationFee(), d.getExperience());
        }
    }

    private void viewReceptionists() {
        System.out.println("\n--- Receptionists List ---");
        List<UserDTO> list = adminController.getReceptionists();
        if (list == null || list.isEmpty()) {
            System.out.println("No receptionists found.");
            return;
        }
        System.out.printf("%-5s %-35s %-10s%n", "ID", "Email", "Status");
        System.out.println("----------------------------------------------------");
        for (UserDTO r : list) {
            System.out.printf("%-5d %-35s %-10s%n", r.getUserId(), r.getEmail(), r.getStatus());
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

    private double readDouble() {
        try {
            return Double.parseDouble(br.readLine().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
}