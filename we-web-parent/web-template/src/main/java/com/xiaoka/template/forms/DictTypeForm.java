package com.xiaoka.template.forms;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;
import com.xiaoka.template.entities.DictTypeEntity;

@Data
public class DictTypeForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;

	/**字典类别代码*/
	private String typeCode;

	/**字典类别名称*/
	private String typeName;

	/**描述*/
	private String description;

	/**状态,0-冻结，1-启用，2--删除*/
	private Integer status;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(DictTypeEntity.class);
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