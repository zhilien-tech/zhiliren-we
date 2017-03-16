/**
 * KaiInvoiceParamForm.java
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
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.entities.TInvoiceInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
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
public class KaiInvoiceParamForm extends DataTablesParamForm {

	private Integer userid;
	private Integer status;//发票状态
	private String kaiDrawer;//开票人
	private Date kaiInvoiceBeginDate;//开票起始日期
	private Date kaiInvoiceEndDate;//截止日期
	private String invoicenum;//发票号
	private String paymentunit;//付款单位

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		SqlExpressionGroup group = new SqlExpressionGroup();
		group.and("ci.shortName", "LIKE", "%" + kaiDrawer + "%").or("uo.ordersnum", "LIKE", "%" + kaiDrawer + "%")
				.or("ci.linkMan", "LIKE", "%" + kaiDrawer + "%").or("pi.PNR", "LIKE", "%" + kaiDrawer + "%");
		cnd.and("opid", "=", userid);
		cnd.and("invoicetype", "=", PayReceiveTypeEnum.RECEIVE.intKey());
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(TInvoiceInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

}
