/**
 * InternationalKaiListForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.invoicemanage.internationalinvoice.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * @author    崔建斌
 * @Date	 2017年3月30日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InternationalKaiSqlForm extends DataTablesParamForm {

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
