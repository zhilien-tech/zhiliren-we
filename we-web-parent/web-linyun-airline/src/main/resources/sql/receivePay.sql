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
	pi.peoplecount peopleCount,
	pi.costpricesum salePrice,
	pi.currency currency,
	ci.shortName,
	pi.orderPnrStatus,
	fi.`issuer` drawer
FROM
	t_pnr_info pi
INNER JOIN t_pay_pnr pp ON pi.id = pp.pnrId
INNER JOIN t_pay p ON p.id = pp.payId
INNER JOIN t_order_customneed oc ON pi.needid = oc.id
INNER JOIN t_up_order uo ON oc.ordernum = uo.id
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
INNER JOIN t_finance_info fi ON uo.id = fi.orderid
$condition
ORDER BY
	p.approveTime DESC
	
	
/*receivePay_payed_list*/
SELECT
  	p.id,
  	pi.id piid,
	uo.ordersnum ordernum,
	pi.PNR pnrNum,
	oc.leavetdate leaveDate,
	pi.peoplecount peopleCount,
	pi.costpricesum salePrice,
	pi.currency currency
FROM
	t_pay p
LEFT JOIN t_pay_pnr pp on pp.payId=p.id
LEFT JOIN t_pnr_info pi on pi.id=pp.pnrId
LEFT JOIN t_order_customneed oc ON oc.id=pi.needid
LEFT JOIN t_up_order uo ON uo.id=oc.ordernum
$condition
ORDER BY
	p.confirmDate DESC

/*receivePay_payed_edit*/
SELECT
	p.*,
	(
		SELECT
			username
		FROM
			t_user u
		WHERE
			u.id = p.proposer
	) proposerMan,
	uo.ordersnum,
	pi.pnr,
	fi.cusgroupnum,
	ci.shortName,
	fi.billingdate,
	pi.peoplecount,
    pi.costpricesum salesprice,
    pi.salespricesum,
	ci.linkMan,
	(
		SELECT
			username
		FROM
			t_user u
		WHERE
			u.id = fi. ISSUER
	) ISSUER
FROM
	t_pay p
INNER JOIN t_pay_pnr pp ON pp.payId = p.id
INNER JOIN t_pnr_info pi ON pi.id = pp.pnrId
INNER JOIN t_order_customneed oc ON pi.needid = oc.id
INNER JOIN t_up_order uo ON oc.ordernum = uo.id
INNER JOIN t_finance_info fi ON uo.id = fi.orderid
LEFT JOIN t_customer_info ci ON uo.userid = ci.id	
$condition

/*receivePay_payed_bank*/
SELECT
	cbc.bankComp bankCompId,
    di.dictName bankCompName,
    cbc.cardName,
    cbc.cardNum
FROM
	t_company_bank_card cbc
LEFT JOIN t_pay p ON p.bankId = cbc.id
LEFT JOIN dict_info di ON di.id=cbc.bankComp
where p.id IN (@PayId)



/*receivePay_pay_Ids*/
SELECT
    p.id id,
	pi.id pid,
	uo.id uid,
	pi.userid operator,
	uo.ordersnum orderNum,
	pi.PNR pnrNum,
	fi.cusgroupnum,
	ci.shortName,
	fi.billingdate billdate,
	oc.peoplecount peopleCount,
	u.userName proposer,
	pi.costpricesum salePrice,
	p.approver,
	p.approveResult,
	p.purpose,
	p.payCurrency
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

/*get_receive_list_by_condition*/
SELECT
	r.id,
	uo.ordersnum,
	oc.leavetdate,
	fi.personcount,
	fi.incometotal,
	ci.shortName,
	fi.billingdate,
	fi. ISSUER,
	r.`status`
FROM
	t_receive r
INNER JOIN t_order_receive ore ON ore.receiveid = r.id
INNER JOIN t_up_order uo ON uo.id = ore.orderid
LEFT JOIN t_finance_info fi ON uo.id = fi.orderid
INNER JOIN t_order_customneed oc ON oc.ordernum = uo.id
INNER JOIN t_customer_info ci ON ci.id = uo.userid
WHERE
	r.id IN (
		SELECT
			r.id
		FROM
			t_receive r
		INNER JOIN t_order_receive ore ON ore.receiveid = r.id
		INNER JOIN t_up_order uo ON uo.id = ore.orderid
		LEFT JOIN t_finance_info fi ON uo.id = fi.orderid
		INNER JOIN t_order_customneed oc ON oc.ordernum = uo.id
		INNER JOIN t_customer_info ci ON ci.id = uo.userid
		$condition
	)

	
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
LEFT JOIN t_customer_info ci ON uo.userid = ci.id
LEFT JOIN t_finance_info fi ON uo.id = fi.orderid
$condition

/*receivePay_loginComp_user_ids*/
SELECT
	uj.userid
FROM
	t_user_job uj
INNER JOIN t_company_job cj ON cj.posid = uj.companyJobId
$condition

/*receivePay_order_pnr_pids*/
SELECT
	uo.id,
	uo.ordersnum,
	pi.PNR
FROM
	t_pnr_info pi
INNER JOIN t_pay_pnr pp ON pi.id = pp.pnrId
INNER JOIN t_order_customneed oc ON oc.id = pi.needid
INNER JOIN t_up_order uo ON uo.id = oc.ordernum
$condition

/*receivePay_order_rec_rids*/
SELECT
	uo.id,
	uo.ordersnum
FROM
	t_up_order uo
INNER JOIN t_order_receive ore ON ore.orderid=uo.id 
INNER JOIN t_receive r ON r.id=ore.receiveid
$condition

/*receivePay_inter_rec_invioce_list*/
SELECT
	r.*, 
	orec.receivestatus,
	ii.id invoiceid,
	u.userName
FROM
	t_receive r
LEFT JOIN t_order_receive orec ON orec.receiveid=r.id
LEFT JOIN t_up_order uo on uo.id=orec.orderid
LEFT JOIN t_invoice_info ii ON r.id = ii.receiveid
LEFT JOIN t_user u ON r.userid = u.id
$condition

/*receivePay_inter_rec_order_list*/
SELECT
	r.id,
	uo.ordersnum,
	fi.personcount,
	fi.incometotal,
	orec.orderstatus,
	orec.receivestatus,
	ci.shortName,
	ci.linkMan,
	pi.leavesdate,
	pi.peoplecount
FROM
	t_up_order uo
LEFT JOIN t_finance_info fi ON uo.id = fi.orderid
INNER JOIN t_order_receive orec ON uo.id = orec.orderid
INNER JOIN t_receive r ON orec.receiveid = r.id
INNER JOIN t_plan_info pi ON uo.id = pi.ordernumber
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
WHERE
	r.id IN (
		SELECT
			r.id
		FROM
			t_up_order uo
		LEFT JOIN t_finance_info fi ON uo.id = fi.orderid
		INNER JOIN t_order_receive orec ON uo.id = orec.orderid
		INNER JOIN t_receive r ON orec.receiveid = r.id
		INNER JOIN t_plan_info pi ON uo.id = pi.ordernumber
		LEFT JOIN t_customer_info ci ON ci.id = uo.userid
		$condition
	)

/*receivePay_inter_pay_invioce_list*/
SELECT
	uo.id,
	uo.ordersnum,
	po.orderstatus,
	po.paystauts
FROM
	t_up_order uo
INNER JOIN t_pay_order po ON po.orderid = uo.id
INNER JOIN t_pay p ON p.id = po.payid
$condition


/*receivePay_inter_pay_order_list*/
SELECT
	uo.id,
	uo.ordersnum,
	po.orderstatus,
	(
		SELECT
			dictCode
		FROM
			dict_info
		WHERE
			id = p.payCurrency
	) AS 'payCurrency',
	pi.leavesdate,
	pi.peoplecount,
	prr.actualnumber,
	prr.currentpay,
	fi.`issuer`,
	ci.shortName,
	ci.linkMan,
	p.approveResult
FROM
	t_up_order uo
INNER JOIN t_pay_order po ON po.orderid = uo.id
INNER JOIN t_pay p ON p.id = po.payid
LEFT JOIN t_plan_info pi ON pi.ordernumber = uo.id
LEFT JOIN t_pay_receive_record prr ON prr.orderid = uo.id
LEFT JOIN t_finance_info fi ON fi.orderid = uo.id
LEFT JOIN t_customer_info ci ON ci.id = uo.userid
WHERE
	uo.id IN (
		SELECT
			uo.id
		FROM
			t_up_order uo
		INNER JOIN t_pay_order po ON po.orderid = uo.id
		INNER JOIN t_pay p ON p.id = po.payid
		LEFT JOIN t_plan_info pi ON pi.ordernumber = uo.id
		LEFT JOIN t_pay_receive_record prr ON prr.orderid = uo.id
		LEFT JOIN t_finance_info fi ON fi.orderid = uo.id
		LEFT JOIN t_customer_info ci ON ci.id = uo.userid
		$condition
	)
	
/*receivePay_inter_pay_order_ids*/
SELECT
	p.*, 
	(
		SELECT
			username
		FROM
			t_user u
		WHERE
			u.id = p.proposer
	) proposerMan,
	uo.id,
	uo.ordersnum,
	fi.cusgroupnum,
	ci.shortName,
	fi.billingdate,
	pi.peoplecount,
	fi.`issuer`
FROM
	t_up_order uo
LEFT JOIN t_pay_order po ON po.orderid = uo.id
INNER JOIN t_pay p ON p.id = po.payid
INNER JOIN t_plan_info pi ON pi.ordernumber = uo.id
INNER JOIN t_customer_info ci ON ci.id = uo.userid
INNER JOIN t_finance_info fi ON fi.orderid = uo.id
$condition

/*receivePay_inter_payed_orders*/
SELECT
	p.id pid,
	uo.id,
	uo.ordersnum,
	po.orderstatus,
	po.paystauts,
	pi.leavesdate,
	pi.peoplecount,
	(
		SELECT
			dictCode
		FROM
			dict_info
		WHERE
			id = p.payCurrency
	) AS 'payCurrency',
	ci.shortName,
	ci.linkMan,
	fi.`issuer`
FROM
	t_up_order uo
LEFT JOIN t_pay_order po ON po.orderid = uo.id
INNER JOIN t_pay p ON p.id = po.payid
INNER JOIN t_plan_info pi ON pi.ordernumber = uo.id
INNER JOIN t_customer_info ci ON ci.id = uo.userid
INNER JOIN t_finance_info fi ON fi.orderid = uo.id
WHERE
	p.id IN (
		SELECT
			p.id pid
		FROM
			t_up_order uo
		LEFT JOIN t_pay_order po ON po.orderid = uo.id
		INNER JOIN t_pay p ON p.id = po.payid
		INNER JOIN t_plan_info pi ON pi.ordernumber = uo.id
		INNER JOIN t_customer_info ci ON ci.id = uo.userid
		INNER JOIN t_finance_info fi ON fi.orderid = uo.id
		$condition
	)
ORDER BY
	po.payDate DESC
	
/*receivePay_inter_payed_edit*/
SELECT
	p.*, 
	(
		SELECT
			username
		FROM
			t_user u
		WHERE
			u.id = p.proposer
	) proposerMan,
	uo.id,
	uo.ordersnum,
	fi.cusgroupnum,
	ci.shortName,
	fi.billingdate,
	pi.peoplecount,
	fi.`issuer`
FROM
	t_up_order uo
LEFT JOIN t_pay_order po ON po.orderid = uo.id
INNER JOIN t_pay p ON p.id = po.payid
INNER JOIN t_plan_info pi ON pi.ordernumber = uo.id
INNER JOIN t_customer_info ci ON ci.id = uo.userid
INNER JOIN t_finance_info fi ON fi.orderid = uo.id
$condition