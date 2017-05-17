/**
 * InterPaymentSqlForm.java
 * com.linyun.airline.admin.order.international.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.form.inter;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.linyun.airline.admin.order.inland.enums.PayReceiveTypeEnum;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月18日 	 
 */
@Data
public class InterAlreadyPayListForm extends DataTablesParamForm {

	private Integer companyid;

	private Integer userid;

	private String startdate;

	private String enddate;

	private Integer paystatus;

	private Integer adminId;

	/**客户名称 订单号 PNR 联系人*/
	private String name;

	/**订单状态*/
	private String orderStatus;

	/**记录状态*/

	/**出发日期 -- 开始出发日期*/
	private Date leavetdate;

	/**出发日期 -- 截止出发日期*/
	private Date backdate;

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("receivePay_inter_pay_listdata");
		Sql sql = Sqls.create(sqlString);
		sql.setParam("recordtype", PayReceiveTypeEnum.PAY.intKey());
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("tp.ordertype", "=", OrderTypeEnum.TEAM.intKey());
		if (!Util.isEmpty(companyid)) {
			cnd.and("tp.companyid", "=", companyid);
		}
		cnd.and("tpo.paystauts", "=", AccountPayEnum.APPROVALPAYED.intKey());
		if (!Util.isEmpty(name)) {
			SqlExpressionGroup sqlex = new SqlExpressionGroup();
			sqlex.and("tuo.ordersnum", "like", "%" + name + "%").or("tci.shortName", "like", "%" + name + "%")
					.or("tci.linkMan", "like", "%" + name + "%")
					.or("getInterPnrByOrderid(tuo.id)", "like", "%" + name + "%");
			cnd.and(sqlex);
		}
		if (!Util.isEmpty(leavetdate)) {
			cnd.and("tpo.payDate", ">=", leavetdate);
		}
		if (!Util.isEmpty(backdate)) {
			cnd.and("tpo.payDate", "<=", backdate);
		}
		if (!Util.isEmpty(paystatus)) {
			cnd.and("tpo.paystauts", "=", paystatus);
		}
		if (!Util.isEmpty(orderStatus)) {
			cnd.and("tpo.orderstatus", "=", orderStatus);
		}
		cnd.orderBy("tpo.paystauts", "asc");
		cnd.orderBy("tpo.payDate", "desc");
		return cnd;
	}

}
