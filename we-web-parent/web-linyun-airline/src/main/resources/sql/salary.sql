/*salary_list*/
select * from(
select ts.*,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months
from t_salary ts) ms
$condition
/*salary_drawer*/
select DISTINCT ms.drawer from(
select ts.drawer,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months,
ts.comId
from t_salary ts 
) ms
$condition
/*salary_years*/
select DISTINCT ms.years from(
select ts.drawer,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months
,ts.comId
from t_salary ts 
) ms
$condition
/*salary_months*/
select DISTINCT ms.months from(
select ts.drawer,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months
,ts.comId
from t_salary ts 
) ms
$condition
/*salary_add*/
select * from(
select SUM(costtotal) as 'costTotal',COUNT(*) as 'groupNumber',SUM(incometotal) as 'incomeTotal',SUM(personcount) as 'headCount',u.userName
as 'drawer',si.baseWages as 'basePay',si.commission,si.comId,month(fi.billingdate) as 'month',year(fi.billingdate) as 'year',fi.issuerid
as 'drawerId'
from t_finance_info fi 
LEFT JOIN t_user u on u.id=fi.issuerid
LEFT JOIN t_salary_increase si on si.userId=u.id
GROUP BY fi.issuerid
) uu
where  month=month(now()) AND year=year(now())