package com.hims.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hims.dao.DoctorDao;
import com.hims.dto.DoctorDTO;
import com.hims.enums.Gender;
import com.hims.util.DbConnection;

public class DoctorDaoImpl implements DoctorDao {

    @Override
    public int createDoctor(DoctorDTO doctor) {
        String sql = "INSERT INTO tbl_doctor (user_id, first_name, last_name, gender, phone, " +
                     "specialization, qualification, experience, consultation_fee) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, doctor.getUserId());
            ps.setString(2, doctor.getFirstName());
            ps.setString(3, doctor.getLastName());
            ps.setString(4, doctor.getGender().name());
            ps.setString(5, doctor.getPhone());
            ps.setString(6, doctor.getSpecialization());
            ps.setString(7, doctor.getQualification());
            ps.setInt(8, doctor.getExperience());
            ps.setDouble(9, doctor.getConsultationFee());
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
    public List<DoctorDTO> getAllDoctors() {
        String sql = "SELECT * FROM tbl_doctor";
        List<DoctorDTO> doctors = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                doctors.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public DoctorDTO getDoctorByUserId(int userId) {
        String sql = "SELECT * FROM tbl_doctor WHERE user_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private DoctorDTO mapRow(ResultSet rs) throws Exception {
        DoctorDTO d = new DoctorDTO();
        d.setDoctorId(rs.getInt("doctor_id"));
        d.setUserId(rs.getInt("user_id"));
        d.setFirstName(rs.getString("first_name"));
        d.setLastName(rs.getString("last_name"));
        d.setGender(Gender.valueOf(rs.getString("gender")));
        d.setPhone(rs.getString("phone"));
        d.setSpecialization(rs.getString("specialization"));
        d.setQualification(rs.getString("qualification"));
        d.setExperience(rs.getInt("experience"));
        d.setConsultationFee(rs.getDouble("consultation_fee"));
        return d;
    }
}
