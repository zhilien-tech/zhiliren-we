package com.linyun.airline.admin.flightinfo.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.flightinfo.form.FlightInfoSqlForm;
import com.linyun.airline.admin.flightinfo.service.FlightinfoViewService;
import com.linyun.airline.forms.TFlightInfoAddForm;
import com.linyun.airline.forms.TFlightInfoForm;
import com.linyun.airline.forms.TFlightInfoUpdateForm;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/flightinfo")
@Filters({//@By(type = AuthFilter.class)
})
public class FlightinfoModule {

	private static final Log log = Logs.get();

	@Inject
	private FlightinfoViewService flightinfoViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TFlightInfoForm sqlParamForm, @Param("..") final Pager pager) {
		return flightinfoViewService.listPage(sqlParamForm, pager);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TFlightInfoAddForm addForm) {
		return flightinfoViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return flightinfoViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TFlightInfoUpdateForm updateForm) {
		return flightinfoViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		flightinfoViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		flightinfoViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	@At
	public Object listData(@Param("..") FlightInfoSqlForm sqlForm) {
		return flightinfoViewService.listPage4Datatables(sqlForm);
	}
}