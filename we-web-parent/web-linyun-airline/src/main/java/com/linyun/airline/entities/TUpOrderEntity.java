package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_up_order")
public class TUpOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单号")
	private String ordersnum;

	@Column
	@Comment("上游公司客户信息")
	private long customid;

	@Column
	@Comment("订单金额")
	private Double amount;

	@Column
	@Comment("下单时间")
	private Date orderstime;

	@Column
	@Comment("订单类型")
	private Integer orderstype;

	@Column
	@Comment("负责人用户ID")
	private Integer userid;

	@Column
	@Comment("结算货币代码")
	private String currencyCode;

	@Column
	@Comment("订单状态")
	private Integer ordersstatus;

	@Column
	@Comment("当前登录用户id")
	private Integer loginUserId;

	@Column
	@Comment("订单pnr状态")
	private Integer orderPnrStatus;

}