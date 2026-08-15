package com.hims.serviceimpl;

import java.util.List;

import com.hims.dao.UserDao;
import com.hims.daoimpl.UserDaoImpl;
import com.hims.dto.UserDTO;
import com.hims.enums.Role;
import com.hims.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public UserDTO login(String email, String password) {
        return userDao.login(email, password);
    }

    @Override
    public int createUser(UserDTO user) {
        return userDao.createUser(user);
    }

    @Override
    public List<UserDTO> getUsersByRole(Role role) {
        return userDao.getUsersByRole(role);
    }
}
