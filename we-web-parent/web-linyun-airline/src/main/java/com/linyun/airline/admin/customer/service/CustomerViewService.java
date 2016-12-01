package com.linyun.airline.admin.customer.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
import org.nutz.json.Json;
import org.nutz.lang.Files;
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
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAgentEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TCustomerInvoiceEntity;
import com.linyun.airline.entities.TCustomerLineEntity;
import com.linyun.airline.entities.TCustomerOutcityEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TCustomerInfoAddForm;
import com.uxuexi.core.common.util.DateUtil;
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
	public Object agent() {
		List<TAgentEntity> agentList = dbDao.query(TAgentEntity.class, null, null);
		return agentList;
	}

	//客户公司
	public Object company(String comName) {
		List<Record> companyList = companyViewService.getCompanyList(CompanyTypeEnum.AGENT.intKey(), comName);
		List<Select2Option> result = transform2SelectOptions(companyList);
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
	public Object addCustomInfo(TCustomerInfoAddForm addForm) {
		if (!Util.isEmpty(addForm.getContractDueTimeString())) {
			//客户信息保存
			addForm.setContractDueTime(DateUtil.string2Date(addForm.getContractDueTimeString(), "yyyy-MM-dd"));
		}
		if (!Util.isEmpty(addForm.getContractTimeString())) {
			addForm.setContractTime(DateUtil.string2Date(addForm.getContractTimeString(), "yyyy-MM-dd"));
		}
		TCustomerInfoEntity customerInfo = this.add(addForm);
		//出发城市城市截取
		Iterable<String> outcityids = Splitter.on(",").split(addForm.getOutcityname());

		//关联城市保存
		List<TCustomerOutcityEntity> outcityEntities = new ArrayList<TCustomerOutcityEntity>();
		for (String dictInfoId : outcityids) {
			TCustomerOutcityEntity outcityEntity = new TCustomerOutcityEntity();
			outcityEntity.setInfoId(customerInfo.getId());
			outcityEntity.setDictInfoId(Long.valueOf(dictInfoId));
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
			lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			lineEntities.add(lineEntity);
		}
		for (String dictInfoId : internationLines) {
			TCustomerLineEntity lineEntity = new TCustomerLineEntity();
			lineEntity.setInfoId(customerInfo.getId());
			lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			lineEntities.add(lineEntity);
		}
		dbDao.insert(lineEntities);

		//	dbDao.updateRelations(before, after);

		//发票信息截取
		Iterable<String> sInvNames = Splitter.on(",").split(addForm.getSInvName());
		//发票信息保存
		List<TCustomerInvoiceEntity> invoiceEntities = new ArrayList<TCustomerInvoiceEntity>();
		for (String dictInfoId : sLine1s) {
			TCustomerInvoiceEntity invoiceEntity = new TCustomerInvoiceEntity();
			invoiceEntity.setInfoId(customerInfo.getId());
			invoiceEntity.setDictInfoId(Long.valueOf(dictInfoId));
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
	public Object toUpdatePage(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//查询客户信息
		obj.put("customer", dbDao.fetch(TCustomerInfoEntity.class, id));
		//准备负责人下拉
		List<TUserEntity> userlist = dbDao.query(TUserEntity.class, null, null);
		obj.put("userlist", userlist);
		//查询出发城市

		Sql sql = Sqls.create(sqlManager.get("customer_cityOption_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("o.infoId", "=", id);
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> outcityEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);
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
		List<DictInfoEntity> innerLineList = getLineList(id, "GJNL");
		obj.put("innerlinelist", Json.toJson(innerLineList));
		//国际
		List<DictInfoEntity> intelLineEntities = getLineList(id, "GJ");
		obj.put("intellinelist", intelLineEntities);
		//发票项
		List<TCustomerInvoiceEntity> invoiceEntities = dbDao.query(TCustomerInvoiceEntity.class,
				Cnd.where("infoId", "=", id), null);
		obj.put("invoicelist", invoiceEntities);
		return obj;
	}

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
	public Object goCity(String outcityName) throws Exception {

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
			//本地出发城市
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
	public Object isLine(String lineName) throws Exception {
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
			//本地线路
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
	public Object isInvioce(String invioceName) throws Exception {
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
			//本地线路
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
	public Object international(String lineName) throws Exception {
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
			//本地线路
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

		return list;

	}

}