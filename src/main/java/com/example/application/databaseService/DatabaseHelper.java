package com.example.application.databaseService;

import com.example.application.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {



    // Метод для получения соединения с базой данных
    public static Connection connect() {
        Settings settings = new Settings();
        try {
            return DriverManager.getConnection(settings.getPathBDSqlite());
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
            return null;
        }
    }

    // Метод для закрытия ресурсов
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии ресурсов: " + e.getMessage());
        }
    }
}