package com.linyun.airline.admin.receivePayment.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TSaveInlandPayAddFrom implements Serializable {
	private static final long serialVersionUID = 1L;

	/**付款订单pay ids */
	private String payIds;

	/**银行名称*/
	private Integer bankComp;

	/**银行卡名称*/
	private Integer cardName;

	/**银行卡卡号*/
	private Integer cardNum;

	/**支付地址*/
	private Integer payAddress;

	/**用途*/
	private Integer purpose;

	/**资金类型*/
	private Integer fundType;

	/**付款时间*/
	private Date payDate;

	/**手续费*/
	private Double payFees;

	/**金额*/
	private Double payMoney;

	/**币种*/
	private Integer payCurrency;

	/**发票*/
	private Integer isInvioce;

	/**水单url*/
	private String receiptUrl;

}
