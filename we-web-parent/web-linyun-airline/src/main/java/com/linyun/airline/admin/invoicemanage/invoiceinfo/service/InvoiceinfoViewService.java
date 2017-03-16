package com.linyun.airline.admin.invoicemanage.invoiceinfo.service;

import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InvoiceinfoViewService extends BaseService<TInvoiceInfoEntity> {

	public Object getInvoiceStatus() {
		Map<String, Object> obj = Maps.newHashMap();

		return obj;
	}
}