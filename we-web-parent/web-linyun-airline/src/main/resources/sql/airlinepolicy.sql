/*airlinepolicy_select2_airlinecompany*/
SELECT
	id,
	dictName
FROM
	dict_info 
$condition
LIMIT 0,5
/*airlinepolicy_select2_area*/
SELECT
	id,
	areaName
FROM
	t_area 
$condition
LIMIT 0,5
/*airlinepolicy_datalist*/
select ap.*,(select dictCode from dict_info where id = ap.airlineCompanyId ) as 'airlineCompanyName',
(select areaName from t_area where id=ap.areaId) as 'areaName'
from 
t_airlinepolicy ap
$condition