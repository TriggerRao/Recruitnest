-- creating job seekers
insert into Job_Seeker
values (1, 'person1', 15, 'Male','Searching');
insert into Job_Seeker
values (2, 'person2', 25, 'Female','Searching');
insert into Job_Seeker
values (3, 'person3', 35, 'Female','Searching');
insert into Job_Seeker
values (4, 'person4', 45, 'Male','Searching');
insert into Job_Seeker
values (5, 'person5', 55, 'Female','Searching');
insert into Job_Seeker
values (6, 'person6', 65, 'Male','Searching');



-- creating companies
insert into company
values (11, "apple", "IT", 50, "apple.com");
insert into company
values (12, "apple2", "IT", 50, "apple2.com");
insert into company
values (13, "apple3", "IT", 50, "apple3.com");
insert into company
values (14, "apple4", "IT", 50, "apple4.com");
insert into company
values (15, "apple5", "IT", 50, "apple5.com");


-- for seeker 1:
insert into Qualification
values (1, "bits", "phd", "cs", 2026);
insert into Qualification
values (1, "bits", "masters", "ai", 2028);
insert into Skills_learnt
values(1, 2026, "Appdev", "IT");
insert into Skills_learnt
values(1, 2026, "Webdev", "IT");
insert into Skills_learnt
values(1, 2028, "SoftwareDev", "IT");

insert into Work_Experience
values (1, 11, "dev", 20000, 10, 2000);
insert into Work_Experience
values (1, 12, "dev", 30000, 5, 2010);
insert into skills_used
values(1, 12, "dev", "DevSkill", "IT");


-- for seeker 2:
insert into Qualification
values (2, "bits", "bachelors", "ai", 2028);
insert into Skills_learnt
values(2, 2028, "Webdev", "IT");
insert into Work_Experience
values (2, 11, "dev", 10000, 10, 2000);
insert into skills_used
values(2, 11, "dev", "Webdev", "SALES");
-- adding jobs:

-- for seeker 3:
insert into Work_Experience
values(3, 12, "dev", 100, 5, 2015);

-- for seeker 4:
insert into Work_Experience
values(4, 12, "dev", 100000, 5, 2015);

-- for seeker 5:
insert into Work_Experience
values(5, 12, "dev", 10, 1, 2019);

-- into apple 1:
insert into Job
values(1, 11, "appleRole1",10,"India", "FullTime", "Vacant");
-- adding skills for above job: 
insert into skills_required values(1, "Webdev", "IT");

insert into Job
values(2, 11, "appleRole2",10,"India", "FullTime", "Vacant");
insert into skills_required values(2, "j2dev", "IT");
insert into skills_required values(2, "Webdev", "IT");

insert into Job
values(3, 11, "appleRole3",10,"India", "FullTime", "Vacant");
insert into skills_required values(3, "Webdev", "IT");
insert into skills_required values(3, "j3dev", "IT");

-- into apple 2:
insert into Job
values(4, 12, "apple2Role1",10,"India", "FullTime", "Vacant");
insert into skills_required values(4, "j4dev", "IT");

insert into Job
values(5, 12, "apple2Role2",10,"India", "FullTime", "Vacant");
insert into skills_required values(5, "j5dev", "IT");

insert into Job
values(6, 12, "apple2Role3",100,"India", "FullTime", "Vacant");
insert into skills_required values(6, "j6dev", "IT");


















