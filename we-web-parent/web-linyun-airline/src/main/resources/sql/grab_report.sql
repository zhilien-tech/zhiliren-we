/*grab_report_list*/
SELECT
	oc.id,
	oc.aircom,
	oc.leavecity,
	oc.arrivecity,
	oc.leavetdate,
	oc.backdate,
	oc.originalprice,
	oc.price,
	oc.currencyCode,
	oc.peoplecount,
	oc.passengertype,
	oc.tickettype,
	oc.rengetype,
	oc.ordernum,
	oc.remark,
	oc.realtimexrate,
	oc.avgexrate,
	oc.paycurrency,
	oc.paymethod,
	p.costprice,
	a.price
FROM
	t_order_customneed oc
LEFT JOIN t_pnr_info p ON oc.id = p.needid
LEFT JOIN t_airline_info a ON oc.id = a.needid
WHERE
	p.PNR = @pnr
/*grab_report_addPnrSystemMap*/
select uo.id as 'orderId',fi.id as 'financeId',prr.id as 'payReceiveRecordId',pi.id as 'pnrId',pi.PNR,uo.orderstype
FROM t_pnr_info pi
LEFT JOIN t_order_customneed oc on oc.id=pi.needid
LEFT JOIN t_up_order uo ON oc.ordernum =uo.id
LEFT JOIN t_finance_info fi on fi.orderid=uo.id
LEFT JOIN t_pay_receive_record prr on prr.orderid=uo.id
$condition
/*grab_report_findPnrSystemMap*/
SELECT pi.PNR,uo.ordersnum,fi.cusgroupnum,fi.personcount,pi.costprice,prr.orderstatus,fi.outausdate,fi.enterausdate,psm.relationStatus,psm.airCode,psm.fileName
from t_pnr_system_map psm
LEFT JOIN t_up_order uo on uo.id=psm.orderId
LEFT JOIN t_finance_info fi on fi.id=psm.financeId
LEFT JOIN t_pay_receive_record prr on prr.id=psm.payReceiveRecordId
LEFT JOIN t_pnr_info pi on pi.id=psm.pnrId
LEFT JOIN t_grab_file gf on gf.id=psm.grabFileId
$condition
/*grab_report_addPnrSystemMap_Inter*/

SELECT
	uo.id AS 'orderId',
	fi.id AS 'financeId',
	prr.id AS 'payReceiveRecordId',
	pi.id AS 'pnrId',
	pi.PNR,
	uo.orderstype
FROM
	t_pnr_info pi
LEFT JOIN t_up_order uo ON pi.orderid = uo.id
LEFT JOIN t_finance_info fi ON fi.orderid = uo.id
LEFT JOIN t_pay_receive_record prr ON prr.orderid = uo.id
$condition


