package com.linyun.airline.common.sabre.dto;

import java.util.Date;

import lombok.Data;

/**
 * 航段
 * <p>
 * 一次旅程有多个航段
 *
 * @author   朱晓川
 * @Date	 2016年12月5日 	 
 */
@Data
public class FlightSegment {

	/**
	 * 经停次数
	 */
	private int StopQuantity;

	/**
	 * 耗时（h）
	 */
	private int ElapsedTime;

	private String ResBookDesigCode;

	private String MarriageGrp;

	/**
	 * 出发时间
	 */
	private Date DepartureDateTime;

	/**
	 * 抵达时间
	 */
	private Date ArrivalDateTime;

	/**
	 * 航班号
	 */
	private String FlightNumber;

	/**
	 * 准点率(%)
	 */
	private int OnTimePerformance;

}
