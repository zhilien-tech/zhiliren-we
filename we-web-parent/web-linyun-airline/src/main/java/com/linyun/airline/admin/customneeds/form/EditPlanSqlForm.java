/**
 * EditPlanSqlForm.java
 * com.linyun.airline.admin.customneeds.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customneeds.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月20日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EditPlanSqlForm extends DataTablesParamForm {

	private Integer id;

	private String travelname1;

	private String leaveairline1;

	private String leavescity1;

	private String backairline1;

	private String backscity1;

	private Integer peoplecount1;

	private Integer dayscount1;

	private String unioncity1;

	private String startdate1;

	private String enddate1;

	private Integer teamtype;

	private int idordernum;

	private int issave;

	private long companyid;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("t.issave", "=", "1");
		cnd.and("t.companyid", "=", companyid);
		if (!Util.isEmpty(travelname1)) {
			cnd.and("t.travelname", "like", "%" + travelname1 + "%");
		}
		if (idordernum == 1) {
			cnd.and("tu.ordersnum", "is not", null);
		} else {
			cnd.and("tu.ordersnum", "is", null);

		}
		if (!Util.isEmpty(leaveairline1)) {
			cnd.and("t.leaveairline", "like", "%" + leaveairline1 + "%");
		}
		if (!Util.isEmpty(leavescity1)) {
			cnd.and("t.leavescity", "like", "%" + leavescity1 + "%");
		}
		if (!Util.isEmpty(backairline1)) {
			cnd.and("t.backairline", "like", "%" + backairline1 + "%");
		}
		if (!Util.isEmpty(backscity1)) {
			cnd.and("t.backscity", "like", "%" + backscity1 + "%");
		}
		if (!Util.isEmpty(peoplecount1)) {
			cnd.and("t.peoplecount", "=", peoplecount1);
		}
		if (!Util.isEmpty(dayscount1)) {
			cnd.and("t.dayscount", "=", dayscount1);
		}
		if (!Util.isEmpty(unioncity1)) {
			cnd.and("t.unioncity", "like", "%" + unioncity1 + "%");
		}
		if (!Util.isEmpty(teamtype)) {
			if (teamtype == 3) {
				cnd.and("t.isclose", "=", 1);
			} else {
				cnd.and("t.teamtype", "=", teamtype);
			}
		}
		if (!Util.isEmpty(startdate1)) {
			cnd.and("t.leavesdate", ">=", startdate1);
		}
		if (!Util.isEmpty(enddate1)) {
			cnd.and("t.leavesdate", "<=", enddate1);
		}
		cnd.orderBy("t.leavesdate", "asc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_editplan_info_list");
		//String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
