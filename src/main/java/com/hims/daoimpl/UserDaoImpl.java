package com.hims.daoimpl;

import com.hims.dto.UserDTO;
import com.hims.enums.Role;
import com.hims.enums.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hims.dao.UserDao;

import com.hims.util.DbConnection;

public class UserDaoImpl implements UserDao {
	Connection con = null;
	PreparedStatement ps = null;

	@Override
	public UserDTO login(String email, String password) {
		UserDTO user = null;
		try {
			con = DbConnection.establishConnection();
			ps = con.prepareStatement("SELECT * FROM tbl_user WHERE email = ? AND password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserDTO();

				user.setUserId(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(Role.valueOf(rs.getString("role")));
				user.setStatus(UserStatus.valueOf(rs.getString("status")));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return user;

	}
}
