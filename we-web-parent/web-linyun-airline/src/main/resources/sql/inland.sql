/*get_inland_listdata*/
SELECT
	tuo.*, tfi.receivable,
	tfi.personcount,
	tci.linkMan userName,
	tci.telephone
FROM
	t_up_order tuo
left JOIN t_finance_info tfi ON tuo.id = tfi.orderid
inner JOIN t_customer_info tci ON tuo.userid = tci.id
$condition

/*get_pnr_visitor_info*/
SELECT
	tvi.*
FROM
	t_visitors_pnr tvp
INNER JOIN t_visitor_info tvi ON tvp.visitorslistid = tvi.id
$condition

/*get_customneed_visitor*/
SELECT
	tvi.*
FROM
	t_pnr_info tpi
INNER JOIN t_visitors_pnr tvp ON tpi.id = tvp.PNRid
INNER JOIN t_visitor_info tvi ON tvp.visitorslistid = tvi.id
$condition

/*get_sea_payapply_list*/
SELECT
	tpi.*, toc.arrivecity,
	toc.leavecity,
	toc.leavetdate,
	tuo.ordersstatus,
	tuo.ordersnum,
	tci.linkMan,
	tci.telephone,
	tai.ailinenum,
	tai.leavetime,
	tai.arrivetime
FROM
	t_pnr_info tpi
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
INNER JOIN t_customer_info tci ON tuo.userid = tci.id
LEFT JOIN (select * from  t_airline_info GROUP BY needid) tai ON tai.needid = tpi.needid
$condition

/*get_sea_invoce_table_data*/
SELECT
	 tuo.*, tfi.billingdate,
	 tfi.cusgroupnum,
	 tci.shortName,
	 tci.linkMan,
	 tfi.issuer,
	 tfi.incometotal
FROM
	t_up_order tuo
INNER JOIN t_customer_info tci ON tuo.userid = tci.id
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
$condition
/*get_sea_payapply_table_data*/
SELECT
	tpi.*, toc.arrivecity,
	toc.leavecity,
	toc.leavetdate,
	tuo.ordersstatus,
	tuo.ordersnum,
	tci.linkMan,
	tci.telephone,
	tfi.cusgroupnum,
	tfi.issuer,
	tfi.incometotal,
	tfi.billingdate
FROM
	t_pnr_info tpi
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
INNER JOIN t_customer_info tci ON tuo.userid = tci.id
INNER JOIN t_finance_info tfi on tuo.id = tfi.orderid
$condition
/*get_shoufukuan_shoukuan_list*/
select tor.* 
from t_receive tr,t_order_receive tor
$condition

/*get_shoukuan_order_list*/
SELECT
	tuo.ordersnum,tfi.personcount,tfi.incometotal
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
$condition

/*get_pay_fukuan_list*/
SELECT
	tuo.ordersnum, tpi.*,
	toc.leavecity,
	toc.arrivecity,
	toc.leavetdate,
	tci.name customename,
	tpp.orderPnrStatus status
FROM
	t_pnr_info tpi
INNER JOIN t_pay_pnr tpp ON tpi.id = tpp.pnrId
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
LEFT JOIN t_customer_info tci ON tci.id = tuo.userid
INNER JOIN t_finance_info tfi ON tuo.id = tfi.orderid
$condition

/*get_fukuan_info_list*/
SELECT
	tpi.*, tuo.ordersnum,
	tfi.billingdate,
	tfi.cusgroupnum,
	tci.NAME customename,
	tci.linkMan,
	(
		SELECT
			username
		FROM
			t_user
		WHERE
			id = tfi. ISSUER
	) ISSUER
FROM
	t_pnr_info tpi
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
INNER JOIN t_finance_info tfi ON tuo.id = tfi.orderid
LEFT JOIN t_customer_info tci ON tuo.userid = tci.id
$condition

/*get_kai_invoice_list_order*/
select tuo.* FROM
t_up_order tuo
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_invoice_info tii ON tr.id = tii.receiveid
$condition

/*get_shou_invoice_list_order*/
SELECT
	tpi.*, tii.invoicedate,
	tii.invoiceitem,
	tii.paymentunit,
	tii.id invoiceid,
	(
		SELECT
			count(*)
		FROM
			t_invoice_detail
		WHERE
			invoiceinfoid = tii.id
	) invoicecount,
	tu.userName
FROM
	t_pnr_info tpi
INNER JOIN t_invoice_info tii ON tii.pnrid = tpi.id
LEFT JOIN t_user tu ON tii.billuserid = tu.id
$condition
