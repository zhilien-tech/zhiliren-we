/**
 * OriginDest.java
 * com.linyun.airline.common.sabre.dto
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.dto;

import lombok.Data;

/**
 * @author   朱晓川
 * @Date	 2017年6月19日 	 
 */
@Data
public class OriginDest {

	/**
	 * 起飞机场/出发城市（必须）
	 * <p>
	 * IATA机场3字代码或者MAC城市三字代码,比如ATL,BJS(北京)
	 */
	private String origin;

	/**
	 * 降落机场/到达城市（必须）
	 * <p>
	 * IATA机场3字代码或者MAC城市三字代码,比如ATL,BJS(北京)
	 */
	private String destination;

	/**
	 * 出发日期（必须）
	 * <P>
	 * 格式:yyyy-MM-dd,最大日期:当前日期 + 192 (天)
	 */
	private String departuredate;

}
