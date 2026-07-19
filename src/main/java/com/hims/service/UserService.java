package com.hims.service;

import com.hims.dto.UserDTO;

public interface UserService {
	public UserDTO login(String email,String password);
}
