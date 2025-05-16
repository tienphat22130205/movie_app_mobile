package com.example.movieapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/movie_app";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Nếu có password thì điền vào đây

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

