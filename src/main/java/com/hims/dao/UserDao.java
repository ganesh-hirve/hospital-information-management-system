package com.hims.dao;

import com.hims.dto.UserDTO;

public interface UserDao {
	public UserDTO login(String email,String password);
}
