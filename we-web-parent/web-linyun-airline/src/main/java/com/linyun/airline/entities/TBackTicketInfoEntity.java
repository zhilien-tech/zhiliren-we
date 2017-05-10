package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_back_ticket_info")
public class TBackTicketInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("退票人")
	private String visitorname;

	@Column
	@Comment("电话")
	private String telephone;

	@Column
	@Comment("申请时间")
	private Date applydate;

	@Column
	@Comment("金额")
	private Double price;

	@Column
	@Comment("税金")
	private Double tax;

	@Column
	@Comment("退款金额")
	private Double backprice;

	@Column
	@Comment("原因")
	private String reason;

	@Column
	@Comment("退票状态")
	private String backstatus;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("游客ID")
	private String visitorid;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("操作时间")
	private Date optime;
}