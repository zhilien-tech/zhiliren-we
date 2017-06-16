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
,psm.id,uo.id as 'orderId'
from t_pnr_system_map psm
LEFT JOIN t_up_order uo on uo.id=psm.orderId
LEFT JOIN t_finance_info fi on fi.id=psm.financeId
LEFT JOIN t_pay_receive_record prr on prr.id=psm.payReceiveRecordId
LEFT JOIN t_pnr_info pi on pi.id=psm.pnrId
LEFT JOIN t_grab_file gf on gf.id=psm.grabFileId
$condition

/*grab_report_listPnrSystemMap_data*/
SELECT
	psm.id,
	uo.ordersnum,
	f.cusgroupnum,
	p.PNR,
	psm.fileName,
	p.peoplecount,
	p.costprice,
	f.enterstarttime,
	f.outstarttime,
	uo.ordersstatus,
	uo.orderstype,
	psm.relationStatus,
	psm.orderId,
	psm.pnrId,
	psm.financeId,
	psm.grabFileId,
	psm.payReceiveRecordId,
	psm.airCode,
	psm.createTime,
	psm.updateTime,
	psm.remark,
	psm.type,
	psm.def4,
	psm.def5
FROM
	t_pnr_system_map psm
LEFT JOIN t_up_order uo ON uo.id = psm.orderId
LEFT JOIN t_finance_info f ON f.orderid = uo.id
LEFT JOIN t_pnr_info p ON psm.pnrId = p.id
LEFT JOIN t_grab_file gf ON gf.id = psm.grabFileId
$condition
/*grab_report_listPnrSystemMap_team_data*/
SELECT
	uo.ordersnum,
	f.cusgroupnum,
	p.PNR,
	rr.actualnumber,
	p.costprice,
	uo.interOrderStatus,
	psm.id,
	psm.orderId,
	psm.pnrId,
	psm.financeId,
	psm.grabFileId,
	psm.payReceiveRecordId,
	psm.fileName,
	psm.airCode,
	psm.relationStatus,
	psm.createTime,
	psm.updateTime,
	psm.remark,
	psm.type,
	psm.def4,
	psm.def5
FROM
	t_pnr_system_map psm
LEFT JOIN t_pnr_info p ON psm.pnrId = p.id
LEFT JOIN t_up_order uo ON uo.id = p.orderid
LEFT JOIN t_finance_info f ON f.orderid = uo.id
LEFT JOIN t_pay_receive_record rr ON rr.orderid = uo.id
LEFT JOIN t_grab_file gf ON psm.grabFileId = gf.id
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

/*grab_report_currency_data*/
SELECT
	dt.id,
	dt.typeCode,
	dt.dictCode,
	dt.dictName,
	dt.description,
	dt.`status`,
	dt.quanPin,
	dt.jianpin,
	dt.createTime
FROM
	dict_info dt
LEFT JOIN t_grab_report gb ON dt.id = gb.dictInfoId
$condition
/*grab_report_delete_empty*/
delete FROM `t_grab_file`
where fileSize=0.00 and id not in (1,2,3,4,5,6,7,8,9); 