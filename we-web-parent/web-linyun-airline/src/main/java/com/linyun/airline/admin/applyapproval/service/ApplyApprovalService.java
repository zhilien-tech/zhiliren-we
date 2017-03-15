/**
 * ApplyApprovalService.java
 * com.linyun.airline.admin.applyapproval.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.applyapproval.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.order.inland.enums.PassengerTypeEnum;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.entities.ApplyApprovalEntity;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月15日 	 
 */

public class ApplyApprovalService extends BaseService<ApplyApprovalEntity> {

	public Map<String, Object> listPage4Datatables(HttpSession session) {

		String sqlString = sqlManager.get("applyapproval_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		/*Long companyId = 23l;*/
		/*TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();*/
		//国际
		cnd.and("companyId", "=", 43);
		cnd.and("orderstype", "=", PassengerTypeEnum.TEAM.intKey());
		cnd.and("orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
		sql.setCondition(cnd);
		List<Record> list = dbDao.query(sql, cnd, null);
		int internationalNum = list.size();
		Cnd cnd1 = Cnd.NEW();
		cnd1.and("companyId", "=", 43);
		cnd1.and("orderstype", "=", PassengerTypeEnum.FIT.intKey());
		cnd1.and("orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
		List<Record> list1 = dbDao.query(sql, cnd1, null);
		int inlandNum = list1.size();
		Map<String, Object> re = MapUtil.map();
		re.put("internationalNum", internationalNum);
		re.put("inlandNum", inlandNum);
		return re;

	}

}
