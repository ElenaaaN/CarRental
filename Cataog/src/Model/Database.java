package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Database {
    private Connection connection;

    // Constructor for opening the connection
    public Database() throws SQLException {
        connect();
    }

    // Method to establish a connection to the database
    private void connect() throws SQLException {
        try {
            // Load the JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/CarRental";
            String user = "root";
            String password = "Elena123"; // Consider using environment variables for credentials
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error connecting to the database: " + e.getMessage());
        }
    }

    // Method to obtain a PreparedStatement
    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            throw new SQLException("Connection is not established.");
        }
        return this.connection.prepareStatement(query);
    }

    // Method to close the connection
    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Optional: Method to check if the connection is valid
    public boolean isConnected() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
