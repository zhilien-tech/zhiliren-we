/**
 * GroupTypeEnum.java
 * com.linyun.airline.admin.drawback.grabfile.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(散客和团队枚举类)
 * @author   崔建斌
 * @Date	 2017年5月3日 	 
 */
public enum GroupTypeEnum implements IEnum {
	GRABMAIL_FIT(1, "散客"), GRABMAIL_TEAM(2, "团队");
	private int key;
	private String value;

	private GroupTypeEnum(int key, String value) {
		this.key = key;
		this.value = value;
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
