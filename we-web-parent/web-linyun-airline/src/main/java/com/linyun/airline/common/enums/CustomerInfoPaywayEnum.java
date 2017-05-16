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
public enum CustomerInfoPaywayEnum implements IEnum {

	CASH(1, "现金"), CHEQUE(2, "支票"), BANK(3, "银行汇款"), THIRDPARTY(4, "第三方"), OTHERS(5, "其他");

	private int key;
	private String value;

	private CustomerInfoPaywayEnum(final int key, final String value) {
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
