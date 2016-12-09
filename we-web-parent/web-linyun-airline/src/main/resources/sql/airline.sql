/*airline_company_info*/
SELECT di.dictName company,
	   di.dictCode dictcode,
	   di2.dictName airline
FROM dict_info di
INNER JOIN dict_relation dr
    ON di.id = dr.sourceId
INNER JOIN dict_info di2
    ON dr.targetId = di2.id
 $condition