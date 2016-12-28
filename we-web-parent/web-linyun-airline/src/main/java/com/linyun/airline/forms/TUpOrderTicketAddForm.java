package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUpOrderTicketAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单ID*/
	private Integer orderid;
		
	/**机票ID*/
	private Integer ticketid;
		
	/**售价（总价）*/
	private Double price;
		
}