import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JobSeekerDashboard extends JFrame {
    private JLabel profileHeadingLabel, idLabel, nameLabel, statusLabel;
    private JButton rateButton, applyfiltersButton, searchButton;
    private JLabel suggestedJobsHeadingLabel;
    private JPanel profilePanel, suggestedJobsPanel, filterPanel, filterOptionsPanel;
    private JTable companiesTable;
    private JScrollPane tableScrollPane;
    private JComboBox<String> roleFilterComboBox;

    public JobSeekerDashboard() {
        setTitle("Job Seeker Dashboard");
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

        nameLabel = new JLabel("Name: David Warner");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.PLAIN, 16f)); // Increase font size
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Increase line spacing
        profilePanel.add(nameLabel);

        statusLabel = new JLabel("Status: Searching for job");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.PLAIN, 16f)); // Increase font size
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Increase line spacing
        profilePanel.add(statusLabel);
        
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password1 = "notroot@123";
        
        try (Connection conn = DriverManager.getConnection(url, user, password1);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM job_seeker WHERE js_id = ?")) {

            stmt.setString(1, SessionData.JobSeeker_ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idLabel.setText("ID: " + rs.getString("js_id"));
                nameLabel.setText("Name: " + rs.getString("name"));
                statusLabel.setText("Status: " + rs.getString("status"));
            } else {
                // Handle case where company ID is not found
                JOptionPane.showMessageDialog(this, "Job Seeker not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        rateButton = new JButton("Rate Company");
        rateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rateButton.setFont(rateButton.getFont().deriveFont(Font.PLAIN, 16f)); // Increase font size
        rateButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // Increase line spacing
        profilePanel.add(rateButton);
        
        JButton trendsButton = new JButton ("Trends and Analytics");
        trendsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle login action here
                
                TrendsInIndustry tinindustry = new TrendsInIndustry();
        		tinindustry.setVisible(true);
                // For demonstration, just show the entered credentials
            }
        });
        profilePanel.add(trendsButton);
        
     // Apply Filters Button
        applyfiltersButton = new JButton("Apply Filters for Job Search");
        applyfiltersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyfiltersButton.setFont(applyfiltersButton.getFont().deriveFont(Font.PLAIN, 16f));
        applyfiltersButton.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        profilePanel.add(applyfiltersButton);

        // Filter Options Panel (initially hidden)
        filterOptionsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        filterOptionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        filterOptionsPanel.setVisible(false);

        // Degree Filter
        JLabel locationLabel = new JLabel("Location:");
        filterOptionsPanel.add(locationLabel);
        JTextField locationField = new JTextField();
        filterOptionsPanel.add(locationField);

        // Age Filter
        JLabel salaryLabel = new JLabel("Salary (Lower Bound):");
        filterOptionsPanel.add(salaryLabel);
        JTextField salaryLowerBoundField = new JTextField();
        filterOptionsPanel.add(salaryLowerBoundField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> filterCandidates());
        filterOptionsPanel.add(searchButton);
        
        

        profilePanel.add(filterOptionsPanel);

        add(profilePanel, BorderLayout.WEST);

        
        suggestedJobsPanel = new JPanel(new BorderLayout());

        suggestedJobsHeadingLabel = new JLabel("Suggested Jobs");
        suggestedJobsHeadingLabel.setFont(suggestedJobsHeadingLabel.getFont().deriveFont(Font.BOLD, 20f)); // Increase font size
        suggestedJobsHeadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Increase line spacing
        suggestedJobsPanel.add(suggestedJobsHeadingLabel, BorderLayout.BEFORE_FIRST_LINE);

        // Create table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Company ID");
        model.addColumn("Company Name");
        model.addColumn("Role");

        // Populate table with dummy data
//        List<Integer> ids = List.of(1000, 1001, 1002, 1003, 1004, 1005);
//        List<String> names = List.of("Google", "Apple", "Samsung", "TRC", "Facebook", "Amazon");
//        List<String> roles = List.of("Web Developer", "Web Developer", "App Developer", "App Developer", "App Developer", "Web Developer");
//        for (int i = 0; i < names.size(); i++) {
//            model.addRow(new Object[]{ids.get(i), names.get(i), roles.get(i)});
//        }

        // Create table with model
        companiesTable = new JTable(model);

        // Add table to scroll pane
        tableScrollPane = new JScrollPane(companiesTable);
        suggestedJobsPanel.add(tableScrollPane, BorderLayout.CENTER);

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
//        suggestedJobsPanel.add(filterPanel, BorderLayout.EAST);

        add(suggestedJobsPanel, BorderLayout.CENTER);
        
        applyfiltersButton.addActionListener(e -> {
            filterOptionsPanel.setVisible(!filterOptionsPanel.isVisible()); // Toggle visibility
        });

        setVisible(true);
    }

    private void filterCandidatesByRole() {
        String selectedRole = (String) roleFilterComboBox.getSelectedItem();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) companiesTable.getModel());
        companiesTable.setRowSorter(sorter);
        if (!selectedRole.equals("All Roles")) {
            sorter.setRowFilter(RowFilter.regexFilter(selectedRole, 2)); // 1 is the index of Role column
        } else {
            sorter.setRowFilter(null);
        }
    }
    
    private void filterCandidates() {
        
    }

    public static void main(String[] args) {
        new JobSeekerDashboard();
    }
}