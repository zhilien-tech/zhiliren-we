/**
 * ReceivePayService.java
 * com.linyun.airline.admin.receivePayment.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.receivePayment.service;

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
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.enums.ComDictTypeEnum;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.admin.receivePayment.entities.TCompanyBankCardEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayOrderEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.admin.receivePayment.form.inter.InterPayEdListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.InterPayListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.InterRecListSearchSqlForm;
import com.linyun.airline.admin.receivePayment.form.inter.TSaveInterPayAddFrom;
import com.linyun.airline.admin.search.service.SearchViewService;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.common.enums.ApprovalResultEnum;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.common.enums.MessageRemindEnum;
import com.linyun.airline.common.enums.MessageWealthStatusEnum;
import com.linyun.airline.common.enums.SearchOrderStatusEnum;
import com.linyun.airline.common.enums.UserJobStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InterReceivePayService extends BaseService<TPayEntity> {

	private static final String YHCODE = "YH";
	private static final String BZCODE = "BZ";
	private static final int ENABLE = BankCardStatusEnum.ENABLE.intKey();
	private static final String YTCODE = ComDictTypeEnum.DICTTYPE_XMYT.key();
	private static final String ZJZLCODE = ComDictTypeEnum.DICTTYPE_ZJZL.key();
	private static final int RECEIVESTATUS = AccountReceiveEnum.RECEIVEDONEY.intKey();
	private static final int APPROVALPAYED = AccountPayEnum.APPROVALPAYED.intKey();
	private static final int APPROVALPAYING = AccountPayEnum.APPROVALPAYING.intKey();
	private static final int APPROVALENABLE = ApprovalResultEnum.ENABLE.intKey();
	private static final int ONJOB = UserJobStatusEnum.ON.intKey();
	//款项已收
	private static final int RECEDMSGTYPE = MessageWealthStatusEnum.RECEIVED.intKey();
	//款项已付
	private static final int PAYEDMSGTYPE = MessageWealthStatusEnum.PAYED.intKey();
	//订单状态
	private static final int FIRBOOKING = SearchOrderStatusEnum.FIRBOOKING.intKey(); //一订

	@Inject
	private UploadService qiniuUploadService;

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private SearchViewService searchViewService;

	@Inject
	private ReceivePayService receivePayService;

	/**
	 * 
	 * 会计收款列表
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listRecData(InterRecListSearchSqlForm form, HttpSession session, HttpServletRequest request) {

		//当前公司下的用户
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		//检索条件
		String name = form.getName();
		Date leaveBeginDate = form.getLeaveBeginDate();
		Date leaveEndDate = form.getLeaveEndDate();
		String orderStatus = form.getOrderStatus();
		if (Util.isEmpty(orderStatus)) {
			orderStatus = "3";
		} else {
			if (Util.eq("firBooking", orderStatus)) {
				//一订
				orderStatus = InternationalStatusEnum.ONEBOOK.intKey() + "";
			}
			if (Util.eq("secBooking", orderStatus)) {
				//二订
				orderStatus = InternationalStatusEnum.TWOBOOK.intKey() + "";
			}
			if (Util.eq("thrBooking", orderStatus)) {
				//三订
				orderStatus = InternationalStatusEnum.THREEBOOK.intKey() + "";
			}
			if (Util.eq("allBooking", orderStatus)) {
				//全款
				orderStatus = InternationalStatusEnum.FULLAMOUNT.intKey() + "";
			}
			if (Util.eq("lastBooking", orderStatus)) {
				//尾款
				orderStatus = InternationalStatusEnum.TAILMONEY.intKey() + "";
			}
			if (Util.eq("outTicket", orderStatus)) {
				//出票
				orderStatus = InternationalStatusEnum.TICKETING.intKey() + "";
			}
		}
		form.setOrderStatus(orderStatus);
		form.setCompanyId(companyId);
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) listdata.get("data");

		String sqlStr = sqlManager.get("receivePay_inter_rec_order_list");
		Sql sql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + name + "%").or("uo.ordersnum", "LIKE", "%" + name + "%")
				.or("ci.linkMan", "LIKE", "%" + name + "%");
		if (!Util.isEmpty(name)) {
			cnd.and(group);
		}
		//出发日期
		if (!Util.isEmpty(leaveBeginDate)) {
			cnd.and("pi.leavesdate", ">=", leaveBeginDate);
		}
		// 返回日期
		if (!Util.isEmpty(leaveEndDate)) {
			cnd.and("pi.leavesdate", "<=", leaveEndDate);
		}
		List<Record> orders = dbDao.query(sql, cnd, null);

		for (Record record : list) {
			//收款id
			String id = record.get("id").toString();
			List<Record> rList = new ArrayList<Record>();
			for (Record r : orders) {
				String rid = r.getString("id");
				if (Util.eq(id, rid)) {
					rList.add(r);
				}
			}
			if (!Util.isEmpty(rList)) {
				record.put("orders", rList);
			}
		}
		List<Record> ordersBC = new ArrayList<Record>();
		for (Record r : list) {
			String oStr = r.getString("orders");
			if (!Util.isEmpty(oStr)) {
				ordersBC.add(r);
			}
		}

		listdata.remove("data");
		listdata.put("data", ordersBC);
		listdata.put("recordsFiltered", ordersBC.size());
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
	 * (保存  确认收款)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInterRec(String recId, HttpSession session) {

		int updateNum = 0;
		int orderRecEd = AccountReceiveEnum.RECEIVEDONEY.intKey();

		TOrderReceiveEntity orderReceiveEntity = dbDao.fetch(TOrderReceiveEntity.class,
				Cnd.where("receiveid", "in", recId));
		if (!Util.isEmpty(orderReceiveEntity)) {
			orderReceiveEntity.setReceivestatus(orderRecEd);
			orderReceiveEntity.setReceiveDate(DateUtil.nowDate());
			updateNum = dbDao.update(orderReceiveEntity);
			int rId = orderReceiveEntity.getReceiveid();
			dbDao.update(TReceiveEntity.class, Chain.make("status", orderRecEd), Cnd.where("id", "=", rId));
		}

		//收款成功添加消息提醒
		if (updateNum > 0) {
			//TODO  ******************************************添加消息提醒***********************************************
			String sqlS = sqlManager.get("receivePay_order_rec_rids");
			Sql sql = Sqls.create(sqlS);
			Cnd cnd = Cnd.NEW();
			cnd.and("r.id", "in", recId);
			List<Record> orderPnrList = dbDao.query(sql, cnd, null);
			for (Record record : orderPnrList) {
				int uid = Integer.valueOf(record.getString("id"));
				String ordernum = record.getString("ordersnum");
				String pnr = "";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
				map.put("remindType", MessageRemindEnum.UNREPEAT.intKey());
				searchViewService.addRemindMsg(map, ordernum, pnr, uid, RECEDMSGTYPE, session);
			}
		}

		return updateNum;
	}

	/**
	 * 付款中列表
	 */
	public Map<String, Object> listPayData(final InterPayListSearchSqlForm form, HttpSession session) {

		//检索条件
		String name = form.getName();
		Date leaveBeginDate = form.getLeaveBeginDate();
		Date leaveEndDate = form.getLeaveEndDate();

		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		String orderStatus = form.getOrderStatus();
		if (Util.isEmpty(orderStatus)) {
			orderStatus = "3";
		} else {
			if (Util.eq("firBooking", orderStatus)) {
				//一订
				orderStatus = InternationalStatusEnum.ONEBOOK.intKey() + "";
			}
			if (Util.eq("secBooking", orderStatus)) {
				//二订
				orderStatus = InternationalStatusEnum.TWOBOOK.intKey() + "";
			}
			if (Util.eq("thrBooking", orderStatus)) {
				//三订
				orderStatus = InternationalStatusEnum.THREEBOOK.intKey() + "";
			}
			if (Util.eq("allBooking", orderStatus)) {
				//全款
				orderStatus = InternationalStatusEnum.FULLAMOUNT.intKey() + "";
			}
			if (Util.eq("lastBooking", orderStatus)) {
				//尾款
				orderStatus = InternationalStatusEnum.TAILMONEY.intKey() + "";
			}
			if (Util.eq("outTicket", orderStatus)) {
				//出票
				orderStatus = InternationalStatusEnum.TICKETING.intKey() + "";
			}
		}
		form.setOrderStatus(orderStatus);
		form.setCompanyId(companyId);
		Map<String, Object> listdata = this.listPage4Datatables(form);
		@SuppressWarnings("unchecked")
		List<Record> payList = (List<Record>) listdata.get("data");

		//查询订单
		String sqlStr = sqlManager.get("receivePay_inter_pay_order_list");
		Sql conSql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + name + "%").or("uo.ordersnum", "LIKE", "%" + name + "%")
				.or("ci.linkMan", "LIKE", "%" + name + "%").or("pi.PNR", "LIKE", "%" + name + "%");
		if (!Util.isEmpty(name)) {
			cnd.and(group);
		}
		//出发日期
		if (!Util.isEmpty(leaveBeginDate)) {
			cnd.and("tpi.leavesdate", ">=", leaveBeginDate);
		}
		// 返回日期
		if (!Util.isEmpty(leaveEndDate)) {
			cnd.and("tpi.leavesdate", "<=", leaveEndDate);
		}
		/*cnd.and("tpi.orderPnrStatus", "=", APPROVALPAYED);*/
		List<Record> orders = dbDao.query(conSql, cnd, null);

		for (Record record : payList) {
			String payId = record.getString("id");
			List<Record> orderList = new ArrayList<Record>();
			for (Record r : orders) {
				String orderId = r.getString("id");
				if (Util.eq(payId, orderId)) {
					orderList.add(r);
				}
			}
			if (!Util.isEmpty(orderList)) {
				record.put("orders", orderList);
			}
		}
		List<Record> ordersBC = new ArrayList<Record>();
		for (Record r : payList) {
			String oStr = r.getString("orders");
			if (!Util.isEmpty(oStr)) {
				ordersBC.add(r);
			}
		}

		listdata.remove("data");
		listdata.put("data", ordersBC);
		listdata.put("recordsFiltered", ordersBC.size());
		return listdata;
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
	public Object listPayEdData(InterPayEdListSearchSqlForm form, HttpSession session) {
		//当前公司下的用户
		String userIds = userInComp(session);
		form.setLoginUserId(userIds);
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
	 * (确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toConfirmPay(String orderIds, HttpSession session) {
		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		Map<String, Object> map = new HashMap<String, Object>();
		Sql sql = Sqls.create(sqlManager.get("receivePay_inter_pay_order_ids"));
		/*String inlandPayIdStr = inlandPayIds.substring(0, inlandPayIds.length() - 1);*/
		Cnd cnd = Cnd.NEW();
		cnd.and("uo.id", "in", orderIds);
		List<Record> orders = dbDao.query(sql, cnd, null);
		String payIds = "";
		if (!Util.isEmpty(orders)) {
			String shortname = orders.get(0).getString("shortname");
			for (Record record : orders) {
				String everyShortName = record.getString("shortname");
				String id = record.getString("id");
				String uid = record.getString("uid");
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
		//申请人
		String proposer = "";
		//审批人
		String approver = "";
		//审批结果
		String approveresult = "";
		//用途
		String purpose = "";
		//币种
		String payCurrency = "";
		//操作人
		String operator = "";
		String operatorList = "";
		for (Record record : orders) {
			if (!Util.isEmpty(record.get("salePrice"))) {
				Double incometotal = (Double) record.get("salePrice");
				totalMoney += incometotal;
			}
			proposer = record.getString("proposer");
			approver = record.getString("approver"); //审批人
			String opr = record.getString("operator"); //操作人
			if (!Util.eq(operator, opr)) {
				operator = opr;
				operatorList += opr + ",";
			}
			approveresult = record.getString("approveresult");
			purpose = record.getString("purpose");
			payCurrency = record.getString("paycurrency");
		}
		map.put("totalMoney", totalMoney);
		map.put("proposer", proposer);
		map.put("approver", approver);
		map.put("purpose", purpose);
		map.put("payCurreny", payCurrency);
		map.put("operators", operatorList);
		if (Util.eq(APPROVALENABLE, approveresult)) {
			map.put("approveresult", "同意");
		} else {
			map.put("approveresult", "拒绝");
		}

		//银行名称
		List<TBankCardEntity> bankList = new ArrayList<TBankCardEntity>();
		try {
			Cnd cnd2 = Cnd.NEW();
			cnd2.and("companyid", "=", companyId);
			cnd2.and("bankName", "!=", "");
			cnd2.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
			cnd2.groupBy("bankName");
			bankList = dbDao.query(TBankCardEntity.class, cnd2, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("bankList", bankList);

		//币种
		List<DictInfoEntity> BZList = new ArrayList<DictInfoEntity>();
		try {
			BZList = externalInfoService.findDictInfoByName("", BZCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("bzList", BZList);

		//付款用途
		List<ComDictInfoEntity> fkytList = new ArrayList<ComDictInfoEntity>();
		try {
			fkytList = receivePayService.findCodeByName("", YTCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fkytList", fkytList);
		//资金种类
		List<ComDictInfoEntity> zjzlList = new ArrayList<ComDictInfoEntity>();
		try {
			zjzlList = receivePayService.findCodeByName("", ZJZLCODE, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("zjzlList", zjzlList);
		map.put("ids", orderIds);

		return map;
	}

	/**
	 * (保存  确认付款页面)
	 *
	 * @param inlandPayIds
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object saveInterPay(TSaveInterPayAddFrom form, HttpSession session) {
		List<TPayEntity> payList = new ArrayList<TPayEntity>();
		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		//当前登陆用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();
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
		//操作人
		String operators = form.getOperators();
		if (Util.eq("false", payNames)) {
			//收款单位不一致，不能付款
			return false;
		}
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
				if (!Util.eq("--请选择--", currency)) {
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
		//更新订单状态
		List<TPayOrderEntity> payOrderList = dbDao.query(TPayOrderEntity.class, Cnd.where("payid", "in", payIds), null);
		int updateNum = 0;
		if (!Util.eq(null, payIds)) {
			updateNum = dbDao.update(TPayOrderEntity.class, Chain.make("paystatus", APPROVALPAYED),
					Cnd.where("payid", "in", payIds));
		}

		//付款成功 操作台添加消息
		if (updateNum > 0) {
			//TODO  ******************************************添加消息提醒***********************************************
			String sqlS = sqlManager.get("receivePay_order_pnr_pids");
			Sql sql = Sqls.create(sqlS);
			Cnd cnd = Cnd.NEW();
			cnd.and("pi.id", "in", payIds);
			List<Record> orderPnrList = dbDao.query(sql, cnd, null);
			for (Record record : orderPnrList) {
				int uid = Integer.valueOf(record.getString("id"));
				String ordernum = record.getString("ordersnum");
				String pnr = record.getString("PNR");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("remindDate", DateTimeUtil.format(DateTimeUtil.nowDateTime()));
				map.put("remindType", MessageRemindEnum.UNREPEAT.intKey());
				searchViewService.addRemindMsg(map, ordernum, "", uid, PAYEDMSGTYPE, session);
			}
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

	//当前公司所有的用户 id
	public String userInComp(HttpSession session) {
		//当前登陆用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserIdd = loginUser.getId();
		//当前公司所有用户id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		String str = sqlManager.get("receivePay_loginComp_user_ids");
		Sql userSql = Sqls.create(str);
		Cnd userCnd = Cnd.NEW();
		userCnd.and("uj.`status`", "=", ONJOB);
		userCnd.and("cj.comId", "=", companyId);
		List<Record> userList = dbDao.query(userSql, userCnd, null);
		String userIds = "";
		for (Record user : userList) {
			userIds += user.getString("userid") + ",";
		}
		if (userIds.length() > 1) {
			userIds = userIds.substring(0, userIds.length() - 1);
		}
		return userIds;
	}

}
