package com.linyun.airline.admin.drawback.grabfile.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileAddForm;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileSqlForm;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileUpdateForm;
import com.linyun.airline.admin.drawback.grabfile.service.GrabfileViewService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/drawback/grabfile")
@Filters
public class GrabfileModule {

	@Inject
	private GrabfileViewService grabfileViewService;

	/**
	 * 分页查询
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TGrabFileSqlForm sqlForm, @Param("..") final Pager pager) {
		return null;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final TGrabFileSqlForm sqlForm) {
		return grabfileViewService.listPage4Datatables(sqlForm);
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
	public Object add(@Param("..") TGrabFileAddForm addForm) {
		return grabfileViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return grabfileViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TGrabFileUpdateForm updateForm) {
		return grabfileViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		grabfileViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		grabfileViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}