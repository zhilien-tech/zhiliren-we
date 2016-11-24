package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerInfoAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**上游公司id*/
	private long comId;

	/**代理商id*/
	private long agentId;

	/**名称*/
	private String name;

	/**简称*/
	private String shortName;

	/**联系人*/
	private String linkMan;

	/**电话*/
	private String telephone;

	/**传真*/
	private String fax;

	/**网址*/
	private String siteUrl;

	/**地址*/
	private String address;

	/**负责人*/
	private String agent;

	/**添加时间*/
	private Date createTime;

	/**出发城市*/
	private String departureCity;

	/**附件管理*/
	private String appendix;

	/**旅行社类型*/
	private long travelType;

	/**付款方式（现金、支票、银行汇款、第三方、其他）*/
	private long payWay;

	/**是否提供发票（0：否   1：是）*/
	private long invoice;

	/**销售价*/
	private Double sellPrice;

	/**货币单位*/
	private String moneyUnit;

	/**结算形式（月结、周结、单结、其他）*/
	private long payType;

	/**合作时间*/
	private Date cooperateTime;

	/**合作到期时间*/
	private Date cooperateDueTime;

	/**签约时间*/
	private Date contractTime;

	/**签约到期时间*/
	private Date contractDueTime;

	/**是否签约（未签约、已签约、禁止合作）*/
	private long contract;

	/**是否禁用*/
	private long forbid;

}