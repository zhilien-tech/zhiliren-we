/**
 * InterPayReceiveService.java
 * com.linyun.airline.admin.order.international.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.international.form.InterPaymentSqlForm;
import com.linyun.airline.admin.order.international.form.InterReceiptSqlForm;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月18日 	 
 */
@IocBean
public class InterPayReceiveService extends BaseService<TReceiveEntity> {

	//银行卡类型
	private static final String YHCODE = "YH";
	@Inject
	private externalInfoService externalInfoService;

	/**
	 * 国际收款列表
	 * <p>
	 * TODO国际收款列表
	 * @param sqlForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object listShouFuKuanData(InterReceiptSqlForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		for (Record record : data) {
			String sqlString = sqlManager.get("get_international_receive_list_order");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.limit();
			cnd.and("tr.id", "=", record.get("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
			record.put("receiveenum", EnumUtil.enum2(AccountReceiveEnum.class));
		}
		return listData;
	}

	/**
	 * 国际付款列表数据
	 * <p>
	 * TODO国际付款列表数据
	 * @param sqlForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object listFuKuanData(InterPaymentSqlForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		for (Record record : data) {
			String sqlString = sqlManager.get("get_international_pay_list_order");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.limit();
			cnd.and("tp.id", "=", record.get("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
			record.put("receiveenum", EnumUtil.enum2(AccountPayEnum.class));
		}
		return listData;
	}

	/**
	 * 打开开发票页面
	 * <p>
	 * TODO打开开发票页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object openInvoice(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		//付款id
		String id = request.getParameter("id");
		//付款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(id));
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class, Cnd.where("receiveid", "=", id), null);
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("get_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.limit();
		cnd.and("tuo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String customename = "";
		if (orders.size() > 0) {
			customename = (String) orders.get(0).get("customename");
		}
		result.put("customename", customename);
		//计算合计金额
		double sumincome = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("incometotal"))) {
				Double incometotal = (Double) record.get("incometotal");
				sumincome += incometotal;
			}
		}
		//订单信息
		result.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TReceiveBillEntity> query2 = dbDao.query(TReceiveBillEntity.class, Cnd.where("receiveid", "=", id), null);
		//银行卡下拉
		result.put("yhkSelect", yhkSelect);
		//订单信息id
		result.put("ids", ids);
		result.put("id", id);
		result.put("receive", fetch);
		if (query2.size() > 0)
			result.put("bill", query2.get(0));

		return result;
	}

}
