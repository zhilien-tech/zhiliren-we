/**
 * ShouInvoiceParamForm.java
 * com.linyun.airline.admin.order.inland.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.uxuexi.core.common.util.Util;
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
@EqualsAndHashCode(callSuper = true)
public class ShouInvoiceParamForm extends DataTablesParamForm {

	private Integer userid;

	private Integer status;//开票状态
	private Integer billuserid;//收票人
	private Date shouInvoiceBeginDate;//收票日期
	private Date shouInvoiceEndDate;//收票日期
	private String PNR;//pnr
	private String paymentunit;//收款单位

	private Long companyid;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("opid", "=", userid);
		cnd.and("invoicetype", "=", InvoiceInfoEnum.Already_INVOICe.intKey());
		if (!Util.isEmpty(companyid)) {
			cnd.and("tii.comId", "=", companyid);
		}
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
