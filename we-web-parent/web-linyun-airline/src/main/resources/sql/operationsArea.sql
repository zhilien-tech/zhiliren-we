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
WHERE m.id=um.msgId 
AND um.userId = u.id
AND u.id=uj.userid 
AND uj.companyJobId=cj.posid 
AND cj.comId=c.id 
AND u.id=@userId
AND m.generateTime between @start AND @end
$condition