/*user_function_all*/
SELECT F.* FROM 
 t_function f,
 t_company_function_map cf,
 t_com_fun_pos_map cfp,
 t_user_job uj
WHERE f.id=cf.funId 
AND cf.id=cfp.companyFunId
AND cfp.jobId=uj.companyJobId
AND uj.userid=@userId