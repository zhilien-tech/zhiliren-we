package com.linyun.airline.forms;

import com.uxuexi.core.web.form.SQLParamForm;
import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.linyun.airline.entities.SUserRoleMapEntity;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SUserRoleMapForm extends SQLParamForm implements Serializable{
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Long id;
	
	/**用户id*/
	private Long userId;
	
	/**角色id*/
	private Long roleId;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(SUserRoleMapEntity.class);
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