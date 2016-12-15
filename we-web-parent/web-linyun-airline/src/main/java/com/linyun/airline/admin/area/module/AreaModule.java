package com.linyun.airline.admin.area.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.area.service.AreaViewService;
import com.linyun.airline.forms.TAreaAddForm;
import com.linyun.airline.forms.TAreaForm;
import com.linyun.airline.forms.TAreaUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/area")
@Filters({//@By(type = AuthFilter.class)
})
public class AreaModule {
	@Inject
	private AreaViewService areaViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TAreaForm sqlParamForm, @Param("..") final Pager pager) {
		return areaViewService.listPage(sqlParamForm, pager);
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listAreaData(@Param("..") final TAreaForm sqlForm) {
		return areaViewService.listPage4Datatables(sqlForm);
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

	//区域查询
	@At
	@POST
	public Object areaSelect2(@Param("q") final String dictAreaName) {
		String typeCode = "QY";
		return areaViewService.areaSelect2(typeCode, dictAreaName);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TAreaAddForm addForm) {
		return areaViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return areaViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TAreaUpdateForm updateForm) {
		return areaViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		areaViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		areaViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 校验区域名称
	 */
	@At
	@POST
	public Object checkAreaNameExist(@Param("areaName") final String areaName, @Param("id") final Long areaId) {
		return areaViewService.checkAreaNameExist(areaName, areaId);
	}
}