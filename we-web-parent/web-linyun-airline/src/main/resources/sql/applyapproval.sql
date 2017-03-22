/*applyapproval_list*/
select * from(
select uo.orderstype,ti.id,ti.PNR,ti.orderPnrStatus,ti.costpricesum,uo.ordersnum,ti.optime,p.purpose,

p.proposer,ci.shortName,p.fundType,p.payFees,p.payCurrency,p.isInvioce,p.approveTime,p.approveResult,p.id as 'usingId',uo.id as 'orderId',
u.userName,(select dictCode from dict_info where id=p.payCurrency) as 'currencyStr',(select dictName from dict_info where id=p.purpose) as 'purposeStr',
(select dictName from dict_info where id=p.fundType) as 'fundTypeStr',p.companyId

FROM t_pnr_info  ti
LEFT JOIN t_order_customneed oc on ti.needid=oc.id
LEFT JOIN t_up_order uo on oc.ordernum =uo.id
LEFT JOIN t_pay_pnr pp on pp.pnrId=ti.id
LEFT JOIN t_pay p on p.id = pp.payId
LEFT JOIN t_customer_info ci on ci.id=uo.userid
LEFT JOIN t_user u on u.id=p.proposer
) temp
$condition
order by optime desc
/*applyapproval_list_international*/
select * from(
select  uo.orderstype,uo.ordersnum,p.purpose,
p.proposer,ci.shortName,p.fundType,p.payFees,p.payCurrency,p.isInvioce,p.approveTime,p.approveResult,p.id as 'usingId',uo.id,
u.userName,(select dictCode from dict_info where id=p.payCurrency) as 'currencyStr',(select dictName from dict_info where id=p.purpose) as 'purposeStr',
(select dictName from dict_info where id=p.fundType) as 'fundTypeStr',p.companyId,uo.paystatus,uo.amount,uo.orderstime
from 
t_up_order uo
LEFT JOIN t_pay_order po on po.orderid=uo.id
LEFT JOIN t_pay p on p.id=po.payid
LEFT JOIN t_customer_info ci on ci.id=uo.userid
LEFT JOIN t_user u on u.id=p.proposer
) temp1
$condition
ORDER BY orderstime desc

/*applyapproval_message_reminder*/
select uo.id,uo.ordersnum
from 
t_pnr_info pi 
LEFT JOIN t_order_customneed oc on oc.id =pi.needid
LEFT JOIN t_up_order uo on uo.id=oc.ordernum
$condition
/*applyapproval_reduce_inland*/
select mi.*,u.userName,uo.ordersnum
from 
t_mitigate_info mi 
LEFT JOIN t_up_order uo on mi.orderid=uo.id
LEFT JOIN t_user u on mi.applyid=u.id
$condition 






