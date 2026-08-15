package com.hims.serviceimpl;

import java.util.List;

import com.hims.dao.AppointmentDao;
import com.hims.daoimpl.AppointmentDaoImpl;
import com.hims.dto.AppointmentDTO;
import com.hims.enums.AppointmentStatus;
import com.hims.service.AppointmentService;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDao appointmentDao = new AppointmentDaoImpl();

    @Override
    public int bookAppointment(AppointmentDTO appointment) {
        return appointmentDao.bookAppointment(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctor(int doctorId) {
        return appointmentDao.getAppointmentsByDoctor(doctorId);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }

    @Override
    public boolean updateStatus(int appointmentId, AppointmentStatus status) {
        return appointmentDao.updateStatus(appointmentId, status);
    }
}
