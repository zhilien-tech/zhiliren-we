package com.linyun.airline.admin.customer.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.upload.UploadAdaptor;

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
	public Object company() {
		List<Record> companyList = companyViewService.getCompanyList(CompanyTypeEnum.AGENT.intKey());
		return null;
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

	//出发城市
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
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(outcityName);
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

	//线路查询
	public Object isLine(String lineName) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();

		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("dictName", "like", lineName);
		cnd.orderBy("dictName", "desc");
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
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(lineName);
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

	//发票项
	public Object isInvioce(String invioceName) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();

		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("dictName", "like", invioceName);
		cnd.orderBy("dictName", "desc");
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
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(invioceName);
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

}