/**
 * ShouInvoiceParamForm.java
 * com.linyun.airline.admin.order.inland.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月9日 	 
 */
@Data
public class ShouInvoiceParamForm extends DataTablesParamForm {

	private Integer userid;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("opid", "=", userid);
		cnd.and("invoicetype", "=", PayReceiveTypeEnum.PAY.intKey());
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_shou_invoice_list_order");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}
}
