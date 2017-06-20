package com.linyun.airline.common.sabre.dto;

import java.util.List;

import lombok.Data;

/**
 * 机票查询结果-航旅信息
 * <p>
 * 一次航旅(AirItinerary)包含多段航段(segment)
 *
 * @author   朱晓川
 * @Date	 2016年12月5日 	 
 */
@Data
public class BFMAirItinerary {

	/**排序号*/
	private int SequenceNumber;

	/**
	 * 航空公司图片地址
	 */
	private String airlineImgUrl;

	/**
	 * 航空公司3字母代码
	 */
	private String airlineCode;

	/**
	 * 航空公司名称
	 */
	private String airlineName;

	/**
	 * 机票类型
	 */
	private String TicketType;

	/**
	 * 航段
	 */
	List<FlightSegment> list;

	/**
	 * 票价信息
	 */
	private List<FlightPriceInfo> priceInfos;

}
