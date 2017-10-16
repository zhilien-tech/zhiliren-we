package com.we.business.sms.util;

import com.uxuexi.core.common.util.ExceptionUtil;
import com.uxuexi.core.common.util.StringUtil;
import com.uxuexi.core.common.util.Util;
import com.we.business.sms.enums.SmsType;

/**
 * 短信发送配置
 */
public class SmsUtil {

	/**
	 * 根据短信类型和激活码，从配置文件获取短信内容
	 * @param smsType
	 * @param code
	 * @return 短信内容
	*/
	public static String getSmsContent(final SmsType smsType, final String code) {
		ExceptionUtil.checkEmptyBEx(smsType, "短信类型不允许为空");
		ExceptionUtil.checkEmptyBEx(code, "验证码不允许为空");

		String key = smsType.key();
		String content = CustomizedPropertyConfigurer.getContextProperty(key);
		ExceptionUtil.checkEmptyBEx(content, "短信内容不允许为空");
		content = StringUtil.decodeUnicode(content);

		if (Util.isEmpty(content)) {
			return "";
		}
		return content.replaceAll("【变量】", code);
	}

}
