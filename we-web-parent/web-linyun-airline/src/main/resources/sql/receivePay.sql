/*receivePay_pay_list*/
SELECT
	pi.id id,
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	oc.leavetdate leaveDate,
	oc.peoplecount peopleCount,
	pi.salesprice salePrice,
	oc.paycurrency currency,
	uo.ordersstatus orderStatus,
	u.userName drawer
FROM
	(
		(
			(
				(
					t_pnr_info pi
					INNER JOIN t_order_customneed oc ON pi.needid = oc.id
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
	pi.id,
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
WHERE
	pi.id=@pnrId
ORDER BY
	leaveDate DESC
