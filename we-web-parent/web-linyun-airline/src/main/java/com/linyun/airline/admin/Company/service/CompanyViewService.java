package com.linyun.airline.admin.Company.service;

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

import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;

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
}