package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TInvoiceInfoForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;

	/**发票项目*/
	private Integer invoiceitem;

	/**发票日期*/
	private String invoicedate;

	/**开票人*/
	private Integer billuserid;

	/**部门*/
	private Integer deptid;

	/**付款单位*/
	private String paymentunit;

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

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TInvoiceInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）

		return cnd;
	}
}