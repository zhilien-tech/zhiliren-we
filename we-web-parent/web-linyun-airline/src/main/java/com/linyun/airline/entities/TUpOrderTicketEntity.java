package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_up_order_ticket")
public class TUpOrderTicketEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("订单ID")
	private Integer orderid;
	
	@Column
    @Comment("机票ID")
	private Integer ticketid;
	
	@Column
    @Comment("售价（总价）")
	private Double price;
	

}