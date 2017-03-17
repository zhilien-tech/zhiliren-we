package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息中的  消息状态
 * <p>
 * 
 * @author   彭辉
 * @Date	 2016年12月07日
 */
public enum MessageWealthStatusEnum implements IEnum {
	RECEIVED(11, "已收款"), PAYED(12, "已付款"), INVIOCE(13, "已开发票"), RECINVIOCE(14, "已收发票"), APPROVAL(15, "已审批"), UNAPPROVAL(
			16, "已拒绝");

	private int key;
	private String value;

	private MessageWealthStatusEnum(final int key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {
		return String.valueOf(key);
	}

	@Override
	public String value() {
		return value;
	}

	public int intKey() {
		return key;
	}
}
