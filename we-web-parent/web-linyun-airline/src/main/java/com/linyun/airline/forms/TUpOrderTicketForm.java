package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TUpOrderTicketEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TUpOrderTicketForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键ID*/
	private Integer id;

	/**订单ID*/
	private Integer orderid;

	/**机票ID*/
	private Integer ticketid;

	/**售价（总价）*/
	private Double price;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TUpOrderTicketEntity.class);
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