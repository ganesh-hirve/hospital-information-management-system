package com.hims.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.hims.controller.AuthController;
import com.hims.dto.UserDTO;
import com.hims.enums.UserStatus;

public class Dashboard {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        try {
            AuthController authController = new AuthController();

            System.out.println("==================================");
            System.out.println("  HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("LOGIN");
            System.out.println("----------------------------------");
            System.out.print("Email    : ");
            String email = br.readLine();
            System.out.print("Password : ");
            String password = br.readLine();

            UserDTO loggedInUser = authController.adminLogin(email, password);

            if (loggedInUser == null) {
                System.out.println("\nInvalid Email or Password!");
                return;
            }

            if (loggedInUser.getStatus() == UserStatus.INACTIVE) {
                System.out.println("\nYour account is deactivated. Please contact the administrator.");
                return;
            }

            switch (loggedInUser.getRole()) {
                case ADMIN:
                    new AdminDashboard(loggedInUser).showDashboard();
                    break;
                case DOCTOR:
                    new DoctorDashboard(loggedInUser).showDashboard();
                    break;
                case RECEPTIONIST:
                    new ReceptionistDashboard(loggedInUser).showDashboard();
                    break;
                default:
                    System.out.println("Unknown role: " + loggedInUser.getRole());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
