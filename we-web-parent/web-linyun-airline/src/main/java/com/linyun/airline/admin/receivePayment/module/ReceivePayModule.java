/**
 * ReceivePayModule.java
 * com.linyun.airline.admin.receivePayment.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/admin/receivePay")
public class ReceivePayModule {

	private static final Log log = Logs.get();

	/**
	 * 跳转到 收付款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object receivePayment() {
		return null;
	}

	/**
	 * 跳转到 收款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmReceive() {
		return null;
	}

	/**
	 * 跳转到 付款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmPay() {
		return null;
	}
}
