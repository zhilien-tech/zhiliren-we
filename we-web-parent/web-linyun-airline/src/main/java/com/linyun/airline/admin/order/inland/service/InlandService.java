/**
 * InlandService.java
 * com.linyun.airline.admin.order.inland.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.customneeds.service.EditPlanService;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.order.inland.form.InlandListSearchForm;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TFlightInfoEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年2月13日 	 
 */
@IocBean
public class InlandService extends BaseService<TUpOrderEntity> {

	private static final Log log = Logs.get();
	//航班号字典代码
	private static final String AIRLINECODE = "HBH";
	//城市字典代码
	private static final String CITYCODE = "CFCS";
	//航空公司字典代码
	private static final String AIRCOMCODE = "HKGS";
	@Inject
	private EditPlanService editPlanService;
	@Inject
	private externalInfoService externalInfoService;

	public Object listData(InlandListSearchForm form) {

		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> data = (List<Record>) listdata.get("data");
		for (Record record : data) {
			//
			List<TOrderCustomneedEntity> customerinfo = dbDao.query(TOrderCustomneedEntity.class,
					Cnd.where("ordernum", "=", record.getInt("id")), null);
			record.put("customerinfo", customerinfo);
			//航班信息
			List<TAirlineInfoEntity> airinfo = new ArrayList<TAirlineInfoEntity>();
			//PNR信息
			List<TPnrInfoEntity> pnrinfo = new ArrayList<TPnrInfoEntity>();
			for (TOrderCustomneedEntity tOrderCustomneedEntity : customerinfo) {
				List<TAirlineInfoEntity> airinfo1 = dbDao.query(TAirlineInfoEntity.class,
						Cnd.where("needid", "=", tOrderCustomneedEntity.getId()), null);
				airinfo.addAll(airinfo1);
				for (TAirlineInfoEntity AirlineInfoEntity : airinfo1) {
					List<TPnrInfoEntity> pnrs = dbDao.query(TPnrInfoEntity.class,
							Cnd.where("airinfoid", "=", AirlineInfoEntity.getId()), null);
					pnrinfo.addAll(pnrs);
				}
			}
			record.put("airinfo", airinfo);
			record.put("pnrinfo", pnrinfo);
		}
		listdata.remove("data");
		listdata.put("data", data);
		return listdata;
	}

	/**
	 * 添加订单
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param data
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public Object addOrderInfo(String data) {

		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
		boolean generateOrder = (boolean) fromJson.get("generateOrder");
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		TUpOrderEntity orderinfo = new TUpOrderEntity();
		orderinfo.setUserid(customerId);
		orderinfo.setOrdersstatus(orderType);
		//生成订单号
		if (generateOrder) {
			orderinfo.setOrdersnum(editPlanService.generateOrderNum());
		}
		TUpOrderEntity insertOrder = dbDao.insert(orderinfo);
		List<Map<String, Object>> customdata = (List<Map<String, Object>>) fromJson.get("customdata");
		for (Map<String, Object> map : customdata) {
			//客户需求出发城市
			String leavecity = (String) map.get("leavecity");
			//客户需求抵达城市
			String arrivecity = (String) map.get("arrivecity");
			//日期
			String leavedate = (String) map.get("leavedate");
			Integer peoplecount = Integer.valueOf((String) map.get("peoplecount"));
			Integer tickettype = Integer.valueOf((String) map.get("tickettype"));
			TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
			customneedEntity.setLeavecity(leavecity);
			customneedEntity.setArrivecity(arrivecity);
			customneedEntity.setLeavetdate(leavedate);
			customneedEntity.setPeoplecount(peoplecount);
			customneedEntity.setTickettype(tickettype);
			//与订单相关
			customneedEntity.setOrdernum(insertOrder.getId());
			TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
			//航班信息
			List<Map<String, Object>> airinfo = (List<Map<String, Object>>) map.get("airinfo");
			for (Map<String, Object> airmap : airinfo) {
				//航空公司
				String aircom = (String) airmap.get("aircom");
				//航班号
				String ailinenum = (String) airmap.get("ailinenum");
				//出发时间
				String leavetime = (String) airmap.get("leavetime");
				//抵达时间
				String arrivetime = (String) airmap.get("arrivetime");
				//成本价
				Double formprice = Double.valueOf((String) airmap.get("formprice"));
				//销售价
				Double price = Double.valueOf((String) airmap.get("price"));
				TAirlineInfoEntity airlineEntity = new TAirlineInfoEntity();
				airlineEntity.setAircom(aircom);
				airlineEntity.setAilinenum(ailinenum);
				airlineEntity.setLeavetime(leavetime);
				airlineEntity.setArrivetime(arrivetime);
				airlineEntity.setFormprice(formprice);
				airlineEntity.setPrice(price);
				airlineEntity.setNeedid(insertCus.getId());
				//添加航班信息
				dbDao.insert(airlineEntity);
			}
		}
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * 为查询订单详情页面准备数据
	 * <p>
	 *为查询订单详情页面准备数据
	 *
	 * @param id
	 * @return 返回页面需要显示的内容
	 */
	public Object queryDetail(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		TUpOrderEntity orderinfo = this.fetch(id);
		result.put("orderinfo", orderinfo);
		//客户信息
		TCustomerInfoEntity custominfo = dbDao.fetch(TCustomerInfoEntity.class, Long.valueOf(orderinfo.getUserid()));
		result.put("custominfo", custominfo);
		//客户负责人
		result.put("responsible", dbDao.fetch(TUserEntity.class, custominfo.getResponsibleId()).getUserName());
		//客户需求信息、航班信息集合
		List<Map<String, Object>> customneedinfo = new ArrayList<Map<String, Object>>();
		//查询客户需求信息
		List<TOrderCustomneedEntity> customs = dbDao.query(TOrderCustomneedEntity.class,
				Cnd.where("ordernum", "=", id), null);
		for (TOrderCustomneedEntity custom : customs) {
			//客户需求信息
			Map<String, Object> cusmap = new HashMap<String, Object>();
			cusmap.put("cusinfo", custom);
			//航班信息
			List<TAirlineInfoEntity> ailines = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("needid", "=", custom.getId()), null);
			cusmap.put("ailines", ailines);
			customneedinfo.add(cusmap);
		}
		//添加客户需求、航班信息
		result.put("customneedinfo", customneedinfo);
		//准备客户姓名下拉
		List<TCustomerInfoEntity> customerInfos = dbDao.query(TCustomerInfoEntity.class, null, null);
		result.put("customerInfos", customerInfos);
		//准备航班号下拉
		result.put("airline", dbDao.query(TFlightInfoEntity.class, null, null));
		//准备城市下拉
		List<TDepartureCityEntity> city = externalInfoService.findCityByCode("", CITYCODE);
		result.put("city", city);
		//准备航空公司下拉
		result.put("aircom", dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", AIRCOMCODE), null));
		return result;

	}

	/**
	 * 保存查询订单详情信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param data
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveOrderInfo(String data) {
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//订单id   从页面隐藏域获取
		Integer id = Integer.valueOf((String) fromJson.get("id"));
		//客户信息id，从页面隐藏域获取
		Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
		//是否生成订单
		boolean generateOrder = (boolean) fromJson.get("generateOrder");
		//订单状态（查询、预定、出票......）
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		TUpOrderEntity orderinfo = this.fetch(id);
		orderinfo.setId(id);
		orderinfo.setUserid(customerId);
		orderinfo.setOrdersstatus(orderType);
		//生成订单号
		if (generateOrder) {
			//如果订单号为空则生成订单号
			if (Util.isEmpty(orderinfo.getOrdersnum())) {
				orderinfo.setOrdersnum(editPlanService.generateOrderNum());
			}
		}
		//更新订单信息
		dbDao.update(orderinfo);
		//查询原有的客户需求信息
		List<TOrderCustomneedEntity> querycusneed = dbDao.query(TOrderCustomneedEntity.class,
				Cnd.where("ordernum", "=", id), null);
		List<Map<String, Object>> customdata = (List<Map<String, Object>>) fromJson.get("customdata");
		for (Map<String, Object> map : customdata) {
			//客户信息id
			String customneedid = (String) map.get("customneedid");
			//客户需求出发城市
			String leavecity = (String) map.get("leavecity");
			//客户需求抵达城市
			String arrivecity = (String) map.get("arrivecity");
			//日期
			String leavedate = (String) map.get("leavedate");
			Integer peoplecount = Integer.valueOf((String) map.get("peoplecount"));
			Integer tickettype = Integer.valueOf((String) map.get("tickettype"));
			TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
			customneedEntity.setLeavecity(leavecity);
			customneedEntity.setArrivecity(arrivecity);
			customneedEntity.setLeavetdate(leavedate);
			customneedEntity.setPeoplecount(peoplecount);
			customneedEntity.setTickettype(tickettype);
			//与订单相关
			customneedEntity.setOrdernum(id);
			if (Util.isEmpty(customneedid)) {
				//新增
				TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
				customneedid = String.valueOf(insertCus.getId());
			} else {
				//修改
				customneedEntity.setId(Integer.valueOf(customneedid));
				dbDao.update(customneedEntity);
			}
			//TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
			//航班信息
			List<Map<String, Object>> airinfo = (List<Map<String, Object>>) map.get("airinfo");
			//查询数据库原有的航班信息
			List<TAirlineInfoEntity> airlines = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("needid", "=", customneedid), null);
			for (Map<String, Object> airmap : airinfo) {
				String airlineid = (String) airmap.get("airlineid");
				//航空公司
				String aircom = (String) airmap.get("aircom");
				//航班号
				String ailinenum = (String) airmap.get("ailinenum");
				//出发时间
				String leavetime = (String) airmap.get("leavetime");
				//抵达时间
				String arrivetime = (String) airmap.get("arrivetime");
				//成本价
				Double formprice = Double.valueOf((String) airmap.get("formprice"));
				//销售价
				Double price = Double.valueOf((String) airmap.get("price"));
				TAirlineInfoEntity airlineEntity = new TAirlineInfoEntity();
				airlineEntity.setAircom(aircom);
				airlineEntity.setAilinenum(ailinenum);
				airlineEntity.setLeavetime(leavetime);
				airlineEntity.setArrivetime(arrivetime);
				airlineEntity.setFormprice(formprice);
				airlineEntity.setPrice(price);
				airlineEntity.setNeedid(Integer.valueOf(customneedid));
				if (Util.isEmpty(airlineid)) {
					//插入
					dbDao.insert(airlineEntity);
				} else {
					//更新
					airlineEntity.setId(Integer.valueOf(airlineid));
					dbDao.update(airlineEntity);
				}
				//添加航班信息
				//dbDao.insert(airlineEntity);
			}
			//如果页面上已经删除航班信息，则删除数据库中的记录
			for (TAirlineInfoEntity tAirlineInfoEntity : airlines) {
				boolean airlineflag = true;
				for (Map<String, Object> airsmap : airinfo) {
					if (tAirlineInfoEntity.getId().equals(Integer.valueOf((String) airsmap.get("airlineid")))) {
						airlineflag = false;
					}
				}
				if (airlineflag) {
					dbDao.delete(tAirlineInfoEntity);
				}
			}//end of  删除航班信息
		}
		//如果页面上已经删除，则应该删除数据库中的记录
		for (TOrderCustomneedEntity cus : querycusneed) {
			boolean flag = true;
			for (Map<String, Object> map : customdata) {
				if (cus.getId().equals(Integer.valueOf((String) map.get("customneedid")))) {
					flag = false;
				}
			}
			if (flag) {
				dbDao.delete(cus);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 为预定订单详情准备数据
	 * <p>
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object bookingDetail(Integer id) {

		Map<String, Object> result = new HashMap<String, Object>();
		TUpOrderEntity orderinfo = this.fetch(id);
		result.put("orderinfo", orderinfo);
		//客户信息
		TCustomerInfoEntity custominfo = dbDao.fetch(TCustomerInfoEntity.class, Long.valueOf(orderinfo.getUserid()));
		result.put("custominfo", custominfo);
		//客户负责人
		result.put("responsible", dbDao.fetch(TUserEntity.class, custominfo.getResponsibleId()).getUserName());
		//客户需求信息、航班信息集合
		List<Map<String, Object>> customneedinfo = new ArrayList<Map<String, Object>>();
		//查询客户需求信息
		List<TOrderCustomneedEntity> customs = dbDao.query(TOrderCustomneedEntity.class,
				Cnd.where("ordernum", "=", id), null);
		for (TOrderCustomneedEntity custom : customs) {
			//客户需求信息
			Map<String, Object> cusmap = new HashMap<String, Object>();
			cusmap.put("cusinfo", custom);
			//航班信息
			List<TAirlineInfoEntity> ailines = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("needid", "=", custom.getId()), null);
			cusmap.put("ailines", ailines);
			customneedinfo.add(cusmap);
		}
		//添加客户需求、航班信息
		result.put("customneedinfo", customneedinfo);
		//准备客户姓名下拉
		List<TCustomerInfoEntity> customerInfos = dbDao.query(TCustomerInfoEntity.class, null, null);
		result.put("customerInfos", customerInfos);
		//准备航班号下拉
		result.put("airline", dbDao.query(TFlightInfoEntity.class, null, null));
		//准备城市下拉
		List<TDepartureCityEntity> city = externalInfoService.findCityByCode("", CITYCODE);
		result.put("city", city);
		//准备航空公司下拉
		result.put("aircom", dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", AIRCOMCODE), null));
		return result;

	}
}
