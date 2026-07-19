package com.hims.serviceimpl;
import com.hims.dao.UserDao;
import com.hims.daoimpl.UserDaoImpl;
import com.hims.dto.UserDTO;
import com.hims.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao userDao=new UserDaoImpl();
	@Override
	public UserDTO login(String email, String password) {
		return userDao.login(email, password);
	}

}
