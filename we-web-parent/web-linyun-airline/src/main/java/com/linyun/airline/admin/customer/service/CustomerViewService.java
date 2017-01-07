package com.linyun.airline.admin.customer.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.linyun.airline.admin.Company.service.CompanyViewService;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TCustomerInvoiceEntity;
import com.linyun.airline.entities.TCustomerLineEntity;
import com.linyun.airline.entities.TCustomerOutcityEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TCustomerInfoAddForm;
import com.linyun.airline.forms.TCustomerInfoUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

@IocBean
public class CustomerViewService extends BaseService<TCustomerInfoEntity> {
	private static final Log log = Logs.get();

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private CompanyViewService companyViewService;

	@Inject
	private UploadService fdfsUploadService;

	//负责人
	public Object agent(HttpSession session) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		Sql sql = Sqls.create(sqlManager.get("customer_agent_list"));
		sql.setParam("comid", companyId);
		List<Record> record = dbDao.query(sql, null, null);
		obj.put("userlist", record);
		return obj;
	}

	//客户公司
	public Object company(String comName) {
		Sql sql = Sqls.create(sqlManager.get("agentCompany_list"));
		sql.setParam("comtype", CompanyTypeEnum.AGENT.intKey());
		sql.setParam("deletestatus", 0);
		Cnd cnd = Cnd.NEW();
		cnd.and("comName", "like", Strings.trim(comName) + "%");
		sql.setCondition(cnd);
		List<Record> agentCompanyList = dbDao.query(sql, null, null);
		List<Select2Option> result = transform2SelectOptions(agentCompanyList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions(List<Record> companyList) {
		return Lists.transform(companyList, new Function<Record, Select2Option>() {

			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();

				op.setId(record.getInt("id"));
				op.setText(record.getString("comName"));
				return op;
			}
		});
	}

	//批量删除
	public Object batchDelete(long[] ids) {
		FormUtil.delete(dbDao, TCustomerInfoEntity.class, ids);
		return JsonResult.success("删除成功");
	}

	//附件上传 返回值文件存储地址
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(File file, HttpSession session) {
		try {
			String ext = Files.getSuffix(file);
			FileInputStream fileInputStream = new FileInputStream(file);
			String url = fdfsUploadService.uploadImage(fileInputStream, ext, null);
			//文件存储地址
			System.out.println(url);
			return url;
			//业务
		} catch (Exception e) {
			return MobileResult.error("操作失败", null);
		}
	}

	/**
	 * 
	 * TODO添加信息
	 * <p>
	 * TODO添加信息
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addCustomInfo(HttpSession session, TCustomerInfoAddForm addForm) {

		if (!Util.isEmpty(addForm.getContractDueTimeString())) {
			//客户信息保存
			addForm.setContractDueTime(DateUtil.string2Date(addForm.getContractDueTimeString(), "yyyy-MM-dd"));
		}
		if (!Util.isEmpty(addForm.getContractTimeString())) {
			addForm.setContractTime(DateUtil.string2Date(addForm.getContractTimeString(), "yyyy-MM-dd"));
		}
		//得到当前用户所在公司的id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		if (!Util.isEmpty(upcompany)) {
			addForm.setUpComId(upcompany.getId());
		}
		TCustomerInfoEntity customerInfo = this.add(addForm);

		//出发城市城市截取
		Iterable<String> outcityids = Splitter.on(",").split(addForm.getOutcityname());
		//出发城市保存
		List<TCustomerOutcityEntity> outcityEntities = new ArrayList<TCustomerOutcityEntity>();
		for (String dictInfoId : outcityids) {
			TCustomerOutcityEntity outcityEntity = new TCustomerOutcityEntity();
			outcityEntity.setInfoId(customerInfo.getId());
			//判断是否为空
			if (!Util.isEmpty(dictInfoId) && Long.valueOf(dictInfoId) != 0) {
				outcityEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			outcityEntities.add(outcityEntity);
		}
		dbDao.insert(outcityEntities);

		//国境内陆截取
		Iterable<String> sLine1s = Splitter.on(",").split(addForm.getSLine1());
		Iterable<String> internationLines = Splitter.on(",").split(addForm.getInternationLine());
		//国境内陆保存
		List<TCustomerLineEntity> lineEntities = new ArrayList<TCustomerLineEntity>();
		for (String dictInfoId : sLine1s) {
			TCustomerLineEntity lineEntity = new TCustomerLineEntity();
			lineEntity.setInfoId(customerInfo.getId());
			if (!Util.isEmpty(dictInfoId)) {
				lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			lineEntities.add(lineEntity);
		}
		if (!Util.isEmpty(internationLines)) {
			for (String dictInfoId : internationLines) {
				TCustomerLineEntity lineEntity = new TCustomerLineEntity();
				lineEntity.setInfoId(customerInfo.getId());
				if (!Util.isEmpty(dictInfoId)) {
					lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
				}
				lineEntities.add(lineEntity);
			}
		}
		dbDao.insert(lineEntities);

		//	dbDao.updateRelations(before, after);

		//发票信息截取
		Iterable<String> sInvNames = Splitter.on(",").split(addForm.getSInvName());
		//发票信息保存
		List<TCustomerInvoiceEntity> invoiceEntities = new ArrayList<TCustomerInvoiceEntity>();
		for (String dictInfoId : sInvNames) {
			TCustomerInvoiceEntity invoiceEntity = new TCustomerInvoiceEntity();
			invoiceEntity.setInfoId(customerInfo.getId());
			if (!Util.isEmpty(dictInfoId)) {
				invoiceEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			invoiceEntities.add(invoiceEntity);
		}
		dbDao.insert(invoiceEntities);

		return null;
	}

	/**
	 * 
	 * TODO跳转到update 
	 * <p>
	 * TODO为更新页面准备数据
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toUpdatePage(HttpSession session, long id) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		//查询客户信息
		TCustomerInfoEntity tCustomerInfoEntity = dbDao.fetch(TCustomerInfoEntity.class, id);
		Date contractTime = tCustomerInfoEntity.getContractTime();
		Date contractDueTime = tCustomerInfoEntity.getContractDueTime();

		//日期格式转换
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String contractTimeStr = null;
		if (!Util.isEmpty(contractTime)) {
			contractTimeStr = sdf.format(contractTime);
			tCustomerInfoEntity.setContractTimeString(contractTimeStr);
		}
		String contractDueTimeStr = null;
		if (!Util.isEmpty(contractDueTime)) {
			contractDueTimeStr = sdf.format(contractDueTime);
			tCustomerInfoEntity.setContractDueTimeString(contractDueTimeStr);
		}

		obj.put("customer", tCustomerInfoEntity);

		//负责人查询
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		Sql sql = Sqls.create(sqlManager.get("customer_agent_list"));
		sql.setParam("comid", companyId);
		List<Record> record = dbDao.query(sql, null, null);
		obj.put("userlist", record);

		//查询公司名称
		Sql comSql = Sqls.create(sqlManager.get("customer_comOption_list"));
		Cnd comCnd = Cnd.NEW();
		comCnd.and("ci.id", "=", id);
		comSql.setCondition(comCnd);
		//只有一个
		List<Record> agentCompanyList = dbDao.query(comSql, null, null);
		//公司名称id 拼串
		String comIds = "";
		String comName = "";
		for (Record r : agentCompanyList) {
			comIds = r.getString("agentId") + "";
			comName = r.getString("name");
		}
		obj.put("comIds", comIds);
		obj.put("comEntity", Lists.transform(agentCompanyList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();

				op.setId(Long.valueOf(record.getString("agentid")));
				op.setText(record.getString("comname"));
				return op;
			}
		}));

		//查询出发城市
		Sql citySql = Sqls.create(sqlManager.get("customer_cityOption_list"));
		Cnd cityCnd = Cnd.NEW();
		cityCnd.and("o.infoId", "=", id);
		cityCnd.orderBy("d.dictName", "desc");
		citySql.setCondition(cityCnd);
		List<DictInfoEntity> outcityEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, citySql);
		//出发城市id 拼串
		String outcityIds = "";
		for (DictInfoEntity outcityEntity : outcityEntities) {
			outcityIds += outcityEntity.getId() + ",";
		}
		if (outcityIds.length() > 0) {
			outcityIds = outcityIds.substring(0, outcityIds.length() - 1);
		}
		obj.put("outcityIds", outcityIds);
		obj.put("outcitylist", Lists.transform(outcityEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));

		//国境内陆
		Sql lineSql = Sqls.create(sqlManager.get("customer_islineOption_list"));
		Cnd lineCnd = Cnd.NEW();
		lineCnd.and("o.infoId", "=", id);
		lineCnd.and("d.typeCode", "=", "GJNL");
		lineCnd.orderBy("d.dictName", "desc");
		lineSql.setCondition(lineCnd);
		List<DictInfoEntity> innerLineEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, lineSql);
		//国境内陆id 拼串
		String innerLineIds = "";
		for (DictInfoEntity innerLineEntity : innerLineEntities) {
			innerLineIds += innerLineEntity.getId() + ",";
		}
		if (innerLineIds.length() > 0) {
			innerLineIds = innerLineIds.substring(0, innerLineIds.length() - 1);
		}
		obj.put("innerCityIds", innerLineIds);
		obj.put("innerlinelist", Lists.transform(innerLineEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));

		//国际
		Sql interLineSql = Sqls.create(sqlManager.get("customer_islineOption_list"));
		Cnd interLineCnd = Cnd.NEW();
		interLineCnd.and("o.infoId", "=", id);
		interLineCnd.and("d.typeCode", "=", "GJ");
		interLineCnd.orderBy("d.dictName", "desc");
		interLineSql.setCondition(interLineCnd);
		List<DictInfoEntity> interLineEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, interLineSql);
		//国际线路id 拼串
		String interLineIds = "";
		for (DictInfoEntity interLineEntity : interLineEntities) {
			interLineIds += interLineEntity.getId() + ",";
		}
		if (interLineIds.length() > 0) {
			interLineIds = interLineIds.substring(0, interLineIds.length() - 1);
		}

		obj.put("interLineIds", interLineIds);
		obj.put("interlinelist", Lists.transform(interLineEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));

		//发票项
		Sql invioceSql = Sqls.create(sqlManager.get("customer_invioceOption_list"));
		Cnd invioceCnd = Cnd.NEW();
		invioceCnd.and("l.infoId", "=", id);
		invioceCnd.and("d.typeCode", "=", "FPXM");
		invioceCnd.orderBy("d.dictName", "desc");
		invioceSql.setCondition(invioceCnd);
		List<DictInfoEntity> invioceEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, invioceSql);
		String invioceIds = "";
		for (DictInfoEntity invicoeEntity : invioceEntities) {
			invioceIds += invicoeEntity.getId() + ",";
		}
		if (invioceIds.length() > 0) {
			invioceIds = invioceIds.substring(0, invioceIds.length() - 1);
		}
		obj.put("invioceIds", invioceIds);
		obj.put("invoicelist", Lists.transform(invioceEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));

		return obj;
	}

	/**
	 * 
	 * TODO客户信息更新
	 *
	 * @param customerId
	 * @param typeCode
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateCustomInfo(HttpSession session, TCustomerInfoUpdateForm updateForm) {
		if (!Util.isEmpty(updateForm.getContractDueTimeString())) {
			//客户信息保存
			updateForm.setContractDueTime(DateUtil.string2Date(updateForm.getContractDueTimeString(), "yyyy-MM-dd"));
		}
		if (!Util.isEmpty(updateForm.getContractTimeString())) {
			updateForm.setContractTime(DateUtil.string2Date(updateForm.getContractTimeString(), "yyyy-MM-dd"));
		}
		//得到当前用户所在公司的id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		if (!Util.isEmpty(upcompany)) {
			updateForm.setUpComId(upcompany.getId());
		}

		this.update(updateForm);

		//出发城市城市截取
		Iterable<String> outcityids = Splitter.on(",").split(updateForm.getOutcityname());
		//出发城市更新
		List<TCustomerOutcityEntity> outCitysAfter = new ArrayList<TCustomerOutcityEntity>();
		for (String dictInfoId : outcityids) {
			TCustomerOutcityEntity outcityEntity = new TCustomerOutcityEntity();
			outcityEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				outcityEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			outCitysAfter.add(outcityEntity);
		}

		List<TCustomerOutcityEntity> outCitysBefore = dbDao.query(TCustomerOutcityEntity.class,
				Cnd.where("infoId", "=", updateForm.getId()), null);
		dbDao.updateRelations(outCitysBefore, outCitysAfter);

		//国境内陆截取
		Iterable<String> sLine1s = Splitter.on(",").split(updateForm.getSLine1());
		//国际截取
		Iterable<String> internationLines = Splitter.on(",").split(updateForm.getInternationLine());

		List<TCustomerLineEntity> linesAfter = new ArrayList<TCustomerLineEntity>();
		for (String dictInfoId : sLine1s) {
			TCustomerLineEntity lineEntity = new TCustomerLineEntity();
			lineEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			linesAfter.add(lineEntity);
		}
		for (String dictInfoId : internationLines) {
			TCustomerLineEntity lineEntity = new TCustomerLineEntity();
			lineEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			linesAfter.add(lineEntity);
		}
		List<TCustomerLineEntity> linesBefore = dbDao.query(TCustomerLineEntity.class,
				Cnd.where("infoId", "=", updateForm.getId()), null);
		dbDao.updateRelations(linesBefore, linesAfter);

		//发票信息截取
		Iterable<String> sInvNames = Splitter.on(",").split(updateForm.getSInvName());
		//发票信息更新
		List<TCustomerInvoiceEntity> invoicesAfter = new ArrayList<TCustomerInvoiceEntity>();
		for (String dictInfoId : sInvNames) {
			TCustomerInvoiceEntity invoiceEntity = new TCustomerInvoiceEntity();
			invoiceEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				invoiceEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			invoicesAfter.add(invoiceEntity);
		}
		List<TCustomerInvoiceEntity> invioceBefore = dbDao.query(TCustomerInvoiceEntity.class,
				Cnd.where("infoId", "=", updateForm.getId()), null);
		dbDao.updateRelations(invioceBefore, invoicesAfter);

		return null;
	}

	/**
	 * 
	 * TODO 线路查询
	 * <p>
	 * TODO根据参数不同， 分别查询国境内陆和国际线路
	 *
	 * @param customerId 客户id
	 * @param typeCode   线路类型
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	private List<DictInfoEntity> getLineList(long customerId, String typeCode) {
		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.typeCode", "=", typeCode);
		cnd.and("l.infoId", "=", customerId);
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> innerLineList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);
		return innerLineList;
	}

	/**
	 * 
	 * TODO出发城市
	 * <p>
	 * TODO出发城市
	 *
	 * @param outcityName
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object goCity(String outcityName, String ids) throws Exception {

		Set<DictInfoEntity> set = Sets.newTreeSet();

		//出发城市
		Sql sql = Sqls.create(sqlManager.get("customer_city_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("dictName", "like", outcityName);
		cnd.orderBy("dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> localOutCityList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);

		if (localOutCityList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				DictInfoEntity info = localOutCityList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localOutCityList);

			//数据字典表中查找出发城市
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(outcityName, "CFCS");
			int needmore = 5 - localOutCityList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (DictInfoEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (DictInfoEntity dict : set) {
				Select2Option op = new Select2Option();
				op.setId(dict.getId());
				op.setText(dict.getDictName());
				list.add(op);
			}
		}
		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}
		return list;
	}

	/**
	 * 
	 * TODO线路查询
	 * <p>
	 * TODO线路查询
	 *
	 * @param lineName
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object isLine(String lineName, String ids) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();

		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.typeCode", "=", "GJNL");
		cnd.and("d.dictName", "like", lineName + "%");
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> localLineList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);

		if (localLineList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				DictInfoEntity info = localLineList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localLineList);

			//数据字典表中查找出发城市
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(lineName, "GJNL");
			int needmore = 5 - localLineList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (DictInfoEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (DictInfoEntity dict : set) {
				Select2Option op = new Select2Option();
				op.setId(dict.getId());
				op.setText(dict.getDictName());
				list.add(op);
			}
		}
		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}
		return list;
	}

	/**
	 * 
	 * TODO查询发票项
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param invioceName
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object isInvioce(String invioceName, String ids) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();

		Sql sql = Sqls.create(sqlManager.get("customer_invioce_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.typeCode", "=", "FPXM");
		cnd.and("d.dictName", "like", invioceName);
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> localInvioceList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);

		if (localInvioceList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				DictInfoEntity info = localInvioceList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localInvioceList);

			//数据字典表中查找出发城市
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(invioceName, "FPXM");
			int needmore = 5 - localInvioceList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (DictInfoEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (DictInfoEntity dict : set) {
				Select2Option op = new Select2Option();
				op.setId(dict.getId());
				op.setText(dict.getDictName());
				list.add(op);
			}
		}
		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}
		return list;
	}

	/**
	 * 
	 * TODO国际线路
	 * <p>
	 * TODO查询国际线路select2
	 *
	 * @param lineName
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object international(String lineName, String ids) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();

		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.dictName", "like", lineName + "%");
		cnd.and("d.typeCode", "=", "GJ");
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> localLineList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);

		if (localLineList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				DictInfoEntity info = localLineList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localLineList);

			//数据字典表中查找出发城市
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(lineName, "GJ");
			int needmore = 5 - localLineList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (DictInfoEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (DictInfoEntity dict : set) {
				Select2Option op = new Select2Option();
				op.setId(dict.getId());
				op.setText(dict.getDictName());
				list.add(op);
			}
		}

		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}

		return list;

	}

	/**
	 * 
	 * TODO(根据客户姓名查询客户信息)
	 * <p>
	 *
	 * @param linkname
	 * @return TODO(客户信息列表)
	 */
	public List<TCustomerInfoEntity> getCustomerInfoByLinkName(String linkname) {
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		try {
			customerInfos = dbDao.query(TCustomerInfoEntity.class,
					Cnd.where("linkMan", "like", Strings.trim(linkname) + "%"), null);
			if (customerInfos.size() > 5) {
				customerInfos = customerInfos.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerInfos;
	}

	/**
	 * 
	 * TODO(根据客户电话查询客户信息)
	 * <p>
	 *
	 * @param linkname
	 * @return TODO(客户信息列表)
	 */
	public List<TCustomerInfoEntity> getCustomerInfoByPhone(String phonenum) {
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		try {
			customerInfos = dbDao.query(TCustomerInfoEntity.class,
					Cnd.where("telephone", "like", Strings.trim(phonenum) + "%"), null);
			if (customerInfos.size() > 5) {
				customerInfos = customerInfos.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerInfos;
	}

	/**
	 * 
	 * TODO(根据id查询客户信息)
	 * <p>
	 *
	 * @param id
	 * @return TODO(客户信息)
	 */
	public Object getCustomerById(Long id) {
		TCustomerInfoEntity customerInfoEntity = dbDao.fetch(TCustomerInfoEntity.class, id);
		String agent = customerInfoEntity.getAgent();
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("id", "=", agent));
		customerInfoEntity.setAgent(userEntity.getUserName());
		Map<String, Object> obj = getOutCitys(id);
		obj.put("customerInfoEntity", customerInfoEntity);

		return JsonUtil.toJson(obj);
	}

	/**
	 * 
	 * TODO(根据客户id查询出发城市)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> getOutCitys(Long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		Sql citySql = Sqls.create(sqlManager.get("customer_cityOption_list"));
		Cnd cityCnd = Cnd.NEW();
		cityCnd.and("o.infoId", "=", id);
		cityCnd.orderBy("d.dictName", "desc");
		citySql.setCondition(cityCnd);
		List<DictInfoEntity> outcityEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, citySql);
		//出发城市id 拼串
		String outcityIds = "";
		for (DictInfoEntity outcityEntity : outcityEntities) {
			outcityIds += outcityEntity.getId() + ",";
		}
		if (outcityIds.length() > 0) {
			outcityIds = outcityIds.substring(0, outcityIds.length() - 1);
		}
		obj.put("outcityIds", outcityIds);
		obj.put("outcitylist", Lists.transform(outcityEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));
		return obj;
	}
}