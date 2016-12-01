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
select l.dictInfoId id,
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
