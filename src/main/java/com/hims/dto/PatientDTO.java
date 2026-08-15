package com.hims.dto;

import com.hims.enums.Gender;

public class PatientDTO {

    private int    patientId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String dob; // YYYY-MM-DD
    private String phone;
    private String bloodGroup;
    private String address;

    public PatientDTO() {}

    public int getPatientId()                  { return patientId; }
    public void setPatientId(int patientId)    { this.patientId = patientId; }

    public String getFirstName()               { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName()                { return lastName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }

    public String getFullName() {
        String fn = (firstName != null) ? firstName : "";
        String ln = (lastName != null) ? lastName : "";
        return (fn + " " + ln).trim();
    }

    // Convenience backward compatibility methods
    public String getName() {
        return getFullName();
    }

    public void setName(String name) {
        if (name != null) {
            String[] parts = name.trim().split("\\s+", 2);
            this.firstName = parts[0];
            this.lastName = (parts.length > 1) ? parts[1] : "";
        }
    }

    public Gender getGender()                  { return gender; }
    public void setGender(Gender gender)       { this.gender = gender; }

    public String getDob()                     { return dob; }
    public void setDob(String dob)             { this.dob = dob; }

    public String getPhone()                   { return phone; }
    public void setPhone(String phone)         { this.phone = phone; }

    public String getBloodGroup()              { return bloodGroup; }
    public void setBloodGroup(String bg)       { this.bloodGroup = bg; }

    public String getAddress()                 { return address; }
    public void setAddress(String address)     { this.address = address; }

    @Override
    public String toString() {
        return "PatientDTO [patientId=" + patientId + ", name=" + getFullName() +
               ", gender=" + gender + ", dob=" + dob +
               ", phone=" + phone + ", bloodGroup=" + bloodGroup + "]";
    }
}
