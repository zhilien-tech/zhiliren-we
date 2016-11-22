package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TFunctionEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TFunctionForm extends SQLParamForm implements Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private long id;

	/**上级功能id*/
	private long parentId;

	/**功能名称*/
	private String name;

	/**访问地止*/
	private String url;

	/**功能等级，是指在功能树中所处的层级*/
	private long level;

	/**创建时间*/
	private DateTime createTime;

	/**更新时间*/
	private DateTime updateTime;

	/**备注*/
	private String remark;

	/**序号*/
	private long sort;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TFunctionEntity.class);
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