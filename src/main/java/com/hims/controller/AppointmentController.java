package com.hims.controller;

import java.util.ArrayList;
import java.util.List;

import com.hims.dto.AppointmentDTO;
import com.hims.dto.DoctorDTO;
import com.hims.enums.AppointmentStatus;
import com.hims.service.AppointmentService;
import com.hims.service.DoctorService;
import com.hims.serviceimpl.AppointmentServiceImpl;
import com.hims.serviceimpl.DoctorServiceImpl;

public class AppointmentController {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final DoctorService      doctorService      = new DoctorServiceImpl();

    public boolean bookAppointment(AppointmentDTO appointment) {
        return appointmentService.bookAppointment(appointment) > 0;
    }

    /**
     * Gets appointments for a doctor using their user_id.
     * Internally resolves user_id → doctor_id first.
     */
    public List<AppointmentDTO> getAppointmentsByDoctor(int userId) {
        DoctorDTO doctor = doctorService.getDoctorByUserId(userId);
        if (doctor == null) return new ArrayList<>();
        return appointmentService.getAppointmentsByDoctor(doctor.getDoctorId());
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    public boolean updateAppointmentStatus(int appointmentId, AppointmentStatus status) {
        return appointmentService.updateStatus(appointmentId, status);
    }
}
