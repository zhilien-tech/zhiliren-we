/*bankcardmanager_find_money*/
select bc.*
from t_bankcard bc 

$condition
order by bc.updateTime desc  