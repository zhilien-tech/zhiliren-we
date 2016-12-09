/**
 * PlanMakeService.java
 * com.linyun.airline.admin.customneeds.service
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customneeds.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.util.ExportExcel;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TPlanInfoEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TPlanInfoAddForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月6日 	 
 */
@IocBean
public class PlanMakeService extends BaseService<TPlanInfoEntity> {

	@Inject
	private externalInfoService externalInfoService;

	private static final String TRAVELCODE = "LXS";
	private static final String AIRLINECODE = "HBH";
	private static final String CITYCODE = "CFCS";
	private static final String QUANGUOLIANYUN = "全国联运";

	private static final String[] EXCEL_DONGHANG_COLUMN_TITLE = { "去程日期", "去程航班", "行程", "回程日期", "回程航班", "人数", "组团社",
			"航程", "价格", "订单号" };
	private static final String EXCEL_DONGHANG_TITLE = "东航";
	private static final String[] EXCEL_NANHANG_COLUMN_TITLE = { "区域", "日期段", "合作旅行社", "行程", "去程航班", "回程航班",
			"去程班期（周几）", "回程班期（周几）", "天数(以国际航班起飞时间计算)", "每月周期第几周发团", "座位数", "团队个数/月", "去程日期", "回程日期", "申请编号", "价格", "TC" };
	private static final String EXCEL_NANHANG_TITLE = "南航";
	private static final String[] EXCEL_LINGYUN_COLUMN_TITLE = { "代理", "出发日期", "回程日期", "目的地", "人数", " ", "价格", "航班备注" };
	private static final String EXCEL_LINGYUN_TITLE = "凌云";
	private static final String[] EXCEL_LINGYUN_FIRST_DATA = { "Agent", "Dep Date", "Return Date", "Dest", "TCP",
			"RLOC", "Fare", "RMP(Optional)" };

	/**
	 * 获取旅行社名称下拉
	 * <p>
	 * 获取旅行社名称下拉
	 * @param name 
	 *
	 * @return 返回旅行社名称的下拉列表
	 */
	public Object getTravelNameSelect(String TravelName) {
		List<DictInfoEntity> travelSelect = new ArrayList<DictInfoEntity>();
		try {
			travelSelect = externalInfoService.findDictInfoByName(TravelName, this.TRAVELCODE);
			if (travelSelect.size() > 5) {
				travelSelect = travelSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return travelSelect;
	}

	/**
	 * 
	 * 获取航班号下拉框
	 * <p>
	 * 获取航班号下拉框
	 *
	 * @param airlinename
	 * @return 获取航班号下拉框
	 */
	public Object getAirLineSelect(String airlinename) {
		List<DictInfoEntity> airlineSelect = new ArrayList<DictInfoEntity>();
		try {
			airlineSelect = externalInfoService.findDictInfoByName(airlinename, this.AIRLINECODE);
			if (airlineSelect.size() > 5) {
				airlineSelect = airlineSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlineSelect;
	}

	/**
	 * 获取城市下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回城市下拉列表
	 */
	public Object getCitySelect(String cityname) {
		List<DictInfoEntity> citySelect = new ArrayList<DictInfoEntity>();
		try {
			citySelect = externalInfoService.findDictInfoByName(cityname, this.CITYCODE);
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citySelect;
	}

	/**
	 * 获取联运城市下拉
	 * <p>
	 * 获取联运城市下拉
	 *
	 * @param cityname
	 * @return 返回联运城市下拉列表
	 */
	public Object getUnionCitySelect(String cityname) {
		List<DictInfoEntity> citySelect = new ArrayList<DictInfoEntity>();
		try {
			citySelect = externalInfoService.findDictInfoByName(cityname, this.CITYCODE);
			DictInfoEntity dictInfoEntity = new DictInfoEntity();
			dictInfoEntity.setDictName(this.QUANGUOLIANYUN);
			citySelect.add(dictInfoEntity);
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citySelect;
	}

	/**
	 * 计划制作
	 * <p>
	 * 计划制作
	 * @param addForm
	 * @param session 
	 * @return 根据页面提交的数据进行航空公司模块的计划制作
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public Object airlineMakePlan(TPlanInfoAddForm addForm, HttpSession session) {
		List<TPlanInfoEntity> planInfos = new ArrayList<TPlanInfoEntity>();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//页面选择的起始日期
		Date startdate = DateUtil.string2Date(addForm.getStartdate(), DateUtil.FORMAT_YYYY_MM_DD);

		//页面选择的结束日期
		Date enddate = DateUtil.string2Date(addForm.getEnddate(), DateUtil.FORMAT_YYYY_MM_DD);
		//根据航班号获取航空公司名称
		String airLineName = this.getAirCompanyByAirLine(addForm.getLeaveairline());
		//自由制作计划
		if (!Util.isEmpty(addForm.getCalenderdate())) {
			//循环小日历的日期
			Iterable<String> calenders = Splitter.on(",").split(addForm.getCalenderdate());
			for (String str : calenders) {
				//选择的日期
				Date calendate = DateUtil.string2Date(str);
				if (DateUtil.dateBetween(calendate, startdate, enddate)) {
					TPlanInfoEntity planInfoEntity = new TPlanInfoEntity();
					planInfoEntity.setTravelname(addForm.getTravelname());
					planInfoEntity.setAirlinename(airLineName);
					planInfoEntity.setLeavesdate(calendate);
					planInfoEntity.setLeaveairline(addForm.getLeaveairline());
					planInfoEntity.setLeavescity(addForm.getLeavescity());
					planInfoEntity.setBacksdate(DateUtil.addDay(calendate, addForm.getDayscount()));
					planInfoEntity.setBackairline(addForm.getBackairline());
					planInfoEntity.setBackscity(addForm.getBackscity());
					planInfoEntity.setPeoplecount(addForm.getPeoplecount());
					planInfoEntity.setDayscount(addForm.getDayscount());
					planInfoEntity.setUnioncity(addForm.getUnioncity());
					planInfoEntity.setTeamtype(addForm.getTeamtype());
					planInfoEntity.setOpid(user.getId());
					planInfoEntity.setCompanyid(company.getId());
					planInfos.add(planInfoEntity);
				}
			}
		}
		//根据每周制作计划
		try {
			if (!Util.isEmpty(addForm.getWeekday())) {
				//获取每周几
				Iterable<String> weeks = Splitter.on(",").split(addForm.getWeekday());
				//将周几放入List
				List<Integer> weeklist = new ArrayList<Integer>();
				for (String week : weeks) {
					weeklist.add(Integer.valueOf(week));
				}
				//循环该日期段
				for (Date date = startdate; DateUtil.dateBetween(date, DateUtil.addDay(startdate, -1),
						DateUtil.addDay(enddate, 1)); date = DateUtil.addDay(date, 1)) {
					//若日期符合所要的周几  则该计划制作
					if (weeklist.contains(this.dayForWeek(date))) {
						TPlanInfoEntity planInfoEntity = new TPlanInfoEntity();
						planInfoEntity.setTravelname(addForm.getTravelname());
						planInfoEntity.setAirlinename(airLineName);
						planInfoEntity.setLeavesdate(date);
						planInfoEntity.setLeaveairline(addForm.getLeaveairline());
						planInfoEntity.setLeavescity(addForm.getLeavescity());
						planInfoEntity.setBacksdate(DateUtil.addDay(date, addForm.getDayscount()));
						planInfoEntity.setBackairline(addForm.getBackairline());
						planInfoEntity.setBackscity(addForm.getBackscity());
						planInfoEntity.setPeoplecount(addForm.getPeoplecount());
						planInfoEntity.setDayscount(addForm.getDayscount());
						planInfoEntity.setUnioncity(addForm.getUnioncity());
						planInfoEntity.setTeamtype(addForm.getTeamtype());
						planInfoEntity.setOpid(user.getId());
						planInfoEntity.setCompanyid(company.getId());
						planInfos.add(planInfoEntity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbDao.insert(planInfos);

	}

	/**
	 * 判断日期是周几
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 * @param pTime
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	private static int dayForWeek(Date pTime) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(pTime);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 每月第几周
	 */
	@SuppressWarnings("unused")
	private static int weekOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		return weekOfMonth;
	}

	/**
	 * 获取航班号所在的航空公司
	 */
	@SuppressWarnings("unused")
	private String getAirCompanyByAirLine(String airLine) {
		String sqlstring = sqlManager.get("airline_company_info");
		Sql sql = Sqls.create(sqlstring);
		sql.setCondition(Cnd.where("di2.dictName", "=", airLine));
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list.get(0).getString("dictcode");
	}

	/**
	 * 保存计划
	 * <p>
	 * 保存计划
	 * @param session 
	 * @return 保存计划
	 */
	public Object savePlanData(HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(Cnd.where("issave", "=", 0).and("companyid", "=", company.getId()));
		List<TPlanInfoEntity> planInfoEntities = this.listPageBean(TPlanInfoEntity.class, sql, null);
		for (TPlanInfoEntity tPlanInfoEntity : planInfoEntities) {
			tPlanInfoEntity.setIssave(1);
		}
		return dbDao.update(planInfoEntities);
	}

	/**
	 * 导出东航模板
	 * EXCEL导出东航模板
	 * @param response
	 * @param session 
	 * @return Excel导出东航模板
	 */
	public Object exportDongHangTemplate(HttpServletResponse response, HttpSession session) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd/MMM", Locale.ENGLISH);
			//定义Excel表格的列标题
			String[] excelColumnTitle = this.EXCEL_DONGHANG_COLUMN_TITLE;
			//设置Excel表格标题
			String title = this.EXCEL_DONGHANG_TITLE;
			//为Excel准备数据
			@SuppressWarnings("unchecked")
			List<Record> rerultList = getMakePlanData(session);
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			for (Record record : rerultList) {
				Object[] obj = { df.format(record.get("leavesdate")), record.get("leaveairline"),
						record.get("dayscount"), df.format(record.get("backsdate")), record.get("backairline"),
						record.get("peoplecount"), record.get("travelname"),
						record.get("leavescity") + "-" + record.get("backscity") + "-" + record.get("leavescity") };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取计划制作导出Excel数据
	 * @param session 
	 */
	private List<Record> getMakePlanData(HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(Cnd.where("issave", "=", 0).and("companyid", "=", company.getId())
				.orderBy("leavesdate", "asc"));
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> rerultList = (List<Record>) sql.getResult();
		return rerultList;
	}

	/**
	 * 获取城市代码
	 */
	private String getCityCode(String cityName) {
		String result = "";
		List<DictInfoEntity> DictInfo = new ArrayList<DictInfoEntity>();
		;
		try {
			DictInfo = externalInfoService.findDictInfoByName(cityName, this.CITYCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!Util.isEmpty(DictInfo)) {
			result = DictInfo.get(0).getDictCode();
		}
		return result;
	}

	/**
	 * 导出南航模板
	 * <p>
	 * 导出南航模板
	 *
	 * @param response
	 * @return 导出南航Excel模板
	 */
	public Object exportNanHangTemplate(HttpServletResponse response, HttpSession session) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
			//定义Excel表格的列标题
			String[] excelColumnTitle = this.EXCEL_NANHANG_COLUMN_TITLE;
			//设置Excel表格标题
			String title = this.EXCEL_NANHANG_TITLE;
			//为Excel准备数据
			@SuppressWarnings("unchecked")
			List<Record> rerultList = getMakePlanData(session);
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			for (Record record : rerultList) {
				Object[] obj = {
						" ",
						" ",
						record.get("travelname"),
						this.getCityCode((String) record.get("leavescity")) + "//"
								+ this.getCityCode((String) record.get("backscity")), record.get("leaveairline"),
						record.get("backairline"), this.dayForWeek((Date) record.get("leavesdate")),
						this.dayForWeek((Date) record.get("leavesdate")), record.get("dayscount"),
						this.weekOfMonth((Date) record.get("leavesdate")), record.get("peoplecount"), " ",
						df.format(record.get("leavesdate")), df.format(record.get("backsdate")) };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 导出凌云模板
	 * <p>
	 * 导出凌云模板
	 *
	 * @param response
	 * @param session 
	 * @return 导出凌云模板
	 */
	public Object exportLingYunTemplate(HttpServletResponse response, HttpSession session) {
		try {
			//设置Excel表格输入的日期格式
			DateFormat df = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
			DateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日");
			//定义Excel表格的列标题
			String[] excelColumnTitle = this.EXCEL_LINGYUN_COLUMN_TITLE;
			//设置Excel表格标题
			String title = this.EXCEL_LINGYUN_TITLE;
			//为Excel准备数据
			@SuppressWarnings("unchecked")
			List<Record> rerultList = getMakePlanData(session);
			//设置Excel数据
			List<Object[]> excelData = new ArrayList<Object[]>();
			excelData.add(this.EXCEL_LINGYUN_FIRST_DATA);
			for (Record record : rerultList) {
				Object[] obj = {
						record.get("travelname"),
						df1.format((Date) record.get("leavesdate")),
						df1.format((Date) record.get("backsdate")),
						this.getCityCode((String) record.get("leavescity")) + "//"
								+ this.getCityCode((String) record.get("backscity")), record.get("peoplecount") };
				excelData.add(obj);
			}
			ExportExcel excel = new ExportExcel(title, excelColumnTitle, excelData, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
