/**
 * ApprovalList.java
 * com.linyun.airline.admin.applyapproval.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabreport.entity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月22日 	 
 */

public class PNRINFOList {

	private int orderId;
	private int financeId;
	private int payReceiveRecordId;
	private int pnrId;
	private String PNR;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getFinanceId() {
		return financeId;
	}

	public void setFinanceId(int financeId) {
		this.financeId = financeId;
	}

	public int getPayReceiveRecordId() {
		return payReceiveRecordId;
	}

	public void setPayReceiveRecordId(int payReceiveRecordId) {
		this.payReceiveRecordId = payReceiveRecordId;
	}

	public int getPnrId() {
		return pnrId;
	}

	public void setPnrId(int pnrId) {
		this.pnrId = pnrId;
	}

	public String getPNR() {
		return PNR;
	}

	public void setPNR(String pNR) {
		PNR = pNR;
	}

}
