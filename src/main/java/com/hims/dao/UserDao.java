package com.hims.dao;

import java.util.List;

import com.hims.dto.UserDTO;
import com.hims.enums.Role;

public interface UserDao {
    UserDTO      login(String email, String password);
    int          createUser(UserDTO user);
    List<UserDTO> getUsersByRole(Role role);
}
