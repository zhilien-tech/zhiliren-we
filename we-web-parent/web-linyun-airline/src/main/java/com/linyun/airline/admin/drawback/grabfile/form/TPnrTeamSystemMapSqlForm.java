/**
 * TPnrTeamSystemMapSqlForm.java
 * com.linyun.airline.admin.drawback.grabfile.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * 团队附件列表展示sql
 * @author   崔建斌
 * @Date	 2017年6月15日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TPnrTeamSystemMapSqlForm extends DataTablesParamForm {
	/**主键*/
	private Integer id;

	/**订单号*/
	private String ordersnum;

	/**客户团号*/
	private String cusgroupnum;
	/**PNR*/
	private String PNR;
	/**人数*/
	private Integer personcount;
	/**成本单价*/
	private double costprice;
	/**出澳时间*/
	private Date outausdate;
	/**进澳时间*/
	private Date enterausdate;
	/**订单状态*/
	private Integer orderstatus;
	/**团散类型*/
	private Integer type;
	/**关联状态*/
	private String relationStatus;
	/**航空公司*/
	private String fileName;

	/**订单id**/
	private Integer orderId;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = sqlManager.get("grab_report_listPnrSystemMap_team_data");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("uo.orderstype", "=", OrderTypeEnum.TEAM.intKey());
		if (!Util.isEmpty(PNR)) {
			cnd.and("p.PNR", "=", this.PNR);
		} else {
			cnd.and("psm.id", "=", -1);
		}
		return cnd;
	}
}
