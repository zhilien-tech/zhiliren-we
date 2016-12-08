/*authoritymanage_list*/
SELECT
	dp.deptName,
	tf.`name` as moduleName,
	tj.`name` as jobName
FROM
	t_department dp
LEFT JOIN t_job tj ON tj.deptId = dp.id
LEFT JOIN t_com_fun_pos_map cpm ON cpm.jobId = tj.id
LEFT JOIN t_company_function_map cfm ON cfm.id = cpm.companyFunId
LEFT JOIN t_function tf ON tf.id = cfm.funId
$condition