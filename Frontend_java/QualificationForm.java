
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QualificationForm extends JFrame {
    private JTextField jobSeekerIdField, degreeField, specialisationField, organisationField, skillsField, domainField, yearField;
    private JButton addQualificationButton;
    private void addSkillsLearntToDatabase() {
        String jobSeekerId = jobSeekerIdField.getText();
        String degree = degreeField.getText();
        String specialisation = specialisationField.getText();
        String organisation = organisationField.getText();
        String skills = skillsField.getText();
        String domain = domainField.getText();
        String year = yearField.getText();

        // Database connection details (replace with your actual values)
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "notroot@123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO skills_learnt(js_id, year, proficiency, domain) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, jobSeekerId);
            statement.setString(2, year);
            statement.setString(3, skills);
            statement.setString(4, domain);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Qualification added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add qualification.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addQualificationToDatabase() {
        String jobSeekerId = jobSeekerIdField.getText();
        String degree = degreeField.getText();
        String specialisation = specialisationField.getText();
        String organisation = organisationField.getText();
        String skills = skillsField.getText();
        String domain = domainField.getText();
        String year = yearField.getText();

        // Database connection details (replace with your actual values)
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "notroot@123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Check if the entry already exists
            String query = "SELECT * FROM qualification WHERE jobseeker_id = ? AND degree = ? AND year = ? AND specialisation = ? AND organization = ?";
            try (PreparedStatement checkStatement = conn.prepareStatement(query)) {
                checkStatement.setString(1, jobSeekerId);
                checkStatement.setString(2, degree);
                checkStatement.setString(3, year);
                checkStatement.setString(4, specialisation);
                checkStatement.setString(5, organisation);
                // Execute the query
                ResultSet resultSet = checkStatement.executeQuery();

                // If there is already a matching record, don't insert
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Qualification already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // If no duplicate found, insert the new qualification
            String insertQuery = "INSERT INTO qualification(jobseeker_id, degree, year, specialisation, organization, skills, domain) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(insertQuery)) {
                statement.setString(1, jobSeekerId);
                statement.setString(2, degree);
                statement.setString(3, year);
                statement.setString(4, specialisation);
                statement.setString(5, organisation);
                statement.setString(6, skills);
                statement.setString(7, domain);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Qualification added successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add qualification.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public QualificationForm() {
        setTitle("Add Qualification");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        JLabel jobSeekerIdLabel = new JLabel("Job Seeker ID:");
        add(jobSeekerIdLabel);
        jobSeekerIdField = new JTextField();
        add(jobSeekerIdField);
        
        JLabel yearIdLabel = new JLabel("Year:");
        add(yearIdLabel);
        yearField = new JTextField();
        add(yearField);

        JLabel degreeLabel = new JLabel("Degree:");
        add(degreeLabel);
        degreeField = new JTextField();
        add(degreeField);

        JLabel specialisationLabel = new JLabel("Specialisation:");
        add(specialisationLabel);
        specialisationField = new JTextField();
        add(specialisationField);

        JLabel organisationLabel = new JLabel("Organisation:");
        add(organisationLabel);
        organisationField = new JTextField();
        add(organisationField);

        JLabel skillsLabel = new JLabel("Skills:");
        add(skillsLabel);
        skillsField = new JTextField();
        add(skillsField);

        JLabel domainLabel = new JLabel("Domain:");
        add(domainLabel);
        domainField = new JTextField();
        add(domainField);

        
        addQualificationButton = new JButton("Add Work Qualification");
        addQualificationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSkillsLearntToDatabase(); // Call the new method to handle database insertion
                addQualificationToDatabase();
            }
        });
        add(addQualificationButton);

        setVisible(true);
    }

    private void clearFields() {
        jobSeekerIdField.setText("");
        degreeField.setText("");
        specialisationField.setText("");
        organisationField.setText("");
        skillsField.setText("");
        domainField.setText("");
    }

    public static void main(String[] args) {
        new QualificationForm();
    }
}
