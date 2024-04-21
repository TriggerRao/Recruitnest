import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddJobOpeningsForm extends JFrame {
    private JLabel headingLabel, jobIdLabel, roleLabel, salaryLabel, locationLabel, typeLabel;
    private JTextField jobIdField, roleField, salaryField, locationField;
    private JComboBox<String> typeComboBox;
    private JButton addButton;
    
    private void addJobOpeningToDatabase() {
        int jobId = Integer.parseInt(jobIdField.getText());
        String role = roleField.getText();
        int salary = Integer.parseInt(salaryField.getText());
        String location = locationField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        int comId = Integer.parseInt(SessionData.Company_ID);
        

        // Database connection details (replace with your actual values)
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "notroot@123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO job(job_id, com_id, role, salary, location, type, status) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            statement.setInt(1, jobId);
            statement.setInt(2, comId);
            statement.setString(3, role);
            statement.setInt(4, salary);
            statement.setString(5, location);
            statement.setString(6, type);
            statement.setString(7, "Vacant");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Job Opening added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add job opening	.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public AddJobOpeningsForm() {
        setTitle("Add Job Openings");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading Label
        headingLabel = new JLabel("Add Job Openings");
        headingLabel.setFont(headingLabel.getFont().deriveFont(Font.BOLD, 20f));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headingLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jobIdLabel = new JLabel("Job ID:");
        formPanel.add(jobIdLabel);
        jobIdField = new JTextField();
        formPanel.add(jobIdField);

        roleLabel = new JLabel("Role:");
        formPanel.add(roleLabel);
        roleField = new JTextField();
        formPanel.add(roleField);

        salaryLabel = new JLabel("Salary:");
        formPanel.add(salaryLabel);
        salaryField = new JTextField();
        formPanel.add(salaryField);

        locationLabel = new JLabel("Location:");
        formPanel.add(locationLabel);
        locationField = new JTextField();
        formPanel.add(locationField);

        typeLabel = new JLabel("Type:");
        formPanel.add(typeLabel);
        String[] types = {"Full-time", "Part-time", "Contract"};
        typeComboBox = new JComboBox<>(types);
        formPanel.add(typeComboBox);

        add(formPanel, BorderLayout.CENTER);

        // Add Button
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addJobOpeningToDatabase(); // Call the new method to handle database insertion
            }
        });
        add(addButton, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    private void clearFields() {
        jobIdField.setText("");
        roleField.setText("");
        salaryField.setText("");
        locationField.setText("");
        typeComboBox.setSelectedIndex(0); // Reset to the first item
    }

    private void submitForm() {
        // Get form data and perform submission logic here
        String jobId = jobIdField.getText();
        String role = roleField.getText();
        String salary = salaryField.getText();
        String location = locationField.getText();
        String type = (String) typeComboBox.getSelectedItem();

        // Perform submission logic, such as storing the data or sending it to a server
        System.out.println("Submitted Form:");
        System.out.println("Job ID: " + jobId);
        System.out.println("Role: " + role);
        System.out.println("Salary: " + salary);
        System.out.println("Location: " + location);
        System.out.println("Type: " + type);

        // You can add your submission logic here
        // For example, you can store the data in a database or send it to a server
    }
}