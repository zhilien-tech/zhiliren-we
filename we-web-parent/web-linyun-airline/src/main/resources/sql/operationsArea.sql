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
	m.msgStatus=@msgStatus
AND 
	m.generateTime between @start AND @end
	

/*msg_user_company_task*/
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
	m.msgStatus=@msgStatus
AND 
	m.generateTime<@now
ORDER BY m.generateTime DESC



/*msg_count_list*/
SELECT DISTINCT
	(
		DATE_FORMAT(m.generateTime, '%Y-%m-%d')
	) AS gtime,
	COUNT(m.generateTime) AS counts,
	m.msgType
FROM
	t_message m
WHERE
	date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes1,'%Y-%m') 
	OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes2,'%Y-%m')
	OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes3,'%Y-%m')
GROUP BY
	generateTime

/*msg_type_list*/
SELECT DISTINCT
	(
		DATE_FORMAT(m.generateTime, '%Y-%m-%d')
	) AS gtime,
	m.msgContent,
	m.msgType
FROM
	t_message m
INNER JOIN t_user_msg um ON m.id = um.msgId
WHERE
	m.msgStatus = @msgStatus
AND 
	um.userId = @userid
AND
	date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes1,'%Y-%m')
OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes2,'%Y-%m')
OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes3,'%Y-%m')

	

/*msg_type*/
SELECT DISTINCT
	(
	DATE_FORMAT(m.generateTime, '%Y-%m-%d')
	) AS gtime,
	m.msgContent,
	m.msgType
FROM
	t_message m
WHERE
	date_format(m.generateTime, '%Y-%m-%d') = date_format(@MincalTimes1,'%Y-%m-%d')
AND
	m.msgStatus=@msgStatus
	
/*agentCompany_list*/
SELECT
	*
FROM
	t_company c
INNER JOIN t_agent a ON a.comId=c.id
AND
	c.comType=@comtype
AND
	c.deletestatus=@deletestatus
LIMIT 0,5

/*get_checkbox_status*/
SELECT
	*
FROM
	t_checkbox_status t
WHERE
	t.userId =@userid


