package com.hims.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hims.dao.UserDao;
import com.hims.dto.UserDTO;
import com.hims.enums.Role;
import com.hims.enums.UserStatus;
import com.hims.util.DbConnection;

public class UserDaoImpl implements UserDao {

    @Override
    public UserDTO login(String email, String password) {
        String sql = "SELECT * FROM tbl_user WHERE email = ? AND password = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserDTO user = new UserDTO();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(Role.valueOf(rs.getString("role")));
                    user.setStatus(UserStatus.valueOf(rs.getString("status")));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int createUser(UserDTO user) {
        String sql = "INSERT INTO tbl_user (email, password, role, status) VALUES (?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getStatus().name());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<UserDTO> getUsersByRole(Role role) {
        String sql = "SELECT * FROM tbl_user WHERE role = ?";
        List<UserDTO> users = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, role.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserDTO u = new UserDTO();
                    u.setUserId(rs.getInt("user_id"));
                    u.setEmail(rs.getString("email"));
                    u.setRole(Role.valueOf(rs.getString("role")));
                    u.setStatus(UserStatus.valueOf(rs.getString("status")));
                    users.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
