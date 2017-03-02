package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_order_receive")
public class TOrderReceiveEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("收款id")
	private Integer receiveid;
	
	@Column
    @Comment("订单id")
	private Integer orderid;
	
	@Column
    @Comment("收款订单状态")
	private Integer receivestatus;
	

}