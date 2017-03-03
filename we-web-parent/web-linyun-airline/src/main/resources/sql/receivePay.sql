/*receivePay_pay_id_list*/
SELECT
	p.id pid,
	p.totalMoney,
	u.userName,
	pp.orderPnrStatus
FROM
	t_pay p
LEFT JOIN t_pay_pnr pp ON p.id = pp.payId
LEFT JOIN t_pnr_info pi ON pi.id = pp.pnrId
LEFT JOIN t_order_customneed oc ON oc.id = pi.needid
LEFT JOIN t_up_order uo ON uo.id = oc.ordernum
LEFT JOIN t_finance_info fi ON fi.orderid = uo.id
LEFT JOIN t_user u ON u.id = fi. ISSUER
$condition
GROUP BY
	pid

/*receivePay_pay_list*/
SELECT
	p.id pid,
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	oc.leavetdate leaveDate,
	oc.peoplecount peopleCount,
	pi.salesprice salePrice,
	oc.paycurrency currency
FROM
	t_pnr_info pi
INNER JOIN t_order_customneed oc ON pi.needid = oc.id
INNER JOIN t_up_order uo ON oc.ordernum = uo.id
INNER JOIN t_finance_info fi ON fi.orderid = uo.id
INNER JOIN t_user u ON fi.`issuer` = u.id
INNER JOIN t_customer_info ci ON ci.id = uo.userid
INNER JOIN t_pay_pnr pp ON pp.pnrId = pi.id
INNER JOIN t_pay p ON p.id = pp.payId
$condition
ORDER BY
	leaveDate DESC
	
/*receivePay_rec_id_list*/
SELECT
	r.id recid,
	r.sum,
	ci.shortName,
	ci.linkMan,
	u.userName,
	uo.ordersstatus orderstatus
FROM
	t_finance_info fi
LEFT JOIN t_up_order uo ON fi.orderid = uo.id
LEFT JOIN t_order_customneed oc ON oc.ordernum = uo.id
LEFT JOIN t_order_receive orec ON orec.orderid = uo.id
LEFT JOIN t_receive r ON orec.receiveid = r.id
LEFT JOIN t_receive_bill rb ON r.id = rb.receiveid
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
LEFT JOIN t_user u ON u.id = fi.`issuer`
$condition
GROUP BY
	r.id
ORDER BY
	oc.leavetdate DESC

/*receivePay_rec_list*/
SELECT
	r.id recid,
	uo.ordersnum,
	oc.leavetdate leavedate,
	fi.personcount,
	fi.incometotal,
	r.sum,
	ci.shortName,
	ci.linkMan,
	u.userName,
	fi.billingdate billdate,
	fi.cusgroupnum,
	uo.ordersstatus orderstatus
FROM
	t_finance_info fi
LEFT JOIN t_up_order uo ON fi.orderid = uo.id
LEFT JOIN t_order_customneed oc ON oc.ordernum = uo.id
LEFT JOIN t_order_receive orec ON orec.orderid = uo.id
LEFT JOIN t_receive r ON orec.receiveid = r.id
LEFT JOIN t_receive_bill rb ON r.id = rb.receiveid
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
LEFT JOIN t_user u ON u.id = fi.`issuer`
$condition
ORDER BY
	oc.leavetdate DESC
	
/*receivePay_payed_list*/
SELECT
	p.id,
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	oc.leavetdate leaveDate,
	oc.peoplecount peopleCount,
	pi.salesprice salePrice,
	oc.paycurrency currency,
	u.userName drawer,
	uo.ordersstatus orderStatus
FROM
	(
		(
			(
				(
					(
						(
							t_pnr_info pi
							INNER JOIN t_order_customneed oc ON pi.needid = oc.id
						)
						INNER JOIN t_pay_pnr pp ON pp.pnrId = pi.id
					)
					INNER JOIN t_pay p ON p.id = pp.payId
				)
				INNER JOIN t_up_order uo ON oc.ordernum = uo.id
			)
			INNER JOIN t_finance_info fi ON fi.orderid = uo.id
		)
		INNER JOIN t_user u ON fi.`issuer` = u.id
	)
INNER JOIN t_customer_info ci ON ci.id = uo.userid
$condition
ORDER BY
	leaveDate DESC
	
/*receivePay_pay_Ids*/
SELECT
	p.id,
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	fi.cusgroupnum custGroupNum,
	fi.billingdate billDate,
	oc.leavetdate leaveDate,
	oc.peoplecount peopleCount,
	u.userName drawer,
	pi.salesprice salePrice,
	p.proposer,
	p.approver,
	p.approveResult
FROM
	(
		(
			(
				(
					(
						(
							t_pnr_info pi
							INNER JOIN t_order_customneed oc ON pi.needid = oc.id
						)
						INNER JOIN t_pay_pnr pp ON pp.pnrId = pi.id
					)
					INNER JOIN t_pay p ON p.id = pp.payId
				)
				INNER JOIN t_up_order uo ON oc.ordernum = uo.id
			)
			INNER JOIN t_finance_info fi ON fi.orderid = uo.id
		)
		INNER JOIN t_user u ON fi.`issuer` = u.id
	)
INNER JOIN t_customer_info ci ON ci.id = uo.userid
$condition
ORDER BY
	leaveDate DESC

/*receivePay_rec_id*/
SELECT
	r.id,
	uo.id uoid,
	uo.ordersnum,
	fi.billingdate billdate,
	fi.cusgroupnum,
	ci.shortName,
	ci.linkMan,
	u.userName,
	uo.amount,
	r.sum,
	r.bankcardid,
	r.bankcardname,
	r.bankcardnum,
	rb.receiptUrl
FROM
	t_finance_info fi
LEFT JOIN t_up_order uo ON fi.orderid = uo.id
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
LEFT JOIN t_order_receive orec ON orec.orderid = uo.id
LEFT JOIN t_receive r ON r.id = orec.receiveid
LEFT JOIN t_receive_bill rb ON rb.receiveid = r.id
LEFT JOIN t_user u ON u.id = fi. ISSUER
$condition
ORDER BY
	r.receivedate DESC