/**
 * InlandService.java
 * com.linyun.airline.admin.order.inland.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.upload.TempFile;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.customneeds.service.EditPlanService;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PassengerTypeEnum;
import com.linyun.airline.admin.order.inland.enums.PayMethodEnum;
import com.linyun.airline.admin.order.inland.form.FuKuanParamForm;
import com.linyun.airline.admin.order.inland.form.InlandListSearchForm;
import com.linyun.airline.admin.order.inland.form.PayApplyListForm;
import com.linyun.airline.admin.order.inland.form.ShouKuanParamFrom;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayPnrEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.common.enums.OrderStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.util.ExcelReader;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TFinanceInfoEntity;
import com.linyun.airline.entities.TFlightInfoEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TVisitorInfoEntity;
import com.linyun.airline.entities.TVisitorsPnrEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
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
	//币种
	private static final String BIZHONGCODE = "BZ";
	//银行卡类型
	private static final String YHCODE = "YH";
	//付款用途
	private static final String FKYTCODE = "FKYT";
	//内陆跨海
	private static final String NLKHCODE = "NLKH";
	private static final String EXCEL_PATH = "download";
	private static final String FILE_EXCEL_NAME = "客户需求游客模板.xlsx";
	@Inject
	private EditPlanService editPlanService;
	@Inject
	private externalInfoService externalInfoService;
	@Inject
	private OrderLogService orderLogService;

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
			}
			for (TOrderCustomneedEntity tOrderCustomneedEntity : customerinfo) {
				List<TPnrInfoEntity> pnrs = dbDao.query(TPnrInfoEntity.class,
						Cnd.where("needid", "=", tOrderCustomneedEntity.getId()), null);
				pnrinfo.addAll(pnrs);
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
	public Object addOrderInfo(String data, HttpServletRequest request) {

		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
		boolean generateOrder = (boolean) fromJson.get("generateOrder");
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		TUpOrderEntity orderinfo = new TUpOrderEntity();
		orderinfo.setUserid(customerId);
		orderinfo.setOrdersstatus(orderType);
		orderinfo.setLoginUserId(new Long(user.getId()).intValue());
		//生成订单号
		if (generateOrder) {
			orderinfo.setOrdersnum(editPlanService.generateOrderNum());
		}
		orderinfo.setOrderstype(OrderTypeEnum.FIT.intKey());
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
			if (!Util.isEmpty(leavedate)) {
				customneedEntity.setLeavetdate(DateUtil.string2Date(leavedate, DateUtil.FORMAT_YYYY_MM_DD));
			}
			customneedEntity.setPeoplecount(peoplecount);
			customneedEntity.setTickettype(tickettype);
			//与订单相关
			customneedEntity.setOrdernum(insertOrder.getId());
			TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
			//航班信息
			List<Map<String, Object>> airinfo = (List<Map<String, Object>>) map.get("airinfo");
			List<TAirlineInfoEntity> airlist = new ArrayList<TAirlineInfoEntity>();
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
				airlist.add(airlineEntity);
				//添加航班信息
			}
			dbDao.insert(airlist);
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
		//准备航班号下拉
		result.put("airline", dbDao.query(TFlightInfoEntity.class, null, null));
		//准备城市下拉
		List<TDepartureCityEntity> city = externalInfoService.findCityByCode("", CITYCODE);
		result.put("city", city);
		//准备航空公司下拉
		result.put("aircom", dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", AIRCOMCODE), null));
		result.put("orderstatusenum", EnumUtil.enum2(OrderStatusEnum.class));
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
	public Object saveOrderInfo(String data, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//订单id   从页面隐藏域获取
		Integer id = Integer.valueOf((String) fromJson.get("id"));
		//客户信息id，从页面隐藏域获取
		Integer customerId = null;
		if (!Util.isEmpty(fromJson.get("customerId"))) {
			customerId = Integer.valueOf((String) fromJson.get("customerId"));
		}
		//是否生成订单
		boolean generateOrder = (boolean) fromJson.get("generateOrder");
		//订单状态（查询、预定、出票......）
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		TUpOrderEntity orderinfo = this.fetch(id);
		orderinfo.setId(id);
		orderinfo.setUserid(customerId);
		orderinfo.setOrdersstatus(orderType);
		orderinfo.setLoginUserId(new Long(user.getId()).intValue());
		//生成订单号
		if (generateOrder) {
			//如果订单号为空则生成订单号
			if (Util.isEmpty(orderinfo.getOrdersnum())) {
				orderinfo.setOrdersnum(editPlanService.generateOrderNum());
			}
		}
		//更新订单信息
		dbDao.update(orderinfo);
		String logcontent = "";
		for (OrderStatusEnum statusenum : OrderStatusEnum.values()) {
			if (orderType == statusenum.intKey()) {
				logcontent = statusenum.value();
			}
		}
		orderLogService.addOrderLog(new Long(user.getId()).intValue(), orderinfo.getId(), orderinfo.getOrdersnum(),
				logcontent);
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
			Integer peoplecount = null;
			if (!Util.isEmpty(map.get("peoplecount"))) {
				peoplecount = Integer.valueOf((String) map.get("peoplecount"));
			}
			Integer tickettype = null;
			if (!Util.isEmpty(map.get("tickettype"))) {
				tickettype = Integer.valueOf((String) map.get("tickettype"));
			}
			TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
			customneedEntity.setLeavecity(leavecity);
			customneedEntity.setArrivecity(arrivecity);
			if (!Util.isEmpty(leavedate)) {
				customneedEntity.setLeavetdate(DateUtil.string2Date(leavedate, DateUtil.FORMAT_YYYY_MM_DD));
			}
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
				Double formprice = null;
				if (!Util.isEmpty(airmap.get("formprice"))) {
					formprice = Double.valueOf((String) airmap.get("formprice"));
				}
				//销售价
				Double price = null;
				if (!Util.isEmpty(airmap.get("price"))) {
					price = Double.valueOf((String) airmap.get("price"));
				}
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
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		//添加客户需求、航班信息
		result.put("customneedinfo", customneedinfo);
		//准备航班号下拉
		result.put("airline", dbDao.query(TFlightInfoEntity.class, null, null));
		//准备城市下拉
		List<TDepartureCityEntity> city = externalInfoService.findCityByCode("", CITYCODE);
		result.put("city", city);
		//准备航空公司下拉
		result.put("aircom", dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", AIRCOMCODE), null));
		//准备币种下拉
		result.put("bzcode", bzcode);
		//准备财务信息
		TFinanceInfoEntity finance = dbDao.fetch(TFinanceInfoEntity.class, Cnd.where("orderid", "=", id));
		result.put("finance", finance);
		//支付方式
		result.put("paymethodenum", EnumUtil.enum2(PayMethodEnum.class));
		//订单状态
		result.put("orderstatusenum", EnumUtil.enum2(OrderStatusEnum.class));
		result.put("querystatus", OrderStatusEnum.SEARCH.intKey());
		//团队类型
		result.put("passengertypeenum", EnumUtil.enum2(PassengerTypeEnum.class));
		//内陆跨海下拉
		//币种下拉
		List<DictInfoEntity> nlkhcode = new ArrayList<DictInfoEntity>();
		try {
			nlkhcode = externalInfoService.findDictInfoByName("", NLKHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("nlkhcode", nlkhcode);
		return result;

	}

	/**
	 * 保存预定订单详情
	 * <p>
	 * TODO保存预定订单详情
	 *
	 * @param data
	 * @param request 
	 * @return TODO
	 */
	@SuppressWarnings("unchecked")
	public Object saveBookingOrderInfo(String data, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//订单id   从页面隐藏域获取
		Integer id = Integer.valueOf((String) fromJson.get("id"));
		//客户信息id，从页面隐藏域获取
		Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
		//订单状态（查询、预定、出票......）
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		TUpOrderEntity orderinfo = this.fetch(id);
		orderinfo.setId(id);
		orderinfo.setUserid(customerId);
		orderinfo.setOrdersstatus(orderType);
		orderinfo.setLoginUserId(new Long(user.getId()).intValue());
		//更新订单信息
		dbDao.update(orderinfo);
		String logcontent = "";
		for (OrderStatusEnum statusenum : OrderStatusEnum.values()) {
			if (orderType == statusenum.intKey()) {
				logcontent = statusenum.value();
			}
		}
		orderLogService.addOrderLog(new Long(user.getId()).intValue(), orderinfo.getId(), orderinfo.getOrdersnum(),
				logcontent);
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
			Integer peoplecount = null;
			if (!Util.isEmpty(map.get("peoplecount"))) {
				peoplecount = Integer.valueOf((String) map.get("peoplecount"));
			}
			Integer tickettype = null;
			if (!Util.isEmpty(map.get("tickettype"))) {
				tickettype = Integer.valueOf((String) map.get("tickettype"));
			}
			Double realtimexrate = null;
			if (!Util.isEmpty(map.get("realtimexrate"))) {
				realtimexrate = Double.valueOf((String) map.get("realtimexrate"));
			}
			Double avgexrate = null;
			if (!Util.isEmpty(map.get("avgexrate"))) {
				avgexrate = Double.valueOf((String) map.get("avgexrate"));
			}
			String paycurrency = (String) map.get("paycurrency");
			Integer paymethod = null;
			if (!Util.isEmpty(map.get("paymethod"))) {
				paymethod = Integer.valueOf((String) map.get("paymethod"));
			}
			String remark = (String) map.get("remark");
			TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
			customneedEntity.setLeavecity(leavecity);
			customneedEntity.setArrivecity(arrivecity);
			if (!Util.isEmpty(leavedate)) {
				customneedEntity.setLeavetdate(DateUtil.string2Date(leavedate, DateUtil.FORMAT_YYYY_MM_DD));
			}
			customneedEntity.setPeoplecount(peoplecount);
			customneedEntity.setTickettype(tickettype);
			customneedEntity.setRealtimexrate(realtimexrate);
			customneedEntity.setAvgexrate(avgexrate);
			customneedEntity.setPaycurrency(paycurrency);
			customneedEntity.setPaymethod(paymethod);
			customneedEntity.setRemark(remark);
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
				Double formprice = null;
				if (!Util.isEmpty(airmap.get("formprice"))) {
					formprice = Double.valueOf((String) airmap.get("formprice"));
				}
				//销售价
				Double price = null;
				if (!Util.isEmpty(airmap.get("price"))) {
					price = Double.valueOf((String) airmap.get("price"));
				}
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
		//获取财务信息
		String financeData = request.getParameter("financeData");
		saveFinanceData(financeData, orderType);
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 保存财务信息
	 */
	@SuppressWarnings("unchecked")
	private Object saveFinanceData(String financeData, Integer orderType) {
		Map<String, String> financeMap = JsonUtil.fromJson(financeData, Map.class);
		//财务信息id
		String id = financeMap.get("id");
		//订单id
		Integer orderid = null;
		if (!Util.isEmpty(financeMap.get("orderid"))) {
			orderid = Integer.valueOf(financeMap.get("orderid"));
		}
		//客户团号
		String cusgroupnum = financeMap.get("cusgroupnum");
		//类型
		Integer teamtype = null;
		if (!Util.isEmpty(financeMap.get("teamtype"))) {
			teamtype = Integer.valueOf(financeMap.get("teamtype"));
		}
		//内陆跨海
		String neilu = financeMap.get("neilu");
		//人数
		Integer personcount = null;
		if (!Util.isEmpty(financeMap.get("personcount"))) {
			personcount = Integer.valueOf(financeMap.get("personcount"));
		}
		//结算状态
		Integer billingstatus = null;
		if (!Util.isEmpty(financeMap.get("billingstatus"))) {
			billingstatus = Integer.valueOf(financeMap.get("billingstatus"));
		}
		//如澳时间
		Date enterausdate = null;
		if (!Util.isEmpty(financeMap.get("enterausdate"))) {
			enterausdate = DateUtil.string2Date(financeMap.get("enterausdate"), DateUtil.FORMAT_YYYY_MM_DD);
		}
		//出澳时间
		Date outausdate = null;
		if (!Util.isEmpty(financeMap.get("outausdate"))) {
			outausdate = DateUtil.string2Date(financeMap.get("outausdate"), DateUtil.FORMAT_YYYY_MM_DD);
		}
		//应收
		Double receivable = null;
		if (!Util.isEmpty(financeMap.get("receivable"))) {
			receivable = Double.valueOf(financeMap.get("receivable"));
		}
		//实收合计
		Double incometotal = null;
		if (!Util.isEmpty(financeMap.get("incometotal"))) {
			incometotal = Double.valueOf(financeMap.get("incometotal"));
		}
		//成本合计
		Double costtotal = null;
		if (!Util.isEmpty(financeMap.get("costtotal"))) {
			costtotal = Double.valueOf(financeMap.get("costtotal"));
		}
		//应返合计
		Double returntotal = null;
		if (!Util.isEmpty(financeMap.get("returntotal"))) {
			returntotal = Double.valueOf(financeMap.get("returntotal"));
		}
		//利润合计
		Double profittotal = null;
		if (!Util.isEmpty(financeMap.get("profittotal"))) {
			profittotal = Double.valueOf(financeMap.get("profittotal"));
		}
		//减免
		Double relief = null;
		if (!Util.isEmpty(financeMap.get("relief"))) {
			relief = Double.valueOf(financeMap.get("relief"));
		}
		//开票日期
		Date billingdate = null;
		if (!Util.isEmpty(financeMap.get("billingdate"))) {
			billingdate = DateUtil.string2Date(financeMap.get("billingdate"), DateUtil.FORMAT_YYYY_MM_DD);
		} else if (orderType.equals(OrderStatusEnum.BILLING.intKey())) {
			billingdate = new Date();
		}
		//销售人员
		String salesperson = financeMap.get("salesperson");
		//开票人
		String issuer = financeMap.get("issuer");
		TFinanceInfoEntity financeInfo = new TFinanceInfoEntity();
		financeInfo.setOrderid(orderid);
		financeInfo.setCusgroupnum(cusgroupnum);
		financeInfo.setTeamtype(teamtype);
		financeInfo.setNeilu(neilu);
		financeInfo.setPersoncount(personcount);
		financeInfo.setBillingstatus(billingstatus);
		financeInfo.setEnterausdate(enterausdate);
		financeInfo.setOutausdate(outausdate);
		financeInfo.setReceivable(receivable);
		financeInfo.setIncometotal(incometotal);
		financeInfo.setCosttotal(costtotal);
		financeInfo.setReturntotal(returntotal);
		financeInfo.setProfittotal(profittotal);
		financeInfo.setRelief(relief);
		financeInfo.setBillingdate(billingdate);
		financeInfo.setSalesperson(salesperson);
		financeInfo.setIssuer(issuer);
		if (Util.isEmpty(id)) {
			dbDao.insert(financeInfo);
		} else {
			financeInfo.setId(Integer.valueOf(id));
			dbDao.update(financeInfo);
		}
		return null;
	}

	/**
	 * 下载模板
	 * <p>
	 * 游客模板下载
	 *
	 * @param request
	 * @param response
	 * @return 
	 */
	public Object downloadVisitorTemplate(HttpServletRequest request, HttpServletResponse response) {
		OutputStream os = null;
		try {
			String filepath = request.getServletContext().getRealPath(EXCEL_PATH);
			String path = filepath + File.separator + FILE_EXCEL_NAME;
			File file = new File(path);// path是根据日志路径和文件名拼接出来的
			String filename = file.getName();// 获取日志文件名称
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);// 输出文件
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	/**
	 *导入游客模板
	 * <p>
	 * TODO导入游客模板
	 *
	 * @param file
	 * @param request
	 * @return TODO
	 */
	public Object importVisitor(TempFile file, HttpServletRequest request) {

		String dingdanid = request.getParameter("dingdanid");
		try {
			InputStream is = new FileInputStream(file.getFile());
			ExcelReader excelReader = new ExcelReader();
			//获取Excel模板第二行之后的数据
			Map<Integer, String[]> map = excelReader.readExcelContent(is);
			List<TVisitorInfoEntity> visitors = new ArrayList<TVisitorInfoEntity>();
			for (int i = 1; i <= map.size(); i++) {
				String[] row = map.get(i);
				TVisitorInfoEntity visitor = new TVisitorInfoEntity();
				//订单id
				visitor.setOrdernum(Integer.valueOf(dingdanid));
				//游客姓名
				visitor.setVisitorname(row[0]);
				//游客电话
				visitor.setPhonenum(row[1]);
				visitors.add(visitor);
			}
			//导入数据库
			dbDao.insert(visitors);
			//删除备份文件
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;

	}

	public Object addPnr(HttpServletRequest request) {
		String dingdanid = request.getParameter("dingdanid");
		String needid = request.getParameter("needid");
		Map<String, Object> result = new HashMap<String, Object>();
		//游客信息
		List<TVisitorInfoEntity> visitors = dbDao.query(TVisitorInfoEntity.class,
				Cnd.where("ordernum", "=", dingdanid), null);
		String includeall = sqlManager.get("get_customneed_visitor");
		Sql includeallsql = Sqls.create(includeall);
		//该客户需求已经使用的游客信息
		List<Record> includevisitor = dbDao.query(includeallsql, Cnd.where("tpi.needid", "=", needid), null);
		//删除已使用的游客信息
		List<TVisitorInfoEntity> removevisitor = new ArrayList<TVisitorInfoEntity>();
		for (TVisitorInfoEntity visitor : visitors) {
			boolean flag = false;
			for (Record record : includevisitor) {
				if (visitor.getId() == record.getInt("id")) {
					flag = true;
				}
			}
			if (flag) {
				removevisitor.add(visitor);
			}
		}
		visitors.removeAll(removevisitor);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		result.put("visitors", visitors);
		result.put("bzcode", bzcode);
		result.put("needid", needid);
		return result;
	}

	/**
	 * 添加PNR信息
	 * <p>
	 * TODO添加PNR信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addPnrInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String needid = request.getParameter("needid");
		String pnr = request.getParameter("pnr");
		String loginid = request.getParameter("loginid");
		String peoplecount = request.getParameter("peoplecount");
		String currency = request.getParameter("currency");
		String costprice = request.getParameter("costprice");
		String costpricesum = request.getParameter("costpricesum");
		String salesprice = request.getParameter("salesprice");
		String salespricesum = request.getParameter("salespricesum");
		String visitor = request.getParameter("visitor");
		TPnrInfoEntity pnrinfo = new TPnrInfoEntity();
		pnrinfo.setPNR(pnr);
		if (!Util.isEmpty(costprice)) {
			pnrinfo.setCostprice(Double.valueOf(costprice));
		}
		if (!Util.isEmpty(costpricesum)) {
			pnrinfo.setCostpricesum(Double.valueOf(costpricesum));
		}
		pnrinfo.setCurrency(currency);
		pnrinfo.setLoginid(loginid);
		if (!Util.isEmpty(peoplecount)) {
			pnrinfo.setPeoplecount(Integer.valueOf(peoplecount));
		}
		if (!Util.isEmpty(salesprice)) {
			pnrinfo.setSalesprice(Double.valueOf(salesprice));
		}
		if (!Util.isEmpty(salespricesum)) {
			pnrinfo.setSalespricesum(Double.valueOf(salespricesum));
		}
		pnrinfo.setNeedid(Integer.valueOf(needid));
		pnrinfo.setUserid(new Long(user.getId()).intValue());
		pnrinfo.setOptime(new Date());
		TPnrInfoEntity insert = dbDao.insert(pnrinfo);
		Iterable<String> visitors = Splitter.on(",").split(visitor);
		List<TVisitorsPnrEntity> visitorpnrs = new ArrayList<TVisitorsPnrEntity>();
		for (String str : visitors) {
			TVisitorsPnrEntity visitorpnr = new TVisitorsPnrEntity();
			visitorpnr.setPNRid(insert.getId());
			visitorpnr.setVisitorslistid(Integer.valueOf(str));
			visitorpnrs.add(visitorpnr);

		}
		dbDao.insert(visitorpnrs);
		return null;
	}

	/**
	 * 异步加载PNR数据
	 * TODO异步加载PNR数据
	 *
	 * @param request
	 * @return TODO异步加载PNR数据
	 */
	public Object loadPNRdata(HttpServletRequest request) {
		String customneedid = request.getParameter("customneedid");
		List<TPnrInfoEntity> query = dbDao.query(TPnrInfoEntity.class, Cnd.where("needid", "=", customneedid), null);
		return query;
	}

	/**
	 * 打开pnr详情页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object pnrDetailPage(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String pnrid = request.getParameter("pnrid");
		//PNR信息
		TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, Long.valueOf(pnrid));
		String sqlString = sqlManager.get("get_pnr_visitor_info");
		Sql sql = Sqls.create(sqlString);
		//pnr中包含的游客信息
		List<Record> visitors = dbDao.query(sql, Cnd.where("tvp.PNRid", "=", pnrinfo.getId()), null);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		result.put("pnrinfo", pnrinfo);
		result.put("visitors", visitors);
		result.put("bzcode", bzcode);
		return result;

	}

	/**
	 * 加载游客的详细信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object showVisitorInfo(Long id) {
		return dbDao.fetch(TVisitorInfoEntity.class, id);

	}

	/**
	 * 跳转到编辑信息页面
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editPnr(HttpServletRequest request) {
		String pnrid = request.getParameter("id");
		//PNR信息
		TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, Long.valueOf(pnrid));
		//客户信息
		TOrderCustomneedEntity custom = dbDao.fetch(TOrderCustomneedEntity.class, Long.valueOf(pnrinfo.getNeedid()));
		Map<String, Object> result = new HashMap<String, Object>();
		String sqlString = sqlManager.get("get_pnr_visitor_info");
		Sql sql = Sqls.create(sqlString);
		//pnr中包含的游客信息 
		List<Record> include = dbDao.query(sql, Cnd.where("tvp.PNRid", "=", pnrinfo.getId()), null);
		String includeall = sqlManager.get("get_customneed_visitor");
		Sql includeallsql = Sqls.create(includeall);
		//该客户需求已经使用的游客信息
		List<Record> includevisitor = dbDao.query(includeallsql, Cnd.where("tpi.needid", "=", pnrinfo.getNeedid()),
				null);
		//所有的游客信息
		List<TVisitorInfoEntity> visitors = dbDao.query(TVisitorInfoEntity.class,
				Cnd.where("ordernum", "=", custom.getOrdernum()), null);
		//删除已使用的游客信息
		List<TVisitorInfoEntity> removevisitor = new ArrayList<TVisitorInfoEntity>();
		for (TVisitorInfoEntity visitor : visitors) {
			boolean flag = false;
			for (Record record : includevisitor) {
				if (visitor.getId() == record.getInt("id")) {
					flag = true;
				}
			}
			if (flag) {
				removevisitor.add(visitor);
			}
		}
		visitors.removeAll(removevisitor);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		result.put("include", include);
		result.put("pnrinfo", pnrinfo);
		result.put("visitors", visitors);
		result.put("bzcode", bzcode);
		return result;
	}

	/**
	 * 保存编辑PNR信息页面
	 * <p>
	 * 保存编辑PNR信息页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editPnrInfo(HttpServletRequest request) {
		String pnr = request.getParameter("pnr");
		String loginid = request.getParameter("loginid");
		String peoplecount = request.getParameter("peoplecount");
		String currency = request.getParameter("currency");
		String costprice = request.getParameter("costprice");
		String costpricesum = request.getParameter("costpricesum");
		String salesprice = request.getParameter("salesprice");
		String salespricesum = request.getParameter("salespricesum");
		String visitor = request.getParameter("visitor");
		String pnrid = request.getParameter("pnrid");
		TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, Long.valueOf(pnrid));
		pnrinfo.setPNR(pnr);
		pnrinfo.setLoginid(loginid);
		if (!Util.isEmpty(peoplecount)) {
			pnrinfo.setPeoplecount(Integer.valueOf(peoplecount));
		}
		pnrinfo.setCurrency(currency);
		pnrinfo.setCostprice(Double.valueOf(costprice));
		pnrinfo.setCostpricesum(Double.valueOf(costpricesum));
		pnrinfo.setSalesprice(Double.valueOf(salesprice));
		pnrinfo.setSalespricesum(Double.valueOf(salespricesum));
		Iterable<String> visitors = Splitter.on(",").split(visitor);
		List<TVisitorsPnrEntity> before = dbDao.query(TVisitorsPnrEntity.class,
				Cnd.where("pNRid", "=", pnrinfo.getId()), null);
		List<TVisitorsPnrEntity> after = new ArrayList<TVisitorsPnrEntity>();
		for (String Str : visitors) {
			TVisitorsPnrEntity visitoepnrs = new TVisitorsPnrEntity();
			visitoepnrs.setPNRid(pnrinfo.getId());
			visitoepnrs.setVisitorslistid(Integer.valueOf(Str));
			after.add(visitoepnrs);
		}
		//更新游客信息
		dbDao.updateRelations(before, after);
		return null;

	}

	/**
	 * 跳转到内陆跨海出票收款页面
	 * <p>
	 * TODO跳转到内陆跨海出票收款页面
	 *
	 * @param request
	 * @return 
	 */
	public Object seaInvoice(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = request.getParameter("ids");
		String sqlString = sqlManager.get("get_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.limit();
		cnd.and("tuo.ordersstatus", "=", OrderStatusEnum.TICKETING.intKey());
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.FIT.intKey());
		cnd.and("tuo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//计算合计金额
		double sumincome = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("incometotal"))) {
				Double incometotal = (Double) record.get("incometotal");
				sumincome += incometotal;
			}
		}
		result.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("yhkSelect", yhkSelect);
		result.put("ids", ids);
		result.put("sumincome", sumincome);
		return result;

	}

	/**
	 * 跳转到内陆跨海出票付款页面
	 * <p>
	 * TODO跳转到内陆跨海出票付款页面
	 *
	 * @param request
	 * @return TODO
	 */
	@SuppressWarnings("deprecation")
	public Object seaPayApply(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = request.getParameter("ids");
		String sqlString = sqlManager.get("get_sea_payapply_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.limit();
		cnd.and("tuo.ordersstatus", "=", OrderStatusEnum.TICKETING.intKey());
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.FIT.intKey());
		cnd.and("tpi.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		result.put("orders", orders);
		try {
			result.put("bzSelect", externalInfoService.findDictInfoByName("", BIZHONGCODE));
			result.put("ytSelect", externalInfoService.findDictInfoByName("", FKYTCODE));
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		result.put("user", user);
		result.put("ids", ids);
		return result;
	}

	/**
	 * TODO提交收款信息
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveSeaInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String ids = request.getParameter("ids");
		String bankcardid = request.getParameter("bankcardid");
		String bankcardname = request.getParameter("bankcardname");
		String bankcardnum = request.getParameter("bankcardnum");
		String billurl = request.getParameter("billurl");
		String sumincome = request.getParameter("sumincome");
		TReceiveEntity receiveEntity = new TReceiveEntity();
		receiveEntity.setBankcardid(Integer.valueOf(bankcardid));
		receiveEntity.setBankcardname(bankcardname);
		receiveEntity.setBankcardnum(bankcardnum);
		receiveEntity.setReceivedate(new Date());
		receiveEntity.setUserid(new Long(user.getId()).intValue());
		receiveEntity.setStatus(AccountReceiveEnum.RECEIVINGMONEY.intKey());
		//客户名称还未填写
		receiveEntity.setCustomename("");
		if (!Util.isEmpty(sumincome)) {
			receiveEntity.setSum(Double.valueOf(sumincome));
		}
		//保存收款信息
		TReceiveEntity insert = dbDao.insert(receiveEntity);
		Iterable<String> split = Splitter.on(",").split(ids);
		//组装订单收款表list
		List<TOrderReceiveEntity> orderreceives = new ArrayList<TOrderReceiveEntity>();
		//更新订单收款状态List
		List<TUpOrderEntity> orders = new ArrayList<TUpOrderEntity>();
		for (String str : split) {
			TOrderReceiveEntity orderreceive = new TOrderReceiveEntity();
			orderreceive.setReceiveid(insert.getId());
			orderreceive.setOrderid(Integer.valueOf(str));
			orderreceive.setReceivestatus(0);
			orderreceives.add(orderreceive);
			//订单信息
			TUpOrderEntity order = dbDao.fetch(TUpOrderEntity.class, Long.valueOf(str));
			order.setReceivestatus(AccountReceiveEnum.RECEIVINGMONEY.intKey());
			orders.add(order);
		}
		//更新订单状态
		dbDao.update(orders);
		//更新订单收款表
		dbDao.insert(orderreceives);
		TReceiveBillEntity receiveBill = new TReceiveBillEntity();
		receiveBill.setReceiptUrl(billurl);
		receiveBill.setReceiveid(insert.getId());
		//更新水单表
		dbDao.insert(receiveBill);
		return null;

	}

	/**
	 * 保存付款信息
	 * <p>
	 * TODO保存付款信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveSeaPayApply(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String ids = request.getParameter("ids");
		String purpose = request.getParameter("purpose");
		String payCurrency = request.getParameter("payCurrency");
		String approver = request.getParameter("approver");
		TPayEntity payEntity = new TPayEntity();
		payEntity.setPurpose(Integer.valueOf(purpose));
		payEntity.setPayCurrency(Integer.valueOf(payCurrency));
		payEntity.setProposer(new Long(user.getId()).intValue());
		payEntity.setApprover(approver);
		TPayEntity insert = dbDao.insert(payEntity);
		Iterable<String> split = Splitter.on(",").split(ids);
		List<TPayPnrEntity> paypnrs = new ArrayList<TPayPnrEntity>();
		//更新PNR付款状态List
		List<TPnrInfoEntity> pnrinfos = new ArrayList<TPnrInfoEntity>();
		for (String str : split) {
			TPayPnrEntity paypnr = new TPayPnrEntity();
			paypnr.setPayId(insert.getId());
			paypnr.setPnrId(Integer.valueOf(str));
			paypnrs.add(paypnr);
			//PNR更新状态
			TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, Long.valueOf(str));
			pnrinfo.setOrderPnrStatus(AccountPayEnum.APPROVAL.intKey());
			pnrinfos.add(pnrinfo);
		}
		//更新pnr状态
		dbDao.update(pnrinfos);
		dbDao.insert(paypnrs);
		return null;

	}

	/**
	 * 收付款（收款）列表数据
	 * <p>
	 * T收付款（收款）列表数据
	 *
	 * @param sqlParamForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listShouFuKuanData(ShouKuanParamFrom sqlParamForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Integer userid = Long.valueOf(user.getId()).intValue();
		sqlParamForm.setUserid(userid);
		Map<String, Object> datatabledata = this.listPage4Datatables(sqlParamForm);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) datatabledata.get("data");
		for (Record record : list) {
			record.put("username", user.getUserName());
			String sqlString = sqlManager.get("get_shoukuan_order_list");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.limit();
			cnd.and("tr.id", "=", record.get("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
			record.put("receiveenum", EnumUtil.enum2(AccountReceiveEnum.class));
		}
		datatabledata.remove("data");
		datatabledata.put("data", list);
		return datatabledata;

	}

	/**
	 * TODO打开开发票页面
	 * <p>
	 * TODO(打开开发票页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object openInvoice(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		//付款id
		String id = request.getParameter("id");
		//付款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(id));
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class, Cnd.where("receiveid", "=", id), null);
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("get_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.limit();
		cnd.and("tuo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//计算合计金额
		double sumincome = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("incometotal"))) {
				Double incometotal = (Double) record.get("incometotal");
				sumincome += incometotal;
			}
		}
		//订单信息
		result.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TReceiveBillEntity> query2 = dbDao.query(TReceiveBillEntity.class, Cnd.where("receiveid", "=", id), null);
		//银行卡下拉
		result.put("yhkSelect", yhkSelect);
		//订单信息id
		result.put("ids", ids);
		result.put("id", id);
		result.put("receive", fetch);
		if (query2.size() > 0)
			result.put("bill", query2.get(0));

		return result;

	}

	/**
	 * 
	 * 付款列表数据
	 * <p>
	 * TODO付款列表数据
	 *
	 * @param sqlParamForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listFuKuanData(FuKuanParamForm sqlParamForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Integer userid = Long.valueOf(user.getId()).intValue();
		sqlParamForm.setUserid(userid);
		Map<String, Object> datatabledata = this.listPage4Datatables(sqlParamForm);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) datatabledata.get("data");
		for (Record record : list) {
			record.put("username", user.getUserName());
			record.put("paystatusenum", EnumUtil.enum2(AccountPayEnum.class));
		}
		datatabledata.remove("data");
		datatabledata.put("data", list);
		return datatabledata;
	}

	/**
	 * 
	 * 打开收发票页面
	 * <p>
	 * TODO打开收发票页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object receiveInvoice(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String sqlString = sqlManager.get("get_fukuan_info_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.limit();
		cnd.and("tpi.id", "=", id);
		List<Record> pnrinfo = dbDao.query(sql, cnd, null);
		double sumjine = 0;
		for (Record record : pnrinfo) {
			if (!Util.isEmpty(record.get("salespricesum"))) {
				Double salespricesum = (Double) record.get("salespricesum");
				sumjine += Double.valueOf(salespricesum);
			}
		}
		result.put("pnrinfo", pnrinfo);
		List<TPayPnrEntity> query = dbDao.query(TPayPnrEntity.class, Cnd.where("pnrId", "=", id), null);
		TPayEntity payinfo = new TPayEntity();
		String billurl = "";
		if (query.size() > 0) {
			payinfo = dbDao.fetch(TPayEntity.class, query.get(0).getId().longValue());
			if (!Util.isEmpty(payinfo)) {
				List<TPayReceiptEntity> query2 = dbDao.query(TPayReceiptEntity.class,
						Cnd.where("payId", "=", payinfo.getId()), null);
				if (query2.size() > 0) {
					billurl = query2.get(0).getReceiptUrl();
				}
			}
		}
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("id", id);
		result.put("billurl", billurl);
		result.put("yhkSelect", yhkSelect);
		result.put("payinfo", payinfo);
		//总金额
		result.put("sumjine", sumjine);
		return result;

	}

	/**
	 * 内陆跨海出票收款列表数据
	 * <p>
	 * TODO内陆跨海出票收款列表数据
	 *
	 * @param sqlform
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object listPayData(PayApplyListForm sqlform, HttpServletRequest request) {

		//获取当前登录用户
		HttpSession session = request.getSession();
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		sqlform.setUserId(new Long(user.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlform);
		return listData;

	}
}
