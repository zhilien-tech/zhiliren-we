/*get_international_list_sql*/
SELECT
tpi.*, tuo.ordersnum,
 tuo.ordersstatus
FROM
	t_up_order tuo
INNER JOIN t_plan_info tpi ON tpi.ordernumber = tuo.id
$condition