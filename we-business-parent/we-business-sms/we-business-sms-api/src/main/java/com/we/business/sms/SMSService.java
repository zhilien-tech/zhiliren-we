package com.we.business.sms;

import java.util.Map;

import com.uxuexi.core.common.exception.impl.BusinessException;
import com.we.business.sms.enums.SmsType;

public interface SMSService {

	/**向手机发送短信内容*/
	public Map<String, Object> send(final String tel, final String content) throws Exception;

	/**
	 * 对某一个手机发送某一种类型的验证码
	 * <p>
	 * 默认参数如下：
	 * <li>
	 * 验证码长度5位数，有效期5分钟，每个用户每个业务每日最多获取5条
	 * <li>
	 * 重新获取验证码的时间限制为1分钟
	 * @param SmsType SMS类型
	 * @param tel 手机号
	 * @return SMSCODE验证码 
	 * @throws Exception 
	 * @throws BusinessException 60秒后才能再次发送激活码，每个用户每日发送激活码次数超过限制5
	*/
	public String sendCaptcha(final SmsType SmsType, final String tel) throws Exception;

	/**
	 * 对某一个手机发送某一种类型的验证码
	 *
	 * @param smsType   验证码类型
	 * @param tel       电话号码
	 * @param maxCount
	 * @param length    数字验证码位数
	 * @param vaidTime  有效期(秒)
	 * @param sendGap  发送间隔(秒) 
	 * @return SmsCode 短信验证码
	 * @throws Exception 
	*/
	public String sendCaptcha(final SmsType smsType, final String tel, final int maxCount, final int length,
			final int vaidTime, final int sendGap) throws Exception;

	/**根据类型查询曾经给某手机发送的验证码*/
	public String getCaptcha(final SmsType smsType, final String tel);

	/**
	 * 使验证码无效
	 */
	public long disableCaptcha(final SmsType smsType, final String tel);

}
