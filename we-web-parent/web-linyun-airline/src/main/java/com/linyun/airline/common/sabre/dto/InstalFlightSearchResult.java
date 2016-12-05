/**
 * InstalFlightSearchResult.java
 * com.linyun.airline.common.sabre.dto
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.dto;

import lombok.Data;

/**
 * 机票查询结果
 *
 * @author   朱晓川
 * @Date	 2016年12月5日 	 
 */
@Data
public class InstalFlightSearchResult {

	/**
	 * 航空公司3字母代码
	 */
	private String airlineCode;

	/**
	 * 航空公司名称
	 */
	private String airlineName;

	/**
	 * 航空公司图片地址
	 */
	private String airlineImgUrl;

	/**
	 * 耗时(分)
	 */
	private int elapsedTime;

}
