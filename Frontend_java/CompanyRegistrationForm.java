// CompanyRegistrationForm.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; 

public class CompanyRegistrationForm extends JFrame {
    private JTextField companyIdField, passwordField, companyNameField, sectorField, websiteField;
    private JButton registerButton, proceedButton;
    
    private void registerCompanyInDatabase() {
        String companyId = companyIdField.getText();
        String password = new String(((JPasswordField) passwordField).getPassword()); // Get password as String
        String companyName = companyNameField.getText();
        String sector = sectorField.getText();
        String website = websiteField.getText();

        // Database connection details (replace with your actual values)
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password1 = "notroot@123";

        try (Connection conn = DriverManager.getConnection(url, user, password1);
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO Company (com_id, com_name, sector, official_website) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, companyId);
            statement.setString(2, companyName);
            statement.setString(3, sector);
            statement.setString(4, website);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Company registered successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register company.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public CompanyRegistrationForm() {
        setTitle("Company Registration Form");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        JLabel companyIdLabel = new JLabel("Company ID:");
        add(companyIdLabel);
        companyIdField = new JTextField();
        add(companyIdField);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        JLabel companyNameLabel = new JLabel("Company Name:");
        add(companyNameLabel);
        companyNameField = new JTextField();
        add(companyNameField);

        JLabel sectorLabel = new JLabel("Sector:");
        add(sectorLabel);
        sectorField = new JTextField();
        add(sectorField);

        JLabel websiteLabel = new JLabel("Official Website:");
        add(websiteLabel);
        websiteField = new JTextField();
        add(websiteField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerCompanyInDatabase(); // Call the new method to handle database insertion
            }
        });
        add(registerButton);
        
        proceedButton = new JButton("Proceed");
        proceedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	LoginFormCompany loginForm = new LoginFormCompany();
        		loginForm.setVisible(true); // Call the new method to handle database insertion
            }
        });
        add(proceedButton);

        setVisible(true);
    }

    private void clearFields() {
        companyIdField.setText("");
        passwordField.setText("");
        companyNameField.setText("");
        sectorField.setText("");
        websiteField.setText("");
    }
}