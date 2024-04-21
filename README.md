# DBMS Project Readme
# Recruitnest - A Hiring and Recruitment Platform

## Overview
    Recruitnest will facilitate the job search and hiring process with its data-driven platform tailored for both job seekers and employers. Job seekers will find opportunities aligned with their skills through empirical search and personalized recommendations, complemented by salary suggestions and skill enhancement advice for career acceleration. Recruitnest will help users with extensive data and feedback for confident job market navigation. Users will be able to explore trends, gain insights, and make informed decisions. For recruiters, the platform will facilitate efficient candidate identification using advanced filters and skills matching, further streamlined by an analytics-driven ranking system. 

## Features

### Job-Seeker View
- ** Home Page:** Option to choose to create a new account for first time Job Seekers and facility to sign in for registered users using saved password and username.
- ** Registration Page:** Registration Page to create a new account new account 
- ** Work Experience Page** Gives opportunity to users(old and new both) to add their work experiences to the database
- ** Qualification Page** Gives opportunity to users(old and new both) to add their qualifications to the database
- ** Login Page:** Sign in page for registered users using username and password
- ** Job Seeker Dashboard** The go to page for job seekers. Suggests Jobs to Job Seekers based on their profile. Job Seekers can also search for jobs with desired filters. The page also has buttons to redirect to ratings and trends page.
- ** Ratings Page:** Gives Job Seeker option to rate their jobs
- ** Trends Page:** The page contains various analytics that are the prime feature of the platform. Graphs illustrate Role vs Year Vs No of Job Opening, Role vs Year Vs Avg. Salary, Company vs Year Vs No of Job Opening, Company vs Year Vs Avg. Salary. A list illustrates the skills that the job seeker can learn for max skill growth. Similarly another field informs the job seeker what should be the ideal salary he/she should earn at his skills


### Company View
- ** Home Page:** Option to choose to create a new account for a company/enterprise to register first time and facility to sign in if already registered using saved password and username.
- ** Registration Page:** Registration Page to create a new account new account 
- ** Login Page:** Sign in page for registered users using username and password
- ** Company Dashboard:** Provides functionality to view distinct available jobs listed by the company along with function of adding a new job opening along with the necessary skills required in the same.
- ** Job Opening:** Feature to filter listed job seekers according to suitable attributes such as gender, age or degree of the candidate. List of suitable candidates with the mentioned constrained are displyed in an ordered manner. Candidates with the skillset demanded by the job opening are listed under these limits.


## Dependencies
- Java
- Java-AWT Library
- Java.sql
- Org.jfree
- JDBC
- CSV Library
- MySQL database

## Database Initialization
1. Create a new MySql database.
2. Import initial database schema using `tables1.sql` and queries using `query10.sql` files.
3. Import dummy data using 'data_in.sql' file.
