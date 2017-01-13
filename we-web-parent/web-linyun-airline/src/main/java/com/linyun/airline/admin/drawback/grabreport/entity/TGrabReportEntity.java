package com.linyun.airline.admin.drawback.grabreport.entity;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_grab_report")
public class TGrabReportEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("备注")
	private String remark;
	
	@Column
    @Comment("汇款")
	private Double remit;
	
	@Column
    @Comment("blance(备用金余额)")
	private Double depositBalance;
	
	@Column
    @Comment("票价(含行李)")
	private Double ticketPrice;
	
	@Column
    @Comment("刷卡费")
	private Double swipe;
	
	@Column
    @Comment("税金/杂项")
	private Double tax;
	
	@Column
    @Comment("消费税(GST)")
	private Double exciseTax1;
	
	@Column
    @Comment("代理费")
	private String agencyFee;
	
	@Column
    @Comment("税返点")
	private String taxRebate;
	
	@Column
    @Comment("退税状态")
	private Integer backStatus;
	
	@Column
    @Comment("实收单价(含操作费)")
	private Double realIncome;
	
	@Column
    @Comment("实收合计(含操作费)")
	private Double realTotal;
	
	@Column
    @Comment("代理费2")
	private String agencyFee2;
	
	@Column
    @Comment("入澳时间")
	private Date inAustralianTime;
	
	@Column
    @Comment("出澳时间")
	private Date outAustralianTime;
	

}