/**
 * ReceivePayModule.java
 * com.linyun.airline.admin.receivePayment.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.module;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.receivePayment.form.InlandPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InlandPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InlandRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.TSaveInlandPayAddFrom;
import com.linyun.airline.admin.receivePayment.service.ReceivePayService;
import com.linyun.airline.entities.TUserEntity;

@IocBean
@At("/admin/receivePay/inland")
public class ReceivePayModule {

	private static final Log log = Logs.get();

	@Inject
	private ReceivePayService receivePayService;

	private Long payCurrency;

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
	 * 跳转到 确认收款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmReceive(@Param("inlandRecId") String inlandRecId, HttpSession session) {
		return receivePayService.toConfirmRec(inlandRecId, session);
	}

	/**
	 * 跳转到 确认付款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmPay(@Param("inlandPayIds") String inlandPayIds) {
		return receivePayService.toConfirmPay(inlandPayIds);
	}

	/**
	 * 
	 *會計付款中   分页
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
	 *會計   已付款分页
	 */
	@At
	public Object inlandPayEdList(@Param("..") final InlandPayEdListSearchSqlForm form, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		form.setLoginUserId(id);
		return receivePayService.listPayEdData(form);
	}

	/**
	 * 
	 * 确认付款
	 */
	@At
	public Object saveInlandPay(@Param("..") final TSaveInlandPayAddFrom form) {
		return receivePayService.saveInlandPay(form);
	}

	/**
	 * 
	 *會計收款分页
	 */
	@At
	public Object inlandRecList(@Param("..") final InlandRecListSearchSqlForm form, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		form.setLoginUserId(id);
		return receivePayService.listRecData(form);
	}

	//水单上传 返回值文件存储地址
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(final @Param("fileId") File file, HttpSession session) {
		return receivePayService.upload(file, session);
	}

}
