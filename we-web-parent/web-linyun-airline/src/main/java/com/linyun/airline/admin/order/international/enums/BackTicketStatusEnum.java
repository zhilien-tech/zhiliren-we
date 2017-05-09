/**
 * BackTicketEnum.java
 * com.linyun.airline.admin.order.international.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年5月9日 	 
 */
public enum BackTicketStatusEnum implements IEnum {
	BACKTICKETING(1, "退票中"), BACKTICKETED(2, "已退票");
	private int key;
	private String value;

	private BackTicketStatusEnum(final int key, final String value) {
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
