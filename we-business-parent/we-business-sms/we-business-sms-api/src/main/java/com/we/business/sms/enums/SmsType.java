/**
 * SMSType.java
 * cn.ccig.youshi.user.enums
 * Copyright (c) 2014, 北京世纪新干线科技有限公司版权所有.
*/

package com.we.business.sms.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * SMS短信的类型
 */
public enum SmsType implements IEnum {

	/**
	 * 用户登录验证码
	 */
	LOGIN("login", "用户登录"),

	/**
	 * 用户注册验证码
	 */
	REGISTER("register", "用户注册"),

	/**
	 * 重置密码
	 */
	RESETPWD("resetpwd", "重置密码");

	private String key;
	private String value;

	private SmsType(final String key, final String value) {
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

}
