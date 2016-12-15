/*employee_list*/
SELECT
	u.id AS userId,
	u.userName,
	u.telephone,
	d.id AS deptId,
	d.deptName,
	j.id AS jobId,
	j.`name` AS jobName
FROM
	t_user u
INNER JOIN t_user_job uj ON u.id = uj.userid
INNER JOIN t_company_job cj ON cj.id = uj.companyJobId
INNER JOIN t_job j ON j.id = cj.posid
INNER JOIN t_department d ON d.id = j.deptId
$condition
