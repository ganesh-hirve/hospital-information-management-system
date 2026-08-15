package com.hims.controller;

import java.util.List;

import com.hims.dto.PatientDTO;
import com.hims.service.PatientService;
import com.hims.serviceimpl.PatientServiceImpl;

public class PatientController {

    private final PatientService patientService = new PatientServiceImpl();

    public boolean registerPatient(PatientDTO patient) {
        return patientService.createPatient(patient) > 0;
    }

    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    public PatientDTO getPatientById(int id) {
        return patientService.getPatientById(id);
    }
}
