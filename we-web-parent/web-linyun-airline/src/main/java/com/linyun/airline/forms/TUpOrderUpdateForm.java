package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUpOrderUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单号*/
	private String ordersnum;
		
	/**上游公司客户信息*/
	private Integer customid;
		
	/**订单金额*/
	private Double amount;
		
	/**下单时间*/
	private Date orderstime;
		
	/**订单类型*/
	private Integer orderstype;
		
	/**负责人用户ID*/
	private Integer userid;
		
	/**结算货币代码*/
	private String currencyCode;
		
	/**订单状态*/
	private Integer ordersstatus;
		
}