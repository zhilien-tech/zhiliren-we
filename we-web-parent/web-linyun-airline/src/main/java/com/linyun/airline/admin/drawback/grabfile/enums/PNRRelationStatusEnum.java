/**
 * Naval.java
 * com.linyun.airline.admin.drawback.grabfile.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 文件夹层级结构枚举
 * @author   崔建斌
 * @Date	 2017年2月21日 	 
 */
public enum PNRRelationStatusEnum implements IEnum {
	RELATION(1, "关联"), NORELATION(0, "未关联");
	private int key;
	private String value;

	private PNRRelationStatusEnum(int key, String value) {
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
