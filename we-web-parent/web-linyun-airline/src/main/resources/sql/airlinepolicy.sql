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