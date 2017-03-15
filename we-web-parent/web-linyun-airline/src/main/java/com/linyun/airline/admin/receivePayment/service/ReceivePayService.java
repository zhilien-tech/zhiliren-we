/**
 * ReceivePayService.java
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

import javax.servlet.http.HttpServletRequest;
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
import com.linyun.airline.admin.receivePayment.form.InlandPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InlandPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.InlandRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.TSaveInlandPayAddFrom;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class ReceivePayService extends BaseService<TPayEntity> {

	//银行卡类型
	private static final String YHCODE = "YH";
	//币种类型
	private static final String BZCODE = "BZ";

	//银行卡状态
	private static final int ENABLE = BankCardStatusEnum.ENABLE.intKey();

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
	public Object listRecData(InlandRecListSearchSqlForm form, HttpSession session, HttpServletRequest request) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		form.setLoginUserId(id);
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) listdata.get("data");
		for (Record record : list) {
			record.put("username", loginUser.getUserName());
			String sqlString = sqlManager.get("get_receive_order_list");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.limit();
			cnd.and("r.id", "=", record.get("id"));
			List<Record> orders = dbDao.query(sql, cnd, null);
			record.put("orders", orders);
		}
		listdata.remove("data");
		listdata.put("data", list);
		return listdata;
	}

	/**
	 * (保存  确认收款)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInlandRec(String recId) {
		/*Sql sql = Sqls.create(sqlManager.get("receivePay_rec_order_id"));
		Cnd cnd = Cnd.NEW();
		cnd.and("r.id", "=", recId);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String ids = "";
		for (Record record : orders) {
			ids += record.getString("id") + ',';
		}
		if (ids.length() > 1) {
			ids = ids.substring(0, (ids.length() - 1));
		}*/

		int orderRecEd = AccountReceiveEnum.RECEIVEDONEY.intKey();
		int updateNum = dbDao.update(TReceiveEntity.class, Chain.make("status", orderRecEd),
				Cnd.where("id", "in", recId));

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
	public Map<String, Object> listPage4Datatables(final InlandPayListSearchSqlForm sqlParamForm, HttpSession session) {

		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		sqlParamForm.setLoginUserId(id);
		sqlParamForm.setOrderPnrStatus(APPROVALPAYING);

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
	public Object listPayEdData(InlandPayEdListSearchSqlForm form, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		form.setLoginUserId(id);
		form.setOrderPnrStatus(APPROVALPAYED);

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
	public Object toConfirmRec(String id, HttpSession session) {
		//当前登录用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();

		Map<String, Object> map = new HashMap<String, Object>();

		//收款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(id));
		List<TOrderReceiveEntity> query = dbDao.query(TOrderReceiveEntity.class, Cnd.where("receiveid", "=", id), null);
		String ids = "";
		for (TOrderReceiveEntity tOrderReceiveEntity : query) {
			ids += tOrderReceiveEntity.getOrderid() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		String sqlString = sqlManager.get("receivePay_toRec_table_data");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("uo.id", "in", ids);
		List<Record> orders = dbDao.query(sql, cnd, null);
		//计算合计金额
		Double sum = 0.0;
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("incometotal"))) {
				Double incometotal = (Double) record.get("incometotal");
				sum += incometotal;
			}
		}
		map.put("sum", sum);

		//订单信息
		map.put("orders", orders);
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//水单信息
		List<TReceiveBillEntity> receipts = dbDao
				.query(TReceiveBillEntity.class, Cnd.where("receiveid", "=", id), null);
		//银行卡下拉
		map.put("yhkSelect", yhkSelect);
		//订单信息id
		map.put("ids", ids);
		map.put("id", id);
		map.put("receive", fetch);
		if (receipts.size() > 0) {
			map.put("receipturl", receipts.get(0));
		}
		return map;

	}

	/**
	 * (确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toConfirmPay(String inlandPnrIds) {
		Map<String, Object> map = new HashMap<String, Object>();

		Sql sql = Sqls.create(sqlManager.get("receivePay_pay_Ids"));
		/*String inlandPayIdStr = inlandPayIds.substring(0, inlandPayIds.length() - 1);*/
		Cnd cnd = Cnd.NEW();
		cnd.and("pi.id", "in", inlandPnrIds);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String payIds = "";
		if (!Util.isEmpty(orders)) {
			String shortname = orders.get(0).getString("shortname");
			for (Record record : orders) {
				String everyShortName = record.getString("shortname");
				String id = record.getString("id");
				if (!Util.eq(shortname, everyShortName)) {
					map.put("sameName", false);
				} else {
					map.put("sameName", true);
				}
				if (!Util.isEmpty(id)) {
					payIds += record.getString("id") + ",";
				}
			}
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

		//银行名称
		List<DictInfoEntity> bankList = new ArrayList<DictInfoEntity>();
		try {
			bankList = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("bankList", bankList);

		//银行名称
		List<DictInfoEntity> BZList = new ArrayList<DictInfoEntity>();
		try {
			BZList = externalInfoService.findDictInfoByName("", BZCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("bzList", BZList);

		//付款用途
		List<DictInfoEntity> fkytList = new ArrayList<DictInfoEntity>();
		try {
			fkytList = externalInfoService.findDictInfoByName("", YTCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fkytList", fkytList);

		map.put("ids", inlandPnrIds);

		return map;
	}

	/**
	 * (保存  确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInlandPay(TSaveInlandPayAddFrom form, HttpSession session) {
		List<TPayEntity> payList = new ArrayList<TPayEntity>();
		//当前公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();

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

		String payNames = form.getPayNames();
		if (Util.eq("false", payNames)) {
			//收款单位不一致，不能付款
			return false;
		}

		/*String payIdStr = payIds.substring(0, payIds.length() - 1);*/

		//付款水单 集合
		List<TPayReceiptEntity> payReceiptList = new ArrayList<TPayReceiptEntity>();
		TPayReceiptEntity payReceiptEntity = new TPayReceiptEntity();

		//银行卡
		TCompanyBankCardEntity companyBankCard = new TCompanyBankCardEntity();
		companyBankCard.setCompanyId(companyId);
		if (!Util.eq("--请选择--", cardName)) {
			companyBankCard.setCardName(cardName);
		}
		if (!Util.eq("--请选择--", bankComp)) {
			companyBankCard.setBankComp(bankComp);
		}
		if (!Util.eq("--请选择--", cardNum)) {
			companyBankCard.setCardNum(cardNum);
		}
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
				if (!Util.eq("--请选择--", cardNum)) {
					payEntity.setPayCurrency(currency);
				}
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

	//根据银行id查询银行卡名称
	public Object getCardNames(long bankId, HttpSession session) {
		List<String> cardNames = new ArrayList<String>();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		DictInfoEntity bankEntity = dbDao.fetch(DictInfoEntity.class, Cnd.where("id", "=", bankId));
		if (!Util.isEmpty(bankEntity)) {
			String bankName = bankEntity.getDictName();
			List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class, Cnd.where("status", "=", ENABLE)
					.and("bankName", "=", bankName).and("companyId", "=", companyId).groupBy("cardName"), null);
			for (TBankCardEntity bankCardEntity : bankCardList) {
				cardNames.add(bankCardEntity.getCardName());
			}
		}
		return cardNames;
	}

	//根据银行卡名称查询银行卡  卡号
	public Object getCardNums(String cardName, HttpSession session) {
		List<String> cardNums = new ArrayList<String>();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class,
				Cnd.where("status", "=", ENABLE).and("cardName", "=", cardName).and("companyId", "=", companyId), null);
		for (TBankCardEntity bankCardEntity : bankCardList) {
			cardNums.add(bankCardEntity.getCardNum());
		}

		return cardNums;
	}

}
