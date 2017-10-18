/**
 * SmsCallResult.java
 * cn.ccig.business.sms.bean
 * Copyright (c) 2015, 北京 快先生科技有限公司版权所有.
*/

package com.we.business.sms.bean;

import lombok.Data;

/**
 * 华信短信发送结果
 * <p>
 * @author   朱晓川
 * @Date	 2017年10月17日
 */
@Data
public class HuaxinSmsCallResult {

	/**接口调用状态码*/
	private String returnstatus;

	/**提示信息*/
	private String message;

	/**任务id*/
	private String taskID;

	/**剩余条数*/
	private int remainpoint;

	/**成功条数*/
	private int successCounts;

}
