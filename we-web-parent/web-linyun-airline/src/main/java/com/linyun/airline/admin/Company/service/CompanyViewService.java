package com.linyun.airline.admin.Company.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TCompanyAddForm;
import com.linyun.airline.forms.TCompanyUpdateForm;
import com.linyun.airline.forms.TUserAddForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();

	/**
	 * 
	 * 获取上游公司和代理商的数量
	 * <p>
	 * TODO
	 *
	 * @param sqlManagerm
	 * @return 返回上游公司、代理商、总数 封装到map中
	 */
	public Map<String, Object> getUpCompanyAndAgentCount(@SuppressWarnings("unused") SqlManager sqlManagerm) {
		Map<String, Object> map = MapUtil.map();
		long upconpany = getCompanyCount(CompanyTypeEnum.UPCOMPANY.intKey());
		long agent = getCompanyCount(CompanyTypeEnum.AGENT.intKey());
		map.put("upconpany", upconpany);
		map.put("agent", agent);
		map.put("totalcompany", upconpany + agent);
		return map;
	}

	/**
	 * 
	 * 获取相应公司的数量
	 * <p>
	 * TODO
	 *
	 * @param comType 公司类型id
	 * @return 返回相应数量
	 */
	@SuppressWarnings("unused")
	private long getCompanyCount(int comType) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		sql.setCondition(cnd);
		return Daos.queryCount(nutDao, sql.toString());
	}

	/**
	 * 
	 * 获取公司部门下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sqlManager
	 * @param departmentForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("hiding")
	public List<Record> getCompanyDepartment(SqlManager sqlManager, long comId) {
		String sqlString = sqlManager.get("company_department_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("td.comId", "=", comId);
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 添加公司信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @param userAddForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addCompany(TCompanyAddForm addForm, TUserAddForm userAddForm) {
		//添加管理员信息数据
		userAddForm.setPassword(MD5.sign("000000", AccessConfig.password_secret, AccessConfig.INPUT_CHARSET));
		userAddForm.setUserName(addForm.getComName() + "系统管理员");
		TUserEntity userEntity = FormUtil.add(dbDao, userAddForm, TUserEntity.class);
		//添加公司信息数据
		addForm.setCreatetime(new Date());
		addForm.setLastupdatetime(new Date());
		addForm.setAdminId(userEntity.getId());
		return this.add(addForm);
	}

	/**
	 * 为修改页面准备信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> getCompanyPageInfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TCompanyEntity companyEntity = this.fetch(id);
		//准备数据
		obj.put("company", companyEntity);
		//准备用户名
		obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getTelephone());
		//准备下拉框
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	public Object updateCompany(TCompanyUpdateForm updateForm) {
		//修改管理员用户名
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, updateForm.getAdminId());
		userEntity.setTelephone(updateForm.getTelephone());
		userEntity.setUserName(updateForm.getComName() + "系统管理员");
		dbDao.update(userEntity);
		//修改公司信息
		updateForm.setLastupdatetime(new Date());
		return this.update(updateForm);
	}
}
