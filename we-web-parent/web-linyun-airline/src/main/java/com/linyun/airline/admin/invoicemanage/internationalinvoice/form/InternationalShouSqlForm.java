/**
 * InternationalShouListForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.invoicemanage.internationalinvoice.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * @author   崔建斌
 * @Date	 2017年3月30日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InternationalShouSqlForm extends DataTablesParamForm {

	private Integer companyid;
	private Integer orderId;
	private Integer userid;

	private Integer status;//开票状态
	private Integer billuserid;//收票人
	private String invoicenum;//发票号
	private Date shouInvoiceBeginDate;//收票日期
	private Date shouInvoiceEndDate;//收票日期
	private String PNR;//pnr
	private String paymentunit;//收款单位
	private String invoiceitem;//发票开具项目

	@Override
	public Sql sql(SqlManager sqlManager) {
		//String sqlString = EntityUtil.entityCndSql(TInvoiceInfoEntity.class);
		String sqlString = sqlManager.get("international_invoice_international_invoice_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("tii.paymentunit", "LIKE", "%" + paymentunit + "%")
				.or("cd.comDictName", "LIKE", "%" + invoiceitem + "%")
				.or("getByInvoicenumQuery(tii.id)", "LIKE", "%" + invoicenum + "%");
		if (!Util.isEmpty(invoicenum)) {
			cnd.and(group);
		}
		cnd.and("tii.ordertype", "=", OrderTypeEnum.TEAM.intKey());
		//cnd.and("invoicetype", "=", InvoiceInfoEnum.RECEIPT_INVOIC_ING.intKey());//开发票中
		if (!Util.isEmpty(companyid)) {
			cnd.and("tii.comId", "=", companyid);
		}
		//开票日期
		if (!Util.isEmpty(shouInvoiceBeginDate)) {
			cnd.and("tii.invoicedate", ">=", shouInvoiceBeginDate);
		}
		//开票日期
		if (!Util.isEmpty(shouInvoiceEndDate)) {
			cnd.and("tii.invoicedate", "<=", shouInvoiceEndDate);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("tii.status", "=", status);
		}
		if (!Util.isEmpty(billuserid)) {
			cnd.and("tii.billuserid", "=", billuserid);
		}
		cnd.orderBy("tii.status", "ASC");
		cnd.orderBy("tii.optime", "DESC");
		return cnd;
	}
}
