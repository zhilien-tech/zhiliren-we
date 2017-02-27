/**
 * ReceivePayService.java
 * com.linyun.airline.admin.receivePayment.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.receivePayment.form.InlandPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.TSaveInlandPayAddFrom;
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

	/**
	 * (保存  确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInlandPay(TSaveInlandPayAddFrom form) {

		List<TPayEntity> payList = new ArrayList<TPayEntity>();

		Integer bankComp = form.getBankComp();
		Integer cardName = form.getCardName();
		Integer cardNum = form.getCardNum();
		Integer payAddress = form.getPayAddress();
		Integer purpose = form.getPurpose();
		Integer fundType = form.getFundType();
		Date payDate = form.getPayDate();
		Double payFees = form.getPayFees();
		Double payMoney = form.getPayMoney();
		Integer currency = form.getPayCurrency();
		Integer isInvioce = form.getIsInvioce();
		String receiptUrl = form.getReceiptUrl();
		String payIds = form.getPayIds();
		String payIdStr = payIds.substring(0, payIds.length() - 1);

		//付款水单 集合
		List<TPayReceiptEntity> payReceiptList = new ArrayList<TPayReceiptEntity>();
		TPayReceiptEntity payReceiptEntity = new TPayReceiptEntity();

		//付款集合
		List<TPayEntity> updateList = new ArrayList<TPayEntity>();
		List<TPayEntity> payEntityList = dbDao.query(TPayEntity.class, Cnd.where("id", "in", payIdStr), null);
		for (TPayEntity payEntity : payEntityList) {
			payEntity.setPayAddress(payAddress);
			payEntity.setProposer(purpose);
			payEntity.setFundType(fundType);
			payEntity.setPayDate(payDate);
			payEntity.setPayFees(payFees);
			payEntity.setPayMoney(payMoney);
			payEntity.setPayCurrency(currency);
			payEntity.setIsInvioce(isInvioce);
			updateList.add(payEntity);

			payReceiptEntity.setId(payEntity.getId());
			payReceiptEntity.setReceiptUrl(receiptUrl);
			payReceiptList.add(payReceiptEntity);
		}

		//添加水单表
		dbDao.insert(payReceiptList);

		return dbDao.update(updateList);
	}
}
