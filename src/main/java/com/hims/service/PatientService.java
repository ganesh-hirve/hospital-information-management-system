package com.hims.service;

import java.util.List;

import com.hims.dto.PatientDTO;

public interface PatientService {
    int              createPatient(PatientDTO patient);
    List<PatientDTO> getAllPatients();
    PatientDTO       getPatientById(int id);
}
