package com.linyun.airline.admin.invoicemanage.invoiceinfo.service;

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
import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TInvoiceInfoSqlForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.entities.TCompanyEntity;
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
		cnd.and("up.comId", "=", companyId);
		cnd.and("u.userName", "IS NOT", null);
		cnd.groupBy("u.userName");
		List<Record> query = dbDao.query(sql, cnd, null);
		obj.put("listIssuer", query);
		return obj;
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