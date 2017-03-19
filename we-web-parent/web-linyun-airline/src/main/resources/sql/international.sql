/*get_international_list_sql*/
SELECT
tpi.*, tuo.ordersnum,
 tuo.ordersstatus
FROM
	t_up_order tuo
INNER JOIN t_plan_info tpi ON tpi.ordernumber = tuo.id
$condition


/*get_international_receive_list*/
SELECT
	tr.*, tii.id invoiceid,
	tu.userName
FROM
	t_receive tr
LEFT JOIN t_invoice_info tii ON tr.id = tii.receiveid
INNER JOIN t_user tu ON tr.userid = tu.id
$condition

/*get_international_receive_list_order*/
SELECT
	tuo.ordersnum,tfi.personcount,tfi.incometotal,tpi.*
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_plan_info  tpi ON tuo.id = tpi.ordernumber
$condition

/*get_international_pay_list*/
SELECT
	tp.*, tii.id invoiceid,
	tu.userName
FROM
	t_pay tp
LEFT JOIN t_invoice_info tii ON tp.id = tii.receiveid
INNER JOIN t_user tu ON tp.proposer = tu.id
$condition

/*get_international_pay_list_order*/
SELECT
	tuo.ordersnum,tfi.personcount,tfi.incometotal,tpi.*
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_pay_order tpo ON tuo.id = tpo.orderid
INNER JOIN t_pay tp ON tpo.payid = tp.id
INNER JOIN t_plan_info  tpi ON tuo.id = tpi.ordernumber
$condition