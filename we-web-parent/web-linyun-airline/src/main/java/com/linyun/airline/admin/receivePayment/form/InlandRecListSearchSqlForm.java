/**
 * InlandPayListSearchForm.java
 * com.linyun.airline.admin.receivePayment.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * (查询 检索付款列表)
 *
 * (这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年2月24日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InlandRecListSearchSqlForm extends DataTablesParamForm {

	/**客户名称 订单号 PNR 联系人*/
	private String name;

	/**订单状态*/
	private String orderStatus;

	/**出发日期 -- 开始出发日期*/
	private Date leaveBeginDate;

	/**出发日期 -- 截止出发日期*/
	private Date leaveEndDate;

	/**当前公司用户id*/
	private String loginUserId;

	public Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(orderStatus)) {
			cnd.and("r.`status`", "=", orderStatus);
		}
		cnd.and("r.userid", "in", loginUserId);
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("receivePay_rec_invioce_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
