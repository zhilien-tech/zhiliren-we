/**
 * TShouInvoiceInfoSqlForm.java
 * com.linyun.airline.admin.invoicemanage.invoiceinfo.from
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.invoicemanage.invoiceinfo.from;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年3月23日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TKaiInvoiceInfoSqlForm extends DataTablesParamForm {

	private Integer userid;
	private Date invoicedate;//开票日期
	private Integer status;//开票状态
	private Integer billuserid;//开票人
	private Date kaiInvoiceBeginDate;//开票日期
	private Date kaiInvoiceEndDate;//开票日期
	private String invoicenum;//发票号
	private String paymentunit;//付款单位
	private String invoiceitem;//发票开具项目
	private String ordersnum;//订单号
	private Long companyid;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ii.paymentunit", "LIKE", "%" + paymentunit + "%")
				.or("cd.comDictName", "LIKE", "%" + invoiceitem + "%")
				.or("getByInvoicenumQuery(ii.id)", "LIKE", "%" + invoicenum + "%")
				.or("getOrderNumByReceiveid(ii.receiveid)", "LIKE", "%" + ordersnum + "%");
		if (!Util.isEmpty(invoicenum)) {
			cnd.and(group);
		}
		if (!Util.isEmpty(companyid)) {
			cnd.and("ii.comId", "=", companyid);
		}
		cnd.and("ii.ordertype", "=", OrderTypeEnum.FIT.intKey());
		cnd.and("ii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
		//开票日期
		if (!Util.isEmpty(kaiInvoiceBeginDate)) {
			cnd.and("ii.invoicedate", ">=", kaiInvoiceBeginDate);
		}
		//开票日期
		if (!Util.isEmpty(kaiInvoiceEndDate)) {
			cnd.and("ii.invoicedate", "<=", kaiInvoiceEndDate);
		}
		if (!Util.isEmpty(status)) {
			cnd.and("ii.status", "=", status);
		}
		cnd.orderBy("ii.status", "ASC");
		cnd.orderBy("ii.optime", "DESC");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		//String sqlString = EntityUtil.entityCndSql(TInvoiceInfoEntity.class);
		String sqlString = sqlManager.get("invoicemanage_kaiinvoice_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
