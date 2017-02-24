/**
 * ReceivePayModule.java
 * com.linyun.airline.admin.receivePayment.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.receivePayment.form.InlandPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.service.ReceivePayService;
import com.linyun.airline.entities.TUserEntity;

@IocBean
@At("/admin/receivePay")
public class ReceivePayModule {

	private static final Log log = Logs.get();

	@Inject
	private ReceivePayService receivePayService;

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

	/**
	 * 
	 *會計付款分页
	 */
	@At
	public Object inlandPayList(@Param("..") final InlandPayListSearchSqlForm form, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		form.setLoginUserId(id);
		return receivePayService.listPage4Datatables(form);
	}

	/**
	 * 
	 *會計付款分页
	 */
	@At
	public Object inlandRecList(HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		return null;
	}

}
