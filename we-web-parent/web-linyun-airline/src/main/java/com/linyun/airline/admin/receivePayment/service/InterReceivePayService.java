/**
 * InternationalReceivePayService.java
 * com.linyun.airline.admin.receivePayment.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.receivePayment.entities.TCompanyBankCardEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.receivePayment.form.InterPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InterPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InterRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.TSaveInlandPayAddFrom;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InterReceivePayService extends BaseService<TPayEntity> {

	//银行卡类型
	private static final String YHCODE = "YH";

	//付款用途
	private static final String YTCODE = "FKYT";
	private static final int RECEIVESTATUS = AccountReceiveEnum.RECEIVEDONEY.intKey();
	private static final int APPROVALPAYED = AccountPayEnum.APPROVALPAYED.intKey();
	private static final int APPROVALPAYING = AccountPayEnum.APPROVALPAYING.intKey();

	@Inject
	private UploadService qiniuUploadService;

	@Inject
	private externalInfoService externalInfoService;

	/**
	 * 
	 * 会计收款列表
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listRecData(InterRecListSearchSqlForm form) {
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> data = (List<Record>) listdata.get("data");
		for (Record record : data) {
			Sql sql = Sqls.create(sqlManager.get("receivePay_rec_list"));
			Cnd cnd = Cnd.NEW();
			cnd.and("r.id", "=", record.getString("recid"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
		}
		listdata.remove("data");
		listdata.put("data", data);
		return listdata;
	}

	/**
	 * (保存  确认收款)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInternationalRec(String recId) {
		Sql sql = Sqls.create(sqlManager.get("receivePay_rec_order_id"));
		Cnd cnd = Cnd.NEW();
		cnd.and("r.id", "=", recId);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String ids = "";
		for (Record record : orders) {
			ids += record.getString("id") + ',';
		}
		if (ids.length() > 1) {
			ids = ids.substring(0, (ids.length() - 1));
		}

		int orderRecEd = AccountReceiveEnum.RECEIVEDONEY.intKey();
		int updateNum = dbDao.update(TUpOrderEntity.class, Chain.make("ordersstatus", orderRecEd),
				Cnd.where("id", "in", ids));

		return updateNum;
	}

	/**
	 * bootstrap插件Datatables分页查询
	 * <p>
	 *
	 * @param sqlParamForm   Sql查询参数封装
	 * @param start   起始记录(页面是从0开始算的)
	 * @param length  查询多少条
	 * @param draw    当前查询序号
	 */
	public Map<String, Object> listPage4Datatables(final InterPayListSearchSqlForm sqlParamForm, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		sqlParamForm.setLoginUserId(id);
		//订单状态 付款中
		sqlParamForm.setOrderStatus(Long.valueOf(APPROVALPAYING));

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
	 * 
	 * TODO(会计已付款查询)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sqlParamForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listPayEdData(InterPayEdListSearchSqlForm form) {
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> data = (List<Record>) listdata.get("data");
		for (Record record : data) {
			Sql sql = Sqls.create(sqlManager.get("receivePay_payed_list"));
			Cnd cnd = Cnd.NEW();
			cnd.and("p.id", "=", record.getString("pid"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
		}
		listdata.remove("data");
		listdata.put("data", data);
		return listdata;
	}

	/**
	 * (确认收款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toConfirmRec(String InternationalRecId, HttpSession session) {
		//当前登录用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();

		Map<String, Object> map = new HashMap<String, Object>();

		Sql sql = Sqls.create(sqlManager.get("receivePay_rec_id"));
		/*String inlandPayIdStr = inlandPayIds.substring(0, inlandPayIds.length() - 1);*/
		Cnd cnd = Cnd.NEW();
		cnd.and("r.id", "=", InternationalRecId);
		cnd.and("uo.loginUserId", "=", loginUserId);
		List<Record> orders = dbDao.query(sql, cnd, null);

		int bankcardid = 0;
		String bankcardname = "";
		String bankcardnum = "";
		String receipturl = "";
		String uoIds = "";
		Double sum = 0.0;
		for (Record record : orders) {
			bankcardid = Integer.valueOf(record.getString("bankcardid"));
			bankcardname = record.getString("bankcardname");
			bankcardnum = record.getString("bankcardnum");
			receipturl = record.getString("receipturl");
			sum = Double.valueOf(record.getString("sum"));
			uoIds += record.getString("uoid");
		}

		DictInfoEntity dictInfoEntity = dbDao.fetch(DictInfoEntity.class, bankcardid);
		map.put("bankComp", dictInfoEntity.getDictName());
		map.put("bankcardname", bankcardname);
		map.put("bankcardnum", bankcardnum);
		map.put("receipturl", receipturl);
		map.put("sum", sum);
		map.put("orders", orders);
		map.put("inlandRecId", InternationalRecId);

		return map;
	}

	/**
	 * (确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toConfirmPay(String inlandPayIds) {
		Map<String, Object> map = new HashMap<String, Object>();

		Sql sql = Sqls.create(sqlManager.get("receivePay_pay_Ids"));
		/*String inlandPayIdStr = inlandPayIds.substring(0, inlandPayIds.length() - 1);*/
		Cnd cnd = Cnd.NEW();
		cnd.and("pi.id", "in", inlandPayIds);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String payIds = "";
		for (Record record : orders) {
			payIds += record.getString("id") + ",";
		}
		map.put("orders", orders);

		//计算合计金额
		double totalMoney = 0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("salePrice"))) {
				Double incometotal = (Double) record.get("salePrice");
				totalMoney += incometotal;
			}
		}
		map.put("totalMoney", totalMoney);

		//银行卡
		List<DictInfoEntity> bankList = new ArrayList<DictInfoEntity>();
		try {
			bankList = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("bankList", bankList);

		//付款用途
		List<DictInfoEntity> fkytList = new ArrayList<DictInfoEntity>();
		try {
			fkytList = externalInfoService.findDictInfoByName("", YTCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fkytList", fkytList);

		map.put("ids", inlandPayIds);
		return map;
	}

	/**
	 * (保存  确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInternationalPay(TSaveInlandPayAddFrom form) {
		List<TPayEntity> payList = new ArrayList<TPayEntity>();

		String bankComp = form.getBankComp();
		String cardName = form.getCardName();
		String cardNum = form.getCardNum();
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
		Double totalMoney = form.getTotalMoney();

		/*String payIdStr = payIds.substring(0, payIds.length() - 1);*/

		//付款水单 集合
		List<TPayReceiptEntity> payReceiptList = new ArrayList<TPayReceiptEntity>();
		TPayReceiptEntity payReceiptEntity = new TPayReceiptEntity();

		//银行卡
		TCompanyBankCardEntity companyBankCard = new TCompanyBankCardEntity();
		companyBankCard.setCardName(cardName);
		companyBankCard.setCardNum(cardNum);
		companyBankCard.setBankComp(bankComp);
		//添加银行卡
		TCompanyBankCardEntity companyBankCardEntity = dbDao.insert(companyBankCard);
		Integer bankId = companyBankCardEntity.getId();

		//订单
		TUpOrderEntity upOrder = new TUpOrderEntity();

		//付款集合
		List<TPayEntity> updateList = new ArrayList<TPayEntity>();
		List<TPayEntity> payEntityList = dbDao.query(TPayEntity.class, Cnd.where("id", "in", payIds), null);
		for (TPayEntity payEntity : payEntityList) {

			if (!Util.eq(null, bankId)) {
				payEntity.setBankId(bankId);
			}
			if (!Util.eq(null, payAddress)) {
				payEntity.setPayAddress(payAddress);
			}
			if (!Util.eq(null, purpose)) {
				payEntity.setPurpose(purpose);
			}
			if (!Util.eq(null, fundType)) {
				payEntity.setFundType(fundType);
			}
			if (!Util.eq(null, payDate)) {
				payEntity.setPayDate(payDate);
			}
			if (!Util.eq(null, payFees)) {
				payEntity.setPayFees(payFees);
			}
			if (!Util.eq(null, payMoney)) {
				payEntity.setPayMoney(payMoney);
			}
			if (!Util.eq(null, totalMoney)) {
				payEntity.setTotalMoney(totalMoney);
			}
			if (!Util.eq(null, currency)) {
				payEntity.setPayCurrency(currency);
			}
			if (!Util.eq(null, isInvioce)) {
				payEntity.setIsInvioce(isInvioce);
			}
			updateList.add(payEntity);

			//添加水单
			if (!Util.isEmpty(receiptUrl)) {
				payReceiptEntity.setReceiptUrl(receiptUrl);
				payReceiptEntity.setId(payEntity.getId());
			}
			payReceiptList.add(payReceiptEntity);
		}

		//添加水单表
		if (!Util.isEmpty(payReceiptList)) {
			dbDao.insert(payReceiptList);
		}

		//更新Pnr表中對應的状态
		List<TPnrInfoEntity> pnrInfoList = dbDao.query(TPnrInfoEntity.class, Cnd.where("id", "in", payIds), null);
		if (!Util.eq(null, payIds)) {
			dbDao.update(TPnrInfoEntity.class, Chain.make("orderPnrStatus", APPROVALPAYED),
					Cnd.where("id", "in", payIds));
		}

		if (!Util.isEmpty(updateList)) {
			dbDao.update(updateList);
		}

		return null;
	}

	//水单上传 返回值文件存储地址
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(File file, HttpSession session) {
		try {
			String ext = Files.getSuffix(file);
			FileInputStream fileInputStream = new FileInputStream(file);
			String url = qiniuUploadService.uploadImage(fileInputStream, ext, null);
			//水单存储地址
			System.out.println(url);
			return url;
			//业务
		} catch (Exception e) {
			return MobileResult.error("操作失败", null);
		}
	}

	/*********************************会计收付款  国际 start******************************************************/
	public Object internationalListPayEdData(InterPayEdListSearchSqlForm form) {
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> data = (List<Record>) listdata.get("data");
		for (Record record : data) {
			Sql sql = Sqls.create(sqlManager.get("receivePay_payed_list"));
			Cnd cnd = Cnd.NEW();
			cnd.and("p.id", "=", record.getString("pid"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
		}
		listdata.remove("data");
		listdata.put("data", data);
		return listdata;
	}

}
