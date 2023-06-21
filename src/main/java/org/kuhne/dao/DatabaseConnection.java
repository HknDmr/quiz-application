package org.kuhne.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = createDatabaseConnection();
        } else if (connection.isClosed()) {
            connection = createDatabaseConnection();
        }
        return connection;
    }

    public static Connection createDatabaseConnection() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");

            String jdbcUrl = "jdbc:h2:~/test";
            String username = "sa";
            String password = "";

            connection = DriverManager.getConnection(jdbcUrl, username, password);

            System.out.println("Database connection is success.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC could not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to database.");
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeDatabaseConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection.");
            e.printStackTrace();
        }
    }
}
