package com.linyun.airline.admin.invoicemanage.invoiceinfo.from;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInvoiceInfoUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**发票项目*/
	private Integer invoiceitem;

	/**发票日期*/
	private String invoicedate;

	/**开票人*/
	private Integer billuserid;

	/**部门*/
	private Integer deptid;

	/**付款单位*/
	private String paymentunit;

	/**备注*/
	private String remark;

	/**差额*/
	private Double difference;

	/**余额*/
	private Double balance;

	/**发票类型（收款、付款）*/
	private Integer invoicetype;

	/**收款id*/
	private Integer receiveid;

	/**pnrid*/
	private Integer pnrid;

	/**借发票*/
	private Integer borrowInvoice;

}