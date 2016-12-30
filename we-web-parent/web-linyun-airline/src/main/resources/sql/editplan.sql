/*get_editplan_info_list*/
SELECT
	t.*, ifnull(tu.ordersnum,'无订单号') dingdanhao
FROM
	t_plan_info t
left JOIN t_up_order_ticket tuo ON t.id = tuo.ticketid
left JOIN t_up_order tu ON tuo.orderid = tu.id
$condition

/*select_max_order_num*/
select  max(SUBSTR(ordersnum ,9,5)) maxnum,t.*
from t_up_order  t
$condition