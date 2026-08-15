package com.hims.service;

import java.util.List;

import com.hims.dto.DoctorDTO;

public interface DoctorService {
    int             createDoctor(DoctorDTO doctor);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO       getDoctorByUserId(int userId);
}
