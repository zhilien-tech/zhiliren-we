/*receivePay_pay_id_list*/
SELECT
  p.id pid,
  p.totalMoney,
  ci.shortName,
  fi.`issuer` userName,
  pi.orderPnrStatus
FROM
	t_pay p
LEFT JOIN t_pay_pnr pp ON pp.payId = p.id
LEFT JOIN t_pnr_info pi ON pi.id = pp.pnrId
LEFT JOIN t_order_customneed oc ON oc.id = pi.needid
LEFT JOIN t_up_order uo ON uo.id = oc.ordernum
LEFT JOIN t_finance_info fi ON fi.orderid=uo.id
LEFT JOIN t_customer_info ci ON ci.id=uo.userid
$condition
GROUP BY
	p.id
ORDER BY
	oc.leavetdate DESC

/*receivePay_pay_list*/
SELECT
	pi.id pid,
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	oc.leavetdate leaveDate,
	oc.peoplecount peopleCount,
	pi.salesprice salePrice,
	oc.paycurrency currency,
	ci.shortName,
	pi.orderPnrStatus,
	fi.`issuer` drawer
FROM
	t_pnr_info pi
INNER JOIN t_pay_pnr pp ON pi.id = pp.pnrId
INNER JOIN t_order_customneed oc ON pi.needid = oc.id
INNER JOIN t_up_order uo ON oc.ordernum = uo.id
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
INNER JOIN t_finance_info fi ON uo.id = fi.orderid
$condition
ORDER BY
	oc.leavetdate DESC
	
	
/*receivePay_payed_list*/
SELECT
  	p.id,
	uo.ordersnum ordernum,
	pi.PNR pnrNum,
	oc.leavetdate leaveDate,
	oc.peoplecount peopleCount,
	pi.salesprice salePrice,
	oc.paycurrency currency
FROM
	t_pay p
LEFT JOIN t_pay_pnr pp on pp.payId=p.id
LEFT JOIN t_pnr_info pi on pi.id=pp.pnrId
LEFT JOIN t_order_customneed oc ON oc.id=pi.needid
LEFT JOIN t_up_order uo ON uo.id=oc.ordernum
$condition
ORDER BY
	leaveDate DESC
	
/*receivePay_pay_Ids*/
SELECT
    p.id id,
	pi.id pid,
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	fi.cusgroupnum,
	ci.shortName,
	fi.billingdate billdate,
	oc.peoplecount peopleCount,
	u.userName proposer,
	pi.salesprice salePrice,
	p.approver,
	p.approveResult
FROM
	t_pnr_info pi
LEFT JOIN t_order_customneed oc ON oc.id = pi.needid
LEFT JOIN t_up_order uo ON uo.id = oc.ordernum
LEFT JOIN t_finance_info fi ON fi.orderid = uo.id
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
LEFT JOIN t_pay_pnr pp ON pp.pnrId = pi.id
LEFT JOIN t_pay p ON p.id = pp.payId
LEFT JOIN t_user u ON u.id = p.proposer
$condition
ORDER BY
	oc.leavetdate DESC

/*receivePay_rec_id_list*/
SELECT
	r.id recid,
	r.sum,
	ci.shortName,
	ci.linkMan,
	fi.`issuer` userName,
	uo.ordersstatus orderstatus
FROM
	t_finance_info fi
LEFT JOIN t_up_order uo ON fi.orderid = uo.id
LEFT JOIN t_order_customneed oc ON oc.ordernum = uo.id
LEFT JOIN t_order_receive orec ON orec.orderid = uo.id
LEFT JOIN t_receive r ON orec.receiveid = r.id
LEFT JOIN t_receive_bill rb ON r.id = rb.receiveid
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
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
	fi.`issuer` userName,
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
$condition
ORDER BY
	oc.leavetdate DESC

/*receivePay_rec_id*/
SELECT
	r.id,
	uo.id uoid,
	uo.ordersnum,
	fi.billingdate billdate,
	fi.cusgroupnum,
	ci.shortName,
	ci.linkMan,
	fi.'issuer' userName,
	fi.incometotal,
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
$condition
ORDER BY
	r.receivedate DESC
	
	
/*receivePay_rec_order_id*/
SELECT
	uo.id
FROM
	t_receive r
LEFT JOIN t_order_receive orec ON orec.receiveid = r.id
LEFT JOIN t_up_order uo ON uo.id=orec.orderid
$condition

/*receivePay_rec_invioce_list*/
SELECT
	r.*, ii.id invoiceid
FROM
	t_receive r
LEFT JOIN t_invoice_info ii ON r.id = ii.receiveid
$condition

/*get_receive_order_list*/
SELECT
	uo.ordersnum,
	oc.leavetdate,
	fi.personcount,
	fi.incometotal,
	ci.shortName,
	fi.billingdate,
	fi. ISSUER,
	r.`status`
FROM
	t_up_order uo
LEFT JOIN t_finance_info fi ON uo.id = fi.orderid
INNER JOIN t_order_receive ore ON uo.id = ore.orderid
INNER JOIN t_receive r ON ore.receiveid = r.id
INNER JOIN t_order_customneed oc ON oc.ordernum = uo.id
INNER JOIN t_customer_info ci ON ci.id = uo.userid
$condition

/*receivePay_toRec_table_data*/
SELECT
	uo.*, 
	fi.billingdate,
	fi.cusgroupnum,
	ci.shortName,
	ci.linkMan,
	fi. ISSUER,
	fi.incometotal
FROM
	t_up_order uo
INNER JOIN t_customer_info ci ON uo.userid = ci.id
LEFT JOIN t_finance_info fi ON uo.id = fi.orderid
$condition