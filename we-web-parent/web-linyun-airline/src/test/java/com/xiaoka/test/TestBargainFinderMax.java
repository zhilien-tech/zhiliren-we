/**
 * TestBargainFinderMax.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import java.util.List;

import com.google.common.collect.Lists;
import com.linyun.airline.common.sabre.dto.OriginDest;
import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.BargainFinderMaxSearchForm;
import com.linyun.airline.common.sabre.service.SabreService;
import com.linyun.airline.common.sabre.service.impl.SoapSabreServiceImpl;
import com.uxuexi.core.common.util.DateUtil;

/**
 * (Sabre-BargainFinderMaxSearch接口查询  --- 测试类)
 * <p>
 * 
 *
 * @author   彭辉
 * 
 * @Date	 2017年7月4日 	 
 */
public class TestBargainFinderMax {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); //获取开始时间

		/*第一段测试*/
		BargainFinderMaxSearchForm form = new BargainFinderMaxSearchForm();

		//出发信息
		List<OriginDest> OriginDests = Lists.newArrayList();
		//出发
		String departuredate = DateUtil.format("2017-07-15", "yyyy-MM-dd'T'HH:mm:ss");
		OriginDest od = new OriginDest();
		od.setOrigin("SYD");
		od.setDestination("BNE");
		od.setDeparturedate(departuredate);

		String departuredate1 = DateUtil.format("2017-07-17", "yyyy-MM-dd'T'HH:mm:ss");
		OriginDest od1 = new OriginDest();
		od1.setOrigin("SYD");
		od1.setDestination("BNE");
		od1.setDeparturedate(departuredate1);

		String departuredate2 = DateUtil.format("2017-07-19", "yyyy-MM-dd'T'HH:mm:ss");
		OriginDest od2 = new OriginDest();
		od2.setOrigin("SYD");
		od2.setDestination("BNE");
		od2.setDeparturedate(departuredate2);

		String departuredate3 = DateUtil.format("2017-07-20", "yyyy-MM-dd'T'HH:mm:ss");
		OriginDest od3 = new OriginDest();
		od3.setOrigin("SYD");
		od3.setDestination("BNE");
		od3.setDeparturedate(departuredate3);

		//返回
		String returndate = DateUtil.format("2017-07-21", "yyyy-MM-dd'T'HH:mm:ss");
		OriginDest odr = new OriginDest();
		odr.setOrigin("BNE");
		odr.setDestination("SYD");
		odr.setDeparturedate(returndate);
		OriginDests.add(od);
		/*OriginDests.add(od1);
		OriginDests.add(od2);
		OriginDests.add(od3);
		OriginDests.add(odr);*/
		form.setOriginDests(OriginDests);

		//乘客数量
		form.setAdt(1); //成人1
		form.setSeatsRequested("1");
		//舱位等级
		List<String> airLevels = Lists.newArrayList();
		//经济舱
		airLevels.add("P");
		airLevels.add("S");
		airLevels.add("Y");
		form.setAirLevel(airLevels);

		SabreService service = new SoapSabreServiceImpl();
		SabreResponse resp = service.bargainFinderMaxSearch(form);

		/*第二段测试数据*/
		BargainFinderMaxSearchForm form2 = new BargainFinderMaxSearchForm();
		List<OriginDest> OriginDests2 = Lists.newArrayList();
		String returndate2 = DateUtil.format("2017-07-21", "yyyy-MM-dd'T'HH:mm:ss");
		OriginDest odr2 = new OriginDest();
		odr2.setOrigin("BNE");
		odr2.setDestination("SYD");
		odr2.setDeparturedate(returndate2);
		OriginDests2.add(od2);
		form2.setOriginDests(OriginDests2);
		//乘客数量
		form2.setAdt(1); //成人1
		form2.setSeatsRequested("1");
		//舱位等级
		List<String> airLevels2 = Lists.newArrayList();
		//经济舱
		airLevels2.add("P");
		airLevels2.add("S");
		airLevels2.add("Y");
		form2.setAirLevel(airLevels2);
		SabreResponse resp2 = service.bargainFinderMaxSearch(form2);

		long endTime = System.currentTimeMillis(); //获取结束时间

		System.out.println("程序运行时间：" + (endTime - startTime) / 1000 + "s");

	}
}
