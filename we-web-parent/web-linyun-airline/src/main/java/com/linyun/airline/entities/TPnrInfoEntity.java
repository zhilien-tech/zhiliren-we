package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


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
	private Integer currency;
	
	@Column
    @Comment("航班号信息id")
	private Integer airinfoid;
	

}