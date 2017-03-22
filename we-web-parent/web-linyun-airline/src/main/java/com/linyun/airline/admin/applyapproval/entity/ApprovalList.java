/**
 * ApprovalList.java
 * com.linyun.airline.admin.applyapproval.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.applyapproval.entity;

import java.util.Date;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月22日 	 
 */
public class ApprovalList {

	private Integer id;
	private Integer orderstype;
	private String PNR;
	private Integer orderPnrStatus;
	private Double costpricesum;
	private String ordernum;
	private Date optime;
	private Integer purpose;
	private Integer purposer;
	private String shortName;
	private Integer fundType;
	private Double payFees;
	private Integer payCurrency;
	private Integer isInvioce;
	private Date approveTime;
	private Integer approveResult;
	private Integer usingId;
	private Integer orderId;
	private String userName;
	private String currencyStr;
	private String purposeStr;
	private String fundTypeStr;
	private Integer companyId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderstype() {
		return orderstype;
	}

	public void setOrderstype(Integer orderstype) {
		this.orderstype = orderstype;
	}

	public String getPNR() {
		return PNR;
	}

	public void setPNR(String pNR) {
		PNR = pNR;
	}

	public Integer getOrderPnrStatus() {
		return orderPnrStatus;
	}

	public void setOrderPnrStatus(Integer orderPnrStatus) {
		this.orderPnrStatus = orderPnrStatus;
	}

	public Double getCostpricesum() {
		return costpricesum;
	}

	public void setCostpricesum(Double costpricesum) {
		this.costpricesum = costpricesum;
	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public Integer getPurpose() {
		return purpose;
	}

	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}

	public Integer getPurposer() {
		return purposer;
	}

	public void setPurposer(Integer purposer) {
		this.purposer = purposer;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public Double getPayFees() {
		return payFees;
	}

	public void setPayFees(Double payFees) {
		this.payFees = payFees;
	}

	public Integer getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(Integer payCurrency) {
		this.payCurrency = payCurrency;
	}

	public Integer getIsInvioce() {
		return isInvioce;
	}

	public void setIsInvioce(Integer isInvioce) {
		this.isInvioce = isInvioce;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Integer getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
	}

	public Integer getUsingId() {
		return usingId;
	}

	public void setUsingId(Integer usingId) {
		this.usingId = usingId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCurrencyStr() {
		return currencyStr;
	}

	public void setCurrencyStr(String currencyStr) {
		this.currencyStr = currencyStr;
	}

	public String getPurposeStr() {
		return purposeStr;
	}

	public void setPurposeStr(String purposeStr) {
		this.purposeStr = purposeStr;
	}

	public String getFundTypeStr() {
		return fundTypeStr;
	}

	public void setFundTypeStr(String fundTypeStr) {
		this.fundTypeStr = fundTypeStr;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
