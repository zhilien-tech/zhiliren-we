package com.linyun.airline.admin.customneeds.module;

import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.customneeds.form.PlanMakeSqlForm;
import com.linyun.airline.admin.customneeds.service.PlanMakeService;
import com.linyun.airline.forms.TPlanInfoAddForm;

@IocBean
@At("/admin/customneeds")
@Filters({//@By(type = AuthFilter.class)
})
public class PlanMakeModule {

	private static final Log log = Logs.get();

	@Inject
	private PlanMakeService planMakeService;

	/**
	 * 获取旅行社下拉
	 */
	@At
	@POST
	public Object getTravelNameSelect(@Param("travelname") String TravelName) {
		return planMakeService.getTravelNameSelect(TravelName);
	}

	/**
	 * 获取航班下拉
	 */
	@At
	@POST
	public Object getAirLineSelect(@Param("airlinename") String airlinename) {
		return planMakeService.getAirLineSelect(airlinename);
	}

	/**
	 * 获取城市下拉
	 */
	@At
	@POST
	public Object getCitySelect(@Param("cityname") String cityname) {
		return planMakeService.getCitySelect(cityname);
	}

	/**
	 * 获取联运城市下拉
	 */
	@At
	@POST
	public Object getUnionCitySelect(@Param("cityname") String cityname) {
		return planMakeService.getUnionCitySelect(cityname);
	}

	/**
	 * 制作计划
	 */
	@At
	@POST
	public Object airlineMakePlan(@Param("..") TPlanInfoAddForm addForm) {
		return planMakeService.airlineMakePlan(addForm);
	}

	/**
	 * 计划制作列表数据
	 */
	@At
	public Object listPlanMakeData(@Param("..") PlanMakeSqlForm sqlForm) {
		return planMakeService.listPage4Datatables(sqlForm);
	}

	/**
	 * 保存计划
	 */
	@At
	public Object savePlanData() {
		return planMakeService.savePlanData();
	}

	/**
	 * 导出东航模板
	 */
	@At
	public Object exportDongHangTemplate(HttpServletResponse response) {
		return planMakeService.exportDongHangTemplate(response);
	}

	/**
	 * 导出南航模板
	 */
	@At
	public Object exportNanHangTemplate(HttpServletResponse response) {
		return planMakeService.exportNanHangTemplate(response);
	}

	/**
	 * 导出凌云模板
	 */
	@At
	public Object exportLingYunTemplate(HttpServletResponse response) {
		return planMakeService.exportLingYunTemplate(response);
	}
}