package com.hims.dto;

import com.hims.enums.Role;
import com.hims.enums.UserStatus;

public class UserDTO {

    private int userId;
    private String email;
    private String password;
    private Role role;
    private UserStatus status;

    public UserDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDTO [userId=" + userId +
                ", email=" + email +
                ", role=" + role +
                ", status=" + status + "]";
    }
}