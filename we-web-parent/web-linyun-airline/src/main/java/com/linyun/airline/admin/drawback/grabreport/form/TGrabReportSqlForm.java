package com.linyun.airline.admin.drawback.grabreport.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.drawback.grabreport.entity.TGrabReportEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabReportSqlForm extends DataTablesParamForm {
	/**主键*/
	private Integer id;

	/**备注*/
	private String remark;

	/**汇款*/
	private Double remit;

	/**blance(备用金余额)*/
	private Double depositBalance;

	/**票价(含行李)*/
	private Double ticketPrice;

	/**刷卡费*/
	private Double swipe;

	/**税金/杂项*/
	private Double tax;

	/**消费税(GST)*/
	private Double exciseTax1;

	/**代理费*/
	private String agencyFee;

	/**税返点*/
	private String taxRebate;

	/**退税状态*/
	private Integer backStatus;

	/**实收单价(含操作费)*/
	private Double realIncome;

	/**实收合计(含操作费)*/
	private Double realTotal;

	/**代理费2*/
	private String agencyFee2;

	/**入澳时间*/
	private Date inAustralianTime;

	/**出澳时间*/
	private Date outAustralianTime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TGrabReportEntity.class);
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