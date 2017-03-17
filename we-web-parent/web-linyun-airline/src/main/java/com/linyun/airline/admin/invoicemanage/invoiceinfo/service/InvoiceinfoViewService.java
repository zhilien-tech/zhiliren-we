package com.linyun.airline.admin.invoicemanage.invoiceinfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InvoiceinfoViewService extends BaseService<TInvoiceInfoEntity> {

	/**
	 * 
	 */
	public Object getIssuerBycompany(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到当前登录公司id
		//TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		//Long companyId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("invoicemanage_issuer_list"));
		//sql.params().set("jobId", companyId);
		List<Record> query = dbDao.query(sql, null, null);
		obj.put("listIssuer", query);
		return obj;
	}
}