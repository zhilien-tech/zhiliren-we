package com.linyun.airline.admin.invoicemanage.invoiceinfo.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TInvoiceInfoAddForm;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TInvoiceInfoSqlForm;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TInvoiceInfoUpdateForm;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.service.InvoiceinfoViewService;
import com.linyun.airline.admin.order.inland.form.KaiInvoiceParamForm;
import com.linyun.airline.admin.order.inland.form.ShouInvoiceParamForm;
import com.linyun.airline.admin.order.inland.service.InlandInvoiceService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/invoicemanage/invoiceinfo")
@Filters
public class InvoiceinfoModule {

	@Inject
	private InvoiceinfoViewService invoiceinfoViewService;

	@Inject
	private InlandInvoiceService inlandInvoiceService;

	/**
	 * 分页查询
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TInvoiceInfoSqlForm sqlForm, @Param("..") final Pager pager) {
		//return invoiceinfoViewService.listPage(sqlForm, null);
		return null;
	}

	/**
	 * 开发票分页查询
	 */
	@At
	public Object listData(@Param("..") final KaiInvoiceParamForm sqlForm, HttpServletRequest request) {
		return inlandInvoiceService.listKaiInvoiceData(sqlForm, request);
	}

	/**
	 * 收发票列表
	 */
	@At
	@POST
	public Object listShouInvoiceData(@Param("..") ShouInvoiceParamForm sqlForm, HttpServletRequest request) {
		return inlandInvoiceService.listShouInvoiceData(sqlForm, request);
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
	public Object add(@Param("..") TInvoiceInfoAddForm addForm) {
		return invoiceinfoViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return invoiceinfoViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TInvoiceInfoUpdateForm updateForm) {
		return invoiceinfoViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		invoiceinfoViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		invoiceinfoViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}