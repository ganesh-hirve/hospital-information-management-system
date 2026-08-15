package com.hims.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hims.dao.AppointmentDao;
import com.hims.dto.AppointmentDTO;
import com.hims.enums.AppointmentStatus;
import com.hims.util.DbConnection;

public class AppointmentDaoImpl implements AppointmentDao {

    @Override
    public int bookAppointment(AppointmentDTO appointment) {
        String sql = "INSERT INTO tbl_appointment (doctor_id, patient_id, appointment_date, appointment_time, reason, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, appointment.getDoctorId());
            ps.setInt(2, appointment.getPatientId());
            ps.setString(3, appointment.getAppointmentDate());
            ps.setString(4, (appointment.getAppointmentTime() != null && !appointment.getAppointmentTime().isEmpty()) ? appointment.getAppointmentTime() : "10:00:00");
            ps.setString(5, (appointment.getReason() != null) ? appointment.getReason() : "General Consultation");
            ps.setString(6, (appointment.getStatus() != null) ? appointment.getStatus().name() : AppointmentStatus.PENDING.name());
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
    public List<AppointmentDTO> getAppointmentsByDoctor(int doctorId) {
        String sql = "SELECT * FROM tbl_appointment WHERE doctor_id = ?";
        List<AppointmentDTO> list = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        String sql = "SELECT * FROM tbl_appointment";
        List<AppointmentDTO> list = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateStatus(int appointmentId, AppointmentStatus status) {
        String sql = "UPDATE tbl_appointment SET status = ? WHERE appointment_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private AppointmentDTO mapRow(ResultSet rs) throws Exception {
        AppointmentDTO a = new AppointmentDTO();
        a.setAppointmentId(rs.getInt("appointment_id"));
        a.setDoctorId(rs.getInt("doctor_id"));
        a.setPatientId(rs.getInt("patient_id"));
        
        java.sql.Date d = rs.getDate("appointment_date");
        a.setAppointmentDate(d != null ? d.toString() : "");
        
        java.sql.Time t = rs.getTime("appointment_time");
        a.setAppointmentTime(t != null ? t.toString() : "");
        
        a.setReason(rs.getString("reason"));
        
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            try {
                a.setStatus(AppointmentStatus.valueOf(statusStr.toUpperCase()));
            } catch (Exception e) {
                a.setStatus(AppointmentStatus.PENDING);
            }
        }
        return a;
    }
}
