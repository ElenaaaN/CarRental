package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainAppFrame extends JFrame {
    private JTable carTable;
    private DefaultTableModel carTableModel;

    public MainAppFrame() {
        this.setTitle("Car Rental - Main Dashboard");
        this.setSize(800, 600);  // Increased size for better viewing
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setLayout(new BorderLayout());

        // Title label in the center
        JLabel titleLabel = new JLabel("Car Rental Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Menu Panel - added the Feedback button and Show Rented Cars button
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton showFeedbackButton = new JButton("Feedback");
        showFeedbackButton.setBackground(new Color(255, 165, 0));  // Orange color
        showFeedbackButton.setForeground(Color.WHITE);
        showFeedbackButton.setFocusPainted(false);
        showFeedbackButton.addActionListener(new FeedbackButtonListener());

        JButton showRentedCarsButton = new JButton("Show Rented Cars");
        showRentedCarsButton.setBackground(new Color(255, 99, 71));  // Red color
        showRentedCarsButton.setForeground(Color.WHITE);
        showRentedCarsButton.setFocusPainted(false);
        showRentedCarsButton.addActionListener(new ShowRentedCarsListener());

        menuPanel.add(showFeedbackButton);
        menuPanel.add(showRentedCarsButton);
        headerPanel.add(menuPanel, BorderLayout.WEST);

        this.add(headerPanel, BorderLayout.NORTH);

        // Car Table - Displays available cars
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Brand", "Model", "Color", "Year", "Price per day", "Available"};
        carTableModel = new DefaultTableModel(columnNames, 0);
        carTable = new JTable(carTableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.CENTER);

        // Footer Panel - Buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton showAvailableButton = new JButton("Show Available Cars");
        showAvailableButton.setBackground(new Color(60, 179, 113));
        showAvailableButton.setForeground(Color.WHITE);
        showAvailableButton.setFocusPainted(false);
        showAvailableButton.addActionListener(new ShowAvailableCarsListener());

        JButton rentButton = new JButton("Rent Selected Car");
        rentButton.setBackground(new Color(100, 149, 237));
        rentButton.setForeground(Color.WHITE);
        rentButton.setFocusPainted(false);
        rentButton.addActionListener(new RentCarListener());

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(220, 20, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        footerPanel.add(showAvailableButton);
        footerPanel.add(rentButton);
        footerPanel.add(exitButton);
        this.add(footerPanel, BorderLayout.SOUTH);

        // Initially display all cars
        displayAllCars();

        this.setVisible(true);
    }

    private void displayAllCars() {
        // Clear existing rows in table
        carTableModel.setRowCount(0);

        try {
            Database database = new Database();
            String query = "SELECT * FROM Car";
            PreparedStatement statement = database.getPreparedStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("Id"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getString("culoare"),
                        resultSet.getInt("an"),
                        resultSet.getInt("pret"),
                        resultSet.getBoolean("disponibilitate") ? "Yes" : "No"
                };
                carTableModel.addRow(row);
            }
            database.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading cars: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ShowAvailableCarsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear existing rows in table
            carTableModel.setRowCount(0);

            try {
                Database database = new Database();
                String query = "SELECT * FROM Car WHERE disponibilitate = TRUE";
                PreparedStatement statement = database.getPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Object[] row = {
                            resultSet.getInt("Id"),
                            resultSet.getString("brand"),
                            resultSet.getString("model"),
                            resultSet.getString("culoare"),
                            resultSet.getInt("an"),
                            resultSet.getInt("pret"),
                            "Yes"
                    };
                    carTableModel.addRow(row);
                }
                database.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(MainAppFrame.this, "Error loading available cars: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RentCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = carTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(MainAppFrame.this, "Please select a car to rent.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int carId = (int) carTableModel.getValueAt(selectedRow, 0);
            boolean isAvailable = carTableModel.getValueAt(selectedRow, 6).equals("Yes");

            if (!isAvailable) {
                JOptionPane.showMessageDialog(MainAppFrame.this, "This car is not available for rent.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update availability in the database
            try {
                Database database = new Database();
                String query = "UPDATE Car SET disponibilitate = FALSE WHERE Id = ?";
                PreparedStatement statement = database.getPreparedStatement(query);
                statement.setInt(1, carId);
                statement.executeUpdate();
                database.closeConnection();
                JOptionPane.showMessageDialog(MainAppFrame.this, "Car rented successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                displayAllCars();  // Refresh the table to reflect the change
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(MainAppFrame.this, "Error renting car: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Action Listener for the Feedback button
    private class FeedbackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open feedback form in a new window
            JFrame feedbackFrame = new JFrame("Submit Feedback");
            feedbackFrame.setSize(400, 300);
            feedbackFrame.setLocationRelativeTo(null);  // Center the window

            JPanel feedbackPanel = new JPanel();
            feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));

            JLabel feedbackLabel = new JLabel("We value your feedback!");
            JTextArea feedbackTextArea = new JTextArea(5, 30);
            JButton submitButton = new JButton("Submit Feedback");

            feedbackPanel.add(feedbackLabel);
            feedbackPanel.add(new JScrollPane(feedbackTextArea));
            feedbackPanel.add(submitButton);

            submitButton.addActionListener(submitAction -> {
                String feedbackText = feedbackTextArea.getText();
                if (feedbackText.isEmpty()) {
                    JOptionPane.showMessageDialog(feedbackFrame, "Please provide your feedback.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Here you can handle the feedback, e.g., saving it in the database or sending it by email
                    JOptionPane.showMessageDialog(feedbackFrame, "Thank you for your feedback!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    feedbackTextArea.setText("");  // Clear the textarea
                    feedbackFrame.dispose();  // Close the feedback window
                }
            });

            feedbackFrame.add(feedbackPanel);
            feedbackFrame.setVisible(true);
        }
    }

    // Action Listener for the Show Rented Cars button
    private class ShowRentedCarsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new frame to display rented cars
            JFrame rentedCarsFrame = new JFrame("Rented Cars");
            rentedCarsFrame.setSize(800, 600);
            rentedCarsFrame.setLocationRelativeTo(null); // Center the window

            DefaultTableModel rentedCarsTableModel = new DefaultTableModel(
                    new String[] {"ID", "Brand", "Model", "Color", "Year", "Price per day", "Rented"}, 0);
            JTable rentedCarsTable = new JTable(rentedCarsTableModel);
            JScrollPane rentedCarsScrollPane = new JScrollPane(rentedCarsTable);
            rentedCarsFrame.add(rentedCarsScrollPane, BorderLayout.CENTER);

            try {
                Database database = new Database();
                String query = "SELECT * FROM Car WHERE disponibilitate = FALSE"; // Get only rented cars
                PreparedStatement statement = database.getPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Object[] row = {
                            resultSet.getInt("Id"),
                            resultSet.getString("brand"),
                            resultSet.getString("model"),
                            resultSet.getString("culoare"),
                            resultSet.getInt("an"),
                            resultSet.getInt("pret"),
                            "Yes"
                    };
                    rentedCarsTableModel.addRow(row);
                }
                database.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(rentedCarsFrame, "Error loading rented cars: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            rentedCarsFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new MainAppFrame();
    }
}
