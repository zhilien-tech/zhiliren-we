/**
 * InternationalReceivePayModule.java
 * com.linyun.airline.admin.receivePayment.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.module;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.receivePayment.form.InterPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InterPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InterRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.TSaveInlandPayAddFrom;
import com.linyun.airline.admin.receivePayment.service.InterReceivePayService;
import com.linyun.airline.entities.TUserEntity;

@IocBean
@At("/admin/receivePay/inter")
public class InterReceivePayModule {

	@Inject
	private InterReceivePayService interReceivePayService;

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
		return interReceivePayService.toConfirmRec(inlandRecId, session);
	}

	/**
	 * 跳转到 确认付款页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object confirmPay(@Param("inlandPayIds") String inlandPayIds) {
		return interReceivePayService.toConfirmPay(inlandPayIds);
	}

	/**
	 * 
	 * 确认付款
	 */
	@At
	public Object saveInternationalPay(@Param("..") final TSaveInlandPayAddFrom form) {
		return interReceivePayService.saveInternationalPay(form);
	}

	/**
	 * 
	 *會計收款分页
	 */
	@At
	public Object interRecList(@Param("..") final InterRecListSearchSqlForm form, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		form.setLoginUserId(id);
		return interReceivePayService.listRecData(form);
	}

	/**
	 * 
	 * 确认收款
	 */
	@At
	public Object saveInternationalRec(@Param("id") final String id) {
		return interReceivePayService.saveInternationalRec(id);
	}

	//水单上传 返回值文件存储地址
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(final @Param("fileId") File file, HttpSession session) {
		return interReceivePayService.upload(file, session);
	}

	/**
	 * 
	 *會計付款国际   付款中分页
	 */
	@At
	public Object internationalPayList(@Param("..") final InterPayListSearchSqlForm form, HttpSession session) {
		return interReceivePayService.listPage4Datatables(form, session);
	}

	/**
	 * 
	 *會計付款国际   已付款分页
	 */
	@At
	public Object internationalPayEdList(@Param("..") final InterPayEdListSearchSqlForm form, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		form.setLoginUserId(id);
		return interReceivePayService.internationalListPayEdData(form);
	}

}
