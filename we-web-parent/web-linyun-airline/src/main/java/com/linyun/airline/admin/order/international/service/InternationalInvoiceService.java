/**
 * InternationalInvoiceService.java
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
import com.linyun.airline.admin.order.international.form.InternationalKaiListForm;
import com.linyun.airline.admin.order.international.form.InternationalShouListForm;
import com.linyun.airline.admin.receivePayment.entities.TCompanyBankCardEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayOrderEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月19日 	 
 */
@IocBean
public class InternationalInvoiceService extends BaseService<TInvoiceInfoEntity> {

	//银行卡类型
	private static final String YHCODE = "YH";

	@Inject
	private externalInfoService externalInfoService;

	@SuppressWarnings("unchecked")
	public Object listKaiInvoiceData(InternationalKaiListForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		sqlForm.setUserid(new Long(user.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		for (Record record : data) {
			//发票详情
			List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
					Cnd.where("invoiceinfoid", "=", record.getInt("id")), null);
			record.put("invoiceDetail", invoiceDetail);
			String sqlString = sqlManager.get("get_kai_invoice_list_order");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("tii.id", "=", record.getInt("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
			String username = "";
			TUserEntity billuser = dbDao.fetch(TUserEntity.class, record.getInt("billuserid"));
			if (!Util.isEmpty(billuser)) {
				username = billuser.getUserName();
			}
			record.put("username", username);
		}
		return listData;
	}

	/**
	 * 收发票列表数据
	 * <p>
	 * TODO收发票列表数据
	 * @param sqlForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("unchecked")
	public Object listShouInvoiceData(InternationalShouListForm sqlForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setCompanyid(new Long(company.getId()).intValue());
		sqlForm.setUserid(new Long(user.getId()).intValue());
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		List<Record> data = (List<Record>) listData.get("data");
		for (Record record : data) {
			//发票详情
			List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
					Cnd.where("invoiceinfoid", "=", record.getInt("id")), null);
			record.put("invoiceDetail", invoiceDetail);
			/*String sqlString = sqlManager.get("get_inter_shou_invoice_list_order");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("tii.id", "=", record.getInt("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);*/
			//record.put("orders", orders);
			String username = "";
			TUserEntity billuser = dbDao.fetch(TUserEntity.class, record.getInt("billuserid"));
			if (!Util.isEmpty(billuser)) {
				username = billuser.getUserName();
			}
			record.put("username", username);
		}
		return listData;
	}

	/**
	 * 打开开发票编辑页面
	 * <p>
	 * TODO打开开发票编辑页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object kaiInvoice(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		//发票id
		String id = request.getParameter("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		//发票明细
		List<TInvoiceDetailEntity> invoicedetail = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", invoiceinfo.getId()), null);
		//付款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(invoiceinfo.getReceiveid()));
		double invoicebalance = fetch.getSum();
		for (TInvoiceDetailEntity detail : invoicedetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", invoicebalance);

		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class,
				Cnd.where("receiveid", "=", fetch.getId()), null);
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("get_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//订单信息
		result.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TReceiveBillEntity> query2 = dbDao.query(TReceiveBillEntity.class,
				Cnd.where("receiveid", "=", fetch.getId()), null);
		//银行卡下拉
		result.put("yhkSelect", yhkSelect);
		//订单信息id
		result.put("ids", ids);
		result.put("id", id);
		result.put("receive", fetch);
		//发票信息
		result.put("invoiceinfo", invoiceinfo);
		//发票明细
		result.put("invoicedetail", invoicedetail);
		if (query2.size() > 0)
			result.put("bill", query2.get(0));

		return result;

	}

	/**
	 * 打开收发票编辑页面
	 * <p>
	 * TODO打开 收发票编辑页面
	 *
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object shouInvoice(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		//发票id
		String id = request.getParameter("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		//发票明细
		List<TInvoiceDetailEntity> invoicedetail = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", invoiceinfo.getId()), null);
		//付款订单表信息
		TPayOrderEntity payorder = dbDao.fetch(TPayOrderEntity.class, invoiceinfo.getOrderpayid().longValue());
		//付款信息
		TPayEntity fetch = dbDao.fetch(TPayEntity.class, Long.valueOf(payorder.getPayid()));
		double invoicebalance = 0;
		if (!Util.isEmpty(fetch.getPayMoney())) {
			invoicebalance = fetch.getPayMoney();
		}
		for (TInvoiceDetailEntity detail : invoicedetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", invoicebalance);

		List<TPayOrderEntity> query = dbDao.query(TPayOrderEntity.class, Cnd.where("payid", "=", fetch.getId()), null);
		String ids = "";
		/*for (TPayOrderEntity tPayOrderEntity : query) {
			ids += tPayOrderEntity.getOrderid() + ",";
		}*/
		//ids = ids.substring(0, ids.length() - 1);
		ids += payorder.getOrderid();
		String sqlString = sqlManager.get("get_sea_invoce_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//订单信息
		result.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TPayReceiptEntity> query2 = dbDao.query(TPayReceiptEntity.class, Cnd.where("payId", "=", fetch.getId()),
				null);
		//银行卡下拉
		result.put("yhkSelect", yhkSelect);
		//订单信息id
		result.put("ids", ids);
		result.put("id", id);
		result.put("receive", fetch);
		TCompanyBankCardEntity companybank = new TCompanyBankCardEntity();
		if (!Util.isEmpty(fetch.getBankId())) {
			companybank = dbDao.fetch(TCompanyBankCardEntity.class, fetch.getBankId().longValue());
		}
		//总金额
		double sumjine = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("incometotal"))) {
				sumjine += Double.valueOf((Double) record.get("incometotal"));
			}
		}
		result.put("sumjine", sumjine);
		result.put("companybank", companybank);
		//发票信息
		result.put("invoiceinfo", invoiceinfo);
		//发票明细
		result.put("invoicedetail", invoicedetail);
		if (query2.size() > 0)
			result.put("bill", query2.get(0));

		return result;
	}

}
