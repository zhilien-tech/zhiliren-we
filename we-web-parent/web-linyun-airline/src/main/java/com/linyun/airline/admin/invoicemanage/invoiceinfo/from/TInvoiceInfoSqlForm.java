package com.linyun.airline.admin.invoicemanage.invoiceinfo.from;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInvoiceInfoSqlForm extends DataTablesParamForm {
	/**id*/
	private Integer id;

	/**发票项目*/
	private Integer invoiceitem;

	/**发票日期*/
	private String invoicedate;

	/**部门*/
	private Integer deptid;

	/**备注*/
	private String remark;

	/**差额*/
	private Double difference;

	/**余额*/
	private Double balance;

	/**发票类型（收款、付款）*/
	private Integer invoicetype;

	/**收款id*/
	private Integer receiveid;

	/**pnrid*/
	private Integer pnrid;

	private Integer userid;

	private Integer status;//开票状态
	private Integer billuserid;//收票人
	private Date shouInvoiceBeginDate;//收票日期
	private Date shouInvoiceEndDate;//收票日期
	private String PNR;//pnr
	private String paymentunit;//收款单位

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("get_shou_invoice_list_order");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("tpi.PNR", "LIKE", "%" + PNR + "%").or("tii.paymentunit", "LIKE", "%" + paymentunit + "%");
		if (!Util.isEmpty(PNR)) {
			cnd.and(group);
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
			cnd.and("ii.billuserid", "=", billuserid);
		}
		cnd.and("opid", "=", userid);
		return cnd;
	}
}