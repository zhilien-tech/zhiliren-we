/*turnoverlist_list*/
select tu.*,tb.balance,tb.currency as curren,DATE_FORMAT(tradeDate, '%Y-%c-%d') as modifyDate
from t_turnover tu 
left join t_bankcard tb
on tu.bankCardId=tb.id
$condition
order by updateTime DESC