/**
 * PayApplyListForm.java
 * com.linyun.airline.admin.order.inland.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.form;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.OrderStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年2月28日 	 
 */
public class PayApplyListForm extends DataTablesParamForm {

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("tuo.ordersstatus", "=", OrderStatusEnum.TICKETING.intKey());
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.FIT.intKey());
		cnd.and("toc.paymethod", "=", 1);
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_sea_payapply_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
