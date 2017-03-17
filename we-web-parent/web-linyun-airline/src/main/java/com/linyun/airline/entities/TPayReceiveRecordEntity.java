package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_pay_receive_record")
public class TPayReceiveRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("记录类型")
	private Integer recordtype;
	
	@Column
    @Comment("成本单价")
	private Double costprice;
	
	@Column
    @Comment("预付款比例")
	private Double prepayratio;
	
	@Column
    @Comment("实际人数")
	private Integer actualnumber;
	
	@Column
    @Comment("免罚金可减人数")
	private Integer freenumber;
	
	@Column
    @Comment("本期罚金")
	private Double currentfine;
	
	@Column
    @Comment("本期应付")
	private Double currentdue;
	
	@Column
    @Comment("税金单价")
	private Double ataxprice;
	
	@Column
    @Comment("本期实付")
	private Double currentpay;
	
	@Column
    @Comment("币种")
	private Integer currency;
	
	@Column
    @Comment("操作人")
	private Integer opid;
	
	@Column
    @Comment("操作时间")
	private Date optime;
	
	@Column
    @Comment("订单id")
	private Integer orderid;
	

}