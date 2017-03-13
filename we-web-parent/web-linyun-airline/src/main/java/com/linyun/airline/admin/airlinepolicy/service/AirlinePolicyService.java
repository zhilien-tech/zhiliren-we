/**
 * AirlinePolicyService.java
 * com.linyun.airline.admin.airlinepolicy.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.airlinepolicy.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;

import com.google.common.collect.Maps;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlinePolicyEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author  孙斌
 * @Date	 2017年3月8日 	 
 */
public class AirlinePolicyService extends BaseService<TAirlinePolicyEntity> {

	public Map<String, Object> listPage4Datatables(DataTablesParamForm sqlParamForm, HttpSession session) {

		checkNull(sqlParamForm, "sqlParamForm不能为空");

		String sqlString = sqlManager.get("bankcardmanager_find_money");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		/*Long companyId = 23l;*/
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		cnd.and("companyId", "=", companyId);
		cnd.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
		sql.setCondition(cnd);
		Pager pager = new OffsetPager(sqlParamForm.getStart(), sqlParamForm.getLength());
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setPager(pager);

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();

		Map<String, Object> re = MapUtil.map();
		re.put("data", list);
		re.put("draw", sqlParamForm.getDraw());
		re.put("recordsTotal", pager.getPageSize());
		re.put("recordsFiltered", pager.getRecordCount());
		return re;

	}

	public Map<String, Object> findConditionList() {

		Map<String, Object> map = Maps.newHashMap();
		/*查询航空公司*/
		List<DictInfoEntity> airlineCompanyList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "HKGS"),
				null);

		/*List<DictInfoEntity> areaList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "HKGS"), null);*/
		map.put("airlineCompanyList", airlineCompanyList);
		return map;

	}

}
