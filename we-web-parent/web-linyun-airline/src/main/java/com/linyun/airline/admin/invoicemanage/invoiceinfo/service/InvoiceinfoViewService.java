package com.linyun.airline.admin.invoicemanage.invoiceinfo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TInvoiceInfoSqlForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.form.KaiInvoiceParamForm;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InvoiceinfoViewService extends BaseService<TInvoiceInfoEntity> {

	/**
	 * 开票人的查询
	 * @param session
	 */
	public Object getIssuerBycompany(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到当前登录公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("get_kai_invoice_search_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("ii.comId", "=", companyId);
		cnd.groupBy("u.userName");
		List<Record> query = dbDao.query(sql, cnd, null);
		obj.put("listIssuer", query);
		return obj;
	}

	/**
	 * 开发票列表
	 * @param session
	 */
	/*public Object kaiQueryInvoiceDate(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到当前登录公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("get_kai_invoice_search_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("ii.comId", "=", comId);
		cnd.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
		cnd.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
		List<Record> query = dbDao.query(sql, cnd, null);
		obj.put("listIssuer", query);
		return obj;
	}*/

	/**
	 * 开发票列表
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public Object listKaiInvoiceData(KaiInvoiceParamForm sqlForm, HttpServletRequest request) {
		//检索条件
		/*Integer status = sqlForm.getStatus();//状态
		String username = sqlForm.getUsername();//开票人
		Date kaiInvoiceBeginDate = sqlForm.getKaiInvoiceBeginDate();//起始时间
		Date kaiInvoiceEndDate = sqlForm.getKaiInvoiceEndDate();//结束时间
		String invoicenum = sqlForm.getInvoicenum();//发票号
		String paymentunit = sqlForm.getPaymentunit();//付款单位
		*/
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		sqlForm.setUserid(new Long(user.getId()).intValue());
		sqlForm.setCompanyid(company.getId());
		Long comId = sqlForm.getCompanyid();//得到公司的id

		String sqlString = sqlManager.get("invoicemanage_kaiinvoice_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("ii.comId", "=", comId);
		cnd.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
		cnd.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
		sql.setCondition(cnd);
		List<Record> listinvodata = dbDao.query(sql, cnd, null);
		int billuserid = listinvodata.get(0).getInt("billuserid");
		sqlForm.setUserid(billuserid);
		Map<String, Object> DatatablesData = this.listPage4Datatables(sqlForm);
		List<Record> listdata = (List<Record>) DatatablesData.get("data");
		for (Record record : listdata) {
			//发票详情
			List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
					Cnd.where("invoiceinfoid", "=", record.getInt("id")), null);
			record.put("invoiceDetail", invoiceDetail);
			String sqlString1 = sqlManager.get("get_kai_invoice_search_list");
			Sql sql1 = Sqls.create(sqlString1);
			Cnd cnd1 = Cnd.NEW();
			/*SqlExpressionGroup group = new SqlExpressionGroup();
			group.and("idd.invoicenum", "LIKE", "%" + invoicenum + "%").or("ii.paymentunit", "LIKE",
					"%" + paymentunit + "%");
			if (!Util.isEmpty(invoicenum)) {
				cnd.and(group);
			}
			//开票日期
			if (!Util.isEmpty(kaiInvoiceBeginDate)) {
				cnd.and("ii.invoicedate", ">=", kaiInvoiceBeginDate);
			}
			//开票日期
			if (!Util.isEmpty(kaiInvoiceEndDate)) {
				cnd.and("ii.invoicedate", "<=", kaiInvoiceEndDate);
			}
			if (!Util.isEmpty(status)) {
				cnd.and("ii.status", "=", status);
			}*/
			cnd1.and("ii.comId", "=", comId);
			cnd1.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
			cnd1.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
			//订单信息
			List<Record> orders = dbDao.query(sql1, cnd1, null);
			record.put("orders", orders);
		}
		List<Record> listdataNew = new ArrayList<Record>();
		for (Record record : listdata) {
			String orders = record.getString("orders");
			if (orders.contains("id")) {
				listdataNew.add(record);
			}
		}
		DatatablesData.remove("data");
		DatatablesData.put("data", listdataNew);
		return DatatablesData;

	}

	/**
	 *收发票列表
	 * <p>
	 * TODO收发票列表
	 *
	 * @param paramForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listShouInvoiceData(TInvoiceInfoSqlForm paramForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		paramForm.setUserid(new Long(user.getId()).intValue());
		Map<String, Object> datatableData = this.listPage4Datatables(paramForm);
		return datatableData;

	}
}