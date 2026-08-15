package com.hims.dao;

import java.util.List;

import com.hims.dto.AppointmentDTO;
import com.hims.enums.AppointmentStatus;

public interface AppointmentDao {
    int                  bookAppointment(AppointmentDTO appointment);
    List<AppointmentDTO> getAppointmentsByDoctor(int doctorId);
    List<AppointmentDTO> getAllAppointments();
    boolean              updateStatus(int appointmentId, AppointmentStatus status);
}
