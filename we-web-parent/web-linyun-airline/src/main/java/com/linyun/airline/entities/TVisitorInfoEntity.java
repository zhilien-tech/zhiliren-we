package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_visitor_info")
public class TVisitorInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单id")
	private Integer ordernum;
	
	@Column
    @Comment("姓名")
	private String visitorname;
	
	@Column
    @Comment("电话")
	private String phonenum;
	

}