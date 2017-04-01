/**
 * InterPaymentSqlForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月18日 	 
 */
@Data
public class InterPaymentSqlForm extends DataTablesParamForm {

	private Integer companyid;

	private Integer userid;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_international_pay_list");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tp.ordertype", "=", OrderTypeEnum.TEAM.intKey());
		if (!Util.isEmpty(companyid)) {
			cnd.and("tp.companyid", "=", companyid);
		}
		cnd.and("tpo.paystauts", "!=", AccountPayEnum.REFUSE.intKey());
		cnd.orderBy("tpo.paystauts", "asc");
		cnd.orderBy("tpo.payDate", "desc");
		return cnd;
	}

}
