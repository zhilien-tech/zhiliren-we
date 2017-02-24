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