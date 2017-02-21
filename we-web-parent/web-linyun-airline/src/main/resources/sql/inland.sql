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