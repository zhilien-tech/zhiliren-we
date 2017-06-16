package com.linyun.airline.admin.drawback.grabreport.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.drawback.grabfile.form.TPnrSystemMapSqlForm;
import com.linyun.airline.admin.drawback.grabfile.form.TPnrTeamSystemMapSqlForm;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportAddForm;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportSqlForm;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportUpdateForm;
import com.linyun.airline.admin.drawback.grabreport.service.GrabreportViewService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/drawback/grabreport")
public class GrabreportModule {

	@Inject
	private GrabreportViewService grabreportViewService;

	/**
	 * 分页查询
	 * @param sqlForm
	 * @param pager
	 */

	@At
	@Ok("jsp")
	public Object list(@Param("..") final TGrabReportSqlForm sqlForm, @Param("..") final Pager pager) {
		return null;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final TGrabReportSqlForm sqlForm) {
		//return grabreportViewService.listPage4Datatables(sqlForm);
		return grabreportViewService.queryReportListData(sqlForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object filePreview(@Param("id") long pid, @Param("flagType") long flagType) {
		return grabreportViewService.addFilePreview(pid, flagType);
	}

	/**
	 * 跳转到团队'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object interFilePreview(@Param("id") long pid, @Param("flagType") long flagType) {
		return grabreportViewService.addFilePreview(pid, flagType);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TGrabReportAddForm addForm, @Param("id") long pid) {
		return grabreportViewService.saveFilePreview(addForm, pid);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object addTeamData(@Param("..") TGrabReportAddForm addForm, @Param("id") long pid) {
		return grabreportViewService.saveFileTeamPreview(addForm, pid);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return grabreportViewService.updateFilePreview(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TGrabReportUpdateForm updateForm) {
		return grabreportViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		grabreportViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		grabreportViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 
	 * 根据输入显示公司名称
	 */
	@At
	@POST
	public Object selectPNRNames(@Param("p") final String findPNR, @Param("PNRName") final String companyName,
			@Param("flagType") final int flagType) {

		return this.grabreportViewService.selectPNRNames(findPNR, companyName, flagType);
	}

	/**
	 * 根据pnr向附件预览表中添加数据
	 */
	@At
	public Object findAndShowPNR(@Param("id") final long id, @Param("pnr") final String pnr,
			@Param("flagType") final int flagType) {
		//grabreportViewService.deleteById(id);
		System.out.println(id + pnr);
		this.grabreportViewService.findAndShowPNR(id, pnr, flagType);
		return JsonResult.success("");
	}

	/***
	 * 散客附件预览表的展示
	 */

	@At
	public Object listPnrSystem(@Param("..") final TPnrSystemMapSqlForm sqlForm) {
		return grabreportViewService.listPage4Datatables(sqlForm);
	}

	/***
	 * 团队附件预览表的展示
	 */

	@At
	public Object teamListPnrSystem(@Param("..") final TPnrTeamSystemMapSqlForm sqlForm) {
		return grabreportViewService.listPage4Datatables(sqlForm);
	}

	/**
	 * 关联与取消
	 */
	@At
	public Object changeRelationStatus(@Param("id") final long id, @Param("flag") final boolean flag) {
		grabreportViewService.changeRelationStatus(id, flag);
		return JsonResult.success("成功");
	}
}