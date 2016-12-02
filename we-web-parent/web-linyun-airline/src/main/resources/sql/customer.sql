/*customer_city_list*/
select l.dictInfoId id,
	d.dictName 
from 
	t_customer_outcity l inner join dict_info d on l.dictInfoId=d.id
and l.infoId=@companyId
$condition

/*customer_line_list*/
select 
	d.* 
from 
	t_customer_line l inner join dict_info d on l.dictInfoId=d.id
and l.infoId=@companyId
$condition

/*customer_invioce_list*/
select 
	l.infoId,
	l.dictInfoId id,
	d.dictName
from 
	t_customer_invioce l inner join dict_info d on l.dictInfoId=d.id
and l.infoId=@companyId
$condition

/*customer_cityOption_list*/
select 
	d.*
FROM t_customer_outcity o INNER JOIN dict_info d on o.dictInfoId = d.id
$condition

/*customer_agent*/
SELECT 
 u.id,u.userName
from t_customer_info c INNER JOIN
t_user u 
where c.agent=u.id
and c.id=@agentId
$condition

/*customer_comOption_list*/
select 
	o.id,
	c.*
FROM t_customer_info o INNER JOIN t_company c on o.agentId = c.id
$condition

/*customer_islineOption_list*/
select 
  	o.infoId,
	d.*
FROM t_customer_line o INNER JOIN dict_info d on o.dictInfoId = d.id

/*customer_invioceOption_list*/
select 
	l.infoId,
	l.dictInfoId id,
	d.*
from 
	t_customer_invioce l inner join dict_info d on l.dictInfoId=d.id
	
/*customer_list_info*/
SELECT
	u.userName,
	t.*
FROM
	t_customer_info t
INNER JOIN t_user u ON t.agent = u.id
$condition

