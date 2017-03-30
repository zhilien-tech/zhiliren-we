/******************************************************************************
version : 0.0.X   BEGIN
******************************************************************************/ 
/*航空政策管理增加地区字段*/
ALTER TABLE `t_airlinepolicy`
ADD COLUMN `areaName`  varchar(128) NULL COMMENT '地区' AFTER `companyId`;
