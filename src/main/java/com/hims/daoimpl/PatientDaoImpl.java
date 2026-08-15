package com.hims.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hims.dao.PatientDao;
import com.hims.dto.PatientDTO;
import com.hims.enums.Gender;
import com.hims.util.DbConnection;

public class PatientDaoImpl implements PatientDao {

    @Override
    public int createPatient(PatientDTO patient) {
        String sql = "INSERT INTO tbl_patient (first_name, last_name, gender, dob, phone, blood_group, address) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patient.getFirstName() != null ? patient.getFirstName() : "");
            ps.setString(2, patient.getLastName() != null ? patient.getLastName() : "");
            ps.setString(3, patient.getGender() != null ? patient.getGender().name() : Gender.OTHER.name());
            ps.setString(4, (patient.getDob() != null && !patient.getDob().trim().isEmpty()) ? patient.getDob() : "2000-01-01");
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getBloodGroup());
            ps.setString(7, patient.getAddress());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        String sql = "SELECT * FROM tbl_patient";
        List<PatientDTO> patients = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public PatientDTO getPatientById(int id) {
        String sql = "SELECT * FROM tbl_patient WHERE patient_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PatientDTO mapRow(ResultSet rs) throws Exception {
        PatientDTO p = new PatientDTO();
        p.setPatientId(rs.getInt("patient_id"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));
        
        String genderStr = rs.getString("gender");
        if (genderStr != null) {
            try {
                p.setGender(Gender.valueOf(genderStr.toUpperCase()));
            } catch (Exception e) {
                p.setGender(Gender.OTHER);
            }
        }
        
        java.sql.Date dobDate = rs.getDate("dob");
        p.setDob(dobDate != null ? dobDate.toString() : "");
        p.setPhone(rs.getString("phone"));
        p.setBloodGroup(rs.getString("blood_group"));
        p.setAddress(rs.getString("address"));
        return p;
    }
}
