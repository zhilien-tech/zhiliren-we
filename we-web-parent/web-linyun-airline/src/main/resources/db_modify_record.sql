
/******************************************************************************
version : 1.0.0   BEGIN
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

ALTER TABLE `t_up_order`
ADD COLUMN `remindTime`  datetime NULL COMMENT '提醒时间' AFTER `costsingleprice`,
ADD COLUMN `remindType`  int NULL COMMENT '提醒类型' AFTER `remindTime`;

ALTER TABLE `t_up_order`
ADD COLUMN `remark`  text NULL COMMENT '备注' AFTER `remindType`;

/*新增登录账号表*/
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     星期三/2017/4/5 下午 3:04:26                      */
/*==============================================================*/


drop table if exists t_login_number;

/*==============================================================*/
/* Table: t_login_number                                        */
/*==============================================================*/
create table t_login_number
(
   id                   int not null auto_increment comment '主键',
   comId                int comment '公司id',
   comTypeCode          varchar(64) comment '字典类别编码',
   comDdictCode         varchar(64) comment '字典代码',
   webURl               varchar(255) comment '网站地址',
   loginNumName         varchar(64) comment '内容',
   airlineName          varchar(128) comment '航空公司',
   status               int comment '状态',
   createTime           datetime comment '创建时间',
   updateTime           datetime comment '修改时间',
   remark               text comment '备注',
   primary key (id)
);

alter table t_login_number comment '登录账号';

alter table t_login_number modify column id int comment '主键';

alter table t_login_number modify column comId int comment '公司id';

alter table t_login_number modify column comTypeCode varchar(64) comment '字典类别编码';

alter table t_login_number modify column comDdictCode varchar(64) comment '字典代码';

alter table t_login_number modify column webURl varchar(255) comment '网站地址';

alter table t_login_number modify column loginNumName varchar(64) comment '内容';

alter table t_login_number modify column airlineName varchar(128) comment '航空公司';

alter table t_login_number modify column status int comment '状态';

alter table t_login_number modify column createTime datetime comment '创建时间';

alter table t_login_number modify column updateTime datetime comment '修改时间';

alter table t_login_number modify column remark text comment '备注';

/*新增第三方支付表*/
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     星期三/2017/4/5 下午 3:06:06                      */
/*==============================================================*/


drop table if exists t_third_payment;

/*==============================================================*/
/* Table: t_third_payment                                       */
/*==============================================================*/
create table t_third_payment
(
   id                   int not null auto_increment comment '主键',
   comId                int comment '公司id',
   userId               int comment '用户id',
   comTypeCode          varchar(64) comment '类别编码',
   comDictCode          varchar(64) comment '字典编码',
   thirdCompanyName     varchar(128) comment '第三方公司名称',
   bankCardName         varchar(128) comment '银行卡名称',
   bankCardNum          varchar(64) comment '银行卡账号',
   status               int comment '状态',
   createTime           datetime comment '创建时间',
   updateTime           datetime comment '更新时间',
   remark               text comment '备注',
   res2                 varchar(64) comment '预留字段2',
   res3                 varchar(64) comment '预留字段3',
   res4                 varchar(64) comment '预留字段4',
   res5                 varchar(64) comment '预留字段5',
   primary key (id)
);

alter table t_third_payment comment '第三方支付';

alter table t_third_payment modify column id int comment '主键';

alter table t_third_payment modify column comId int comment '公司id';

alter table t_third_payment modify column userId int comment '用户id';

alter table t_third_payment modify column comTypeCode varchar(64) comment '类别编码';

alter table t_third_payment modify column comDictCode varchar(64) comment '字典编码';

alter table t_third_payment modify column thirdCompanyName varchar(128) comment '第三方公司名称';

alter table t_third_payment modify column bankCardName varchar(128) comment '银行卡名称';

alter table t_third_payment modify column bankCardNum varchar(64) comment '银行卡账号';

alter table t_third_payment modify column status int comment '状态';

alter table t_third_payment modify column createTime datetime comment '创建时间';

alter table t_third_payment modify column updateTime datetime comment '更新时间';

alter table t_third_payment modify column remark text comment '备注';

alter table t_third_payment modify column res2 varchar(64) comment '预留字段2';

alter table t_third_payment modify column res3 varchar(64) comment '预留字段3';

alter table t_third_payment modify column res4 varchar(64) comment '预留字段4';

alter table t_third_payment modify column res5 varchar(64) comment '预留字段5';

ALTER TABLE `t_finance_info`
ADD COLUMN `enteraircom`  varchar(32) NULL COMMENT '进澳航空公司' AFTER `issuerid`,
ADD COLUMN `outaircom`  varchar(32) NULL COMMENT '出澳航空公司' AFTER `enteraircom`,
ADD COLUMN `enterstarttime`  varchar(16) NULL COMMENT '进澳出发时间' AFTER `outaircom`,
ADD COLUMN `enterarrivetime`  varchar(16) NULL COMMENT '进澳抵达时间' AFTER `enterstarttime`,
ADD COLUMN `outstarttime`  varchar(16) NULL COMMENT '出澳出发时间' AFTER `enterarrivetime`,
ADD COLUMN `outarrivetime`  varchar(16) NULL COMMENT '出澳抵达时间' AFTER `outstarttime`;
/******************************************************************************
version : 1.0.0   END   2017-04-12
******************************************************************************/ 

/******************************************************************************
version : 1.0.1   BEGIN   2017-04-13
******************************************************************************/ 
ALTER TABLE `t_message`
ADD COLUMN `upOrderStatus`  int NULL COMMENT '订单状态' AFTER `upOrderId`;

create table t_inter_message
(
   id                   int not null auto_increment comment '主键id',
   reminddate           datetime comment '提醒时间',
   remindtype           int comment '提醒类型',
   orderstatus          int comment '订单状态',
   orderid              int comment '订单ID',
   primary key (id)
);

alter table t_inter_message comment '国际消息提醒表';

ALTER TABLE `t_receive`
ADD COLUMN `bankcardnameid`  int NULL COMMENT '银行卡id' AFTER `orderstatus`;
/******************************************************************************
version : 1.0.1   END   2017-04-17
******************************************************************************/ 
/******************************************************************************
version : 1.0.2   BEGIN   2017-04-19
******************************************************************************/ 
ALTER TABLE `t_turnover`
ADD COLUMN `historymoney`  double(32,2) NULL DEFAULT NULL COMMENT '卡里历史余额' AFTER `invoiceStatus`;
ALTER TABLE `t_bankcard`
MODIFY COLUMN `initialAmount`  double(32,2) NULL DEFAULT NULL COMMENT '初始金额' AFTER `balance`;
ALTER TABLE `t_turnover`
MODIFY COLUMN `money`  double(32,2) NULL DEFAULT NULL COMMENT '金额' AFTER `averageRate`;
/******************************************************************************
version : 1.0.2   END   2017-04-20
******************************************************************************/ 

/******************************************************************************
version : 1.0.3   BEGIN   2017-04-21
******************************************************************************/
ALTER TABLE `t_visitor_info`
ADD COLUMN `num`  varchar(32) NULL COMMENT '序号' AFTER `pnrid`,
ADD COLUMN `birthday`  varchar(32) NULL COMMENT '出生日期' AFTER `num`,
ADD COLUMN `validuntil`  varchar(32) NULL COMMENT '有效期至' AFTER `birthday`;
/******************************************************************************
version : 1.0.3   END   2017-04-21
******************************************************************************/

/******************************************************************************
version : 1.0.4   BEGIN   2017-04-26
******************************************************************************/
ALTER TABLE `t_mitigate_info`
ADD COLUMN `application`  varchar(32) NULL COMMENT '用途' AFTER `ordertype`;

ALTER TABLE `t_customerneeds`
ADD COLUMN `remark`  text NULL COMMENT '备注' AFTER `companyid`;
/******************************************************************************
version : 1.0.4   END   2017-04-26
******************************************************************************/

/******************************************************************************
version : 1.0.5   BEGIN   2017-05-04
******************************************************************************/
ALTER TABLE `t_pnr_info`
ADD COLUMN `averagerate`  double NULL COMMENT '平均汇率' AFTER `orderid`,
ADD COLUMN `currentrate`  double NULL COMMENT '实时汇率' AFTER `averagerate`,
ADD COLUMN `adultcount`  int NULL COMMENT '成人数' AFTER `currentrate`,
ADD COLUMN `adultcostprice`  double NULL COMMENT '成人成本单价' AFTER `adultcount`,
ADD COLUMN `adultcostpricesum`  double NULL COMMENT '成人成本总价' AFTER `adultcostprice`,
ADD COLUMN `adultsalesprice`  double NULL COMMENT '成人销售总价' AFTER `adultcostpricesum`,
ADD COLUMN `adultsalespricesum`  double NULL COMMENT '成人销售总价' AFTER `adultsalesprice`,
ADD COLUMN `childcount`  int NULL COMMENT '儿童人数' AFTER `adultsalespricesum`,
ADD COLUMN `childcostprice`  double NULL COMMENT '儿童成本单价' AFTER `childcount`,
ADD COLUMN `childcostpricesum`  double NULL COMMENT '儿童成本总价' AFTER `childcostprice`,
ADD COLUMN `childsalesprice`  double NULL COMMENT '儿童销售单价' AFTER `childcostpricesum`,
ADD COLUMN `childsalespricesum`  double NULL COMMENT '儿童销售总价' AFTER `childsalesprice`,
ADD COLUMN `babycount`  int NULL COMMENT '婴儿人数' AFTER `childsalespricesum`,
ADD COLUMN `babycostprice`  double NULL COMMENT '婴儿成本单价' AFTER `babycount`,
ADD COLUMN `babycostpricesum`  double NULL COMMENT '婴儿成本总价' AFTER `babycostprice`,
ADD COLUMN `babysalesprice`  double NULL COMMENT '婴儿销售单价' AFTER `babycostpricesum`,
ADD COLUMN `babysalespricesum`  double NULL COMMENT '婴儿销售总价' AFTER `babysalesprice`,
ADD COLUMN `costpricesumrmb`  double NULL COMMENT '成本RMB总价' AFTER `babysalespricesum`,
ADD COLUMN `salespricesumrmb`  double NULL COMMENT '销售RMB总价' AFTER `costpricesumrmb`;

ALTER TABLE `t_finance_info`
ADD COLUMN `enterleavecity`  varchar(16) NULL COMMENT '进澳出发城市' AFTER `outarrivetime`,
ADD COLUMN `enterarrivecity`  varchar(16) NULL COMMENT '进澳抵达城市' AFTER `enterleavecity`,
ADD COLUMN `outleavecity`  varchar(255) NULL COMMENT '出澳出发城市' AFTER `enterarrivecity`,
ADD COLUMN `outarrivecity`  varchar(255) NULL COMMENT '出澳抵达城市' AFTER `outleavecity`;

ALTER TABLE `t_pay_receive_record`
MODIFY COLUMN `prepayratio`  varchar(32) NULL DEFAULT NULL COMMENT '预付款比例' AFTER `costprice`,
ADD COLUMN `inputtype`  int NULL COMMENT '输入类型' AFTER `actualyreduce`;

ALTER TABLE `t_visitor_info`
ADD COLUMN `remark`  varchar(256) NULL COMMENT '备注' AFTER `validuntil`;

create table t_back_ticket_info
(
   id                   int not null auto_increment comment '主键id',
   visitorname          varchar(32) comment '退票人',
   telephone            varchar(16) comment '电话',
   applydate            date comment '申请日期',
   price                double comment '金额',
   tax                  double comment '税金',
   backprice            double comment '退款金额',
   reason               varchar(32) comment '原因',
   backstatus           varchar(32) comment '退票状态',
   remark               varchar(256) comment '备注',
   visitorid            int comment '游客id',
   opid                 int comment '操作人',
   optime               datetime comment '操作时间',
   primary key (id)
);

alter table t_back_ticket_info comment '退票表';

ALTER TABLE `t_order_customneed`
ADD COLUMN `neilu`  varchar(32) NULL COMMENT '内陆跨海' AFTER `thirdcustomid`;
/******************************************************************************
version : 1.0.5   END   2017-05-04
******************************************************************************/


/******************************************************************************
version : 1.0.6   BEGIN   2017-05-10
******************************************************************************/
/*客户管理 出发城市手动输入*/
ALTER TABLE `t_customer_info`
ADD COLUMN `outCityName`  varchar(255) NULL COMMENT '出发城市' AFTER `address`;
/*客户管理 电话位数修改*/
ALTER TABLE `t_customer_info`
MODIFY COLUMN `telephone`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话' AFTER `linkMan`;


create table t_back_ticket_file
(
   id                   int not null auto_increment comment '主键ID',
   filename             varchar(1024) comment '文件名',
   fileurl              varchar(1024) comment '文件路径',
   backticketid         int comment '退票ID',
   opid                 int comment '操作人',
   optime               datetime comment '操作时间',
   primary key (id)
);

alter table t_back_ticket_file comment '退票附件表';
/*国际查询PNR的function*/
DROP FUNCTION IF EXISTS `getInterPnrByOrderid`;

CREATE FUNCTION `getInterPnrByOrderid`(orderid int)
 RETURNS varchar(1024) CHARSET utf8
BEGIN
	declare tmpName varchar(50) default '';
  
	DECLARE result VARCHAR(1024) DEFAULT '';
	# 遍历数据结束标志
  DECLARE done INT DEFAULT 0;
	#Routine body goes here...
	DECLARE pnr_cur CURSOR FOR SELECT tpi.pnr pnr from t_pnr_info tpi INNER JOIN t_up_order tuo ON tpi.orderid = tuo.id where tuo.id = orderid;
	
	# 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN pnr_cur;
		 cursor_loop:LOOP
				FETCH pnr_cur INTO tmpName;

				IF done=1 THEN
					LEAVE cursor_loop;
				END IF;

			 SET tmpName = CONCAT(tmpName ,'のし') ;
			 SET result = CONCAT(result ,tmpName) ;

		 END LOOP cursor_loop;
 CLOSE pnr_cur;
	  
	RETURN result;
END;
/*内陆查询PNR的function*/
DROP FUNCTION IF EXISTS `getInlandPnrByOrderid`;

CREATE FUNCTION `getInlandPnrByOrderid`(orderid int)
 RETURNS varchar(1024) CHARSET utf8
BEGIN
	declare tmpName varchar(50) default '';
  
	DECLARE result VARCHAR(1024) DEFAULT '';
	# 遍历数据结束标志
  DECLARE done INT DEFAULT 0;
	#Routine body goes here...
	DECLARE pnr_cur CURSOR FOR SELECT
									tpi.pnr pnr
								FROM
									t_pnr_info tpi
								INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
								INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id 
								where tuo.id = orderid;
	
	# 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN pnr_cur;
		 cursor_loop:LOOP
				FETCH pnr_cur INTO tmpName;

				IF done=1 THEN
					LEAVE cursor_loop; 
				END IF;

			 SET tmpName = CONCAT(tmpName ,'のし') ;
			 SET result = CONCAT(result ,tmpName) ;

		 END LOOP cursor_loop;
 CLOSE pnr_cur;
	  
	RETURN result;
END;

DROP FUNCTION IF EXISTS `getOrderNumByReceiveid`;

CREATE  FUNCTION `getOrderNumByReceiveid`(receive int)
 RETURNS varchar(1024) CHARSET utf8
BEGIN
	declare tmpName varchar(50) default '';
  
	DECLARE result VARCHAR(1024) DEFAULT '';
	# 遍历数据结束标志
  DECLARE done INT DEFAULT 0;
	#Routine body goes here...
	DECLARE pnr_cur CURSOR FOR select ordersnum from  t_up_order where id in(select orderid from t_order_receive where receiveid = receive);
	
	# 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN pnr_cur;
		 cursor_loop:LOOP
				FETCH pnr_cur INTO tmpName;

				IF done=1 THEN
					LEAVE cursor_loop; 
				END IF;

			 SET tmpName = CONCAT(tmpName ,'のし') ;
			 SET result = CONCAT(result ,tmpName) ;

		 END LOOP cursor_loop;
 CLOSE pnr_cur;
	  
	RETURN result;
END;


DROP FUNCTION IF EXISTS `getPNRByReceiveid`;

CREATE FUNCTION `getPNRByReceiveid`(receive int)
 RETURNS varchar(1024) CHARSET utf8
BEGIN
	declare tmpName varchar(50) default '';
  
	DECLARE result VARCHAR(1024) DEFAULT '';
	# 遍历数据结束标志
  DECLARE done INT DEFAULT 0;
	#Routine body goes here... 
	DECLARE pnr_cur CURSOR FOR SELECT
									tpi.pnr pnr
								FROM
									t_pnr_info tpi
								INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
								INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
								WHERE
									tuo.id IN (
										SELECT
											tor.orderid
										FROM
											t_order_receive tor
										WHERE
											tor.receiveid = receive
									);
	
	# 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN pnr_cur;
		 cursor_loop:LOOP
				FETCH pnr_cur INTO tmpName;

				IF done=1 THEN
					LEAVE cursor_loop; 
				END IF;

			 SET tmpName = CONCAT(tmpName ,'のし') ;
			 SET result = CONCAT(result ,tmpName) ;

		 END LOOP cursor_loop;
 CLOSE pnr_cur;
	  
	RETURN result;
END;

DROP FUNCTION IF EXISTS `getInterPNRByReceiveid`;

CREATE  FUNCTION `getInterPNRByReceiveid`(receive int)
 RETURNS varchar(1024) CHARSET utf8
BEGIN
	declare tmpName varchar(50) default '';
  
	DECLARE result VARCHAR(1024) DEFAULT '';
	# 遍历数据结束标志
  DECLARE done INT DEFAULT 0;
	#Routine body goes here...  
	DECLARE pnr_cur CURSOR FOR select pnr from t_pnr_info where orderid in (select orderid from t_order_receive where receiveid = receive);
	
	# 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN pnr_cur;
		 cursor_loop:LOOP
				FETCH pnr_cur INTO tmpName;

				IF done=1 THEN
					LEAVE cursor_loop; 
				END IF;

			 SET tmpName = CONCAT(tmpName ,'のし') ;
			 SET result = CONCAT(result ,tmpName) ;

		 END LOOP cursor_loop;
 CLOSE pnr_cur;
	  
	RETURN result;
END;

DROP FUNCTION IF EXISTS `getByInvoicenumQuery`;

CREATE FUNCTION `getByInvoicenumQuery`(invoiceId int)
 RETURNS varchar(1024) CHARSET utf8
BEGIN
	declare tmpName varchar(50) default '';
  
	DECLARE result VARCHAR(1024) DEFAULT '';
	# 遍历数据结束标志
  DECLARE done INT DEFAULT 0; 
	#Routine body goes here...
	DECLARE pnr_cur CURSOR FOR SELECT 
																idd.invoicenum invoicenum
															FROM
																t_invoice_detail idd
															where idd.invoiceinfoid = invoiceId;
	
	# 将结束标志绑定到游标 
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN pnr_cur;
		 cursor_loop:LOOP
				FETCH pnr_cur INTO tmpName;

				IF done=1 THEN
					LEAVE cursor_loop; 
				END IF;

			 SET tmpName = CONCAT(tmpName ,'のし') ;
			 SET result = CONCAT(result ,tmpName) ;

		 END LOOP cursor_loop;
 CLOSE pnr_cur;
	  
	RETURN result;
END;

/*根据订单id查询最早出发日期*/
DROP FUNCTION IF EXISTS `getMinLeavedateByOrderid`;

CREATE FUNCTION `getMinLeavedateByOrderid`(orderid int)
 RETURNS date
BEGIN
	declare tmpDate DATE;
	
	SELECT
	min(tai.leavedate) 
	into tmpDate
	FROM
		t_airline_info tai
	INNER JOIN t_pnr_info tpi ON tai.pnrid = tpi.id
	INNER JOIN t_order_receive tor ON tpi.orderid = tor.orderid
	where tor.receiveid = orderid
	GROUP BY
		tpi.orderid;

	RETURN tmpDate;
END;

/*根据订单id查询最晚出发日期*/
DROP FUNCTION IF EXISTS `getMaxLeavedateByOrderid`;

CREATE FUNCTION `getMaxLeavedateByOrderid`(orderid int)
 RETURNS date
BEGIN
	declare tmpDate DATE;
	
	SELECT
	max(tai.leavedate) 
	into tmpDate 
	FROM
		t_airline_info tai
	INNER JOIN t_pnr_info tpi ON tai.pnrid = tpi.id
	INNER JOIN t_order_receive tor ON tpi.orderid = tor.orderid
	where tor.receiveid = orderid
	GROUP BY
		tpi.orderid;

	RETURN tmpDate;
END;

/*******************发票表中增加借发票字段,发票明细表中增加税控金额字段*******************************/
ALTER TABLE `t_invoice_info`
ADD COLUMN `borrowInvoice`  int(11) NULL COMMENT '借发票' AFTER `orderstatus`;

ALTER TABLE `t_invoice_detail`
ADD COLUMN `fiscalAmount`  double(64,2) NULL COMMENT '税控金额' AFTER `imagename`;

ALTER TABLE `t_airline_info`
ADD COLUMN `peoplescount`  int NULL COMMENT '人数' AFTER `pnrid`;

ALTER TABLE `t_pay`
ADD COLUMN `openbank`  varchar(128) NULL COMMENT '开户银行' AFTER `orderstatus`,
ADD COLUMN `openname`  varchar(128) NULL COMMENT '开户名称' AFTER `openbank`,
ADD COLUMN `opennumber`  varchar(128) NULL COMMENT '开户账号' AFTER `openname`;


/*********************** 客户管理付款方式 ***************************/
ALTER TABLE `t_customer_info`
MODIFY COLUMN `payWay`  varchar(32) NULL DEFAULT NULL COMMENT '付款方式（现金、支票、银行汇款、第三方、其他）' AFTER `travelType`;

ALTER TABLE `t_customer_info`
MODIFY COLUMN `responsibleId`  int(32) NULL DEFAULT NULL COMMENT '负责人' AFTER `inlandLine`,
MODIFY COLUMN `appendix`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '附件管理' AFTER `responsibleId`,
MODIFY COLUMN `createTime`  datetime NULL DEFAULT NULL COMMENT '添加时间' AFTER `appendix`,
MODIFY COLUMN `appendixName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称' AFTER `createTime`,
MODIFY COLUMN `departureCity`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出发城市' AFTER `appendixName`,
MODIFY COLUMN `travelType`  int(11) NULL DEFAULT NULL COMMENT '旅行社类型' AFTER `departureCity`,
MODIFY COLUMN `payWay`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款方式（现金、支票、银行汇款、第三方、其他）' AFTER `travelType`;

ALTER TABLE `t_customer_info`
MODIFY COLUMN `paywayName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款方式名称' AFTER `payWay`;
/******************************************************************************
version : 1.0.6   END   2017-05-18
******************************************************************************/

/******************************************************************************
version : 1.0.7   BEGIN(cuijianbin)   2017-05-19
******************************************************************************/
ALTER TABLE `t_grab_report`
MODIFY COLUMN `pnrInfoId`  int(11) NULL DEFAULT NULL COMMENT 'pnrInfoId' AFTER `id`,
ADD COLUMN `dictInfoId`  int NULL COMMENT '字典信息id' AFTER `taxRebate`;

ALTER TABLE `t_grab_report`
ADD COLUMN `currency`  varchar(32) NULL COMMENT '币种' AFTER `paidUnitPrice`;

ALTER TABLE `t_grab_report`
ADD COLUMN `total`  double(32,2) NULL COMMENT '总计' AFTER `paidUnitPrice`;

ALTER TABLE `t_grab_report`
ADD COLUMN `exciseTax2`  double(32,2) NULL COMMENT '消费税2' AFTER `total`;

ALTER TABLE `t_grab_report`
ADD COLUMN `realTicketPrice`  double(32,2) NULL COMMENT '实收票价' AFTER `exciseTax2`;

ALTER TABLE `t_grab_report`
ADD COLUMN `pnrRelationId`  int NULL COMMENT 'pnr系统关联id' AFTER `realTicketPrice`;

ALTER TABLE `t_grab_file`
MODIFY COLUMN `fileSize`  double(128,2) NULL DEFAULT NULL COMMENT '文件大小' AFTER `url`;

ALTER TABLE `t_grab_file`
CHANGE COLUMN `folderName` `customnum`  int(64) NULL DEFAULT NULL COMMENT '客户团号计数' AFTER `parentId`;

/*(penghui)*/
ALTER TABLE `t_customer_info`
MODIFY COLUMN `fax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真' AFTER `shortName`,
MODIFY COLUMN `siteUrl`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网址' AFTER `fax`,
MODIFY COLUMN `address`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址' AFTER `siteUrl`,
MODIFY COLUMN `outCityName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出发城市' AFTER `address`,
MODIFY COLUMN `interLine`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国际' AFTER `outCityName`,
MODIFY COLUMN `inlandLine`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国境内陆' AFTER `interLine`,
MODIFY COLUMN `responsibleId`  int(32) NULL DEFAULT NULL COMMENT '负责人' AFTER `inlandLine`,
MODIFY COLUMN `appendix`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '附件管理' AFTER `responsibleId`,
MODIFY COLUMN `createTime`  datetime NULL DEFAULT NULL COMMENT '添加时间' AFTER `appendix`,
MODIFY COLUMN `appendixName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称' AFTER `createTime`,
MODIFY COLUMN `departureCity`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出发城市' AFTER `appendixName`,
MODIFY COLUMN `travelType`  int(11) NULL DEFAULT NULL COMMENT '旅行社类型' AFTER `departureCity`,
MODIFY COLUMN `payWay`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款方式（现金、支票、银行汇款、第三方、其他）' AFTER `travelType`,
MODIFY COLUMN `paywayName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款方式名称' AFTER `payWay`,
MODIFY COLUMN `invoice`  int(11) NULL DEFAULT NULL COMMENT '是否提供发票（0：否   1：是）' AFTER `paywayName`,
MODIFY COLUMN `sellPrice`  double NULL DEFAULT NULL COMMENT '销售价' AFTER `invoice`,
MODIFY COLUMN `moneyUnit`  varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货币单位' AFTER `sellPrice`,
MODIFY COLUMN `payType`  int(11) NULL DEFAULT NULL COMMENT '结算形式（月结、周结、单结、其他）' AFTER `moneyUnit`,
MODIFY COLUMN `paytypeName`  varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他结算方式' AFTER `payType`,
MODIFY COLUMN `cooperateTime`  datetime NULL DEFAULT NULL COMMENT '合作时间' AFTER `paytypeName`,
MODIFY COLUMN `cooperateDueTime`  datetime NULL DEFAULT NULL COMMENT '合作到期时间' AFTER `cooperateTime`,
MODIFY COLUMN `contractTime`  datetime NULL DEFAULT NULL COMMENT '签约时间' AFTER `cooperateDueTime`,
MODIFY COLUMN `contractDueTime`  datetime NULL DEFAULT NULL COMMENT '签约到期时间' AFTER `contractTime`,
MODIFY COLUMN `contract`  int(11) NULL DEFAULT NULL COMMENT '是否签约（未签约、已签约、禁止合作）' AFTER `contractDueTime`,
MODIFY COLUMN `forbid`  int(11) NULL DEFAULT NULL COMMENT '是否禁用' AFTER `contract`,
MODIFY COLUMN `business`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务范围' AFTER `forbid`,
MODIFY COLUMN `creditLine`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信用额度' AFTER `business`,
MODIFY COLUMN `discountFare`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '票价折扣' AFTER `creditLine`,
MODIFY COLUMN `arrears`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已欠款' AFTER `discountFare`,
MODIFY COLUMN `preDeposit`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预存款' AFTER `arrears`,
MODIFY COLUMN `fees`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手续费' AFTER `preDeposit`,
MODIFY COLUMN `exchangeRates`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '汇率' AFTER `fees`,
MODIFY COLUMN `taxRefund`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退税' AFTER `exchangeRates`,
ADD COLUMN `comPhone`  varchar(255) NULL COMMENT '公司电话' AFTER `shortName`;

ALTER TABLE `t_customer_info`
ADD COLUMN `manBankInfo`  varchar(255) NULL COMMENT '联系人账户名称' AFTER `telephone`,
ADD COLUMN `manBankName`  varchar(255) NULL COMMENT '联系人银行名称' AFTER `manBankInfo`,
ADD COLUMN `manBankNum`  varchar(64) NULL COMMENT '联系人银行卡号' AFTER `manBankName`,
ADD COLUMN `manWeChat`  varchar(255) NULL COMMENT '微信号码' AFTER `manBankNum`,
ADD COLUMN `manQQ`  varchar(255) NULL COMMENT '联系人QQ号' AFTER `manWeChat`,
ADD COLUMN `manEmail`  varchar(255) NULL COMMENT 'E-Mail' AFTER `manQQ`,
ADD COLUMN `manRemark`  varchar(1024) NULL COMMENT '联系人备注' AFTER `manQQ`,
ADD COLUMN `compTaxNum`  varchar(255) NULL COMMENT '纳税人识别号' AFTER `manRemark`,
ADD COLUMN `compBank`  varchar(255) NULL COMMENT '公司开户银行' AFTER `compTaxNum`,
ADD COLUMN `compBankNum`  varchar(255) NULL COMMENT '公司开户账号' AFTER `compBank`,
ADD COLUMN `compBankCode`  varchar(255) NULL COMMENT '公司开户行号' AFTER `compBankNum`;


