package com.linyun.airline.admin.invoice.module;

import com.linyun.airline.admin.invoice.service.InvoiceViewService;
import com.linyun.airline.forms.TInvoiceUpdateForm;
import com.linyun.airline.forms.TInvoiceAddForm;
import com.linyun.airline.forms.TInvoiceForm;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.dao.pager.Pager;
import org.nutz.mvc.annotation.*;

import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/invoice")
@Filters({//@By(type = AuthFilter.class)
	})
public class InvoiceModule {

	private static final Log log = Logs.get();
	
	@Inject
	private InvoiceViewService invoiceViewService;
	
	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TInvoiceForm sqlParamForm,@Param("..") final Pager pager) {
    	return invoiceViewService.listPage(sqlParamForm,pager);
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
	public Object add(@Param("..")TInvoiceAddForm addForm) {
		return invoiceViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return invoiceViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..")TInvoiceUpdateForm updateForm) {
		return invoiceViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		invoiceViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		invoiceViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}
	
}