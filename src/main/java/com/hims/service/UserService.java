package com.hims.service;

import java.util.List;

import com.hims.dto.UserDTO;
import com.hims.enums.Role;

public interface UserService {
    UserDTO       login(String email, String password);
    int           createUser(UserDTO user);
    List<UserDTO> getUsersByRole(Role role);
}
