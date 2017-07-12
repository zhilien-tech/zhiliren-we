/**
 * SabreConfig.java
 * com.linyun.airline.common.sabre.config
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre;

import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author   朱晓川
 * @Date	 2016年11月17日 	 
 */
public class SabreConfig {

	//打log用
	protected static Log log = Logs.getLog(SabreConfig.class);

	/**
	 * 航班查询
	 */
	public static final String INSTAL_FLIGHTS_URl = "/v1/shop/flights";

	public static final String BARGAIN_FINDER_MAX_URl = "/v3.1.0/shop/flights?mode=live";

	/**
	 * MAC多机场城市代码查询
	 */
	public static final String MAC_LOOKUP_URI = "/v1/lists/supported/cities";

	protected static final String CHARSET = "UTF-8";

	protected static final String contentType = "application/x-www-form-urlencoded";

}
