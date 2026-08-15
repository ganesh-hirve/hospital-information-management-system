package com.hims.dao;

import java.util.List;

import com.hims.dto.DoctorDTO;

public interface DoctorDao {
    int           createDoctor(DoctorDTO doctor);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO     getDoctorByUserId(int userId);
}
