/**
 * InlandInvoiceModule.java
 * com.linyun.airline.admin.order.inland.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;

import com.linyun.airline.admin.order.inland.service.InlandInvoiceService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月3日 	 
 */
@IocBean
@At("admin/inland")
public class InlandInvoiceModule {

	@Inject
	private InlandInvoiceService inlandInvoiceService;

	/**
	 * 保存付款发票信息
	 */
	@At
	@POST
	public Object saveInvoiceInfo(HttpServletRequest request) {
		return inlandInvoiceService.saveInvoiceInfo(request);
	}

	@At
	@POST
	public Object saveReceiveInvoiceInfo(HttpServletRequest request) {
		return inlandInvoiceService.saveReceiveInvoiceInfo(request);
	}
}
