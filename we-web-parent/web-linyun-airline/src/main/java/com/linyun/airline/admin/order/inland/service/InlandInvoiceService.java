/**
 * InlandInvoiceService.java
 * com.linyun.airline.admin.order.inland.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月3日 	 
 */
@IocBean
public class InlandInvoiceService extends BaseService<TInvoiceInfoEntity> {

	/**
	 * 保存付款发票信息
	 * <p>
	 * 保存付款发票信息
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object saveInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		TInvoiceInfoEntity invoiceinfo = new TInvoiceInfoEntity();
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(fromJson.get("billuserid"))) {
			invoiceinfo.setBilluserid(Integer.valueOf((String) fromJson.get("billuserid")));
		}
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		if (!Util.isEmpty(fromJson.get("difference"))) {
			invoiceinfo.setDifference(Double.valueOf((String) fromJson.get("difference")));
		}
		if (!Util.isEmpty(fromJson.get("balance"))) {
			invoiceinfo.setBalance(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setInvoicetype(PayReceiveTypeEnum.PAY.intKey());
		if (!Util.isEmpty(fromJson.get("pnrid"))) {
			invoiceinfo.setPnrid(Integer.valueOf((String) fromJson.get("pnrid")));
		}
		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		//保存发票信息
		TInvoiceInfoEntity insert = dbDao.insert(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			invoiceDetailEntity.setInvoicebalance(Double.valueOf(map.get("invoicebalance")));
			invoiceDetailEntity.setInvoiceurl(map.get("invoiceurl"));
			invoiceDetailEntity.setInvoiceinfoid(insert.getId());
			invoicedetails.add(invoiceDetailEntity);
		}
		//保存发票明细
		dbDao.insert(invoicedetails);
		return null;

	}

	@SuppressWarnings("unchecked")
	public Object saveReceiveInvoiceInfo(HttpServletRequest request) {

		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		TInvoiceInfoEntity invoiceinfo = new TInvoiceInfoEntity();
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(fromJson.get("billuserid"))) {
			invoiceinfo.setBilluserid(Integer.valueOf((String) fromJson.get("billuserid")));
		}
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		if (!Util.isEmpty(fromJson.get("difference"))) {
			invoiceinfo.setDifference(Double.valueOf((String) fromJson.get("difference")));
		}
		if (!Util.isEmpty(fromJson.get("balance"))) {
			invoiceinfo.setBalance(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setInvoicetype(PayReceiveTypeEnum.RECEIVE.intKey());
		if (!Util.isEmpty(fromJson.get("pnrid"))) {
			invoiceinfo.setReceiveid(Integer.valueOf((String) fromJson.get("pnrid")));
		}
		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		//保存发票信息
		TInvoiceInfoEntity insert = dbDao.insert(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			invoiceDetailEntity.setInvoicebalance(Double.valueOf(map.get("invoicebalance")));
			invoiceDetailEntity.setInvoiceurl(map.get("invoiceurl"));
			invoiceDetailEntity.setInvoiceinfoid(insert.getId());
			invoicedetails.add(invoiceDetailEntity);
		}
		//保存发票明细
		dbDao.insert(invoicedetails);
		return null;

	}

}
