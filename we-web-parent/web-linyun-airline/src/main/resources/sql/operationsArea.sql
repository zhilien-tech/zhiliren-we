/*msg_user_company*/
SELECT
	m.id,
	m.generateTime,
  	c.comName, 
	u.userName,
	m.msgContent
FROM
 	t_message m,
 	t_user_msg um,
 	t_user u,
 	t_user_job uj,
 	t_company_job cj,
 	t_company c
WHERE 
	m.id=um.msgId 
AND 
	um.userId = u.id
AND 
	u.id=uj.userid 
AND 
	uj.companyJobId=cj.posid 
AND 
	cj.comId=c.id 
AND 
	u.id=@userId
AND 
	m.generateTime between @start AND @end
$condition

/*msg_user_agentCompany  数据未显示*/
SELECT
	m.id,
	m.generateTime,
	c.comName,
	ci.shortName,
	u.userName,
	m.msgContent,
	c.id
FROM
	t_message m,
	t_user_msg um,
	t_user u,
	t_user_job uj,
	t_company_job cj,
	t_company c,
	t_upcompany uc,
	t_agent ag,
	t_customer_info ci
WHERE 
	m.id=um.msgId
AND 
	um.userId = u.id
AND 
	u.id=uj.userid
AND 
	uj.companyJobId=cj.posid
AND 
	cj.comId=c.id
AND 
	c.id=ag.comId
AND 
	ag.comId=ci.agentId
AND 
	u.id=@userId
AND
	m.generateTime<@now
ORDER BY m.generateTime DESC
$condition

/*msg_user_company_task testDate*/
SELECT
	m.id,
	m.generateTime,
  	c.comName, 
  	ci.shortName,
	u.userName,
	m.msgContent
FROM
 	t_message m,
 	t_user_msg um,
 	t_user u,
 	t_user_job uj,
 	t_company_job cj,
 	t_company c,
  	t_upcompany uc,
  	t_customer_info ci
WHERE 
	m.id=um.msgId 
AND 
	um.userId = u.id
AND 
	u.id=uj.userid 
AND 
	uj.companyJobId=cj.posid 
AND 
	cj.comId=c.id
AND
	c.id=uc.comId
AND
	uc.comId=ci.comId
AND 
	u.id=@userId
AND 
	m.generateTime<@now
ORDER BY m.generateTime DESC
$condition