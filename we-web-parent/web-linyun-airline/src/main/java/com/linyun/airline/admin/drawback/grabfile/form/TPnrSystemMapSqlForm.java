package com.linyun.airline.admin.drawback.grabfile.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TPnrSystemMapSqlForm extends DataTablesParamForm {
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

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		//String sqlString = EntityUtil.entityCndSql(TGrabReportEntity.class);
		System.out.println("=========================================");
		String sqlString = sqlManager.get("grab_report_findPnrSystemMap");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(PNR)) {
			cnd.and("pi.PNR", "=", this.PNR);
		} else {
			cnd.and("psm.id", "=", -1);

		}

		return cnd;
	}
}