/**
 * SearchTicketSqlForm.java
 * com.linyun.airline.admin.search.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.search.form;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(查询团购票)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年1月6日 	 
 */
@Data
public class SearchTicketSqlForm extends DataTablesParamForm {

	/**
	 * 起飞机场/出发城市
	 */
	private String origin;

	/**
	 * 降落机场/到达城市
	 */
	private String destination;

	/**
	 * 出发日期
	 */
	private String departuredate;

	/**
	 * 返程日期
	 */
	private String returndate;

	/**
	 * 航空公司
	 */
	private String includedcarriers;

	/**
	 * 乘客数
	 */
	private Integer passengercount;

	private int issave;

	private long companyId;

	/**
	 * 舱位等级
	 */
	private String airLevel;

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		cnd.and("tt.issave", "=", "1");
		cnd.and("tt.companyid", "=", companyId);
		if (!Util.isEmpty(origin)) {
			cnd.and("tt.leavescity", "=", origin);
		}
		if (!Util.isEmpty(destination)) {
			cnd.and("tt.backscity", "=", destination);
		}
		if (!Util.isEmpty(departuredate)) {
			cnd.and("date_format(tt.leavesdate,'%Y-%m-%d')", "like", departuredate);
		}
		if (!Util.isEmpty(returndate)) {
			cnd.and("date_format(tt.backsdate,'%Y-%m-%d')", "like", returndate);
		}
		if (!Util.isEmpty(includedcarriers)) {
			cnd.and("tt.airlinename", "=", includedcarriers);
		}
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("team_ticket_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

}
