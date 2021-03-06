/**
 * SmsCallResult.java
 * cn.ccig.business.sms.bean
 * Copyright (c) 2015, 北京 快先生科技有限公司版权所有.
*/

package com.we.business.sms.bean;

import lombok.Data;

/**
 * 互亿无线短信发送结果
 * <p>
 * @author   朱晓川
 * @Date	 2017年10月17日
 */
@Data
public class HuyiSmsCallResult {

	/**接口调用状态码*/
	private String code;

	/**仅当提交成功后，此字段值才有意义（消息ID）*/
	private String smsid;

	/**提示信息*/
	private String msg;

}
