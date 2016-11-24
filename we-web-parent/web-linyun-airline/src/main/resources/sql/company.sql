/*company_list*/
select t.id,
		t.adminId,
		t.comName,
		case when comType = 1 then 
			'上游公司'
		when comType = 2 then
			'代理商'
		else
			'其他'
		end  comType,
		t.remark,
		t.connect,
		t.mobile,
		t.email,
		t.phonenumber,
		t.address,
		t.license,
		t.opid,
		t.createtime,
		t.lastupdatetime,
		t.deletestatus,
    IFNULL(t1.countuser,0) renshu
from t_company t left join (select count(tuj.id) countuser,tcj.comId  
										from t_company_job tcj,t_user_job tuj 
										where tcj.id = tuj.companyJobId GROUP BY tcj.comId) t1 
				on t1.comId = t.id
$condition
/*company_list_count*/
select count(*)
from t_company t left join (select count(tuj.id) countuser,tcj.comId  
										from t_company_job tcj,t_user_job tuj 
										where tcj.id = tuj.companyJobId GROUP BY tcj.comId) t1 
				on t1.comId = t.id
$condition

/*company_user_info_list*/
select tu.*,tcj.comId,tuj.id ujid,tj.`name` zhiwei,td.deptName department
from 
	 t_user tu right join t_user_job tuj on tuj.userid = tu.id 
		left join t_company_job  tcj  on tcj.id = tuj.companyJobId 
		left join t_job tj on tcj.posid = tj.id 
		left join 	t_department td on tj.deptId = td.id
$condition
/*company_department_list*/
select td.*
from t_department td LEFT JOIN t_company tc on td.comId = tc.id
$condition