drop database project;
create database project;
use project;
create table company(Com_ID numeric(20), Com_Name varchar(100), Sector varchar(100), Com_Rating numeric(10), Official_Website varchar(100),primary key(Com_ID));
create table Job(Job_ID numeric(20), Com_ID numeric(20), JRole varchar(100), Salary numeric(10), Location varchar(100), JType varchar(100), JStatus varchar(100),primary key(Job_ID));
create table Job_Seeker(JS_ID numeric(20), Name varchar(100), Age numeric(10), Gender varchar(100), Status varchar(100),primary key(JS_ID));
create table Work_Experience(JS_ID numeric(20), Com_ID numeric(20), WE_Role varchar(100), WE_Salary numeric(10), Duration numeric(10), Year numeric (4),primary key(JS_ID,Com_ID ,WE_Role),
 foreign key (JS_ID) references Job_Seeker(JS_ID) on delete cascade,
 foreign key (Com_ID) references company(Com_ID) on delete cascade);
create table Qualification(JS_ID numeric(20), Organization varchar(100), Degree varchar(100), Specialization varchar(100), Year numeric(4) ,primary key(JS_ID, Year),
foreign key (JS_ID) references Job_Seeker(JS_ID) on delete cascade);
create table Applies(JS_ID numeric(20), Job_ID numeric(20),primary key(JS_ID, Job_ID),
foreign key (JS_ID) references Job_Seeker(JS_ID) on delete cascade,
foreign key (Job_ID) references Job(Job_ID) on delete cascade);
create table Accepted(JS_ID numeric(20), Job_ID numeric(20),primary key(JS_ID, Job_ID),
foreign key (JS_ID) references Job_Seeker(JS_ID) on delete cascade,
foreign key (Job_ID) references Job(Job_ID) on delete cascade);
create table Offered(JS_ID numeric(20), Job_ID numeric(20),primary key(JS_ID, Job_ID), 
foreign key (JS_ID) references Job_Seeker(JS_ID) on delete cascade,
foreign key (Job_ID) references Job(Job_ID) on delete cascade);
create table Skills_used(JS_ID numeric(20), Com_ID numeric(20), WE_Role varchar(100),Proficiency varchar(100), Domain varchar(100),primary key(JS_ID,Com_ID, WE_Role,Proficiency,Domain));
-- foreign key (JS_ID) references Work_Experience(JS_ID) on delete cascade,
-- foreign key (Com_ID) references Work_Experience(Com_ID) on delete cascade,
-- foreign key (WE_Role) references Work_Experience(WE_Role) on delete cascade)
create table Skills_learnt(JS_ID numeric(20), Year numeric(4) ,  Proficiency varchar(100), Domain varchar(100),primary key(JS_ID,Year,Proficiency,Domain)
-- foreign key (JS_ID) references Job_Seeker(JS_ID) on delete cascade,
-- foreign key (Year) references Qualification(Year) on delete cascade
);
create table Skills_Required(Job_ID numeric(20), Proficiency varchar(100), Domain varchar(100),primary key(Job_ID,Proficiency,Domain),
foreign key (Job_ID) references Job(Job_ID) on delete cascade);
show tables;





-- create table Skill(Proficiency varchar(100), Domain varchar(100),primary key(Proficiency,Domain) );