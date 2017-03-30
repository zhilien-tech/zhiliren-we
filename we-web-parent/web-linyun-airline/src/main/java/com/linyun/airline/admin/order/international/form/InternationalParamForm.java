/**
 * InternationalParamForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月15日 	 
 */
@Data
public class InternationalParamForm extends DataTablesParamForm {

	private Integer companyid;

	private Integer ordersstatus;

	private String ticketing;

	private String ticketingpay;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		if (!Util.isEmpty(ordersstatus) && ordersstatus != 0) {
			cnd.and("tuo.ordersstatus", "=", ordersstatus);
		}
		if (!Util.isEmpty(companyid)) {
			cnd.and("tpi.companyid", "=", companyid);
		}
		if (!Util.isEmpty(ordersstatus) && ordersstatus.equals(InternationalStatusEnum.TICKETING)) {
			if (!Util.isEmpty(ticketing)) {
				SqlExpressionGroup sqlex = new SqlExpressionGroup();
				sqlex.and("tuo.receivestatus", "=", "").or("tuo.receivestatus", "is", null);
				cnd.and(sqlex);
			}
			if (!Util.isEmpty(ticketingpay)) {
				SqlExpressionGroup sqlex = new SqlExpressionGroup();
				sqlex.and("tuo.paystatus", "=", "").or("tuo.paystatus", "is", null);
				cnd.and(sqlex);
			}
		}
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_international_list_sql");
		Sql sql = Sqls.create(sqlString);
		if (!Util.isEmpty(ordersstatus) && ordersstatus != 0) {
			sql.setParam("ordersstatus", ordersstatus);
		}
		sql.setCondition(cnd());
		return sql;
	}

}
