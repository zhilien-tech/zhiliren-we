package com.linyun.airline.admin.invoicemanage.invoiceinfo.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.admin.invoicemanage.invoiceinfo.from.TInvoiceInfoSqlForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.order.inland.form.KaiInvoiceParamForm;
import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayPnrEntity;
import com.linyun.airline.admin.receivePayment.entities.TPayReceiptEntity;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TInvoiceDetailEntity;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.linyun.airline.entities.TOrderReceiveEntity;
import com.linyun.airline.entities.TReceiveBillEntity;
import com.linyun.airline.entities.TReceiveEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class InvoiceinfoViewService extends BaseService<TInvoiceInfoEntity> {

	private static final String BIZHONGCODE = "BZ";
	//银行卡类型
	private static final String YHCODE = "YH";
	//付款用途
	private static final String FPXMCODE = "FPXM";

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private UploadService qiniuUploadService;

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
		Integer status = sqlForm.getStatus();//状态
		Integer billuserid = sqlForm.getBilluserid();//开票人
		Date kaiInvoiceBeginDate = sqlForm.getKaiInvoiceBeginDate();//起始时间
		Date kaiInvoiceEndDate = sqlForm.getKaiInvoiceEndDate();//结束时间
		String invoicenum = sqlForm.getInvoicenum();//发票号
		String paymentunit = sqlForm.getPaymentunit();//付款单位
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
		if (!Util.isEmpty(status)) {
			cnd.and("ii.status", "=", status);
		}
		//开发票中
		cnd.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());
		cnd.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
		sql.setCondition(cnd);
		List<Record> listinvodata = dbDao.query(sql, cnd, null);
		/*if (listinvodata.size() > 0) {
			int billuserid = listinvodata.get(0).getInt("billuserid");
			sqlForm.setUserid(billuserid);
		} else {
			sqlForm.setUserid(0);
		}*/
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
			SqlExpressionGroup group = new SqlExpressionGroup();
			/*group.and("idd.invoicenum", "LIKE", "%" + invoicenum + "%").or("ii.paymentunit", "LIKE",
					"%" + paymentunit + "%");
			if (!Util.isEmpty(invoicenum)) {
				cnd.and(group);
			}*/
			//开票日期
			if (!Util.isEmpty(kaiInvoiceBeginDate)) {
				cnd1.and("ii.invoicedate", ">=", kaiInvoiceBeginDate);
			}
			//开票日期
			if (!Util.isEmpty(kaiInvoiceEndDate)) {
				cnd1.and("ii.invoicedate", "<=", kaiInvoiceEndDate);
			}
			if (!Util.isEmpty(status)) {
				cnd1.and("ii.status", "=", status);
			}
			if (!Util.isEmpty(billuserid)) {
				cnd1.and("ii.billuserid", "=", billuserid);
			}
			cnd1.and("ii.comId", "=", comId);
			cnd1.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
			cnd1.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
			//订单信息
			List<Record> orders = dbDao.query(sql1, cnd1, null);
			record.put("orders", orders);
			//订单信息
			record.put("username", dbDao.fetch(TUserEntity.class, Long.valueOf(record.getInt("billuserid")))
					.getUserName());
			record.put("invoiceinfoenum", EnumUtil.enum2(InvoiceInfoEnum.class));
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
	 * 打开开发票页面
	 * @param request
	 */
	public Object kaiInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Map<String, Object> result = Maps.newHashMap();
		//发票id
		String id = request.getParameter("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		//发票明细
		List<TInvoiceDetailEntity> invoicedetail = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", invoiceinfo.getId()), null);
		//付款信息
		TReceiveEntity fetch = dbDao.fetch(TReceiveEntity.class, Long.valueOf(invoiceinfo.getReceiveid()));
		double invoicebalance = 0.00;
		if (!Util.isEmpty(fetch)) {
			invoicebalance = fetch.getSum();
		}
		for (TInvoiceDetailEntity detail : invoicedetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", formatDouble(invoicebalance));
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class, Cnd.where("comTypeCode", "=", FPXMCODE)
				.and("comId", "=", company.getId()), null);
		result.put("ytselect", ytselect);
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
	 * 保存开发票数据
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public Object saveKaiInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		String id = (String) fromJson.get("id");
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(fromJson.get("billuserid"))) {
			invoiceinfo.setBilluserid(Integer.valueOf((String) fromJson.get("billuserid")));
		}
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		Double difference = null;
		if (!Util.isEmpty(fromJson.get("difference"))) {
			difference = formatDouble(Double.valueOf((String) fromJson.get("difference")));
		}
		invoiceinfo.setDifference(difference);
		Double balance = null;
		if (!Util.isEmpty(fromJson.get("balance"))) {
			balance = formatDouble(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setBalance(balance);
		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setStatus(InvoiceInfoEnum.INVOIC_ED.intKey());
		//保存发票信息
		dbDao.update(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		//之前的发票信息
		List<TInvoiceDetailEntity> before = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", id), null);
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity entity = new TInvoiceDetailEntity();
			entity.setInvoicenum(map.get("invoicenum"));
			Double invoicebalance = null;
			if (!Util.isEmpty(map.get("invoicebalance"))) {
				invoicebalance = Double.valueOf(map.get("invoicebalance"));
			}
			entity.setInvoicebalance(formatDouble(invoicebalance));
			entity.setInvoiceurl(map.get("invoiceurl"));
			entity.setImagename(map.get("filename"));
			entity.setInvoiceinfoid(Integer.valueOf(id));
			invoicedetails.add(entity);
		}
		dbDao.updateRelations(before, invoicedetails);
		return null;
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

	/**
	 * 打开收发票页面
	 * @param request
	 */
	public Object shouInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		List<ComDictInfoEntity> ytselect = dbDao.query(ComDictInfoEntity.class, Cnd.where("comTypeCode", "=", FPXMCODE)
				.and("comId", "=", company.getId()), null);
		Map<String, Object> result = new HashMap<String, Object>();
		//发票id
		String id = request.getParameter("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		//发票明细
		List<TInvoiceDetailEntity> invoiceDetail = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", invoiceinfo.getId()), null);
		String sqlString = sqlManager.get("get_fukuan_info_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tpi.id", "=", invoiceinfo.getPnrid());
		List<Record> pnrinfo = dbDao.query(sql, cnd, null);
		result.put("pnrinfo", pnrinfo);
		double sumjine = 0;
		for (Record record : pnrinfo) {
			if (!Util.isEmpty(record.get("salespricesum"))) {
				Double salespricesum = (Double) record.get("salespricesum");
				sumjine += Double.valueOf(salespricesum);
			}
		}
		result.put("sumjine", formatDouble(sumjine));
		double invoicebalance = sumjine;
		for (TInvoiceDetailEntity detail : invoiceDetail) {
			if (!Util.isEmpty(detail.getInvoicebalance())) {
				invoicebalance -= detail.getInvoicebalance();
			}
		}
		result.put("invoicebalance", formatDouble(invoicebalance));
		List<TPayPnrEntity> query = dbDao.query(TPayPnrEntity.class, Cnd.where("pnrId", "=", invoiceinfo.getPnrid()),
				null);
		TPayEntity payinfo = new TPayEntity();
		String billurl = "";
		if (query.size() > 0) {
			payinfo = dbDao.fetch(TPayEntity.class, query.get(0).getId().longValue());
			if (!Util.isEmpty(payinfo)) {
				List<TPayReceiptEntity> query2 = dbDao.query(TPayReceiptEntity.class,
						Cnd.where("payId", "=", payinfo.getId()), null);
				if (query2.size() > 0) {
					billurl = query2.get(0).getReceiptUrl();
				}
			}
		}
		List<DictInfoEntity> yhkSelect = new ArrayList<DictInfoEntity>();
		try {
			yhkSelect = externalInfoService.findDictInfoByName("", YHCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//付款银行卡信息
		Record companybank = new Record();
		String pagesqlStr = sqlManager.get("get_fukuan_invoice_page_data");
		Sql pagesql = Sqls.create(sqlString);
		Cnd pagecnd = Cnd.NEW();
		cnd.and("tpp.pnrId", "=", id);
		List<Record> banks = dbDao.query(pagesql, pagecnd, null);
		if (banks.size() > 0) {
			companybank = banks.get(0);
		}
		result.put("ytselect", ytselect);
		result.put("companybank", companybank);
		result.put("id", id);
		result.put("billurl", billurl);
		result.put("yhkSelect", yhkSelect);
		result.put("payinfo", payinfo);
		result.put("invoiceDetail", invoiceDetail);
		result.put("invoiceinfo", invoiceinfo);
		return result;

	}

	/**
	 * 保存收发票数据
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public Object saveShouInvoiceInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		String jsondata = request.getParameter("data");
		Map<String, Object> fromJson = JsonUtil.fromJson(jsondata, Map.class);
		String id = (String) fromJson.get("id");
		//发票信息
		TInvoiceInfoEntity invoiceinfo = dbDao.fetch(TInvoiceInfoEntity.class, Long.valueOf(id));
		if (!Util.isEmpty(fromJson.get("invoiceitem"))) {
			invoiceinfo.setInvoiceitem(Integer.valueOf((String) fromJson.get("invoiceitem")));
		}
		if (!Util.isEmpty(fromJson.get("invoicedate"))) {
			invoiceinfo.setInvoicedate(DateUtil.string2Date((String) fromJson.get("invoicedate"),
					DateUtil.FORMAT_YYYY_MM_DD));
		}
		if (!Util.isEmpty(fromJson.get("billuserid"))) {
			invoiceinfo.setBilluserid(Integer.valueOf((String) fromJson.get("billuserid")));
		}
		if (!Util.isEmpty(fromJson.get("deptid"))) {
			invoiceinfo.setDeptid(Integer.valueOf((String) fromJson.get("deptid")));
		}
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		invoiceinfo.setRemark((String) fromJson.get("remark"));
		invoiceinfo.setPaymentunit((String) fromJson.get("paymentunit"));
		Double difference = null;
		if (!Util.isEmpty(fromJson.get("difference"))) {
			difference = formatDouble(Double.valueOf((String) fromJson.get("difference")));
		}
		invoiceinfo.setDifference(difference);
		Double balance = null;
		if (!Util.isEmpty(fromJson.get("balance"))) {
			balance = formatDouble(Double.valueOf((String) fromJson.get("balance")));
		}
		invoiceinfo.setBalance(balance);
		invoiceinfo.setOpid(new Long(user.getId()).intValue());
		invoiceinfo.setOptime(new Date());
		invoiceinfo.setStatus(InvoiceInfoEnum.Already_INVOICe.intKey());//已收发票
		//保存发票信息
		dbDao.update(invoiceinfo);
		List<Map<String, String>> details = (List<Map<String, String>>) fromJson.get("invoicedetails");
		List<TInvoiceDetailEntity> before = dbDao.query(TInvoiceDetailEntity.class,
				Cnd.where("invoiceinfoid", "=", id), null);
		List<TInvoiceDetailEntity> invoicedetails = new ArrayList<TInvoiceDetailEntity>();
		for (Map<String, String> map : details) {
			TInvoiceDetailEntity invoiceDetailEntity = new TInvoiceDetailEntity();
			invoiceDetailEntity.setInvoicenum(map.get("invoicenum"));
			Double invoicebalance = null;
			if (!Util.isEmpty(map.get("invoicebalance"))) {
				invoicebalance = formatDouble(Double.valueOf(map.get("invoicebalance")));
			}
			invoiceDetailEntity.setInvoicebalance(invoicebalance);
			invoiceDetailEntity.setInvoiceurl(map.get("invoiceurl"));
			invoiceDetailEntity.setImagename(map.get("filename"));
			invoiceDetailEntity.setInvoiceinfoid(Integer.valueOf(id));
			invoicedetails.add(invoiceDetailEntity);
		}
		dbDao.updateRelations(before, invoicedetails);
		return null;
	}

	/**
	 * 保留两位小数
	 */
	private Double formatDouble(Double doublenum) {
		Double result = null;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		if (!Util.isEmpty(doublenum)) {
			String format = decimalFormat.format(doublenum);
			result = Double.valueOf(format);
		}
		return result;
	}
}