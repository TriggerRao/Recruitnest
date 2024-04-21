import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDashboard extends JFrame {
    private JLabel profileHeadingLabel, idLabel, nameLabel, websiteLabel;
    private JButton addJobOpeningsButton, applyfiltersButton;
    private JLabel suggestedCandidatesHeadingLabel;
    private JPanel profilePanel, suggestedCandidatesPanel, filterPanel, filterOptionsPanel;
    private JTable candidatesTable;
    private JScrollPane tableScrollPane;
    private JComboBox<String> roleFilterComboBox;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JTextField degreeField, ageLowerBoundField;

    public CompanyDashboard() {
        setTitle("Company Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Profile Panel
        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

        profileHeadingLabel = new JLabel("Profile");
        profileHeadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileHeadingLabel.setFont(profileHeadingLabel.getFont().deriveFont(Font.BOLD, 24f)); // Increase font size
        profileHeadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Increase line spacing
        profilePanel.add(profileHeadingLabel);

        idLabel = new JLabel("ID: CP12345");
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        idLabel.setFont(idLabel.getFont().deriveFont(Font.PLAIN, 16f)); // Increase font size
        idLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Increase line spacing
        profilePanel.add(idLabel);

        nameLabel = new JLabel("Name: ABC Company");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.PLAIN, 16f)); // Increase font size
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Increase line spacing
        profilePanel.add(nameLabel);

        websiteLabel = new JLabel("Website: Not hiring");
        websiteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        websiteLabel.setFont(websiteLabel.getFont().deriveFont(Font.PLAIN, 16f)); // Increase font size
        websiteLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Increase line spacing
        profilePanel.add(websiteLabel);
        
     // Load company data from database
        
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password1 = "notroot@123";
        
        try (Connection conn = DriverManager.getConnection(url, user, password1);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM company WHERE com_id = ?")) {

            stmt.setString(1, SessionData.Company_ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idLabel.setText("ID: " + rs.getString("com_id"));
                nameLabel.setText("Name: " + rs.getString("com_name"));
                websiteLabel.setText("Status: " + rs.getString("official_website"));
            } else {
                // Handle case where company ID is not found
                JOptionPane.showMessageDialog(this, "Company not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        addJobOpeningsButton = new JButton("Add Job Openings");
        addJobOpeningsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addJobOpeningsButton.setFont(addJobOpeningsButton.getFont().deriveFont(Font.PLAIN, 16f)); // Increase font size
        addJobOpeningsButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // Increase line spacing
        profilePanel.add(addJobOpeningsButton);
        
     // Apply Filters Button
        applyfiltersButton = new JButton("Apply Filters for Candidate Search");
        applyfiltersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyfiltersButton.setFont(applyfiltersButton.getFont().deriveFont(Font.PLAIN, 16f));
        applyfiltersButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        profilePanel.add(applyfiltersButton);

        // Filter Options Panel (initially hidden)
        filterOptionsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        filterOptionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        filterOptionsPanel.setVisible(false);

        // Gender Filter
        JLabel genderLabel = new JLabel("Gender:");
        filterOptionsPanel.add(genderLabel);
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        filterOptionsPanel.add(genderPanel);

        // Degree Filter
        JLabel degreeLabel = new JLabel("Degree:");
        filterOptionsPanel.add(degreeLabel);
        JTextField degreeField = new JTextField();
        filterOptionsPanel.add(degreeField);

        // Age Filter
        JLabel ageLabel = new JLabel("Age (Lower Bound):");
        filterOptionsPanel.add(ageLabel);
        JTextField ageLowerBoundField = new JTextField();
        filterOptionsPanel.add(ageLowerBoundField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            filterCandidatesByGender(); 
            filterCandidatesByDegree();
            filterCandidatesByAge();
        });

        filterOptionsPanel.add(searchButton);

        profilePanel.add(filterOptionsPanel);

        add(profilePanel, BorderLayout.WEST);
        
        JButton searchCandidatesButton = new JButton("Search Candidates");
        searchCandidatesButton.addActionListener(e -> {
            String jobIdStr = JOptionPane.showInputDialog(this, "Enter Job ID:");
            if (jobIdStr != null && !jobIdStr.isEmpty()) {
                try {
                    int jobId = Integer.parseInt(jobIdStr);
                    loadCandidatesByJobId(jobId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Job ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        profilePanel.add(searchCandidatesButton);

        // Suggested Candidates Panel
        suggestedCandidatesPanel = new JPanel(new BorderLayout());

        suggestedCandidatesHeadingLabel = new JLabel("Suggested Candidates");
        suggestedCandidatesHeadingLabel.setFont(suggestedCandidatesHeadingLabel.getFont().deriveFont(Font.BOLD, 20f)); // Increase font size
        suggestedCandidatesHeadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Increase line spacing
        suggestedCandidatesPanel.add(suggestedCandidatesHeadingLabel, BorderLayout.BEFORE_FIRST_LINE);
        
        

     // Create table model (initially empty)
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Job ID");
        model.addColumn("Name");
        candidatesTable = new JTable(model);
        tableScrollPane = new JScrollPane(candidatesTable);
        suggestedCandidatesPanel.add(tableScrollPane, BorderLayout.CENTER);
//
//        // Create role filter combo box
//        roleFilterComboBox = new JComboBox<>();
//        roleFilterComboBox.addItem("All Roles");
//        roleFilterComboBox.addItem("Web Developer");
//        roleFilterComboBox.addItem("App Developer");
//
//        // Add filter action listener
//        roleFilterComboBox.addActionListener(e -> filterCandidatesByRole());
//
//        // Create filter panel
//        filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        filterPanel.add(roleFilterComboBox);
//
//        // Add filter panel to the top of suggested candidates panel
//        suggestedCandidatesPanel.add(filterPanel, BorderLayout.EAST);

        add(suggestedCandidatesPanel, BorderLayout.CENTER);
  
        addJobOpeningsButton.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddJobOpeningsForm();
            }
        });
        
     // ActionListener for Apply Filters Button
        applyfiltersButton.addActionListener(e -> {
            filterOptionsPanel.setVisible(!filterOptionsPanel.isVisible()); // Toggle visibility
        });

        setVisible(true);


        setVisible(true);
    }

    private void filterCandidatesByRole() {
        String selectedRole = (String) roleFilterComboBox.getSelectedItem();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) candidatesTable.getModel());
        candidatesTable.setRowSorter(sorter);
        if (!selectedRole.equals("All Roles")) {
            sorter.setRowFilter(RowFilter.regexFilter(selectedRole, 1)); // 1 is the index of Role column
        } else {
            sorter.setRowFilter(null);
        }
    }
    
    private void filterCandidatesByGender() {
        String gender = null;
        if (maleRadio.isSelected()) {
            gender = "Male";
        } else if (femaleRadio.isSelected()) {
            gender = "Female";
        }

        if (gender != null) {
            String query = "SELECT * FROM job_seeker WHERE gender = ?";
            executeQueryAndUpdateTable(query, gender);
        }
    }

    private void filterCandidatesByDegree() {
        String degree = degreeField.getText().trim();

        if (!degree.isEmpty()) {
            String query = "SELECT job_seeker.* FROM job_seeker JOIN qualification ON job_seeker.js_id = qualification.js_id WHERE qualification.degree = ? AND job_seeker.status = 'Searching for job'";
            executeQueryAndUpdateTable(query, degree);
        }
    }

    private void filterCandidatesByAge() {
        String ageLowerBoundStr = ageLowerBoundField.getText().trim();
        int ageLowerBound = 0;
        if (!ageLowerBoundStr.isEmpty()) {
            try {
                ageLowerBound = Integer.parseInt(ageLowerBoundStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid age lower bound.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (ageLowerBound > 0) {
            String query = "SELECT * FROM job_seeker WHERE Age >= ?";
            executeQueryAndUpdateTable(query, ageLowerBound);
        }
    }

    private void executeQueryAndUpdateTable(String query, Object... params) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "notroot@123");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 1. Set Parameters:
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]); 
            }

            // 2. Execute Query:
            ResultSet rs = stmt.executeQuery();

            // 3. Clear Existing Data and Update Table:
            DefaultTableModel model = (DefaultTableModel) candidatesTable.getModel();
            model.setRowCount(0); 
            while (rs.next()) {
                // a. Get Column Values:
                int id = rs.getInt("js_id");
                String name = rs.getString("name");
//                String role = rs.getString("role");
                // ... (Get other column values based on your table schema)

                // b. Create Row and Add to Model:
                model.addRow(new Object[]{id, name,}); // Add more values as needed
            }

            // 4. Refresh Table Display:
            candidatesTable.revalidate();
            candidatesTable.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadCandidatesByJobId(int jobId) {
        String query = "SELECT * " +
                "FROM job_seeker " +
                "LEFT JOIN ( " +
                "    SELECT js_id, SUM(duration) AS total_experience " +
                "    FROM work_experience " +
                "    GROUP BY js_id " +
                ") AS total_exp ON job_seeker.js_id = total_exp.js_id " +
                "WHERE NOT EXISTS ( " +
                "    SELECT proficiency, domain " +
                "    FROM skills_required " +
                "    WHERE skills_required.job_id = ? " +
                "    AND (proficiency, domain) NOT IN ( " +
                "        SELECT domain, proficiency " +
                "        FROM skills_used " +
                "        WHERE js_id = job_seeker.js_id " +
                "        UNION " +
                "        SELECT proficiency, domain " +
                "        FROM skills_learnt " +
                "        WHERE js_id = job_seeker.js_id " +
                "    ) " +
                ") AND job_seeker.status = 'Searching' " +
                "ORDER BY total_exp.total_experience DESC " +
                "LIMIT 10";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "notroot@123");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, jobId);
            ResultSet rs = stmt.executeQuery();

            // Clear existing data and update table
            DefaultTableModel model = (DefaultTableModel) candidatesTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                int id = rs.getInt("js_id");
                String name = rs.getString("name");
                model.addRow(new Object[]{id, name});
            }
            candidatesTable.revalidate();
            candidatesTable.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public static void main(String[] args) {
        new CompanyDashboard();
    }
}