// RecruitNestHomePage.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RecruitNestHomePage extends JFrame {
    private JLabel headingLabel;
    private JLabel loginAsLabel;
    private JButton companyButton, jobSeekerButton;
    private JButton oldJSButton, newJSButton, oldCompanyButton, newCompanyButton;

    public RecruitNestHomePage() {
        setTitle("Welcome to RecruitNest");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        

        headingLabel = new JLabel("Welcome to RecruitNest");
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setVerticalAlignment(JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Larger font size
        add(headingLabel);

        loginAsLabel = new JLabel("Login as");
        loginAsLabel.setHorizontalAlignment(JLabel.CENTER);
        loginAsLabel.setFont(new Font("Arial", Font.PLAIN, 15)); // Smaller font size
        add(loginAsLabel);

        companyButton = new JButton("Company");
        companyButton.setBounds(200, 200, 200, 100);
        companyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	add(oldCompanyButton);
                oldCompanyButton.setVisible(true);
                oldJSButton.setVisible(false);
                
                oldCompanyButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent ee) {
                		LoginFormCompany loginForm = new LoginFormCompany();
                		loginForm.setVisible(true);
                	}
                });
                add(newCompanyButton);
                newCompanyButton.setVisible(true);
                newJSButton.setVisible(false);
                
                newCompanyButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent ee) {
                		CompanyRegistrationForm comForm = new CompanyRegistrationForm();
                		comForm.setVisible(true);
                	}
                });
            }
        });
        add(companyButton);

        jobSeekerButton = new JButton("Job Seeker");
        jobSeekerButton.setBounds(200, 200, 200, 100);
        jobSeekerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	add(oldJSButton);
                oldJSButton.setVisible(true);
                oldCompanyButton.setVisible(false);
                
                oldJSButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent ee) {
                		LoginFormJobSeeker loginForm = new LoginFormJobSeeker();
                		loginForm.setVisible(true);
                	}
                });
                add(newJSButton);
                newJSButton.setVisible(true);
                newCompanyButton.setVisible(false);
                
                newJSButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent ee) {
                		JobSeekerRegistrationForm jsForm = new JobSeekerRegistrationForm();
                		jsForm.setVisible(true);
                	}
                });
            }
        });
        add(jobSeekerButton);

        oldJSButton = new JButton("Sign in as Job Seeker");
        oldJSButton.setVisible(false);

        newJSButton = new JButton("Sign up as Job Seeker");
        newJSButton.setVisible(false);
        
        oldCompanyButton = new JButton("Sign in as Company");
        oldCompanyButton.setVisible(false);

        newCompanyButton = new JButton("Sign up as Company");
        newCompanyButton.setVisible(false);

        setVisible(true);
    }
}