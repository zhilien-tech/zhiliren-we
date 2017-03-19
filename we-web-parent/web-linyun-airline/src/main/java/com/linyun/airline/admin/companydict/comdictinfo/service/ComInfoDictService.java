/**
 * InfoServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
<<<<<<< HEAD
*/

package com.linyun.airline.admin.companydict.comdictinfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.companydict.comdictinfo.entity.ComDictInfoEntity;
import com.linyun.airline.admin.companydict.comdictinfo.form.ComInfoUpdateForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
public class ComInfoDictService extends BaseService<ComDictInfoEntity> {

	/**
	 * 公司字典类别名称查询
	 * @param session
	 */
	public Object getComDictTypeName(final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到当前登录公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("company_select_dicttypename"));
		Cnd cnd = Cnd.NEW();
		cnd.and("cty.comId", "=", companyId);
		List<Record> query = dbDao.query(sql, cnd, null);
		obj.put("listTypeName", query);
		return obj;
	}

	public boolean update(ComInfoUpdateForm form) {
		// 修改字典信息实体
		FormUtil.modify(dbDao, form, ComDictInfoEntity.class);
		return true;
	}

	//编辑时数据回显
	public Map<String, Object> findDirinfo(long id, final HttpSession session) {
		Map<String, Object> obj = Maps.newHashMap();
		//从session中得到公司id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long comId = company.getId();//得到公司的id
		Sql sql = Sqls.create(sqlManager.get("company_dict_updateinfo"));
		Cnd cnd = Cnd.NEW();
		cnd.and("cin.id", "=", id);
		cnd.and("cin.comId", "=", comId);
		List<Record> single = dbDao.query(sql, cnd, null);
		obj.put("single", single);
		return obj;
	}

	/**
	 * 获取字典类别名称下拉框
	 * @param sqlManager
	 * @param typeCode
	 * @param departmentForm
	 */
	@SuppressWarnings("all")
	public List<Record> getTypeNameSelect(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(ComDictInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("status", "=", DataStatusEnum.ENABLE.intKey());//状态为已启用的数据
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 获取字典信息名称下拉框
	 * @param typeCode
	 * @param dictName
	 */

	public List<Record> getDictNameList(String typeCode, String dictName) {
		String sqlString = sqlManager.get("dict_info_area");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("i.typeCode", "=", "QY");
		cnd.and("i.status", "=", 1);
		cnd.and("i.dictName", "like", dictName + "%");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}
}
