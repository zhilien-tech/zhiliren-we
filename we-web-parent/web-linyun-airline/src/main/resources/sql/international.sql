/*get_international_list_sql*/
SELECT
	tpi.*, tuo.ordersnum,
	tuo.ordersstatus,
	tor.orderstatus receiveorderstatus
FROM
	t_up_order tuo
INNER JOIN t_plan_info tpi ON tpi.ordernumber = tuo.id
LEFT JOIN t_order_receive tor ON tor.orderid = tuo.id
AND tor.orderstatus = @ordersstatus
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
	tpo.*, tuo.ordersnum,
	tpi.peoplecount,
	tii.remark,
	tii.id invoiceid,
	tfi.costtotal
FROM
	t_pay_order tpo
INNER JOIN t_up_order tuo ON tpo.orderid = tuo.id
INNER JOIN t_pay tp ON tpo.payid = tp.id
INNER JOIN t_plan_info tpi ON tuo.id = tpi.ordernumber
LEFT JOIN t_finance_info tfi ON tfi.orderid = tuo.id
LEFT JOIN t_invoice_info tii ON tpo.id = tii.orderpayid
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

/*get_inter_shou_invoice_list_order*/
select tuo.* FROM
t_up_order tuo
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_invoice_info tii ON tr.id = tii.receiveid
$condition

/*get_international_invoice_list*/
SELECT
	tii.*, tuo.ordersnum
FROM
	t_invoice_info tii
INNER JOIN t_pay_order tpo ON tii.orderpayid = tpo.id
INNER JOIN t_up_order tuo ON tpo.orderid = tuo.id
$condition

