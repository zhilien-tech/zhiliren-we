/**
 * ReceivePayService.java
 * com.linyun.airline.admin.receivePayment.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.form.InlandPayListSearchSqlForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class ReceivePayService extends BaseService<TPayEntity> {

	/**
	 * bootstrap插件Datatables分页查询
	 * <p>
	 *
	 * @param sqlParamForm   Sql查询参数封装
	 * @param start   起始记录(页面是从0开始算的)
	 * @param length  查询多少条
	 * @param draw    当前查询序号
	 */
	public Map<String, Object> listPage4Datatables(final InlandPayListSearchSqlForm sqlParamForm) {
		checkNull(sqlParamForm, "sqlParamForm不能为空");
		Sql sql = sqlParamForm.sql(sqlManager);

		Pager pager = new OffsetPager(sqlParamForm.getStart(), sqlParamForm.getLength());
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();

		Map<String, Object> map = MapUtil.map();
		map.put("data", list);
		map.put("draw", sqlParamForm.getDraw());
		map.put("recordsTotal", pager.getPageSize());
		map.put("recordsFiltered", pager.getRecordCount());
		return map;
	}

	/**
	 * (确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toConfirmPay(String inlandPayIds) {
		Sql sql = Sqls.create(sqlManager.get("receivePay_pay_Ids"));
		List<Record> list = new ArrayList<Record>();
		Iterable<String> split = Splitter.on(",").split(inlandPayIds);
		Record record = new Record();
		for (String pnrId : split) {
			sql.params().set("pnrId", pnrId);
			record = dbDao.fetch(sql);
			list.add(record);
		}

		return Json.toJson(list);
	}
}
