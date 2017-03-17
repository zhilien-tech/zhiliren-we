/**
 * InternationalService.java
 * com.linyun.airline.admin.order.international.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.customneeds.service.EditPlanService;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PassengerTypeEnum;
import com.linyun.airline.admin.order.inland.enums.PayMethodEnum;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.admin.order.international.form.InternationalParamForm;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.util.ExcelReader;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TFinanceInfoEntity;
import com.linyun.airline.entities.TFlightInfoEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TPayReceiveRecordEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TVisitorInfoEntity;
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
 * @Date	 2017年3月15日 	 
 */
@IocBean
public class InternationalService extends BaseService<TUpOrderEntity> {

	//航班号字典代码
	private static final String AIRLINECODE = "HBH";
	//城市字典代码
	private static final String CITYCODE = "CFCS";
	//内陆跨海
	private static final String NLKHCODE = "NLKH";
	//币种
	private static final String BIZHONGCODE = "BZ";
	@Inject
	private EditPlanService editPlanService;
	@Inject
	private externalInfoService externalInfoService;

	/**
	 * 查询国际列表
	 * <p>
	 * TODO查询国际列表
	 * @param paramForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object internationalListData(InternationalParamForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		paramForm.setCompanyid(new Long(company.getId()).intValue());
		Map<String, Object> listPageData = this.listPage4Datatables(paramForm);
		List<Record> list = (List<Record>) listPageData.get("data");
		for (Record record : list) {
			List<TAirlineInfoEntity> query = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("planid", "=", record.get("id")).orderBy("leavedate", "asc"), null);
			record.put("airinfo", query);
			record.put("orderstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		}
		listPageData.remove("data");
		listPageData.put("data", list);
		return listPageData;
	}

	/**
	 * 跳转到添加国际订单页面
	 * <p>
	 * TODO跳转到添加国际订单页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveAddOrder(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
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
		orderinfo.setOrderstype(OrderTypeEnum.TEAM.intKey());
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
	 * 跳转到国际订单详情页
	 * <p>
	 * TODO跳转到国际订单详情页
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object internationalDetail(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		result.put("user", user);
		String orderid = request.getParameter("orderid");
		//订单数据
		TUpOrderEntity orderinfo = this.fetch(Long.valueOf(orderid));
		result.put("orderinfo", orderinfo);
		TCustomerInfoEntity custominfo = new TCustomerInfoEntity();
		if (!Util.isEmpty(orderinfo.getUserid())) {
			custominfo = dbDao.fetch(TCustomerInfoEntity.class, orderinfo.getUserid().longValue());
		}
		result.put("custominfo", custominfo);
		//异步加载
		TFinanceInfoEntity finance = dbDao.fetch(TFinanceInfoEntity.class, Cnd.where("orderid", "=", orderid));
		result.put("finance", finance);
		result.put("internationalstatusenum", EnumUtil.enum2(InternationalStatusEnum.class));
		result.put("passengertypeenum", EnumUtil.enum2(PassengerTypeEnum.class));
		//内陆跨海下拉
		List<DictInfoEntity> nlkhcode = new ArrayList<DictInfoEntity>();
		try {
			nlkhcode = externalInfoService.findDictInfoByName("", NLKHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("nlkhcode", nlkhcode);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		result.put("bzcode", bzcode);
		result.put("paymethodenum", EnumUtil.enum2(PayMethodEnum.class));
		result.put("receivestatus", PayReceiveTypeEnum.RECEIVE.intKey());
		result.put("paystatus", PayReceiveTypeEnum.PAY.intKey());
		return result;
	}

	/**
	 * 保存国际订单详情数据
	 * <p>
	 * TODO保存国际订单详情数据
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveInternationalDetail(HttpServletRequest request) {
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		Long orderedid = Long.valueOf((String) fromJson.get("orderedid"));
		TUpOrderEntity orderinfo = this.fetch(orderedid);
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		orderinfo.setOrdersstatus(orderType);
		dbDao.update(orderinfo);
		String financeData = request.getParameter("financeData");
		saveFinanceData(financeData, orderType);
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
		} else if (orderType.equals(InternationalStatusEnum.TICKETING.intKey())) {
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
	 * 跳转到添加航段页面
	 * <p>
	 * TODO跳转到添加航段页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addAirinfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		result.put("orderid", orderid);
		return result;
	}

	/**
	 * 保存航段信息
	 * <p>
	 * TODO保存航段信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveAirinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//判断是否是主航段
		Integer orderid = Integer.valueOf((String) fromJson.get("orderid"));
		List<TPnrInfoEntity> query = dbDao.query(TPnrInfoEntity.class, Cnd.where("orderid", "=", orderid), null);
		String pnr = (String) fromJson.get("pnr");
		TPnrInfoEntity pnrinfo = new TPnrInfoEntity();
		pnrinfo.setOrderid(orderid);
		pnrinfo.setPNR(pnr);
		//是否是主航段
		if (query.size() > 0) {
			pnrinfo.setMainsection(0);
		} else {
			pnrinfo.setMainsection(1);
		}
		TPnrInfoEntity insertpnr = dbDao.insert(pnrinfo);
		List<Map<String, String>> airinfos = (List<Map<String, String>>) fromJson.get("airinfos");
		List<TAirlineInfoEntity> ailines = new ArrayList<TAirlineInfoEntity>();
		for (Map<String, String> map : airinfos) {
			TAirlineInfoEntity airline = new TAirlineInfoEntity();
			airline.setLeavecity(map.get("leavecity"));
			airline.setArrvicity(map.get("arrivecity"));
			if (!Util.isEmpty(map.get("leavedate"))) {
				airline.setLeavedate(DateUtil.string2Date(map.get("leavedate"), DateUtil.FORMAT_YYYY_MM_DD));
			}
			airline.setLeavetime(map.get("leavetime"));
			airline.setArrivetime(map.get("arrivetime"));
			airline.setAilinenum(map.get("ailinenum"));
			airline.setPnrid(insertpnr.getId());
			ailines.add(airline);
		}
		return dbDao.insert(ailines);

	}

	/**
	 * 跳转到编辑航段信息页面
	 * <p>
	 * TODO跳转到编辑航段信息页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editAirinfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String pnrid = request.getParameter("pnrid");
		TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, Long.valueOf(pnrid));
		List<TAirlineInfoEntity> airlineinfo = dbDao.query(TAirlineInfoEntity.class, Cnd.where("pnrid", "=", pnrid),
				null);
		result.put("pnrinfo", pnrinfo);
		result.put("airinfo", airlineinfo);
		//准备城市下拉数据
		//List<DictInfoEntity> city = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", CITYCODE), null);
		List<TDepartureCityEntity> city = externalInfoService.findCityByCode("", CITYCODE);
		result.put("city", city);
		//航班号下拉
		result.put("airline", dbDao.query(TFlightInfoEntity.class, null, null));
		return result;
	}

	/**
	 * 保存编辑航段信息
	 * <p>
	 * TODO保存编辑航段信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveEditAirinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String data = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		//判断是否是主航段
		Integer pnrid = Integer.valueOf((String) fromJson.get("pnrid"));
		String pnr = (String) fromJson.get("pnr");
		TPnrInfoEntity pnrinfo = dbDao.fetch(TPnrInfoEntity.class, pnrid.intValue());
		pnrinfo.setPNR(pnr);
		dbDao.update(pnrinfo);
		List<Map<String, String>> airinfos = (List<Map<String, String>>) fromJson.get("airinfos");
		//查询之前的
		List<TAirlineInfoEntity> before = dbDao.query(TAirlineInfoEntity.class, Cnd.where("pnrid", "=", pnrid), null);
		//组装之后的
		List<TAirlineInfoEntity> ailines = new ArrayList<TAirlineInfoEntity>();
		for (Map<String, String> map : airinfos) {
			TAirlineInfoEntity airline = new TAirlineInfoEntity();
			airline.setLeavecity(map.get("leavecity"));
			airline.setArrvicity(map.get("arrivecity"));
			if (!Util.isEmpty(map.get("leavedate"))) {
				airline.setLeavedate(DateUtil.string2Date(map.get("leavedate"), DateUtil.FORMAT_YYYY_MM_DD));
			}
			airline.setLeavetime(map.get("leavetime"));
			airline.setArrivetime(map.get("arrivetime"));
			airline.setAilinenum(map.get("ailinenum"));
			airline.setPnrid(pnrid);
			ailines.add(airline);
		}
		dbDao.updateRelations(before, ailines);
		return null;
	}

	/**
	 * 上传游客信息
	 * <p>
	 * TODO上传游客信息
	 * @param file 
	 * @param pnrid 
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object uploadVisitorInfo(File file, Integer pnrid, HttpServletRequest request) {
		//String dingdanid = request.getParameter("dingdanid");
		//String pnrid = request.getParameter("pnrid");
		try {
			InputStream is = new FileInputStream(file);
			ExcelReader excelReader = new ExcelReader();
			//获取Excel模板第二行之后的数据
			Map<Integer, String[]> map = excelReader.readExcelContent(is);
			List<TVisitorInfoEntity> visitors = new ArrayList<TVisitorInfoEntity>();
			for (int i = 1; i <= map.size(); i++) {
				String[] row = map.get(i);
				TVisitorInfoEntity visitor = new TVisitorInfoEntity();
				//订单id
				visitor.setPnrid(Integer.valueOf(pnrid));
				//游客姓名
				visitor.setVisitorname(row[0]);
				//游客电话
				visitor.setPhonenum(row[1]);
				visitor.setGender(row[2]);
				visitor.setVisitortype(row[3]);
				visitor.setCardtype(row[4]);
				visitor.setCardnum(row[5]);
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

	/**
	 * 游客信息
	 * <p>
	 * TODO游客信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object visitorInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String pnrid = request.getParameter("pnrid");
		List<TVisitorInfoEntity> visitors = dbDao.query(TVisitorInfoEntity.class, Cnd.where("pnrid", "=", pnrid), null);
		result.put("visitors", visitors);
		return result;
	}

	/**
	 * 跳转到添加记录页面
	 * <p>
	 * TODO跳转到添加记录页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addRecord(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		String payreceivestatus = request.getParameter("payreceivestatus");
		String ordersstatus = request.getParameter("ordersstatus");
		result.put("orderid", orderid);
		result.put("recordtype", payreceivestatus);
		result.put("ordersstatus", ordersstatus);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		result.put("bzcode", bzcode);
		return result;
	}

	/**
	 * 保存记录
	 * <p>
	 * TODO保存记录
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveRecord(HttpServletRequest request, TPayReceiveRecordEntity payreceive) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		payreceive.setOpid(new Long(user.getId()).intValue());
		payreceive.setOptime(new Date());
		for (InternationalStatusEnum payenum : InternationalStatusEnum.values()) {
			if (Integer.valueOf(payreceive.getOrderstatus()).equals(payenum.intKey())) {
				payreceive.setOrderstatus(payenum.value());
				break;
			}
		}
		return dbDao.insert(payreceive);
	}

	/**
	 * 跳转到编辑记录页面
	 * <p>
	 * TODO跳转到编辑记录页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editRecord(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String recordid = request.getParameter("recordid");
		TPayReceiveRecordEntity recordinfo = dbDao.fetch(TPayReceiveRecordEntity.class, Long.valueOf(recordid));
		result.put("recordinfo", recordinfo);
		//币种下拉
		List<DictInfoEntity> bzcode = new ArrayList<DictInfoEntity>();
		try {
			bzcode = externalInfoService.findDictInfoByName("", BIZHONGCODE);
		} catch (Exception e) {
			e.printStackTrace();

		}
		result.put("bzcode", bzcode);
		return result;
	}

	/**
	 *加载详情页面航程信息
	 * <p>
	 * TODO加载详情页面航程信息
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadAirlineInfo(HttpServletRequest request) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String orderid = request.getParameter("orderid");
		List<TPnrInfoEntity> pnrinfo = dbDao.query(TPnrInfoEntity.class, Cnd.where("orderid", "=", orderid), null);
		for (TPnrInfoEntity tPnrInfoEntity : pnrinfo) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pnrinfo", tPnrInfoEntity);
			List<TAirlineInfoEntity> airinfo = dbDao.query(TAirlineInfoEntity.class,
					Cnd.where("pnrid", "=", tPnrInfoEntity.getId()), null);
			map.put("airinfo", airinfo);
			result.add(map);
		}
		return result;
	}

	/**
	 * 加载详情页面收付款记录信息
	 * <p>
	 * TODO加载详情页面收付款记录信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object loadPayReceiveRecord(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderid = request.getParameter("orderid");
		List<TPayReceiveRecordEntity> record = dbDao.query(TPayReceiveRecordEntity.class,
				Cnd.where("orderid", "=", orderid), null);
		result.put("receivestatus", PayReceiveTypeEnum.RECEIVE.intKey());
		result.put("paystatus", PayReceiveTypeEnum.PAY.intKey());
		result.put("record", record);
		return result;
	}

	/**
	 * 保存编辑记录
	 * <p>
	 * TODO保存编辑记录
	 *
	 * @param request
	 * @param payreceive
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditRecord(HttpServletRequest request, TPayReceiveRecordEntity payreceive) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		payreceive.setOpid(new Long(user.getId()).intValue());
		payreceive.setOptime(new Date());
		return dbDao.update(payreceive);
	}

	/**
	 *编辑游客信息页面
	 * <p>
	 * TODO编辑游客信息页面
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object editVisitorInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String visitorid = request.getParameter("visitorid");
		TVisitorInfoEntity visitorinfo = dbDao.fetch(TVisitorInfoEntity.class, Long.valueOf(visitorid));
		result.put("visitorinfo", visitorinfo);
		return result;
	}

	/**
	 * 保存编辑游客信息
	 * <p>
	 * TODO保存编辑游客信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveEditVisitorInfo(HttpServletRequest request) {
		String id = request.getParameter("id");
		TVisitorInfoEntity visitorinfo = dbDao.fetch(TVisitorInfoEntity.class, Long.valueOf(id));
		visitorinfo.setVisitorname(request.getParameter("visitorname"));
		visitorinfo.setCardtype(request.getParameter("cardtype"));
		visitorinfo.setGender(request.getParameter("gender"));
		visitorinfo.setCardnum(request.getParameter("cardnum"));
		visitorinfo.setVisitortype(request.getParameter("visitortype"));
		return dbDao.update(visitorinfo);
	}

	/**
	 * 打开退票页面
	 * <p>
	 * TODO打开退票页面
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object backTicket(HttpServletRequest request) {

		return null;
	}
}