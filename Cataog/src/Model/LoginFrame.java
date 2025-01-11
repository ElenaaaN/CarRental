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

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JButton registerButton;

    public LoginFrame() {
        this.setTitle("Car Rental - Login");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Welcome to Car Rental", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        this.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Email:");
        this.usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        this.passwordField = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(this.usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(this.passwordField);

        this.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.loginButton = new JButton("Login");
        this.loginButton.setBackground(new Color(60, 179, 113));
        this.loginButton.setForeground(Color.WHITE);
        this.loginButton.setFocusPainted(false);

        this.cancelButton = new JButton("Cancel");
        this.cancelButton.setBackground(new Color(220, 20, 60));
        this.cancelButton.setForeground(Color.WHITE);
        this.cancelButton.setFocusPainted(false);

        this.registerButton = new JButton("Register");
        this.registerButton.setBackground(new Color(100, 149, 237));
        this.registerButton.setForeground(Color.WHITE);
        this.registerButton.setFocusPainted(false);

        buttonPanel.add(this.loginButton);
        buttonPanel.add(this.registerButton);
        buttonPanel.add(this.cancelButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.loginButton.addActionListener(new LoginButtonListener(this));
        this.registerButton.addActionListener(e -> new RegistrationFrame());
        this.cancelButton.addActionListener(e -> System.exit(0));

        this.setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        private LoginFrame loginFrame;

        public LoginButtonListener(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginFrame.usernameField.getText();
            String password = new String(loginFrame.passwordField.getPassword());

            try {
                Database database = new Database();
                String query = "SELECT * FROM User WHERE email = ?";
                PreparedStatement statement = database.getPreparedStatement(query);
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("parola");
                    if (storedPassword.equals(password)) {
                        JOptionPane.showMessageDialog(loginFrame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loginFrame.dispose();
                        new MainAppFrame();  // Assuming you have a main application frame to go to after login
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Email not registered. Please register first.", "Error", JOptionPane.ERROR_MESSAGE);
                    new RegistrationFrame();  // Open registration frame if email doesn't exist
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(loginFrame, "Error connecting to the database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
