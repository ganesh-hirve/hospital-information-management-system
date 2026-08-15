package com.hims.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {

    private static final String url;
    private static final String user;
    private static final String password;

    static {
        try (InputStream in = DbConnection.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            Properties props = new Properties();
            props.load(in);
            url      = props.getProperty("db.url");
            user     = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB config from config.properties", e);
        }
    }

    /** Returns a new Connection — caller must close it (use try-with-resources). */
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
