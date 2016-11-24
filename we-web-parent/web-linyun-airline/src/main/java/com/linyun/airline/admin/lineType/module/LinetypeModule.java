package com.linyun.airline.admin.lineType.module;

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

import com.linyun.airline.admin.lineType.service.LinetypeViewService;
import com.linyun.airline.forms.TLineTypeAddForm;
import com.linyun.airline.forms.TLineTypeForm;
import com.linyun.airline.forms.TLineTypeUpdateForm;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/linetype")
@Filters({//@By(type = AuthFilter.class)
})
public class LinetypeModule {

	private static final Log log = Logs.get();

	@Inject
	private LinetypeViewService linetypeViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TLineTypeForm sqlParamForm, @Param("..") final Pager pager) {
		return linetypeViewService.listPage(sqlParamForm, pager);
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
	public Object add(@Param("..") TLineTypeAddForm addForm) {
		return linetypeViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return linetypeViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TLineTypeUpdateForm updateForm) {
		return linetypeViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		linetypeViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		linetypeViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}