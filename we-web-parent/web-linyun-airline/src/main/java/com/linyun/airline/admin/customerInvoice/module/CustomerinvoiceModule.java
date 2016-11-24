package com.linyun.airline.admin.customerInvoice.module;

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

import com.linyun.airline.admin.customerInvoice.service.CustomerinvoiceViewService;
import com.linyun.airline.forms.TCustomerInvoiceAddForm;
import com.linyun.airline.forms.TCustomerInvoiceForm;
import com.linyun.airline.forms.TCustomerInvoiceUpdateForm;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/customerinvoice")
@Filters({//@By(type = AuthFilter.class)
})
public class CustomerinvoiceModule {

	private static final Log log = Logs.get();

	@Inject
	private CustomerinvoiceViewService customerinvoiceViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TCustomerInvoiceForm sqlParamForm, @Param("..") final Pager pager) {
		return customerinvoiceViewService.listPage(sqlParamForm, pager);
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
	public Object add(@Param("..") TCustomerInvoiceAddForm addForm) {
		return customerinvoiceViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return customerinvoiceViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCustomerInvoiceUpdateForm updateForm) {
		return customerinvoiceViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		customerinvoiceViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		customerinvoiceViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}