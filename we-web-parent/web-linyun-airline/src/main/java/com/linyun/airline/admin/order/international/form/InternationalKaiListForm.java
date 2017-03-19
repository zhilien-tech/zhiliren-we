/**
 * InternationalKaiListForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月19日 	 
 */
@Data
public class InternationalKaiListForm extends DataTablesParamForm {

	private Integer companyid;

	private Integer userid;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(TInvoiceInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("ordertype", "=", OrderTypeEnum.TEAM.intKey());
		cnd.and("invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
		if (!Util.isEmpty(companyid)) {
			cnd.and("comId", "=", companyid);
		}
		return cnd;
	}
}
