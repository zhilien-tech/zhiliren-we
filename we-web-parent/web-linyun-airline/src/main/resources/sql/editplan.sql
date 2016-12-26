/*get_editplan_info_list*/
select t.*,'123' dingdanhao
from t_plan_info t
$condition

/*select_max_order_num*/
select  max(SUBSTR(ordersnum ,9,5)) maxnum,t.*
from t_up_order  t
$condition