package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_invoice_detail")
public class TInvoiceDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("发票信息id")
	private Integer invoiceinfoid;
	
	@Column
    @Comment("发票号")
	private String invoicenum;
	
	@Column
    @Comment("发票金额")
	private Double invoicebalance;
	
	@Column
    @Comment("发票图片url")
	private String invoiceurl;
	
	@Column
    @Comment("操作人")
	private Integer opid;
	
	@Column
    @Comment("操作时间")
	private Date optime;
	

}