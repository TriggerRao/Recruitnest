import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JobSeekerRegistrationForm extends JFrame {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JTextField ageField;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JComboBox statusCombo;
    private JButton addQualificationButton, addWorkExperienceButton;
    private JButton registerButton, proceedButton;

    public JobSeekerRegistrationForm() {
    	
//    	try {
//    		Class.forName("com.mysql.cj.jdbc.Driver");
//    	}
//    	catch(Exception e) {
//    		System.out.println(e);
//    	}
//    	
//    	try {
//			Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","notroot@123");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
        setTitle("Job Seeker Registration Form");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Name:");
        add(nameLabel);
        nameField = new JTextField();
        add(nameField);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        JLabel ageLabel = new JLabel("Age:");
        add(ageLabel);
        ageField = new JTextField();
        add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        add(genderLabel);
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        add(genderPanel);

        JLabel statusLabel = new JLabel("Status:");
        add(statusLabel);
        String[] statusOptions = {"Employed", "Searching for job"};
        statusCombo = new JComboBox(statusOptions);
        add(statusCombo);

        addQualificationButton = new JButton("Add Qualification");
        addQualificationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                QualificationForm qualForm = new QualificationForm();
                qualForm.setVisible(true);
            }
        });
        add(addQualificationButton);

        addWorkExperienceButton = new JButton("Add Work Experience");
        addWorkExperienceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WorkExperienceForm workExpForm = new WorkExperienceForm();
                workExpForm.setVisible(true);
            }
        });
        add(addWorkExperienceButton);

        setVisible(true);
        
        registerButton = new JButton("Register"); 
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 1. Retrieve data from form fields
                String name = nameField.getText();
//                String password = new String(passwordField.getPassword());
                int age = Integer.parseInt(ageField.getText());
                String gender = maleRadio.isSelected() ? "Male" : "Female";
                String status = (String) statusCombo.getSelectedItem();

                // 2. Establish database connection
                try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/project", "root", "notroot@123");
                     PreparedStatement stmt = conn.prepareStatement(
                             "INSERT INTO Job_Seeker (JS_ID, name, age, gender, status) VALUES (?, ?, ?, ?, ?)")) {

                    // 3. Set values for the prepared statement
                	stmt.setInt(1, 13);
                    stmt.setString(2, name);
//                    stmt.setString(2, password);
                    stmt.setInt(3, age);
                    stmt.setString(4, gender);
                    stmt.setString(5, status);

                    // 4. Execute the insert statement
                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(JobSeekerRegistrationForm.this,
                                "Registration successful!");
                        // You might want to clear the form fields here
                    } else {
                        JOptionPane.showMessageDialog(JobSeekerRegistrationForm.this,
                                "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(JobSeekerRegistrationForm.this,
                            "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(registerButton);
        
        proceedButton = new JButton("Proceed");
        proceedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	LoginFormJobSeeker loginForm = new LoginFormJobSeeker();
        		loginForm.setVisible(true); // Call the new method to handle database insertion
            }
        });
        add(proceedButton);
        
        setVisible(true);

    }
}
