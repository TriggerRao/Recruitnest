SELECT DISTINCT SU.Domain, SU.Proficiency
FROM Skills_used SU
WHERE SU.JS_ID = 1
UNION
SELECT DISTINCT Q.Domain, Q.Proficiency
FROM Skills_learnt Q
WHERE Q.JS_ID = 1;
-- this is query 10's subquery

select proficiency, domain from skills_used where js_id = 1 union select proficiency,domain from skills_learnt where js_id = 1;

select Job_ID as jid, Com_ID, jrole from job where(
	(select proficiency, domain from skills_required where skills_required.Job_ID = job.Job_ID)
    in
    (select domain, proficiency from skills_used where js_id = 1 union select proficiency,domain from skills_learnt where js_id = 1)
    );
  
  
  
  -- 10 

SELECT (avg(salary) + max(salary))/2 as expected_earnings, count(*) as count
FROM job
WHERE NOT EXISTS (
    SELECT proficiency, domain
    FROM skills_required
    where skills_required.job_id = job.job_id
    AND (proficiency, domain) NOT IN (select domain, proficiency from skills_used where js_id = 2 union select proficiency,domain from skills_learnt where js_id = 2)
)
and job.jstatus = "Vacant";


-- 4
SELECT *
FROM job
WHERE NOT EXISTS (
    SELECT proficiency, domain
    FROM skills_required
    where skills_required.job_id = job.job_id
    AND (proficiency, domain) NOT IN (select domain, proficiency from skills_used where js_id = 2 union select proficiency,domain from skills_learnt where js_id = 2)
)
and job.jstatus = "Vacant"
order by salary desc;

select * from job_seeker;
select domain, proficiency from skills_used where js_id = 1 union select proficiency,domain from skills_learnt where js_id = 1;

select *
from job_seeker
WHERE NOT EXISTS (
    SELECT proficiency, domain
    FROM skills_required
    where skills_required.job_id = 3
    AND (proficiency, domain) NOT IN (select domain, proficiency from skills_used where js_id = job_seeker.js_id union select proficiency,domain from skills_learnt where js_id = job_seeker.js_id)
) and job_seeker.status = "Searching";




-- ( select proficiency, domain from skills_required where skills_required.Job_ID = job.Job_ID)
-- in
-- (select domain, proficiency from skills_used where js_id = 1 union select proficiency,domain from skills_learnt where js_id = 1)


select proficiency, domain from skills_required where Job_ID = 1;

select proficiency, domain from skills_required where skills_required.Job_ID = 1;




-- 5
-- old 5
SELECT *
FROM job_seeker
left JOIN (
    SELECT js_id, SUM(duration) AS total_experience
    FROM work_experience
    GROUP BY js_id
) AS total_exp ON job_seeker.js_id = total_exp.js_id
WHERE NOT EXISTS (
    SELECT proficiency, domain
    FROM skills_required
    WHERE skills_required.job_id = 1
    AND (proficiency, domain) NOT IN (
        SELECT domain, proficiency
        FROM skills_used
        WHERE js_id = job_seeker.js_id
        UNION
        SELECT proficiency, domain
        FROM skills_learnt
        WHERE js_id = job_seeker.js_id
    )
) AND job_seeker.status = 'Searching'
ORDER BY total_exp.total_experience DESC;



-- updated 5
SELECT job_seeker.*
FROM job_seeker
LEFT JOIN (
    SELECT we.js_id, SUM(we.duration) AS total_experience
    FROM work_experience we
    JOIN skills_used su ON we.js_id = su.js_id AND we.com_id = su.com_id AND we.we_role = su.we_role
    JOIN skills_required sr ON su.proficiency = sr.proficiency AND su.domain = sr.domain
    WHERE sr.job_id = 1 -- Replace with the actual job ID
    GROUP BY we.js_id
) AS total_exp ON job_seeker.js_id = total_exp.js_id
WHERE NOT EXISTS (
    SELECT proficiency, domain
    FROM skills_required
    WHERE skills_required.job_id = 1 -- Replace with the actual job ID
    AND (proficiency, domain) NOT IN (
        SELECT domain, proficiency
        FROM skills_used
        WHERE js_id = job_seeker.js_id
        UNION
        SELECT proficiency, domain
        FROM skills_learnt
        WHERE js_id = job_seeker.js_id
    )
) AND job_seeker.status = 'Searching'
ORDER BY COALESCE(total_exp.total_experience, 0) DESC;

 
-- for second querie consider Age/Gender/Degree

select * from job_seeker where job_seeker.gender = 'Male';

-- select * from job_seeker where job_seeker.location in ("location1", "location2", "location3");

select * from job_seeker where job_seeker.age >30 order by job_seeker.age ;


SELECT job_seeker.*
FROM job_seeker
JOIN qualification ON job_seeker.js_id = qualification.js_id
WHERE qualification.degree = 'bachelors'
AND job_seeker.status = 'Searching';

-- Job

 select * from Job where Job.location = 'location1';
 
 select * from job where job.salary >90 order by job.salary desc;

-- query 9
WITH CandidateSkills AS (
    SELECT js_id, proficiency, domain
    FROM skills_learnt
    WHERE js_id = 1 -- Replace 'candidate_id' with the actual candidate ID
    UNION
    SELECT js_id, proficiency, domain
    FROM skills_used
    WHERE js_id = 1 -- Replace 'candidate_id' with the actual candidate ID
),
QualifiedJobs AS (
    SELECT job_id,
           COUNT(*) AS missing_skills_count
    FROM skills_required sr
    WHERE NOT EXISTS (
        SELECT 1
        FROM CandidateSkills cs
        WHERE cs.proficiency = sr.proficiency
        AND cs.domain = sr.domain
    )
    GROUP BY job_id
    HAVING missing_skills_count = 1
),
TopJobs AS (
    SELECT qj.job_id, j.salary
    FROM QualifiedJobs qj
    JOIN job j ON qj.job_id = j.job_id
    ORDER BY j.salary DESC
    LIMIT 5 -- Limit to top 5 highest-paying jobs
),
TopJobSkills AS (
    SELECT ts.job_id, sr.proficiency, sr.domain
    FROM TopJobs ts
    JOIN skills_required sr ON ts.job_id = sr.job_id
    WHERE NOT EXISTS (
        SELECT 1
        FROM CandidateSkills cs
        WHERE cs.proficiency = sr.proficiency
        AND cs.domain = sr.domain
    )
)
SELECT DISTINCT proficiency, domain
FROM TopJobSkills;

-- 6

UPDATE company
SET Com_Rating = (60+ ( Com_Rating) * 
                  (SELECT COUNT(*) FROM work_experience WHERE Com_ID = 12)) /
                 ((SELECT COUNT(*) FROM work_experience WHERE Com_ID = 12) + 1)
WHERE Com_ID = 12; -- change @toID

select * from company where com_id=1;


-- 7

select avg(we_salary), count(*), year, duration from work_experience where 2020 < year+duration and 2020>=year group by avg(salary);

SELECT
    we_role,
    COUNT(*) AS total_jobs,
    AVG(we_salary) AS avg_salary
FROM
    work_experience
WHERE
    2019 >= year
    AND 2019 < year + duration
GROUP BY
    we_role;
    
    
    
    -- 8 (trends in companies)
    
SELECT
    com_id,
    COUNT(*) AS total_jobs,
    AVG(we_salary) AS avg_salary
FROM
    work_experience
WHERE
    2008>= year
    AND 2008 < year + duration
GROUP BY
    com_id;



