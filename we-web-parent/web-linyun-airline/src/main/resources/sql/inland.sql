/*get_inland_listdata*/
SELECT
	tuo.*, tfi.receivable,
	toc.id  customsid,
	tfi.personcount,
	tci.linkMan userName,
	tci.telephone
FROM
	t_up_order tuo
INNER JOIN t_order_customneed toc ON tuo.id = toc.ordernum
left JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_customer_info tci ON tuo.userid = tci.id
$condition