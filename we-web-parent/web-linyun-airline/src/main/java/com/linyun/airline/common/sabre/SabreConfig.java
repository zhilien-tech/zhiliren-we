/**
 * SabreConfig.java
 * com.linyun.airline.common.sabre.config
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre;

import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2016年11月17日 	 
 */
public class SabreConfig {

	//打log用
	protected static Log log = Logs.getLog(SabreConfig.class);

	/**
	 * API环境分为测试环境和生产环境，测试的时候使用测试环境
	 */
	protected static final String test_environment = "https://api.test.sabre.com";

	protected static final String prod_environment = "https://api.sabre.com";

	protected static final String CHARSET = "UTF-8";

	protected static final String AUTH_URI = "/v2/auth/token";

	protected static final String Client_ID = "V1:mq2kolgs7tunkpe0:DEVCENTER:EXT";

	protected static final String Secret = "satULB47";

	protected static final String contentType = "application/x-www-form-urlencoded";

}
