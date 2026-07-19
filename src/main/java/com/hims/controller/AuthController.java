package com.hims.controller;
import com.hims.dto.UserDTO;
import com.hims.service.UserService;
import com.hims.serviceimpl.UserServiceImpl;
public class AuthController {
	UserService userService =new UserServiceImpl();
		public UserDTO adminLogin(String email,String password) {
			return userService.login(email, password);
		}
}
