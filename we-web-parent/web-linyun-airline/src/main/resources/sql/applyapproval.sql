/*applyapproval_list*/
select * from(
select p.*,pi.orderPnrStatus,uo.orderstype,ci.`name`,uj.userid,c.id as 'companyId',uo.ordersnum,pi.id as 'usingId'
from t_pay p
LEFT JOIN t_pay_pnr pp ON pp.payId = p.id
LEFT JOIN t_pnr_info pi ON pi.id = pp.pnrId
LEFT JOIN t_order_customneed oc ON oc.id = pi.needid
LEFT JOIN t_up_order uo ON uo.id = oc.ordernum
LEFT JOIN t_customer_info ci ON ci.id=uo.userid


LEFT JOIN t_user_job uj on uj.userid=uo.loginUserId
LEFT JOIN t_company_job cj on cj.id=uj.companyJobId
left join t_company c on c.id=cj.comId ) temp


$condition
order by payDate desc





