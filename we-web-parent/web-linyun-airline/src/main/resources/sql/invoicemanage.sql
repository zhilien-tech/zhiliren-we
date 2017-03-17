/*invoicemanage_issuer_list*/
SELECT
	fi.id,
	fi.`issuer`
FROM
	t_finance_info fi
LEFT JOIN t_up_order ord ON ord.id = fi.orderid
LEFT JOIN t_customer_info cus ON cus.id = ord.userid
LEFT JOIN t_upcompany up ON up.id = cus.upComId
LEFT JOIN t_company com ON com.id = up.comId
LEFT JOIN t_agent ag ON com.id = ag.comId
WHERE
	TRIM(fi.`issuer`) IS NOT NULL
AND TRIM(fi.`issuer`) != ""
$condition
group by fi.`issuer`