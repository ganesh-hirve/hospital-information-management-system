package com.hims.serviceimpl;

import java.util.List;

import com.hims.dao.DoctorDao;
import com.hims.daoimpl.DoctorDaoImpl;
import com.hims.dto.DoctorDTO;
import com.hims.service.DoctorService;

public class DoctorServiceImpl implements DoctorService {

    private final DoctorDao doctorDao = new DoctorDaoImpl();

    @Override
    public int createDoctor(DoctorDTO doctor) {
        return doctorDao.createDoctor(doctor);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorDao.getAllDoctors();
    }

    @Override
    public DoctorDTO getDoctorByUserId(int userId) {
        return doctorDao.getDoctorByUserId(userId);
    }
}
