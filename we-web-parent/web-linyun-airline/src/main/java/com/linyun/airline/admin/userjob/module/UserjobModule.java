package com.linyun.airline.admin.userjob.module;

import com.linyun.airline.admin.userjob.service.UserjobViewService;
import com.linyun.airline.forms.TUserJobUpdateForm;
import com.linyun.airline.forms.TUserJobAddForm;
import com.linyun.airline.forms.TUserJobForm;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.dao.pager.Pager;
import org.nutz.mvc.annotation.*;

import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/userjob")
@Filters({//@By(type = AuthFilter.class)
	})
public class UserjobModule {

	private static final Log log = Logs.get();
	
	@Inject
	private UserjobViewService userjobViewService;
	
	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TUserJobForm sqlParamForm,@Param("..") final Pager pager) {
    	return userjobViewService.listPage(sqlParamForm,pager);
    }
    
    /**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null ;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..")TUserJobAddForm addForm) {
		return userjobViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return userjobViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..")TUserJobUpdateForm updateForm) {
		return userjobViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		userjobViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		userjobViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}
	
}