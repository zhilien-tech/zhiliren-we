package com.linyun.airline.common.sabre.form;

import java.util.List;

import lombok.Data;

import com.google.common.collect.Lists;
import com.linyun.airline.common.sabre.dto.OriginDest;

/**
 * Sabre-BargainFinderMaxSearch接口查询参数
 * 
 * @author   朱晓川
 * @Date	 2016年11月15日 	 
 */
@Data
public class BargainFinderMaxSearchForm {

	/**出发到达信息*/
	private List<OriginDest> OriginDests = Lists.newArrayList();

	/**
	 * 航空公司（可选）
	 * <P>
	 * 2-letter IATA 代码,多个航空公司用逗号分隔.比如UA,DL,US
	 * 
	 */
	private List<String> carriers;

	/**
	 * 销售国家（可选）
	 * <p>
	 * ISO 3166 标准2字母国家代码,默认值为US（美国）
	 */
	private String pointofsalecountry;

	/**
	 * 每次查询记录数（可选）
	 * <p>
	 * 也就是pageSize，默认值为50
	 */
	private Integer limit;

	/**
	 * 从总记录的第几条开始查（可选）
	 * <p>
	 * limit 1,n;分页--从第几条开始查，查几条。最小值为1
	 */
	private Integer offset;

	//成人
	private Integer adt;
	//儿童
	private Integer cnn;
	//婴儿
	private Integer inf;
	//舱位等级
	private List<String> airLevel;

	/**
	 * 座位数:The sum of all seats required by all passenger groups.
	 */
	private String SeatsRequested;

	/**
	 * 用户 页面检索条件
	 */
	//起飞机场、出发城市
	private String origin;
	//降落机场、到达城市
	private String destination;
	//出发日期
	private String departuredate;
	//返程日期
	private String returndate;
	//成人
	private String agentSelect;
	//儿童
	private String childrenSelect;
	//婴儿
	private String babySelect;
	//舱位等级
	private int airLev;
	// 航空公司
	private String airlineCode;

}
