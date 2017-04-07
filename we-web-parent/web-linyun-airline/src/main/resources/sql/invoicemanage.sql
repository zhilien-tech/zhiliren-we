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
	
/*invoicemanage_kaiinvoice_list*/
SELECT
	id,
	invoiceitem,
	invoicedate,
	billuserid,
	deptid,
	paymentunit,
	remark,
	difference,
	balance,
	invoicetype,
	receiveid,
	pnrid,
	opid,
	optime,
	STATUS,
	comId,
	payid,
	ordertype
FROM
	t_invoice_info ii
$condition

/*invoicemanage_kaiinvoice_search_list*/
SELECT
	tuo.ordersnum,
	tuo.peoplecount,
	ii.*, 
	u.id AS userIds,
	idd.invoicenum,
	(
		SELECT
			count(*)
		FROM
			t_invoice_detail
		WHERE
			invoiceinfoid = ii.id
	) invoicecount,
	u.userName
FROM
	t_invoice_info ii
LEFT JOIN t_order_receive ore ON ii.receiveid = ore.receiveid
LEFT JOIN t_up_order tuo ON tuo.id = ore.orderid
LEFT JOIN t_customer_info cus ON cus.id = tuo.userid
LEFT JOIN t_user u ON u.id = ii.billuserid
LEFT JOIN dict_info info ON info.id = ii.invoiceitem
LEFT JOIN t_invoice_detail idd ON ii.id=idd.invoiceinfoid
$condition

/*invoicemanage_shou_invoice_list*/
SELECT
	tii.comId,
	tuo.ordersnum,
	tpi.*, tii.invoicedate,
	tii.`status`,
	tii.invoiceitem,
	tii.paymentunit,
	tii.id invoiceid,
	tii.remark,
	(
		SELECT
			count(*)
		FROM
			t_invoice_detail
		WHERE
			invoiceinfoid = tii.id
	) invoicecount,
	tu.fullName
FROM
	t_pnr_info tpi
INNER JOIN t_invoice_info tii ON tii.pnrid = tpi.id
LEFT JOIN t_user tu ON tii.billuserid = tu.id
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
$condition

/*invoicemanage_invoice_getfullname_list*/
SELECT
	tuo.ordersnum,
	ii.*, 
	u.id AS userIds,
	u.fullName
FROM
	t_invoice_info ii
LEFT JOIN t_order_receive ore ON ii.receiveid = ore.receiveid
LEFT JOIN t_up_order tuo ON tuo.id = ore.orderid
LEFT JOIN t_customer_info cus ON cus.id = tuo.userid
LEFT JOIN t_user u ON u.id = ii.billuserid
LEFT JOIN dict_info info ON info.id = ii.invoiceitem
$condition