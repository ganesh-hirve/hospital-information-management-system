package com.hims.serviceimpl;

import java.util.List;

import com.hims.dao.PatientDao;
import com.hims.daoimpl.PatientDaoImpl;
import com.hims.dto.PatientDTO;
import com.hims.service.PatientService;

public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao = new PatientDaoImpl();

    @Override
    public int createPatient(PatientDTO patient) {
        return patientDao.createPatient(patient);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientDao.getAllPatients();
    }

    @Override
    public PatientDTO getPatientById(int id) {
        return patientDao.getPatientById(id);
    }
}
