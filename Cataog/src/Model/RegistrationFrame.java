package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationFrame extends JFrame {
    private JTextField usernameField; // Email
    private JTextField firstNameField; // Nume
    private JTextField lastNameField; // Prenume
    private JTextField phoneNumberField; // numar
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;

    public RegistrationFrame() {
        this.setTitle("Car Rental - Register");
        this.setSize(400, 400); // Increased height for better spacing
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Register New User", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        this.add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Changed to 0 rows for dynamic height
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel firstNameLabel = new JLabel("First Name:");
        this.firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        this.lastNameField = new JTextField();
        JLabel usernameLabel = new JLabel("Email:");
        this.usernameField = new JTextField();
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        this.phoneNumberField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        this.passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        this.confirmPasswordField = new JPasswordField();

        // Add labels and fields to the form panel
        formPanel.add(firstNameLabel);
        formPanel.add(this.firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(this.lastNameField);
        formPanel.add(usernameLabel);
        formPanel.add(this.usernameField);
        formPanel.add(phoneNumberLabel);
        formPanel.add(this.phoneNumberField);
        formPanel.add(passwordLabel);
        formPanel.add(this.passwordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(this.confirmPasswordField);

        this.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.registerButton = new JButton("Register");
        this.registerButton.setBackground(new Color(60, 179, 113));
        this.registerButton.setForeground(Color.WHITE);
        this.registerButton.setFocusPainted(false);
        this.registerButton.addActionListener(new RegisterButtonListener(this));
        buttonPanel.add(this.registerButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Visibility
        this.setVisible(true);
    }

    public static class RegisterButtonListener implements ActionListener {
        private RegistrationFrame registrationFrame;

        public RegisterButtonListener(RegistrationFrame registrationFrame) {
            this.registrationFrame = registrationFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String firstName = registrationFrame.firstNameField.getText();
            String lastName = registrationFrame.lastNameField.getText();
            String email = registrationFrame.usernameField.getText();
            String phoneNumber = registrationFrame.phoneNumberField.getText();
            String password = new String(registrationFrame.passwordField.getPassword());
            String confirmPassword = new String(registrationFrame.confirmPasswordField.getPassword());

            if (password.equals(confirmPassword)) {
                try {
                    Database database = new Database();
                    // Check if email already exists
                    String query = "SELECT * FROM User WHERE email = ?";
                    PreparedStatement statement = database.getPreparedStatement(query);
                    statement.setString(1, email);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(registrationFrame, "Email is already registered.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Insert new user
                        query = "INSERT INTO User (Nume, Prenume, email, numar, parola) VALUES (?, ?, ?, ?, ?)";
                        statement = database.getPreparedStatement(query);
                        statement.setString(1, firstName);
                        statement.setString(2, lastName);
                        statement.setString(3, email);
                        statement.setInt(4, Integer.parseInt(phoneNumber)); // Assuming phone number is an integer
                        statement.setString(5, password);
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(registrationFrame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        registrationFrame.dispose();
                    }

                    database.closeConnection();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(registrationFrame, "Error registering user!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(registrationFrame, "Phone number must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(registrationFrame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
