package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TSalaryAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**成本合计*/
	private double costTotal;

	/**收入合计*/
	private double incomeTotal;

	/**提成*/
	private String commission;

	/**实发提成*/
	private double actualCommission;

	/**基本工资*/
	private double basePay;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**工资合计*/
	private double salaryTotal;

	/**备注*/
	private String remark;
	/**开票人*/
	private String drawer;
	/**团数*/
	private int groupNumber;
	/**人头数*/
	private int headCount;

}