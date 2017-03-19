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
com.id = @comId
AND	TRIM(fi.`issuer`) IS NOT NULL
AND TRIM(fi.`issuer`) != ""
$condition
group by fi.`issuer`

/*invoicemanage_search_list*/
SELECT
	ice.`status`,
	u.userName,
	ice.billuserid,
	ice.invoicedate,
	de.invoicenum,
	ice.paymentunit
FROM
	t_finance_info fi
LEFT JOIN t_up_order ord ON ord.id = fi.orderid
LEFT JOIN t_customer_info cus ON cus.id = ord.userid
LEFT JOIN t_upcompany up ON up.id = cus.upComId
LEFT JOIN t_company com ON com.id = up.comId
LEFT JOIN t_agent ag ON com.id = ag.comId
LEFT JOIN t_order_receive ore ON ord.id = ore.orderid
LEFT JOIN t_receive re ON re.id = ore.receiveid
LEFT JOIN t_invoice_info ice ON re.id = ice.receiveid
LEFT JOIN t_user u ON u.id = ice.billuserid
LEFT JOIN t_invoice_detail de ON ice.id = de.invoiceinfoid
$condition
GROUP BY
	u.userName