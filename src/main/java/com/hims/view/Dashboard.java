package com.hims.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.hims.controller.AuthController;
import com.hims.dto.UserDTO;
import com.hims.enums.Role;

public class Dashboard {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			AuthController user=new AuthController();
		System.out.println("--------------------------");
		System.out.println("HOSPITAL MANAGEMENT SYSTEM");
		System.out.println("--------------------------");
		System.out.println("LOGIN:");
		System.out.println("Enter Email:");
		String email=br.readLine();
		System.out.println("Enter password:");
		String password=br.readLine();
		UserDTO loggedInUser = user.adminLogin(email, password);

		if (loggedInUser != null) {

		    switch (loggedInUser.getRole()) {

		        case ADMIN:
		            System.out.println("\n===== ADMIN DASHBOARD =====");
		            break;

		        case DOCTOR:
		            System.out.println("\n===== DOCTOR DASHBOARD =====");
		            break;

		        case RECEPTIONIST:
		            System.out.println("\n===== RECEPTIONIST DASHBOARD =====");
		            break;

		        default:
		            System.out.println("Invalid Role!");
		    }

		} else {
		    System.out.println("Invalid Email or Password!");
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
