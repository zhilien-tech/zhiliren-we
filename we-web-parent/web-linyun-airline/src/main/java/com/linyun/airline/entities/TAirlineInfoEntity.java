package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_airline_info")
public class TAirlineInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("航空公司代码")
	private String aircom;

	@Column
	@Comment("出发城市")
	private String leavecity;

	@Column
	@Comment("到达城市")
	private String arrvicity;

	@Column
	@Comment("去程日期")
	private Date leavedate;

	@Column
	@Comment("回程日期")
	private Date backdate;

	@Column
	@Comment("原价(单价)")
	private Double formprice;

	@Column
	@Comment("售价(单价)")
	private Double price;

	@Column
	@Comment("结算货币代码")
	private String currencyCode;

	@Column
	@Comment("数量")
	private Integer passengercount;

	@Column
	@Comment("乘客类型")
	private Integer passengertype;

	@Column
	@Comment("机票类型")
	private Integer tickettype;

	@Column
	@Comment("旅程类型")
	private Integer traveltype;

	@Column
	@Comment("需求id")
	private Integer needid;

	@Column
	@Comment("计划id")
	private Integer planid;

	@Column
	@Comment("出发时间")
	private String leavetime;

	@Column
	@Comment("抵达时间")
	private String arrivetime;

	@Column
	@Comment("航班号")
	private String ailinenum;

}