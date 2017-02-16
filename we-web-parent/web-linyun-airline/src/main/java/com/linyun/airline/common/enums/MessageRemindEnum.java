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
public enum MessageRemindEnum implements IEnum {
	Mouth(1, "自然月1日"), Week(2, "自然每周一"), Day(3, "每天"), Hour(4, "每小时"), Minute(4, "每分钟");

	private int key;
	private String value;

	private MessageRemindEnum(final int key, final String value) {
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
