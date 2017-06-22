/**
 * OrderTypeEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 舱位等级
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年6月22日 	 
 */
public enum AirLineLevelEnum implements IEnum {
	ECONOMY("Y", "经济舱"), PREMIUM_ECONOMY("PremiumEconomy", "高端经济舱"), FIRST("F", "头等舱"), BUSINESS("C", "公务舱");
	private String key;
	private String value;

	private AirLineLevelEnum(final String key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String value() {
		return value;
	}

	public String intKey() {
		return key;
	}
}
