package com.library.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database utility class for managing database connections
 * Uses connection pooling for better performance
 */
public class DatabaseUtil {
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_DRIVER;

    // Static block to load database configuration
    static {
        loadDatabaseConfig();
    }

    /**
     * Load database configuration from config.properties file
     */
    private static void loadDatabaseConfig() {
        Properties props = new Properties();
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Unable to find config.properties");
                // Use default values for development
                DB_URL = "jdbc:postgresql://localhost:5432/library_db_1";
                DB_USERNAME = "postgres";
                DB_PASSWORD = "postgres";
                DB_DRIVER = "org.postgresql.Driver";
                return;
            }

            props.load(input);
            DB_URL = props.getProperty("db.url");
            DB_USERNAME = props.getProperty("db.username");
            DB_PASSWORD = props.getProperty("db.password");
            DB_DRIVER = props.getProperty("db.driver");

            // Load the PostgreSQL JDBC driver
            Class.forName(DB_DRIVER);
            System.out.println("Database configuration loaded successfully");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get a database connection
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            return conn;
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Close database connection
     * 
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test database connection
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get database URL (for debugging purposes)
     * 
     * @return database URL
     */
    public static String getDatabaseUrl() {
        return DB_URL;
    }
}
