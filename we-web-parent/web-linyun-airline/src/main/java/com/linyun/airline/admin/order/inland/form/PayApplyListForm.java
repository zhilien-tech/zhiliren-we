/**
 * PayApplyListForm.java
 * com.linyun.airline.admin.order.inland.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.admin.order.inland.enums.PayMethodEnum;
import com.linyun.airline.common.enums.AccountPayEnum;
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
@Data
public class PayApplyListForm extends DataTablesParamForm {

	private Integer userId;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("tuo.ordersstatus", "=", OrderStatusEnum.TICKETING.intKey());
		cnd.and("tuo.orderstype", "=", OrderTypeEnum.FIT.intKey());
		cnd.and("tuo.loginUserId", "=", userId);
		cnd.and("toc.paymethod", "=", PayMethodEnum.THIRDPART.intKey());
		SqlExpressionGroup exp = new SqlExpressionGroup();
		exp.and("tpi.orderPnrStatus", "=", "").or("tpi.orderPnrStatus", "is", null)
				.or("tpi.orderPnrStatus", "=", AccountPayEnum.REFUSE.intKey());
		cnd.and(exp);
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
