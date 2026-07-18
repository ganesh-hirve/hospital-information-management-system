package com.hims.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	public Connection con = null;

	public static Connection establishConnection() {

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hospital_management_system", "root", "root");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public static void main(String[] args) {
		Connection con = DbConnection.establishConnection();
		if (con != null) {
			System.out.println("Connected...");
		} else {
			System.out.println("Not connected..");
		}
	}
}
