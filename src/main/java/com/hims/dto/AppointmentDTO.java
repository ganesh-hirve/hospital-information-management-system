package com.hims.dto;

import com.hims.enums.AppointmentStatus;

public class AppointmentDTO {

    private int               appointmentId;
    private int               doctorId;
    private int               patientId;
    private String            appointmentDate; // format: YYYY-MM-DD
    private String            appointmentTime; // format: HH:MM:SS
    private String            reason;
    private AppointmentStatus status;

    public AppointmentDTO() {}

    public int getAppointmentId()                    { return appointmentId; }
    public void setAppointmentId(int id)             { this.appointmentId = id; }

    public int getDoctorId()                         { return doctorId; }
    public void setDoctorId(int doctorId)            { this.doctorId = doctorId; }

    public int getPatientId()                        { return patientId; }
    public void setPatientId(int patientId)          { this.patientId = patientId; }

    public String getAppointmentDate()               { return appointmentDate; }
    public void setAppointmentDate(String date)      { this.appointmentDate = date; }

    public String getAppointmentTime()               { return appointmentTime; }
    public void setAppointmentTime(String time)      { this.appointmentTime = time; }

    public String getReason()                        { return reason; }
    public void setReason(String reason)             { this.reason = reason; }

    public AppointmentStatus getStatus()             { return status; }
    public void setStatus(AppointmentStatus status)  { this.status = status; }

    @Override
    public String toString() {
        return "AppointmentDTO [appointmentId=" + appointmentId +
               ", doctorId=" + doctorId + ", patientId=" + patientId +
               ", date=" + appointmentDate + ", time=" + appointmentTime +
               ", reason=" + reason + ", status=" + status + "]";
    }
}
