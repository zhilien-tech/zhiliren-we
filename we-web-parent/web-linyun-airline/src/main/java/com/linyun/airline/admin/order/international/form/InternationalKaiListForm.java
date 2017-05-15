/**
 * InternationalKaiListForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.form;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.admin.invoicemanage.invoiceinfo.enums.InvoiceInfoEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
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

	private String startdate;

	private String enddate;

	private String searchInfo;

	private Integer kaiinvoicestatus;

	private Integer adminId;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("get_kaiinvoice_info_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(userid) && !Util.isEmpty(adminId) && !userid.equals(adminId)) {
			cnd.and("tii.opid", "=", userid);
		}
		if (!Util.isEmpty(startdate)) {
			Date startdates = DateUtil.string2Date(startdate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tii.invoicedate", ">=", startdates);
		}
		if (!Util.isEmpty(enddate)) {
			Date enddates = DateUtil.string2Date(enddate, DateUtil.FORMAT_YYYY_MM_DD);
			cnd.and("tii.invoicedate", "<=", enddates);
		}
		if (!Util.isEmpty(searchInfo)) {
			SqlExpressionGroup exp = new SqlExpressionGroup();
			exp.and("tii.paymentunit", "like", "%" + searchInfo + "%")
					.or("getByInvoicenumQuery(tii.id)", "like", "%" + searchInfo + "%")
					.or("getOrderNumByReceiveid(tii.receiveid)", "like", "%" + searchInfo + "%")
					.or("tod.comDictName", "like", "%" + searchInfo + "%");
			cnd.and(exp);
		}
		if (!Util.isEmpty(companyid)) {
			cnd.and("tii.comId", "=", companyid);
		}
		if (!Util.isEmpty(kaiinvoicestatus)) {
			cnd.and("tii.status", "=", kaiinvoicestatus);
		}
		cnd.and("tii.ordertype", "=", OrderTypeEnum.TEAM.intKey());
		cnd.and("tii.invoicetype", "=", InvoiceInfoEnum.INVOIC_ING.intKey());//开发票中
		return cnd;
	}
}
