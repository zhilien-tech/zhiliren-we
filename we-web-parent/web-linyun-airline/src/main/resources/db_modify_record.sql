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
