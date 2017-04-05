/******************************************************************************
version : 0.0.X   BEGIN
******************************************************************************/ 
/*航空政策管理增加地区字段*/
ALTER TABLE `t_airlinepolicy`
ADD COLUMN `areaName`  varchar(128) NULL COMMENT '地区' AFTER `companyId`;

ALTER TABLE `t_pay`
ADD COLUMN `orderstatus`  int NULL COMMENT '订单状态' AFTER `status`;

ALTER TABLE `t_receive`
ADD COLUMN `orderstatus`  int NULL COMMENT '订单状态' AFTER `companyid`;

ALTER TABLE `t_invoice_info`
ADD COLUMN `orderpayid`  int NULL COMMENT '订单付款id' AFTER `ordertype`;

ALTER TABLE `t_invoice_info`
ADD COLUMN `orderstatus`  int NULL COMMENT '订单状态' AFTER `orderpayid`;

ALTER TABLE `t_up_order`
ADD COLUMN `airlinecom`  varchar(32) NULL COMMENT '航空公司' AFTER `companyId`,
ADD COLUMN `peoplecount`  int NULL COMMENT '人数' AFTER `airlinecom`,
ADD COLUMN `costsingleprice`  double NULL COMMENT '成本单价' AFTER `peoplecount`;

ALTER TABLE `t_pay_receive_record`
ADD COLUMN `orderstatusid`  int NULL COMMENT '订单状态id' AFTER `orderstatus`;

ALTER TABLE `t_pay_receive_record`
ADD COLUMN `actualyreduce`  int NULL COMMENT '实际减少人数' AFTER `orderstatusid`;

ALTER TABLE `t_order_customneed`
ADD COLUMN `thirdcustomid`  int NULL COMMENT '第三方支付ID' AFTER `paymethod`;