/*receivePay_pay_list*/
SELECT
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	oc.leavetdate leaveDate,
	oc.peoplecount peopleCount,
	pi.salesprice salePrice,
	pi.currency currency,
	uo.ordersstatus orderStatus,
	u.userName drawer
FROM
	(
		(
			(
				(
					(
						t_pnr_info pi
						INNER JOIN t_order_customneed oc ON pi.needid = oc.id
					)
					INNER JOIN t_up_order uo ON oc.ordernum = uo.id
					AND uo.loginUserId = @loginUserId
				)
				INNER JOIN t_finance_info fi ON fi.orderid = uo.id
			)
			INNER JOIN t_user u ON fi.`issuer` = u.id
		)
		INNER JOIN dict_info di ON fi.paycurrency = di.id
	)
INNER JOIN t_customer_info ci ON ci.id = uo.userid
ORDER BY leaveDate DESC