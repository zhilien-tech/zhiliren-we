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
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.authority.job.entity.TJobEntity;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyJobEntity;
import com.linyun.airline.entities.TDepartmentEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TUserJobEntity;
import com.linyun.airline.forms.TCompanyAddForm;
import com.linyun.airline.forms.TCompanyUpdateForm;
import com.linyun.airline.forms.TUserAddForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();

	//管理员所在的部门
	private static final String MANAGE_DEPART = "公司管理部";
	//管理员职位
	private static final String MANAGE_POSITION = "公司管理员";
	//公司管理员账号初始密码
	private static final String MANAGE_PASSWORD = "000000";

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
	 * 获取公司下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param comType 公司类型（枚举）
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<Record> getCompanyList(int comType) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	/**
	 * 
	 * 获取客户公司下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param comType 公司类型（枚举）
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public List<Record> getCompanyList(int comType, String comName) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		cnd.and("comName", "like", Strings.trim(comName) + "%");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
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
		userAddForm.setPassword(this.MANAGE_PASSWORD);
		userAddForm.setUserName(addForm.getTelephone());
		userAddForm.setStatus(1);
		TUserEntity userEntity = FormUtil.add(dbDao, userAddForm, TUserEntity.class);
		//添加公司信息数据
		addForm.setCreatetime(new Date());
		addForm.setLastupdatetime(new Date());
		addForm.setAdminId(userEntity.getId());
		TCompanyEntity company = this.add(addForm);
		//添加管理员所在的部门信息
		TDepartmentEntity depart = new TDepartmentEntity();
		depart.setComId(company.getId());
		depart.setDeptName(this.MANAGE_DEPART);
		TDepartmentEntity department = dbDao.insert(depart);
		//添加公司管理员的职位信息
		TJobEntity jobEntity = new TJobEntity();
		jobEntity.setCreateTime(new Date());
		jobEntity.setDeptId(department.getId());
		jobEntity.setName(this.MANAGE_POSITION);
		TJobEntity job = dbDao.insert(jobEntity);
		//在公司职位表中添加管理员的公司职位信息
		TCompanyJobEntity companyJobEntity = new TCompanyJobEntity();
		companyJobEntity.setComId(company.getId());
		companyJobEntity.setPosid(job.getId());
		TCompanyJobEntity companyJob = dbDao.insert(companyJobEntity);
		//添加用户就职表
		TUserJobEntity userJobEntity = new TUserJobEntity();
		userJobEntity.setCompanyJobId(companyJob.getId());
		userJobEntity.setHireDate(new Date());
		userJobEntity.setStatus(userEntity.getStatus());
		userJobEntity.setUserid(userEntity.getId());
		return dbDao.insert(userJobEntity);
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
		obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getUserName());
		//准备下拉框
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	public Object updateCompany(TCompanyUpdateForm updateForm) {
		//修改管理员用户名
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, updateForm.getAdminId());
		userEntity.setUserName(updateForm.getTelephone());
		dbDao.update(userEntity);
		//修改公司信息
		updateForm.setLastupdatetime(new Date());
		return this.update(updateForm);
	}

	/**
	 * 
	 * 验证公司名称唯一
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param comName
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> validatorCompanyName(String comName) {
		Map<String, Object> result = new HashMap<String, Object>();
		TCompanyEntity companyEntity = dbDao.fetch(TCompanyEntity.class, Cnd.where("comName", "=", comName));
		if (Util.isEmpty(companyEntity)) {
			result.put("valid", true);
		} else {
			result.put("valid", false);
		}
		// TODO Auto-generated method stub
		return result;

	}

	/**
	 * 移除员工
	 * 使员工离职
	 *
	 * @param id
	 * @return 使员工离职
	 */
	public Object removeUser(long id) {
		TUserEntity user = dbDao.fetch(TUserEntity.class, id);
		user.setStatus(2);
		return dbDao.update(user);

	}
}
