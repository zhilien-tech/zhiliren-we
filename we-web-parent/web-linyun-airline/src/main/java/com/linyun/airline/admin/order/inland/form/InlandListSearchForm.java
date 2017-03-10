/**
 * InlandListSearchForm.java
 * com.linyun.airline.admin.order.inland.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.form;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年2月13日 	 
 */
public class InlandListSearchForm extends DataTablesParamForm {

	private Integer ordersstatus;

	private Integer ticketing;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("orderstype", "=", OrderTypeEnum.FIT.intKey());
		if (!Util.isEmpty(ordersstatus) && ordersstatus != 0) {
			cnd.and("ordersstatus", "=", ordersstatus);
		}
		if (!Util.isEmpty(ticketing)) {
			SqlExpressionGroup sqlex = new SqlExpressionGroup();
			sqlex.and("receivestatus", "=", "").or("receivestatus", "is", null);
			cnd.and(sqlex);
		}
		cnd.orderBy("tuo.ordersnum", "desc");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_inland_listdata");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
