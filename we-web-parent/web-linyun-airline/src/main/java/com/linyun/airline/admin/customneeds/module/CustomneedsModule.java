package com.linyun.airline.admin.customneeds.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.customneeds.form.TCustomNeedsSqlForm;
import com.linyun.airline.admin.customneeds.service.CustomneedsViewService;
import com.linyun.airline.forms.TCustomerneedsAddForm;
import com.linyun.airline.forms.TCustomerneedsForm;
import com.linyun.airline.forms.TCustomerneedsUpdateForm;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/customneeds")
@Filters({//@By(type = AuthFilter.class)
})
public class CustomneedsModule {

	private static final Log log = Logs.get();

	@Inject
	private CustomneedsViewService customneedsViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TCustomerneedsForm sqlParamForm, @Param("..") final Pager pager) {
		return null;
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
	public Object add(@Param("..") TCustomerneedsAddForm addForm) {
		return customneedsViewService.addCustomNeedsInfo(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return customneedsViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCustomerneedsUpdateForm updateForm) {
		return customneedsViewService.updateCustomNeedsInfo(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		customneedsViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		customneedsViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 查询客户需求列表数据
	 */
	@At
	public Object listData(@Param("..") final TCustomNeedsSqlForm sqlParamForm) {
		return customneedsViewService.listPage4Datatables(sqlParamForm);
	}

	/**
	 * 关闭客户需求信息
	 */
	@At
	@POST
	public Object closeCustomNeeds(@Param("id") long id) {
		return customneedsViewService.closeCustomNeeds(id);
	}

	/**
	 * 导入Excel
	 */
	@At
	@POST
	@Ok("jsp")
	@AdaptBy(type = UploadAdaptor.class, args = { "/uploadTemp", "8192", "UTF-8", "10" })
	public Object inportExcelData(@Param("excelFile") TempFile file, HttpServletRequest request) {
		return customneedsViewService.inportExcelData(file, request);
	}

	/**
	 * 下载模板
	 */
	@At
	@GET
	@Ok("json")
	public Object downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		return customneedsViewService.downloadTemplate(request, response);
	}
}