/**
 * TAuthoritySqlForm.java
 * com.linyun.airline.admin.authority.authoritymanage.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.authoritymanage.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年12月4日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TAuthoritySqlForm extends DataTablesParamForm {

	//公司id
	private long comId;
	//主键
	private long id;
	//部门id
	private long deptId;
	//部门名称
	private String deptName;
	//职位id
	private long jobId;
	//职位名称
	private String name;

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		cnd.and("c.id", "=", comId);
		cnd.and("f.parentId", "=", 0);
		cnd.groupBy("d.id");
		cnd.orderBy("deptId", "DESC");
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("authoritymanage_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}
}
