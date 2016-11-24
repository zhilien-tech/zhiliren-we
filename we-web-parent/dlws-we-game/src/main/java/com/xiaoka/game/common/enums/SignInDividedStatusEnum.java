package com.xiaoka.game.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 *
 */
public enum SignInDividedStatusEnum implements IEnum {

	NO(0, "未分成"),YES(1, "已分成");
	private int key;
	private String value;

	private SignInDividedStatusEnum(final int key, final String value) {
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
