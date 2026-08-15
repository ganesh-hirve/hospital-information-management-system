package com.hims.dao;

import java.util.List;

import com.hims.dto.PatientDTO;

public interface PatientDao {
    int             createPatient(PatientDTO patient);
    List<PatientDTO> getAllPatients();
    PatientDTO      getPatientById(int id);
}
