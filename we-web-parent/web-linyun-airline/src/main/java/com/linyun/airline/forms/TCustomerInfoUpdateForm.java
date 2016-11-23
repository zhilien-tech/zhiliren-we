package com.linyun.airline.forms;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerInfoUpdateForm extends ModForm implements Serializable {
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
	private Timestamp createTime;

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
	private Timestamp cooperateTime;

	/**合作到期时间*/
	private Timestamp cooperateDueTime;

	/**签约时间*/
	private Timestamp contractTime;

	/**签约到期时间*/
	private Timestamp contractDueTime;

	/**是否签约（未签约、已签约、禁止合作）*/
	private long contract;

	/**是否禁用*/
	private long forbid;

	/**业务范围*/
	private String business;

	public void setCreateTime(Timestamp createTime) {
		if (Util.isEmpty(createTime)) {
			this.createTime = null;
		} else {
			this.createTime = createTime;
		}
	}

	public void setCooperateTime(Timestamp cooperateTime) {
		if (Util.isEmpty(cooperateTime)) {
			this.cooperateTime = null;
		} else {
			this.cooperateTime = cooperateTime;
		}
	}

	public void setCooperateDueTime(Timestamp cooperateDueTime) {
		if (Util.isEmpty(cooperateDueTime)) {
			this.cooperateDueTime = null;
		} else {
			this.cooperateDueTime = cooperateDueTime;
		}
	}

	public void setContractTime(Timestamp contractTime) {
		if (Util.isEmpty(contractTime)) {
			this.contractTime = null;
		} else {
			this.contractTime = contractTime;
		}
	}

	public void setContractDueTime(Timestamp contractDueTime) {
		if (Util.isEmpty(contractDueTime)) {
			this.contractDueTime = null;
		} else {
			this.contractDueTime = contractDueTime;
		}
	}
}
