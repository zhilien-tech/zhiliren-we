/**
 * BargainFinderSearch.java
 * com.linyun.airline.common.sabre.bean
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.bean;

import lombok.Data;

import com.linyun.airline.common.sabre.dto.SabreResponse;

/**
 * 机票缓存信息
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年7月6日 	 
 */
@Data
public class BargainFinderSearch {

	/**
	 * 机票信息
	 */
	private SabreResponse resp;

	/**
	 * 过期时间
	 */
	private int expires_in;

	private long loadTimeMillis;
}
