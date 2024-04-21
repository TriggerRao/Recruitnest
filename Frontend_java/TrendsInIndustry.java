import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TrendsInIndustry extends JFrame {
    public TrendsInIndustry() {
        setTitle("Trends in Industry");
        setSize(1000, 600); // Increased size of the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create line chart
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createLineChart(
                "Average Salary Over Years", // Chart title
                "Year",                      // X-axis label
                "Average Salary",           // Y-axis label
                dataset                     // Dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400)); // Increased size of the ChartPanel

        // Add line chart to the panel
        JPanel chartPanelContainer = new JPanel();
        chartPanelContainer.add(chartPanel);
        add(chartPanelContainer, BorderLayout.NORTH);

        // List of skills for maximum wage growth
        List<String> skills = getRecommendedSkills(1); // Replace 1 with actual candidate ID

        JList<String> skillsList = new JList<>(skills);
        skillsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane skillsScrollPane = new JScrollPane(skillsList);
        skillsScrollPane.setPreferredSize(new Dimension(200, 100));

        // Add skills list to the panel
        JPanel skillsPanel = new JPanel();
        skillsPanel.add(new JLabel("Skills for Maximum Wage Growth:"));
        skillsPanel.add(skillsScrollPane);
        add(skillsPanel, BorderLayout.CENTER);

        // Suggested salary
        double expectedEarnings = calculateExpectedEarnings();
        JLabel suggestedSalaryLabel = new JLabel("Suggested Salary: $" + String.format("%.2f", expectedEarnings));
        JPanel suggestedSalaryPanel = new JPanel();
        suggestedSalaryPanel.add(suggestedSalaryLabel);
        add(suggestedSalaryPanel, BorderLayout.SOUTH);


        setVisible(true);
    }
    
    private List getRecommendedSkills(int candidateId) {
        List<Strings> skills = new ArrayList<>();
        String query = 
                "WITH CandidateSkills AS ( " +
                "    SELECT js_id, proficiency, domain " +
                "    FROM skills_learnt " +
                "    WHERE js_id = ? " +
                "    UNION " +
                "    SELECT js_id, proficiency, domain " +
                "    FROM skills_used " +
                "    WHERE js_id = ? " +
                "), " +
                "QualifiedJobs AS ( " +
                "    SELECT job_id, COUNT(*) AS missing_skills_count " +
                "    FROM skills_required sr " +
                "    WHERE NOT EXISTS ( " +
                "        SELECT 1 " +
                "        FROM CandidateSkills cs " +
                "        WHERE cs.proficiency = sr.proficiency " +
                "        AND cs.domain = sr.domain " +
                "    ) " +
                "    GROUP BY job_id " +
                "    HAVING missing_skills_count = 1 " +
                "), " +
                "TopJobs AS ( " +
                "    SELECT qj.job_id, j.salary " +
                "    FROM QualifiedJobs qj " +
                "    JOIN job j ON qj.job_id = j.job_id " +
                "    ORDER BY j.salary DESC " +
                "    LIMIT 5 " +
                "), " +
                "TopJobSkills AS ( " +
                "    SELECT ts.job_id, sr.proficiency, sr.domain " +
                "    FROM TopJobs ts " +
                "    JOIN skills_required sr ON ts.job_id = sr.job_id " +
                "    WHERE NOT EXISTS ( " +
                "        SELECT 1 " +
                "        FROM CandidateSkills cs " +
                "        WHERE cs.proficiency = sr.proficiency " +
                "        AND cs.domain = sr.domain " +
                "    ) " +
                ") " +
                "SELECT DISTINCT proficiency, domain " +
                "FROM TopJobSkills";
        
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "notroot@123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, candidateId);
            stmt.setInt(2, candidateId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String skill = rs.getString("proficiency") + " in " + rs.getString("domain");
                skills.add(skill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return skills;
    }
    
    private double calculateExpectedEarnings() {
        String query = "SELECT (AVG(salary) + MAX(salary))/2 AS expected_earnings, COUNT(*) AS count " +
                "FROM job " +
                "WHERE NOT EXISTS ( " +
                "    SELECT proficiency, domain " +
                "    FROM skills_required " +
                "    WHERE skills_required.job_id = job.job_id " +
                "    AND (proficiency, domain) NOT IN ( " +
                "        SELECT domain, proficiency " +
                "        FROM skills_used " +
                "        WHERE js_id = 2 " + // Replace '2' with the actual candidate ID
                "        UNION " +
                "        SELECT proficiency, domain " +
                "        FROM skills_learnt " +
                "        WHERE js_id = 2 " +  // Replace '2' with the actual candidate ID
                "    ) " +
                ") AND job.jstatus = 'Vacant'";
        
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "notroot@123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("expected_earnings");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }



    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Add dummy data for the line chart
        dataset.addValue(60000, "Average Salary", "2010");
        dataset.addValue(62000, "Average Salary", "2011");
        dataset.addValue(64000, "Average Salary", "2012");
        dataset.addValue(66000, "Average Salary", "2013");
        dataset.addValue(68000, "Average Salary", "2014");
        dataset.addValue(70000, "Average Salary", "2015");
        dataset.addValue(72000, "Average Salary", "2016");
        dataset.addValue(74000, "Average Salary", "2017");
        dataset.addValue(76000, "Average Salary", "2018");
        dataset.addValue(78000, "Average Salary", "2019");
        dataset.addValue(80000, "Average Salary", "2020");
        dataset.addValue(82000, "Average Salary", "2021");
        dataset.addValue(84000, "Average Salary", "2022");
        dataset.addValue(86000, "Average Salary", "2023");
        dataset.addValue(88000, "Average Salary", "2024");
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrendsInIndustry::new);
    }
}