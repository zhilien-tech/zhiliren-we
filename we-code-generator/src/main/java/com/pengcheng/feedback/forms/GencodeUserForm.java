package com.pengcheng.feedback.forms;

import com.uxuexi.core.web.form.SQLParamForm;
import com.uxuexi.core.db.util.EntityUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.pengcheng.feedback.entities.GencodeUserEntity;

import java.io.Serializable;

@Data
/*@EqualsAndHashCode(callSuper = true)*/
public class GencodeUserForm implements SQLParamForm, Serializable{
	private static final long serialVersionUID = 1L;
	/**主键*/
	private Integer id;
	
	/**姓名*/
	private String name;
	
	/**年龄*/
	private Integer age;
	
	/**地址*/
	private String address;
	
	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(GencodeUserEntity.class);
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