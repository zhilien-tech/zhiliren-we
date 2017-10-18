/**
 * SMSConfig.java
 * com.we.business.sms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.we.business.sms;

/**
 * @author   朱晓川
 * @Date	 2017年10月16日 	 
 */
public class SMSConstants {

	/**
	 * 验证码条数在redis数据库中键的固定前缀
	 */
	public static final String SMSCOUNT = "smscount";
	/**
	 * 重新获取验证码在redis数据库中键的固定前缀
	 */
	public static final String REGETSMS = "regetsms";
	/**
	 * 短信验证码在redis数据库中键的固定前缀
	 */
	public static final String SMSCODE = "smscode";

}
