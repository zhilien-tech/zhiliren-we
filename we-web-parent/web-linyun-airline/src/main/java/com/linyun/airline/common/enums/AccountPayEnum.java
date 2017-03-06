/**
 * OrderStatusEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 会计 付款订单状态
 * <p>
 * @author   彭辉
 * @Date	 2017年3月4日 	 
 */
public enum AccountPayEnum implements IEnum {

	APPROVAL(0, "审批中"), APPROVALPAYING(1, "付款中"), APPROVALPAYED(2, "已付款"), APPROVALRefuse(3, "拒绝");

	private int key;
	private String value;

	private AccountPayEnum(final int key, final String value) {
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
