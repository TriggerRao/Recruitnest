import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkExperienceForm extends JFrame {
    private JTextField companyIdField, roleField, salaryField, durationField, skillsField, domainField;
    private JButton addWorkExperienceButton;
    
    private void addSkillsToDatabase() {
        String companyId = companyIdField.getText();
        String role = roleField.getText();
        String salary = salaryField.getText();
        String duration = durationField.getText();
        String skills = skillsField.getText();
        String domain = domainField.getText();

        // Database connection details (replace with your actual values)
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "notroot@123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(
            		 "INSERT INTO skills_used( Com_ID, WE_Role, WE_salary, Duration, proficiency, domain) VALUES (?, ?, ?, ?, ?, ?)")) {

       
            statement.setString(1, companyId);
            statement.setString(2, role);
            statement.setString(3, salary);
            statement.setString(4, duration);
            statement.setString(5, skills);
            statement.setString(6, domain);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Skills updated successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update skills or company ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public WorkExperienceForm() {
        setTitle("Add Work Experience");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        JLabel companyIdLabel = new JLabel("Company ID:");
        add(companyIdLabel);
        companyIdField = new JTextField();
        add(companyIdField);

        JLabel roleLabel = new JLabel("Role:");
        add(roleLabel);
        roleField = new JTextField();
        add(roleField);

        JLabel salaryLabel = new JLabel("Salary:");
        add(salaryLabel);
        salaryField = new JTextField();
        add(salaryField);

        JLabel durationLabel = new JLabel("Duration:");
        add(durationLabel);
        durationField = new JTextField();
        add(durationField);

        JLabel skillsLabel = new JLabel("Skills:");
        add(skillsLabel);
        skillsField = new JTextField();
        add(skillsField);

        JLabel domainLabel = new JLabel("Domain:");
        add(domainLabel);
        domainField = new JTextField();
        add(domainField);

        addWorkExperienceButton = new JButton("Add Work Experience");
        addWorkExperienceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSkillsToDatabase(); // Call the method to update skills in the database
            }
        });
        add(addWorkExperienceButton);

        setVisible(true);
    }

    private void clearFields() {
        companyIdField.setText("");
        roleField.setText("");
        salaryField.setText("");
        durationField.setText("");
        skillsField.setText("");
        domainField.setText("");
    }

    public static void main(String[] args) {
        new WorkExperienceForm();
    }
}
