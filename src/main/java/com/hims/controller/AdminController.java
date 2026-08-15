package com.hims.controller;

import java.util.List;

import com.hims.dto.DoctorDTO;
import com.hims.dto.UserDTO;
import com.hims.enums.Role;
import com.hims.enums.UserStatus;
import com.hims.service.DoctorService;
import com.hims.service.UserService;
import com.hims.serviceimpl.DoctorServiceImpl;
import com.hims.serviceimpl.UserServiceImpl;

public class AdminController {

    private final UserService   userService   = new UserServiceImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();

    /**
     * Creates a user account with DOCTOR role, then creates the doctor profile.
     * Returns true if both inserts succeed.
     */
    public boolean createDoctor(String email, String password, DoctorDTO doctor) {
        UserDTO user = new UserDTO();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.DOCTOR);
        user.setStatus(UserStatus.ACTIVE);

        int userId = userService.createUser(user);
        if (userId <= 0) return false;

        doctor.setUserId(userId);
        int doctorId = doctorService.createDoctor(doctor);
        return doctorId > 0;
    }

    /**
     * Creates a user account with RECEPTIONIST role.
     */
    public boolean createReceptionist(String email, String password) {
        UserDTO user = new UserDTO();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.RECEPTIONIST);
        user.setStatus(UserStatus.ACTIVE);
        return userService.createUser(user) > 0;
    }

    /** Returns all registered doctors. */
    public List<DoctorDTO> getDoctors() {
        return doctorService.getAllDoctors();
    }

    /** Returns all registered receptionists. */
    public List<UserDTO> getReceptionists() {
        return userService.getUsersByRole(Role.RECEPTIONIST);
    }
}
