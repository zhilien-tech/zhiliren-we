package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_pnr_info")
public class TPnrInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("PNR")
	private String pNR;

	@Column
	@Comment("成本单价")
	private Double costprice;

	@Column
	@Comment("成本总价")
	private Double costpricesum;

	@Column
	@Comment("销售单价")
	private Double salesprice;

	@Column
	@Comment("销售总价")
	private Double salespricesum;

	@Column
	@Comment("人数")
	private Integer peoplecount;

	@Column
	@Comment("登陆账号")
	private String loginid;

	@Column
	@Comment("币种")
	private String currency;

	@Column
	@Comment("航班号信息id")
	private Integer airinfoid;

	@Column
	@Comment("客户需求id")
	private Integer needid;

	@Column
	@Comment("订单pnr状态")
	private Integer orderPnrStatus;

	@Column
	@Comment("操作人")
	private Integer userid;

	@Column
	@Comment("操作时间")
	private Date optime;

	@Column
	@Comment("是否主航段")
	private Integer mainsection;

	@Column
	@Comment("订单id（国际用）")
	private Integer orderid;

	@Column
	@Comment("平均汇率")
	private Double averagerate;

	@Column
	@Comment("实时汇率")
	private Integer currentrate;

	@Column
	@Comment("成人数")
	private Integer adultcount;

	@Column
	@Comment("成人成本单价")
	private Double adultcostprice;

	@Column
	@Comment("成人成本总价")
	private Double adultcostpricesum;

	@Column
	@Comment("成人销售单价")
	private Double adultsalesprice;

	@Column
	@Comment("成人销售总价")
	private Double adultsalespricesum;

	@Column
	@Comment("儿童人数")
	private Integer childcount;

	@Column
	@Comment("儿童成本单价")
	private Double childcostprice;

	@Column
	@Comment("儿童成本总价")
	private Double childcostpricesum;

	@Column
	@Comment("儿童销售单价")
	private Double childsalesprice;

	@Column
	@Comment("儿童销售总价")
	private Double childsalespricesum;

	@Column
	@Comment("婴儿人数")
	private Integer babycount;

	@Column
	@Comment("婴儿成本单价")
	private Double babycostprice;

	@Column
	@Comment("婴儿成本总价")
	private Double babycostpricesum;

	@Column
	@Comment("婴儿销售单价")
	private Double babysalesprice;

	@Column
	@Comment("婴儿销售总价")
	private Double babysalespricesum;

	@Column
	@Comment("成本RMB总价")
	private Double costpricesumrmb;

	@Column
	@Comment("销售RMB总价")
	private Double salespricesumrmb;

}