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
